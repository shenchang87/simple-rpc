package com.sc.annotation;


import com.sc.registry.center.ZooKeeperServiceDiscovery;
import com.sc.registry.center.ZooKeeperServiceRegistry;
import com.sc.rpc.server.RpcServer;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RpcServer.class,ZooKeeperServiceDiscovery.class, ZooKeeperServiceRegistry.class})
public @interface EnableRpcServer {
}
