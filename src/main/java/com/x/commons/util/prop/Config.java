package com.x.commons.util.prop;

import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.file.Files;
import com.x.commons.util.prop.data.Part;
import com.x.commons.util.prop.data.Prop;
import com.x.commons.util.reflact.Clazzs;
import com.x.commons.util.string.Strings;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/23 11:31
 */
public class Config {

    /**
     * 将制定路径下的配置文件解析成Part对象数组
     *
     * @param path
     * @return
     * @throws IOException
     */
    public Part[] load(String path) throws IOException {
        Map<String, Part> parts = New.linkedMap();
        // 读取xxx.properties的配置文件
        String txt = Files.readTxt(path);
        if (Strings.isNotNull(txt)) {
            // 修正文本
            txt = txt.replaceAll("\r\n", "\n").replaceAll("\r", "\n");
            // 分割
            String[] lines = txt.split("\n");
            // 遍历
            if (!XArrays.isEmpty(lines)) {
                // 部分配置名字样式,如：[user]
                Pattern partNamePattern = Pattern.compile("^\\[\\s*(.*)\\s*\\]$");
                // 注释
                SB note = New.sb();
                Part part = null;
                for (String line : lines) {
                    line = line.trim();
                    if (Strings.isNotNull(line)) {
                        // 非注释
                        if (!line.startsWith("#")) {
                            Matcher m = partNamePattern.matcher(line);
                            // 块名
                            if (m.matches()) {
                                // 找到所有结果
                                MatchResult result = m.toMatchResult();
                                if (result.groupCount() > 0) {
                                    String partName = result.group(1);
                                    // 名字有效
                                    if (Strings.isNotNull(partName)) {
                                        if (parts.containsKey(partName)) {
                                            part = parts.get(partName);
                                        } else {
                                            part = new Part(note.get(), partName);
                                            parts.put(partName, part);
                                        }
                                        if (note.length() > 0) {
                                            note = New.sb();
                                        }
                                    }
                                }
                            } else {
                                // 属性
                                if (part != null) {
                                    int eqIndex = line.indexOf("=");
                                    if (eqIndex > 0) {
                                        String key = line.substring(0, eqIndex).trim();
                                        if (Strings.isNotNull(key)) {
                                            String value = line.substring(eqIndex + 1).trim();
                                            part.putProp(new Prop(note.get(), key, value));
                                            if (note.length() > 0) {
                                                note = New.sb();
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (line.startsWith("#")) {
                                // 注释
                                line = line.replaceAll("^#\\s*", "");
                                note.append(line);
                            }
                        }
                    }
                }
            }
        }
        return parts.values().toArray(new Part[0]);
    }

    /**
     * 将Part对象解析成指定对象
     *
     * @param clazz
     * @param part
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T toClass(Class<T> clazz, Part part) throws Exception {
        if (clazz != null && part != null) {
            T bean = Clazzs.newInstance(clazz);
            Map<String, Prop> props = part.getPropMap();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                if (props.containsKey(name)) {
                    Prop prop = props.get(name);
                    field.set(bean, prop.getValue());
                }
            }
            return bean;
        }
        return null;
    }

    /**
     * 将制定类保存成文件
     *
     * @param filename
     * @param clazz
     * @param charset
     * @throws Exception
     */
    public void toFile(String filename, Class<?> clazz, String charset) throws Exception {
        if (clazz != null && Strings.isNotNull(filename)) {
            String name = clazz.getName();
            Part part = new Part("", name);
            Field[] fields = clazz.getDeclaredFields();
            Object o = Clazzs.newInstance(clazz);
            for (Field field : fields) {
                field.setAccessible(true);
                String key = field.getName();
                String value = Strings.of(field.get(o));
                part.putProp(new Prop("", key, value));
            }
            save(filename, charset, part);
        }
    }

    private boolean save(String filename, String charset, Part... parts) {
        SB text = New.sb();
        for (Part part : parts) {
            text.append(this.fixNote(part.getNote()));
            text.append("[");
            text.append(part.getName());
            text.append("]\r\n");
            Prop[] props = part.getProps();
            for (Prop prop : props) {
                text.append(this.fixNote(prop.getNote()));
                text.append(prop.getKey());
                text.append("=");
                text.append(prop.getValue());
                text.append("\r\n");
            }
        }
        return Files.writeTxt(text.get(), filename, Charset.forName(charset));
    }

    private String fixNote(String note) {
        if (Strings.isNotNull(note)) {
            note = note.replaceAll("\r\n", "\n");
            note = note.replaceAll("\r", "\n");
            String[] lines = note.split("[\n]");
            if (XArrays.isEmpty(lines)) {
                SB snote = New.sb();

                for (String line : lines) {
                    line = line.trim();
                    if (line.length() == 0) {
                        snote.append("\r\n");
                    } else if (!line.startsWith("#")) {
                        snote.append("# ");
                        snote.append(line);
                        snote.append("\r\n");
                    }
                }
                return snote.toString();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
}
