package com.sc.rpc.client;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class InterfaceFactoryBean<T> implements FactoryBean<T> {
    private Class<T> interfaceClass;

    /**
     * 在bean注册时设置
     */
    private String serviceVersion;

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    /**
     * 新建bean
     * @return
     * @throws Exception
     */

    public T getObject() throws Exception {
        //利用反射具体的bean新建实现，不支持T为接口。
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},new DymicInvocationHandler(interfaceClass,serviceVersion));
    }

    /**
     * 获取bean
     * @return
     */

    public Class<?> getObjectType() {
        return interfaceClass;
    }


    public boolean isSingleton() {
        return true;
    }

}