package me.youzhilane.dojo;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassFileTransformerDemo implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if("me/youzhilane/dojo/service/HelloService".equals(className)){
            System.out.println("**********************************************");
            try {
                ClassPool cp = ClassPool.getDefault();
                CtClass cc = cp.get(className.replace("/", "."));
                CtMethod m = cc.getDeclaredMethod("say");
                m.insertBefore("{ System.out.println(\"Before sayHello\"); }");
                m.insertAfter("{ System.out.println(\"After sayHello\"); }");
                System.out.println("**********************************************");
                return cc.toBytecode();
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
