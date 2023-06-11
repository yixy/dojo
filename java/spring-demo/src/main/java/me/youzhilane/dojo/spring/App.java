package me.youzhilane.dojo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class App {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);
        UserService bean = (UserService) context.getBean("userService");
        bean.test();
    }

//    userService: InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation
//    UserService[getClass().getSimpleName]: constructor
//    userService: InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation
//    userService: InstantiationAwareBeanPostProcessor.postProcessProperties; -未设置属性值-  userService.userInfo:null userService.testFlag:null; PropertyValues:PropertyValues: length=0
//            ====== UserService[getClass().getSimpleName]: 已设置属性值 - me.youzhilane.dojo.spring.UserInfo@22c86919 - The Flag is set ======
//    userService: BeanNameAware.setBeanName
//    userService: BeanFactoryAware.setBeanFactory - org.springframework.beans.factory.support.DefaultListableBeanFactory@2d778add: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.event.internalEventListenerProcessor,org.springframework.context.event.internalEventListenerFactory,app,org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory,myBeanPostProcessor,userInfo,userService,org.springframework.boot.autoconfigure.AutoConfigurationPackages,org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,propertySourcesPlaceholderConfigurer,org.springframework.boot.autoconfigure.aop.AopAutoConfiguration$ClassProxyingConfiguration,forceAutoProxyCreatorToUseClassProxying,org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration,applicationAvailability,org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor,org.springframework.boot.context.internalConfigurationPropertiesBinder,org.springframework.boot.context.properties.BoundConfigurationProperties,org.springframework.boot.context.properties.EnableConfigurationPropertiesRegistrar.methodValidationExcludeFilter,org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration,lifecycleProcessor,spring.lifecycle-org.springframework.boot.autoconfigure.context.LifecycleProperties,org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,spring.info-org.springframework.boot.autoconfigure.info.ProjectInfoProperties,org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration,spring.sql.init-org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties,org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer$DependsOnDatabaseInitializationPostProcessor,org.springframework.boot.autoconfigure.ssl.SslAutoConfiguration,sslPropertiesSslBundleRegistrar,sslBundleRegistry,spring.ssl-org.springframework.boot.autoconfigure.ssl.SslProperties,org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,taskExecutorBuilder,applicationTaskExecutor,spring.task.execution-org.springframework.boot.autoconfigure.task.TaskExecutionProperties,org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration,taskSchedulerBuilder,spring.task.scheduling-org.springframework.boot.autoconfigure.task.TaskSchedulingProperties,org.springframework.aop.config.internalAutoProxyCreator]; root of factory hierarchy
//    userService: BeanPostProcessor.postProcessBeforeInitialization
//    userService: @PostConstruct
//    userService InitializingBean.afterPropertiesSet
//    userService: BeanPostProcessor.postProcessAfterInitialization
//[2023-06-11 20:46:14.938] - 121049 INFO [main] --- me.youzhilane.dojo.spring.App: Started App in 0.739 seconds (process running for 0.952)
//    me.youzhilane.dojo.spring.UserInfo@22c86919
//    userService: @PreDestroy
//    userService: DisposableBean.destroy
}
