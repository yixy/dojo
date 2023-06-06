package me.youzhilane.dojo.spring;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ApplicationContext {
    private Class configClass;
    public ApplicationContext(Class appConfigClass) {
        this.configClass=appConfigClass;

        //scan
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScanAnotation.value(); // me.youzhilane.service
            path=path.replace(".","/");
            ClassLoader classLoader = ApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            String protocol = resource.getProtocol();
            if ("file".equals(protocol)) {// for class file in linux/mac
                File file = new File(resource.getFile());
                if (file.isDirectory()) {// /classpath/me/youzhilane/service
                    File[] files = file.listFiles();
                    for (File f : files) {
                        String fileName = f.getAbsolutePath();
                        if (fileName.endsWith(".class")) {
                            // me/youzhilane/service/UserService
                            String className = fileName.substring(
                                    fileName.indexOf("me/youzhilane/dojo"),
                                    fileName.indexOf(".class"));
                            setBean(className,classLoader);
                        }
                    }
                }
            }else if("jar".equals(protocol)){// for jar in linux/mac
                try {
                    JarFile jarFile = ((JarURLConnection) resource.openConnection()).getJarFile();
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while(entries.hasMoreElements()){
                        JarEntry entry = entries.nextElement();
                        String className = entry.getName();
                        if(className.startsWith(path)&&className.endsWith(".class")){
                            className=className.substring(0,className.indexOf(".class"));
                            setBean(className,classLoader);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    private void setBean(String className, ClassLoader classLoader){
        className=className.replace("/",".");
        Class<?> clazz = null;
        try {
            clazz = classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (clazz.isAnnotationPresent(Component.class)) {
            //Bean
            System.out.println(className);
        }
    }

    public Object getBean(String beanName){
        return null;
    }
}
