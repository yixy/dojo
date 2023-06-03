package me.youzhilane.dojo;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.Morph;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class BytebuddyTest {
    private String path;

    @Before
    public void init(){
        path=BytebuddyTest.class.getClassLoader().getResource("").getPath();
        System.out.println(path);
    }

    @Test
    public void testCreateSubClass() throws IOException {
        NamingStrategy.SuffixingRandom suffixingRandom = new NamingStrategy.SuffixingRandom("youzhilane-demo");
        //define subclass of Object, which is not loaded in JVM
        DynamicType.Unloaded<Object> unloaded = new ByteBuddy()
                //define class name strategy
                .with(suffixingRandom)
                //define subclass of Object
                .subclass(Object.class)
                //specific class name, overide the name strategy
                .name("a.b.C")
                .make();
        //get bytecode
        byte[] bytes = unloaded.getBytes();
        unloaded.saveIn(new File(path));
        //inject .class to a jar
        //unloaded.inject(new File("/xxx/yyy.jar"));
    }

    @Test
    public void testIntercept() throws  InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        NamingStrategy.SuffixingRandom suffixingRandom = new NamingStrategy.SuffixingRandom("youzhilane-demo");
        Class<?> dynamicType = new ByteBuddy()
                //define class name strategy
                .with(suffixingRandom)

                //create a class in three Type: subclass/rebase/redefine
                //[subclass]
                //.subclass(DemoApp.class)
                //[rebase]: rename original func and create new func
                //.rebase(DemoApp.class)
                //[redefine]: like rebase but not keep the original func
                .subclass(DemoApp.class)
                .name("a.b.DemoAppHello")

                //1. match method to be intercepted
                .method(named("toString").and(returns(TypeDescription.STRING)))
                //define fixed interceptor
                .intercept(FixedValue.value("Hello,world!"))

                //2. new field
                .defineField("name",String.class,Modifier.PRIVATE)
                .implement(NameInterface.class)
                .intercept(FieldAccessor.ofField("name"))

                //3. new method
                .defineMethod("say",String.class, Modifier.PUBLIC+Modifier.STATIC)
                .withParameter(String[].class,"args")
                //custom interceptor. for static func
                .intercept(MethodDelegation.to(SayInterceptor.class))
                //custom interceptor. for normal func
                //.intercept(MethodDelegation.to(new SayInterceptor()))

                //4 intecept: MethodDelegation for annotation. !!!!!!!!!!!!!!It seems only worked for subclass or rebase type in method match ???!!!!!!!!!
                .method(named("sayMyName"))
                //custom interceptor. for annotation ( func sign and return value do not need same )
                .intercept(MethodDelegation
                        //告诉bytebuddy参数的类型是MyCallable
                        //自定义MyCallable,在拦截器处使用@Morph代替@SuperCall进行动态参数修改
                        .withDefaultConfiguration()
                        .withBinders(Morph.Binder.install(MyCallable.class))
                        .to(new NameIntercepter()))
                //拦截任意的构造方法
                //.constructor(any())
                //.intercept(
                //        //指定在构造方法执行完成之后再委托给拦截器
                //        SuperMethodCall.INSTANCE.andThen(
                //                MethodDelegation.to(new xxxxIntercepter())
                //        )
                //)

                //gen bytecode
                .make()
                //load .class to JVM
                .load(getClass().getClassLoader())
                .getLoaded();

        Object obj = dynamicType.getConstructor().newInstance();
        Method say = obj.getClass().getMethod("say",String[].class);
        Method sayMyName = obj.getClass().getMethod("sayMyName",String.class);

        String[] params=new String[]{"foo","bar"};
        System.out.println(say.invoke(obj, (Object) params));

        System.out.println(sayMyName.invoke(obj,"Mr.White"));
        System.out.println("the end.....");
    }

    @Test
    public void testAgentBuilder(){
        NamingStrategy.SuffixingRandom suffixingRandom = new NamingStrategy.SuffixingRandom("youzhilane-demo");
        ByteBuddy byteBuddy = new ByteBuddy().with(suffixingRandom);
        AgentBuilder agentBuilxder = new AgentBuilder.Default(byteBuddy).ignore(
                nameStartsWith("net.bytebuddy.")
        );
    }
}
