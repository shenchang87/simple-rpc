package com.sc.annotation;


import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface RcpService {
    /*
     * 服务接口类
     */
 Class<?> value();

    /**
     * 服务版本号
     */
    String version() default "";


}
