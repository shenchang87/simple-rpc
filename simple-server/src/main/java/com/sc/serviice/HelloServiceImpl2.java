package com.sc.serviice;


import com.sc.annotation.RcpService;
import com.sc.api.HelloService;
import com.sc.api.Person;

@RcpService(value =HelloService.class, version = "sample.hello2")
public class HelloServiceImpl2 implements HelloService {


    public String hello(String name) {
        return "你好  sd ! " + name;
    }

    public String hello(Person person) {
        return "你好! " + person.getFirstName() + " " + person.getLastName();
    }
}
