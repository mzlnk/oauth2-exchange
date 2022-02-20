package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode

import io.mzlnk.oauth2.exchange.core.authorizationcode.client.KeycloakAuthorizationCodeExchangeClient
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.OAuth2ExchangeCoreAutoConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
import org.springframework.boot.logging.LogLevel
import org.springframework.boot.test.context.runner.ApplicationContextRunner

import static org.assertj.core.api.Assertions.assertThat

class KeycloakAuthorizationCodeExchangeDefaultConfigurationTest {

    private static final String KEYCLOAK_EXCHANGE_PREFIX = 'oauth2.exchange.providers.keycloak'

    private ApplicationContextRunner contextRunner

    @BeforeEach
    void "Set up tests"() {
        def initializer = new ConditionEvaluationReportLoggingListener(LogLevel.INFO);

        this.contextRunner = new ApplicationContextRunner()
                .withInitializer(initializer)
    }

    @Test
    void "Should application context not fail to start if required properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, KeycloakAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.host=https://some-host.com")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.realm=some-realm")
                .run((context) -> {
                    assertThat(context).hasNotFailed()
                })
    }

    @Test
    void "Should application context not fail to start if required properties are not present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, KeycloakAuthorizationCodeExchangeDefaultConfiguration))
                .run((context) -> {
                    assertThat(context).hasNotFailed()
                })
    }

    @Test
    void "Should auto-configure all components if properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, KeycloakAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.host=https://some-host.com")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.realm=some-realm")
                .run((context) -> {
                    assertThat(context).hasBean('defaultKeycloakExchangeClient')
                    assertThat(context).hasBean('defaultKeycloakResponseHandler')
                    assertThat(context).hasBean('defaultKeycloakExchange')
                })
    }

    @Test
    void "Should not auto-configure all components if no properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, KeycloakAuthorizationCodeExchangeDefaultConfiguration))
                .run((context) -> {
                    assertThat(context).doesNotHaveBean('defaultKeycloakExchangeClient')
                    assertThat(context).doesNotHaveBean('defaultKeycloakResponseHandler')
                    assertThat(context).doesNotHaveBean('defaultKeycloakExchange')
                })
    }

    @Test
    void "Should not auto-configure all components if not all properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, KeycloakAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .run((context) -> {
                    assertThat(context).doesNotHaveBean('defaultKeycloakExchangeClient')
                    assertThat(context).doesNotHaveBean('defaultKeycloakResponseHandler')
                    assertThat(context).doesNotHaveBean('defaultKeycloakExchange')
                })
    }

    @Test
    void "Should auto-configure exchange client with proper properties values"() {
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def host = 'https://some-host.com'
        def realm = 'some-realm'

        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, KeycloakAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.client-id=${clientId}")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.client-secret=${clientSecret}")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.redirect-uri=${redirectUri}")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.host=${host}")
                .withPropertyValues("${KEYCLOAK_EXCHANGE_PREFIX}.realm=${realm}")
                .run((context) -> {
                    assertThat(context).hasBean('defaultKeycloakExchangeClient')

                    def exchangeClient = context.getBean('defaultKeycloakExchangeClient', KeycloakAuthorizationCodeExchangeClient)
                    assert exchangeClient.clientId == clientId
                    assert exchangeClient.clientSecret == clientSecret
                    assert exchangeClient.redirectUri == redirectUri
                    // TODO: implement getter for host field - GH-48
                    // assert exchangeClient.host == host
                    // TODO: implement getter for realm field - GH-48
                    // assert exchangeClient.realm == realm
                })
    }

}
