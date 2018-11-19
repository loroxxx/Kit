package com.luo.kit;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by luoyuanq on 2018/11/19.
 */
public class ClassKit {

    private static Logger log = LoggerFactory.getLogger(ClassKit.class);

    /**
     * 查找某个包下的所有class的set集合,默认递归
     *
     * @param packageName
     * @return
     */
    public static Set<Class> readClasses(String packageName) {
        boolean recursive = true;

        Set<Class> classes = new HashSet<>();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = ClassKit.class.getClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                System.out.println(url);
                String filePath = new URI(url.getFile()).getPath();
                if (url.toString().startsWith("file:")) {//查找本地文件夹下的所有class文件
                    findClassByPackage(packageName, filePath, recursive, classes);
                }

                if (url.toString().startsWith("jar:file:") || url.toString().startsWith("wsjar:file:")) {//查找jar包下的所有class文件
                    getJarClasses(url, packageDirName, packageName, recursive, classes);
                }

            }
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            log.error("Add user custom view class error Can't find such Class files.");
        }
        return classes;
    }


    private static Set<Class> findClassByPackage(final String packageName, final String packagePath,
                                                 final boolean recursive, Set<Class> classes) throws ClassNotFoundException {
        File dir = new File(packagePath);
        if ((!dir.exists()) || (!dir.isDirectory())) {
            log.warn("The package [{}] not found.", packageName);
        }
        File[] dirFiles = dir.listFiles(file1 -> (recursive && file1.isDirectory()) || (file1.getName().endsWith(".class")));
        if (null != dirFiles && dirFiles.length > 0) {
            for (File file : dirFiles) {
                if (file.isDirectory()) {
                    findClassByPackage(packageName + '.' + file.getName(), file.getAbsolutePath(), recursive, classes);
                } else {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    Class<?> clazz = Class.forName(packageName + '.' + className);
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }


    private static Set<Class> getJarClasses(final URL url, final String packageDirName, String packageName, final boolean recursive, Set<Class> classes) {
        try {
            JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
            Enumeration<JarEntry> eje = jarFile.entries();
            while (eje.hasMoreElements()) {
                JarEntry entry = eje.nextElement();
                String name = entry.getName();
                if (name.charAt(0) == '/') {
                    name = name.substring(1);
                }
                if (!name.startsWith(packageDirName)) {
                    continue;
                }
                int idx = name.lastIndexOf('/');
                if (idx != -1) {
                    packageName = name.substring(0, idx).replace('/', '.');
                }
                if (idx == -1 && !recursive) {
                    continue;
                }
                if (!name.endsWith(".class") || entry.isDirectory()) {
                    continue;
                }
                String className = name.substring(packageName.length() + 1, name.length() - 6);
                Class<?> clazz = Class.forName(packageName + '.' + className);

                classes.add(clazz);
            }

        } catch (IOException e) {
            log.error("The scan error when the user to define the view from a jar package file.", e);
        } catch (ClassNotFoundException e) {
            log.error("", e);
        }
        return classes;
    }


    /**
     * @param cls
     * @return cls文件的所在的包的URL
     */
    public static URL getClassPackageURL(Class<?> cls) {
        return cls.getResource("");
    }


    public static void main(String[] args) {

//        readClasses("com.luo").stream().forEach(System.out::println);

        new ClassKit().readClasses("io.netty").stream().forEach(System.out::println);
    }


}
