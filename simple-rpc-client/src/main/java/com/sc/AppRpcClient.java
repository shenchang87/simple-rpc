package com.sc;

import com.sc.annotation.EnableRpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRpcClient
public class AppRpcClient {
    public static void main(String[] args) {
        SpringApplication.run(AppRpcClient.class,args);
    }
}
