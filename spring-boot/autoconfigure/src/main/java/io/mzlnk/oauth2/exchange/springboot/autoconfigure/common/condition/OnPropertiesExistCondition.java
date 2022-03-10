package io.mzlnk.oauth2.exchange.springboot.autoconfigure.common.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;

public class OnPropertiesExistCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        var annotationMetadata = metadata.getAnnotationAttributes(ConditionalOnPropertiesExist.class.getName());
        if (annotationMetadata == null) {
            return false;
        }

        String prefix = (String) annotationMetadata.get("prefix");
        String[] properties = (String[]) annotationMetadata.get("properties");

        boolean result = Arrays.stream(properties)
                .map(property -> "%s.%s".formatted(prefix, property))
                .allMatch(context.getEnvironment()::containsProperty);

        return result;
    }

}
