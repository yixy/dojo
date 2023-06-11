package me.youzhilane.dojo.spring;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("userService")
//@Scope("prototype")
public class UserService implements BeanNameAware, InitializingBean, BeanFactoryAware, DisposableBean {
    @Autowired
    public UserInfo userInfo;
    private String beanName;

    public String getTestFlag() {
        return testFlag;
    }

    //5. 调用Setter设置Bean的属性值
    public void setTestFlag(String testFlag) {
        System.out.println("====== "+getClass().getSimpleName()+"[getClass().getSimpleName]: 已设置属性值 - "+this.userInfo +" - " +this.testFlag+" ======");
        this.testFlag = testFlag;
    }

    @Value("The Flag is set")
    public String testFlag;

    //2. 在 Bean 实例化前执行的回调方法
    public UserService() {
        System.out.println(getClass().getSimpleName()+"[getClass().getSimpleName]: constructor");
    }

    public void test(){
        System.out.println(this.userInfo);
    }

    //6. get beanName from spring (属性值设置后被调用)
    @Override
    public void setBeanName(String beanName) {
        System.out.println(beanName+": BeanNameAware.setBeanName");
        this.beanName=beanName;
    }

    //7. 让 Bean 获取所属的 Bean 工厂。将BeanFactory容器实例设置到Bean中；
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println(this.beanName+": BeanFactoryAware.setBeanFactory - "+beanFactory);
    }

    //9. @PostConstruct 等价于 bean标签通过init-method属性定义的初始化方法
    @PostConstruct
    public void init() {
        System.out.println(this.beanName+": @PostConstruct");
    }

    //10. InitializingBean.afterPropertiesSet回调方法
    @Override
    public void afterPropertiesSet() {
        System.out.println(this.beanName+" InitializingBean.afterPropertiesSet");
    }


    //12. 对于scope="singleton"的Bean， @PreDestroy 等价于 通过bean标签的destroy-method属性指定Bean的销毁方法
    @PreDestroy
    public void preDestroy() {
        System.out.println(this.beanName+": @PreDestroy");
    }

    //13. 对于scope="singleton"的Bean， Spring将执行Bean的这个方法，完成Bean资源的释放等操作
    @Override
    public void destroy() throws Exception {
        System.out.println(this.beanName+": DisposableBean.destroy");
    }
}
