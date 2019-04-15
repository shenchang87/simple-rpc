package com.sc.controller;

import com.sc.api.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rpc")
public class HelloController {

    @Resource
    HelloService helloService;

    @RequestMapping("/hello")
    public  String Hello(){
        return   helloService.hello("aaaa");
    }
}
