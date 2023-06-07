package me.youzhilane.dojo.spring;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ApplicationContext {
    private Class configClass;
    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap =new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,Object> singletonObjects =new ConcurrentHashMap<>();
    public ApplicationContext(Class appConfigClass) {
        this.configClass=appConfigClass;

        //scan package and create beanDefinition
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
                            setBeanDefinition(className,classLoader);
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
                            setBeanDefinition(className,classLoader);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //create singleton beans
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if ("singleton".equals(beanDefinition.getScope())) {
                Object bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName,bean);
            }
        }
    }

    private void setBeanDefinition(String className, ClassLoader classLoader){
        className=className.replace("/",".");
        Class<?> clazz = null;
        try {
            clazz = classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (clazz.isAnnotationPresent(Component.class)) {
            //new BeanDefinition
            System.out.println(className);
            Component componentAnnotation = clazz.getAnnotation(Component.class);
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setType(clazz);
            if (clazz.isAnnotationPresent(Scope.class)) {
                Scope scopAnnotation = clazz.getAnnotation(Scope.class);
                beanDefinition.setScope(scopAnnotation.value());
            }else {
                beanDefinition.setScope("singleton");
            }
            beanDefinitionMap.put(componentAnnotation.value(),beanDefinition);
        }
    }

    private Object createBean(String beanName,BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getType();
        Object obj;

        try {
            obj = clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public Object getBean(String beanName){
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException();
        }else {
            if ("singleton".equals(beanDefinition.getScope())) {
                Object bean = singletonObjects.get(beanName);
                if (bean == null) {
                    bean = createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName,bean);
                }
                return bean;
            }else {
                return createBean(beanName,beanDefinition);
            }
        }
    }
}
