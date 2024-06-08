package cn.yahaoyang.aop.requestIntercept;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authentication {
}
