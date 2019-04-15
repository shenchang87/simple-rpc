package com.sc.annotation;


import com.sc.rpc.client.ImportProxyBean;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ImportProxyBean.class})
public @interface EnableRpcClient {
}
