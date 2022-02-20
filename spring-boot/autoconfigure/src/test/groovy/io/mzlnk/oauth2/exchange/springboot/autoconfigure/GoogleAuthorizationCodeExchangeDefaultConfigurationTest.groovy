package io.mzlnk.oauth2.exchange.springboot.autoconfigure

import io.mzlnk.oauth2.exchange.core.authorizationcode.GoogleAuthorizationCodeExchange
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode.GoogleAuthorizationCodeExchangeDefaultConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
import org.springframework.boot.logging.LogLevel
import org.springframework.boot.test.context.runner.ApplicationContextRunner

import static org.assertj.core.api.Assertions.assertThat

class GoogleAuthorizationCodeExchangeDefaultConfigurationTest {

    private static final String GOOGLE_EXCHANGE_PREFIX = 'oauth2.exchange.providers.google'

    private ApplicationContextRunner contextRunner

    @BeforeEach
    void "Set up tests"() {
        def initializer = new ConditionEvaluationReportLoggingListener(LogLevel.INFO);

        this.contextRunner = new ApplicationContextRunner()
                .withInitializer(initializer)
    }

//    @Test
//    void "Should default response handler be configured if property is present"() {
//        this.contextRunner
//                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, GoogleAuthorizationCodeExchangeDefaultConfiguration))
//                .withPropertyValues("oauth2.exchange.providers.google.client-id=some-client-id")
//                .withPropertyValues("oauth2.exchange.providers.google.client-secret=some-client-secret")
//                .withPropertyValues("oauth2.exchange.providers.google.redirect-uri=some-redirect-uri")
//                .withPropertyValues("abc.a3=1234")
////                .withPropertyValues(
////                        "${GOOGLE_EXCHANGE_PREFIX}.client-id='some-client-id'",
////                        "${GOOGLE_EXCHANGE_PREFIX}.client-secret='some-client-secret'",
////                        "${GOOGLE_EXCHANGE_PREFIX}.redirect-uri='some-redirect-uri'")
//
//                .run((context) -> {
//                    context.getProperties()
//                    assertThat(context).hasSingleBean(GoogleAuthorizationCodeExchange)
//                })
//
//    }


}
