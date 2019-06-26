package com.wq.log;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WriteLog {

    String operDesc() default "";

    String operModule() default "";

    /*无默认值，为必填项*/
    OperType operType();


}
