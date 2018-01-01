package cn.zhangcy.cad.Core;


import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleField {
    String Name() default "";
    double Min() default 0.0;
    double Max() default 1.0;
}
