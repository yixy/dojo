package me.youzhilane.dojo;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import javassist.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

@Slf4j
public class Agent {
    //$ java -javaagent:target/bytebuddy-demo-1.0-SNAPSHOT-jar-with-dependencies.jar -jar target_jar
    public static void premain(String args, Instrumentation instrumentation) {
        log.info("Enter premain, args:{}",args);
        instrumentation.addTransformer(new ClassFileTransformerDemo(),true);
        log.info("Leave premain, args:{}",args);
    }

    //$ java -jar target/bytebuddy-demo-1.0-SNAPSHOT-jar-with-dependencies.jar ${targetPID} /${pathtobytebuddy-demo}/bytebuddy-demo/target/bytebuddy-demo-1.0-SNAPSHOT-jar-with-dependencies.jar
    public static void agentmain(String args, Instrumentation instrumentation) throws NotFoundException, CannotCompileException, IOException, UnmodifiableClassException, ClassNotFoundException {
        log.info("Enter agentmain, args:{}",args);
        Class[] classes = instrumentation.getAllLoadedClasses();
        for (Class clazz : classes) {
            if (clazz.getName().equals("me.youzhilane.dojo.service.HelloService")) {
                // 使用JavaAssist修改字节码
                ClassPool pool = ClassPool.getDefault();
                pool.getDefault().insertClassPath(new ClassClassPath(clazz));
                CtClass ctClass = pool.get(clazz.getName());
                CtMethod ctMethod = ctClass.getDeclaredMethod("say");
                ctMethod.insertBefore("{ System.out.println(\"Before sayHello\"); }");
                ctMethod.insertAfter("{ System.out.println(\"After sayHello\"); }");
                // 重新定义类
                byte[] bytecode = ctClass.toBytecode();
                instrumentation.redefineClasses(new ClassDefinition(clazz, bytecode));
                break;
            }
        }
        log.info("Leave agentmain, args:{}",args);
    }
    public static void main(String[] args) throws InterruptedException, IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        System.out.println("args:"+args[0]);
        System.out.println("args:"+args[1]);
        //System.out.println("args:"+args[2]);
        VirtualMachine vmObj = VirtualMachine.attach(args[0]);//targetJvmPid 为目标 JVM 的进程 ID
        vmObj.loadAgent(args[1], "");  // agentJarPath 为 agent jar 包的路径，cfg 为传递给 agent 的参数
    }
}
