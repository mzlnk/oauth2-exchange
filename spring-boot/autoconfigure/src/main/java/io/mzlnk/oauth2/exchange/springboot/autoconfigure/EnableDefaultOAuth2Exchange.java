package io.mzlnk.oauth2.exchange.springboot.autoconfigure;

import io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode.GoogleAuthorizationCodeExchangeDefaultConfiguration;
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode.MicrosoftAuthorizationCodeExchangeDefaultConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({
        GoogleAuthorizationCodeExchangeDefaultConfiguration.class,
        MicrosoftAuthorizationCodeExchangeDefaultConfiguration.class
})
public @interface EnableDefaultOAuth2Exchange {

}
