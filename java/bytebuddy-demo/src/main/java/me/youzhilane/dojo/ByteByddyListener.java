package me.youzhilane.dojo;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

@Slf4j
public class ByteByddyListener implements AgentBuilder.Listener {
    //当某个类被加载时回调此方法
    @Override
    public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
        log.info("onDiscovery typeName:{}",typeName);
    }
    //当某个类完成transform时回调此方法
    @Override
    public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
        log.info("onTransformation typeName:{}",typeDescription.getActualName());
    }
    //当某个类被忽略或未匹配时回调此方法
    @Override
    public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
        log.info("onIgnored typeName:{}",typeDescription.getActualName());
    }
    //当transform过程中发生异常时回调此方法
    @Override
    public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
        log.info("onError typeName:{}",typeName);
    }
    //当某个类处理完时（transform、ignore、err等）回调此方法
    @Override
    public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
        log.info("onComplete typeName:{}",typeName);
    }
}
