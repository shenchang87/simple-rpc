package com.sc;


import com.sc.annotation.EnableRpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRpcServer
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
