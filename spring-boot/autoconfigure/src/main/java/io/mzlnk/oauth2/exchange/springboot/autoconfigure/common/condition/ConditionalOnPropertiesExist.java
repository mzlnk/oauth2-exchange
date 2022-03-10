package io.mzlnk.oauth2.exchange.springboot.autoconfigure.common.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnPropertiesExistCondition.class)
public @interface ConditionalOnPropertiesExist {

    String prefix();
    String[] properties();

}
