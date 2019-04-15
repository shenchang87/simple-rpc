package com.sc.annotation;


import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface RcpScan {

    /**
     * 扫描接口路径
     */
    String value() ;
}
