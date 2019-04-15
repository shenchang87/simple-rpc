package com.sc.rpc.client;

import com.sc.util.PackageSanUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;


public class ImportProxyBean  implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private static ApplicationContext applicationContext;
    private String   backPackage=null;
    private  String  zkAddress=null;

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

        System.out.println("postProcessBeanDefinitionRegistry");
        PackageSanUtil ps=new PackageSanUtil();
        List<String> list=ps.scanPackage(backPackage);

        for (String pakName : list) {
            System.out.println(pakName);
          Class<?> beanClazz= null;
            try {
                beanClazz = Class.forName(pakName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            definition.getPropertyValues().add("interfaceClass", beanClazz);
            definition.getPropertyValues().add("serviceVersion", zkAddress);
            definition.setBeanClass(InterfaceFactoryBean.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            beanDefinitionRegistry.registerBeanDefinition(beanClazz.getSimpleName(), definition);
        }

    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取系统环境变量
        System.out.println("setApplicationContext");
        this.applicationContext=applicationContext;
         Environment environment= applicationContext.getEnvironment();
        backPackage=environment.getProperty("rpc.backPackage");
        zkAddress=environment.getProperty("rpc.registry_address","");
        System.out.println(backPackage);
        System.out.println(zkAddress);

    }

    public void setEnvironment(Environment environment) {
        System.out.println("Environment---------------------");
    }
}
