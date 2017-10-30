package cn.ylapl.util.jackson;

import java.lang.annotation.*;

/**
 * ${DESCRIPTION}
 *
 * @author
 * @create 2017-09-09 下午3:44
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(JSONS.class)   // 让方法支持多重@JSON 注解
public @interface JSON {
    Class<?> type();
    String include() default "";
    String filter() default "";
}
