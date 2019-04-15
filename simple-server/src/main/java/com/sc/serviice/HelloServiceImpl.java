package com.sc.serviice;


import com.sc.annotation.RcpService;
import com.sc.api.HelloService;
import com.sc.api.Person;

@RcpService(value =HelloService.class)
public class HelloServiceImpl implements HelloService {


    public String hello(String name) {
        return "Hello! sc " + name;
    }


    public String hello(Person person) {
        return "Hello! " + person.getFirstName() + " " + person.getLastName();
    }
}
