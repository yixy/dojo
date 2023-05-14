package me.youzhilane.dojo;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class NameIntercepter {

    @RuntimeType
    public Object aaa(
            //intercept the target obj, using in obj.func() or constructor
            @This Object targetObj,
            @Origin Class<?> clazz,
            //using in obj.func() or static func()
            @Origin Method targetMethod,
            //parameters, using in obj.func() or static func() or constructor
            @AllArguments Object[] targetMethodArgs,
            //using in obj.func() or constructor
            @Super Object targetObj2,

            //call target func (original function). using in subclass[obj.func()] or rebase[obj.func() or static func()]
            //注意拦截成员方法和静态方法不能使用redefine类型，因为redefine中不保留原始方法，无法在interceptor中通过zuper调用。
            //拦截静态方法也不能用subclass类型，因为静态方法不能被继承
            //@SuperCall Callable<?> zuper
            //call target func with dynamic params
            @Morph MyCallable zuper
            ){
        System.out.println("targetObj="+targetObj);
        System.out.println("clazz="+clazz);
        System.out.println("targetMethod.getName()="+targetMethod.getName());
        System.out.println("Arrays.toString(targetMethodArgs)="+ Arrays.toString(targetMethodArgs));
        System.out.println("targetObj2="+targetObj2);
        Object call=null;
        try {
            if(targetMethodArgs!=null&& targetMethodArgs.length>0){
                targetMethodArgs[0]="Heisenberg";
            }
            //do not use reflect, because of recursion.
            call=zuper.call(targetMethodArgs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return call;
    }
}
