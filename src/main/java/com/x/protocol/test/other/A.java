package com.x.protocol.test.other;

import com.x.protocol.anno.core.Actor;
import lombok.SneakyThrows;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Date 2018-12-23 16:08
 * @Author AD
 */
@Actor(cmd = "A1", doc = "Test")
public class A {

    @SneakyThrows
    public static void main(String[] args) {
        final boolean contain = A.class.isAnnotationPresent(Actor.class);
        System.out.println(contain);
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packageName = "com.x.commons.util";
        final String s = packageName.replaceAll("\\.", "/");
        // System.getOutputStream.println(s);
        final Enumeration<URL> resources = loader.getResources(s);
        // System.getOutputStream.println(resources);
        if (resources != null) {
            final URL[] urls = ((URLClassLoader) loader).getURLs();
            for (int i = 0; i < urls.length; ++i) {
                URL url = urls[i];
                //-- /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/lib/tools.jar
                //-- /Users/sunday/IdeaProjects/X-Tools/target/classes/
                final String path = url.getPath();
                if (!path.endsWith("classes/")) {
                    String newPath = path + "!/" + s;
                    String newPaths[] = newPath.split("!");
                    // System.getOutputStream.println(XArrays.toString(newPaths));
                    final String jarPath = newPaths[0].substring(newPaths[0].indexOf("/"));
                    // System.getOutputStream.println(jarPath);
                    // System.getOutputStream.println("===========");
                    JarFile jarFile = null;
                    try {
                        jarFile = new JarFile(jarPath);
                        final Enumeration<JarEntry> entries = jarFile.entries();
                        while (entries.hasMoreElements()) {
                            final JarEntry jarEntry = entries.nextElement();
                            if (!jarEntry.isDirectory()) {
                                String jarEntryName = jarEntry.getName();
                                // System.getOutputStream.println(jarEntryName);
                                if (jarEntryName != null && jarEntryName.length() >= 8) {
                                    if (jarEntryName.charAt(0) == 47) {// 47=/
                                        jarEntryName = jarEntryName.substring(1);
                                    }
                                    if (jarEntryName.startsWith("com") && jarEntryName.endsWith(".class")) {
                                        jarEntryName = jarEntryName.replaceAll("/", ".");
                                        jarEntryName = jarEntryName.substring(0, jarEntryName.length() - 6);
                                        System.out.println(jarEntryName);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

}
