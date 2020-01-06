package com.x.commons.util.file;

import com.x.commons.encrypt.MD5;
import com.x.commons.enums.Charsets;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.date.DateTimes;
import com.x.commons.util.reflact.Loader;
import com.x.commons.util.string.Strings;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @Date 2018-12-20 22:36
 * @Author AD
 */
public final class Files {

    // ------------------------ 成员变量 ------------------------

    /**
     * Windows：
     * <p>
     * 　　“/”是表示参数，“\”是表示本地路径。
     * <p>
     * Linux和Unix：
     * <p>
     * 　　“/”表示路径，“\”表示转义，“-”和“--”表示参数。
     * <p>
     * 网络：
     * <p>
     * 　　由于网络使用Unix标准，所以网络路径用“/”。
     */
    public static final String SP = File.separator;

    private static final ClassLoader LOADER = Loader.get();

    private static String APP_PATH;

    // ------------------------ 构造方法 ------------------------

    public Files() {}

    // ------------------------ 成员方法 ------------------------

    /**
     * 返回resource文件夹的路径，默认以文件系统的分隔符结尾
     *
     * @return
     */
    public static String getResourcesPath() {
        return getResourcesPath(true);
    }

    /**
     * 返回resource文件夹的路径，指定是否以文件系统的分隔符结尾
     *
     * @param endWithSP 是否已文件系统分隔符结尾
     * @return
     */
    public static String getResourcesPath(boolean endWithSP) {
        SB sb = New.sb();
        sb.append(getAppPath()).append("src").append(SP);
        sb.append("main").append(SP).append("resources");
        return endWithSP ? sb.append(SP).get() : sb.get();
    }

    /**
     * 获取当前App路径，默认有文件分隔符结尾
     *
     * @return
     */
    public static String getAppPath() {
        return getAppPath(true);
    }

    /**
     * 获取当前应用所在的路径
     *
     * @param endWithSP 返回的路径是否要以系统文件分隔符结束
     * @return
     */
    public static String getAppPath(boolean endWithSP) {
        if (Strings.isNull(APP_PATH)) {
            String absPath = new File("").getAbsolutePath();
            if (absPath.endsWith(SP)) {
                if (absPath.length() > 1) {
                    absPath = absPath.substring(0, absPath.length() - 1);
                } else {
                    absPath = "";
                }
            }
            APP_PATH = absPath;
        }
        return endWithSP ? APP_PATH + SP : APP_PATH;
    }

    public static String getLocalPath(String path, boolean endWithSP) {
        if (Strings.isNull(path)) {
            return getAppPath(endWithSP);
        } else {
            if (path.indexOf(":\\") == 1) {
                return fixPath(path, SP, endWithSP);
            } else {
                if (path.startsWith("/")) {
                    return fixPath(path, SP, endWithSP);
                } else {
                    return fixPath(getAppPath(true) + path, SP, endWithSP);
                }
            }
        }
    }

    public static String fixPath(String path, String fileSeparator, boolean endWithSP) {
        if (Strings.isNull(path)) {
            return "";
        }
        String fixPath;
        // 如果文件系统分隔符是反斜杠，将正斜杠全部替换成反斜杠
        if ("\\".equals(fileSeparator)) {
            fixPath = path.replaceAll("/", "\\\\");
        } else if ("/".equals(fileSeparator)) {
            // 将所有的反斜杠全部替换成正斜杠
            fixPath = path.replaceAll("\\\\", "/");
        } else {
            fixPath = path;
        }
        if (endWithSP) {
            if (!fixPath.endsWith(fileSeparator)) {
                fixPath = fixPath + fileSeparator;
            }
        } else {
            if (fixPath.endsWith(fileSeparator)) {
                fixPath = fixPath.substring(0, fixPath.length() - 1);
            }
        }
        return fixPath;

    }

    /**
     * 在resources根目录下创建指定文件
     *
     * @param filename 文件名
     * @return 成功：File对象；失败：null
     */
    public static File createFileAtResource(String filename) {
        return createFileAtResource("", filename);
    }

    /**
     * 在resources文件夹下创建指定目录和文件
     *
     * @param folder   文件夹目录，没有回自动创建，无需以分隔符结尾
     * @param filename 文件名
     * @return 成功：File对象；失败：null
     */
    public static File createFileAtResource(String folder, String filename) {
        String folderPath = fixPath(getResourcesPath() + folder, SP, true);
        return createFile(folderPath, filename);
    }

    /**
     * 指定文件夹目录创建文件
     *
     * @param folder   文件夹目录，没有回自动创建，无需以分隔符结尾
     * @param filename 文件名
     * @return 成功：File对象；失败：null
     */
    public static File createFile(String folder, String filename) {
        boolean succeed = createFile(folder, filename, "");
        if (succeed) {
            return new File(endSP(folder) + filename);
        }
        return null;
    }


    /**
     * 默认以UTF-8的编码创建文件
     *
     * @param folder   文件夹路径（包含文件夹名）
     * @param filename 文件名
     * @param content  文件内容
     * @return true:成功 <p> false:失败
     */
    public static boolean createFile(String folder, String filename, String content) {
        return createFile(folder, filename, content, Charsets.UTF8);
    }

    /**
     * 创建文件
     *
     * @param folder   文件夹路径（包含文件夹名）
     * @param filename 文件名
     * @param content  文件内容
     * @param charset  文件字符编码
     * @return true:成功 <p> false:失败
     */
    public static boolean createFile(String folder, String filename, String content, Charset charset) {
        if (createFolder(folder)) {
            String path = endSP(folder) + filename;
            try {
                File file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    path = path + DateTimes.now();
                    file = new File(path);
                    file.createNewFile();
                }
                try (PrintWriter writer = new PrintWriter(file, charset.name());) {
                    writer.print(content);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean createFolder(String path) {
        if (Strings.isNull(path)) {
            return false;
        }
        try {
            File file = new File(path);
            return !file.exists() ? file.mkdirs() : true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createFolders(String[] paths) {
        String appPath = getAppPath(true);
        for (int i = 0, L = paths.length; i < L; ++i) {
            createFolder(appPath + paths[i]);
        }
    }

    /**
     * 以UTF-8的方式读取xxx.txt文本
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readTxt(String path) throws IOException {
        return readTxt(path, Charsets.UTF8.name());
    }

    /**
     * 读取xxx.txt文本
     *
     * @param path     文本路径
     * @param encoding 字符编码
     * @return
     * @throws IOException
     */
    public static String readTxt(String path, String encoding) throws IOException {
        SB sb = New.sb();
        String result = "";
        InputStream in = null;

        try {
            in = Loader.get().getResourceAsStream(path);
            if (in == null) {
                File file = new File(path);
                if (!file.exists()) {
                    System.out.println("========== Files.readTxt() appPath:" + APP_PATH);
                    in = new FileInputStream(getAppPath(true) + SP + path);
                } else {
                    in = new FileInputStream(path);
                }
            }
            try (InputStreamReader reader = getUnicodeReader(in, encoding);
                 BufferedReader bufReader = new BufferedReader(reader);) {
                String content = "";
                while ((content = bufReader.readLine()) != null) {
                    /**
                     * \r是回车,\n是换行.有的控件是要\r\n才能达到换行的
                     * enter+newline with different platforms:
                     * windows: \r\n
                     * mac: \r
                     * unix/linux: \n
                     */
                    sb.append(content + "\r\n");
                }
            }
            result = sb.toString();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            result = "";
        } finally {
            try {
                if (in != null) {
                    ((InputStream) in).close();
                }
            } catch (Exception e) {
            }

        }
        return result;
    }

    /**
     * 是否存在文件
     *
     * @param path
     * @return
     */
    public static boolean fileExists(String path) {
        try {
            File var1 = new File(path);
            return var1.exists();
        } catch (Exception var2) {
            var2.printStackTrace();
            return false;
        }
    }

    /**
     * 慎用<p>
     * 删除文件夹下所有文件（包括path本身）
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        try {
            // 递归删除文件夹下所有文件
            deleteInnerFile(path);
            // 删除自身文件夹
            File file = new File(path);
            return file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 慎用<P>
     * 删除文件夹下所有文件(不包含path本身)
     *
     * @param path 必须是一个目录（文件夹）
     * @return
     */
    public static boolean deleteInnerFile(String path) {
        boolean succeed = false;
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            return succeed;
        } else {
            String[] names = file.list();
            File f = null;

            for (int i = 0, L = names.length; i < L; ++i) {
                if (path.endsWith(SP)) {
                    f = new File(path + names[i]);
                } else {
                    f = new File(path + SP + names[i]);
                }
                // 如果是文件，直接删除
                if (f.isFile()) {
                    f.delete();
                }
                // 如果是文件夹
                if (f.isDirectory()) {
                    // 递归删除删除文件夹里的文件
                    deleteInnerFile(path + SP + names[i]);
                    // 删除文件夹(先递归删除文件夹里的文件，在删除文件夹本身)
                    deleteFile(path + SP + names[i]);
                    succeed = true;
                }
            }
            return succeed;
        }
    }

    /**
     * 拷贝文件->文件夹
     *
     * @param srcPath    必须是文件，不能是文件夹
     * @param targetPath 必须是文件夹
     * @return
     */
    public static boolean copyFile(String srcPath, String targetPath) {
        File src = new File(srcPath);
        File target = new File(targetPath);
        if (!src.exists() || !src.isFile() || !target.isDirectory()) {
            return false;
        } else {
            // 将目标文件夹创建为文件流，须是文件，不能是目录(文件夹)
            int last = srcPath.lastIndexOf(SP);
            String fileName = srcPath.substring(last + 1, srcPath.length());
            // 修正为文件
            targetPath = targetPath.endsWith(
                    SP) ? targetPath + fileName : targetPath + SP + fileName;
            try (FileInputStream in = new FileInputStream(srcPath);
                 FileOutputStream out = new FileOutputStream(targetPath);) {
                byte[] buf = new byte[1444];
                int length;
                while ((length = in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 拷贝整个文件夹目录（包括srcPath文件夹）
     *
     * @param srcPath    需拷贝的（文件/文件夹）路径
     * @param targetPath 目标路径，必须是文件夹
     * @return
     */
    public static boolean copyFolder(String srcPath, String targetPath) {
        File file = new File(srcPath);
        if (file.isFile()) {
            return copyFile(srcPath, targetPath);
        }
        // 找出文件夹名字
        int last = srcPath.lastIndexOf(SP);
        String lastFolderName = srcPath.substring(last, srcPath.length());
        // 修正目标路径
        if (targetPath.endsWith(SP)) {
            targetPath = targetPath + lastFolderName;
        } else {
            targetPath = targetPath + SP + lastFolderName;
        }
        // 创建目标文件夹目录
        new File(targetPath).mkdirs();
        // 拷贝源文件夹内所有文件
        return copyFolderInner(srcPath, targetPath);

    }

    /**
     * 拷贝文件夹内所有文件（不包含srcPath本身）
     *
     * @param srcPath    源路径,如果是文件夹，不建议用"/"命名，会扫描出src所在目录所有path
     * @param targetPath 目标路径
     * @return
     */
    private static boolean copyFolderInner(String srcPath, String targetPath) {
        boolean succeed;
        try {
            // 修正目标路径，
            // String target = fixTargetPath(srcPath, targetPath);
            // 创建目标路径
            new File(targetPath).mkdirs();
            // 获取path
            File file = new File(srcPath);
            // 获取path下的simple name
            String[] names = file.list();
            if (names == null) {
                return false;
            }
            File src = null;
            // 遍历源path
            for (int i = 0, L = names.length; i < L; ++i) {
                // 修正path
                if (srcPath.endsWith(SP)) {
                    src = new File(srcPath + names[i]);
                } else {
                    src = new File(srcPath + SP + names[i]);
                }
                // 文件
                if (src.isFile()) {
                    try (FileInputStream in = new FileInputStream(src);
                         FileOutputStream out = new FileOutputStream(
                                 targetPath + SP + src.getName());) {
                        byte[] buf = new byte[5120];
                        while (true) {
                            int length;
                            if ((length = in.read(buf)) == -1) {
                                out.flush();
                                in.close();
                                out.close();
                                break;
                            }
                            out.write(buf, 0, length);
                        }
                    }
                }
                // 目录
                if (src.isDirectory() && !copyFolderInner(srcPath + SP + names[i],
                                                          targetPath + SP + names[i])) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            succeed = false;
        }
        return succeed;
    }

    public static String getMD5(File file) throws Exception {
        return file == null ? "" : new MD5().encode(file);
    }

    /**
     * @param path
     * @param old
     * @param newChar
     */
    public static void editNames(String path, String old, String newChar) {
        Arrays.stream(getFiles(path, old))
                .forEach(f -> f.renameTo(new File(f.getAbsolutePath().replace(old, newChar))));
    }

    /**
     * 获取某个文件夹路径，不用Paths.get(path),会报找不到文件异常
     *
     * @param path 必须是一个文件夹路径
     * @return
     * @throws Exception
     */
    public static Path getPath(String path) throws Exception {
        URL url = LOADER.getResource(path);
        return Paths.get(url.toURI());
    }

    /**
     * 适用于获取项目路径下的文件，如编译后的根路径或根路径下的某个文件夹路径
     *
     * @param path 如：项目根路径-"/"或""，根路径下的文件夹路径为文件夹名，如：config
     * @return java.io.File
     * @author AD
     * @date 2019-10-17 22:03
     */
    public static File getFile(String path) {
        URL url = LOADER.getResource(path);
        try {
            File file = new File(url.toURI());
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 适用于获取电脑文件
     *
     * @param path 如："/Users/sunday/Java/StudyVideo/SpringMVC"为文件夹路径
     * @return path路径下于pattern匹配的所有文件,
     * @author AD
     * @date 2019-10-17 22:00
     */
    public static File[] getFiles(String path, String pattern) {
        return new File(path).listFiles(f -> f.getName().contains(pattern));
    }

    /**
     * 递归获取文件夹路径下所有文件
     *
     * @param path
     * @param list
     * @return
     */
    public static File[] getFiles(String path, List<File> list) {
        File[] files = getFiles(path, "");
        for (File file : files) {
            if (file.isFile()) {
                list.add(file);
            } else {
                getFiles(file.getPath(), list);
            }
        }
        return list.toArray(new File[list.size()]);
    }

    public static File[] getFiles(String packageName) {

        try {
            URI uri = LOADER.getResource(packageName.replace(".", SP)).toURI();
            return new File(uri).listFiles();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param path
     * @return
     * @throws Exception
     */
    public static Properties toProperties(String path) throws Exception {
        try (InputStream in = Loader.get().getResourceAsStream(path);) {
            Properties prop = new Properties();
            prop.load(in);
            return prop;
        }
    }

    public static InputStreamReader getUnicodeReader(InputStream in) throws IOException {
        return getUnicodeReader(in, Charsets.UTF8.name());
    }

    public static InputStreamReader getUnicodeReader(InputStream in, String encoding) throws IOException {
        if (in == null) {
            return null;
        } else {
            byte[] buf = new byte[4];
            PushbackInputStream back = new PushbackInputStream(in, 4);
            int byteNums = back.read(buf, 0, buf.length);
            int unread;
            if (buf[0] == 0 && buf[1] == 0 && buf[2] == -2 && buf[3] == -1) {
                encoding = "UTF-32BE";
                unread = byteNums - 4;
            } else if (buf[0] == -1 && buf[1] == -2 && buf[2] == 0 && buf[3] == 0) {
                encoding = "UTF-32LE";
                unread = byteNums - 4;
            } else if (buf[0] == -17 && buf[1] == -69 && buf[2] == -65) {
                encoding = "UTF-8";
                unread = byteNums - 3;
            } else if (buf[0] == -2 && buf[1] == -1) {
                encoding = "UTF-16BE";
                unread = byteNums - 2;
            } else if (buf[0] == -1 && buf[1] == -2) {
                encoding = "UTF-16LE";
                unread = byteNums - 2;
            } else {
                if (encoding == null || encoding.equals("")) {
                    encoding = Charset.defaultCharset().name();
                }

                unread = byteNums;
            }

            if (unread > 0) {
                back.unread(buf, byteNums - unread, unread);
            }

            return new InputStreamReader(back, encoding);
        }
    }

    // ------------------------ 私有方法 ------------------------
    private static String fixTargetPath(String srcPath, String targetPath) {
        int last = srcPath.lastIndexOf(SP);
        String folderName = srcPath.substring(last, srcPath.length());
        return targetPath.endsWith(SP) ? targetPath + folderName : targetPath + SP + folderName;
    }

    private static String endSP(String path) {
        return path.endsWith(SP) ? path : path + SP;
    }

    public static void main(String[] args) throws Exception {
        // String path = "/Users/sunday/Java/StudyVideo/SpringMVC";
        // String old = "北京动力节点-SpringMVC4-";
        // String newChar = "";
        // editNames(path, old, newChar);
    }

}
