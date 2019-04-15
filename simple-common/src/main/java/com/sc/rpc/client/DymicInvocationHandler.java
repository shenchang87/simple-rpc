package com.sc.rpc.client;

import com.sc.bean.RpcRequest;
import com.sc.bean.RpcResponse;
import com.sc.registry.center.ServiceDiscovery;
import com.sc.registry.center.ZooKeeperServiceDiscovery;
import com.sc.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

public class DymicInvocationHandler implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DymicInvocationHandler.class);

    private  Class<?> interfaceClass;
    private  String  serviceVersion;
    private String serviceAddress;
    private String  zkAddrsss;

    private ServiceDiscovery  serviceDiscovery =null;

    public DymicInvocationHandler(Class<?> interfaceClass, String  zkAddrsss){
        System.out.println("interfaceClass"+interfaceClass.toString());
        System.out.println("zkAddrsss"+ zkAddrsss);
        this.interfaceClass =interfaceClass;
        this.zkAddrsss=zkAddrsss;
        serviceDiscovery =  new ZooKeeperServiceDiscovery(zkAddrsss);
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 创建 RPC 请求对象并设置请求属性
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setInterfaceName(method.getDeclaringClass().getName());
        request.setServiceVersion(serviceVersion);
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        if (serviceDiscovery != null) {
            String serviceName = interfaceClass.getName();
            if (StringUtil.isNotEmpty(serviceVersion)) {
                serviceName += "-" + serviceVersion;
            }

            serviceAddress = serviceDiscovery.discover(serviceName);
            LOGGER.debug("discover service: {} => {}", serviceName, serviceAddress);
        }
        if (StringUtil.isEmpty(serviceAddress)) {
            throw new RuntimeException("server address is empty");
        }
        // 从 RPC 服务地址中解析主机名与端口号
        String[] array = StringUtil.split(serviceAddress, ":");
        String host = array[0];
        int port = Integer.parseInt(array[1]);
        // 创建 RPC 客户端对象并发送 RPC 请求
        RpcClient client = new RpcClient(host, port);
        long time = System.currentTimeMillis();
        RpcResponse response = client.send(request);
        LOGGER.debug("time: {}ms", System.currentTimeMillis() - time);
        if (response == null) {
            throw new RuntimeException("response is null");
        }
        // 返回 RPC 响应结果
        if (response.hasException()) {
            throw response.getException();
        } else {
            return response.getResult();
        }
    }
}
