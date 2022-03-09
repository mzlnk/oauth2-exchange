package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode

import io.mzlnk.oauth2.exchange.core.authorizationcode.client.OktaOAuth2AuthorizationServerClient
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.OktaOAuth2Client
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.OktaOAuth2SingleSignOnClient
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.OAuth2ExchangeCoreAutoConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
import org.springframework.boot.logging.LogLevel
import org.springframework.boot.test.context.runner.ApplicationContextRunner

import static org.assertj.core.api.Assertions.assertThat

class OktaAuthorizationCodeExchangeDefaultConfigurationTest {

    private static final String OKTA_EXCHANGE_PREFIX = 'oauth2.exchange.providers.okta'

    private ApplicationContextRunner contextRunner

    @BeforeEach
    void "Set up tests"() {
        def initializer = new ConditionEvaluationReportLoggingListener(LogLevel.INFO)

        this.contextRunner = new ApplicationContextRunner()
                .withInitializer(initializer)
    }

    @Test
    void "Should application context not fail to start if required properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, OktaAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-type=SINGLE_SIGN_ON")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.okta-domain=some-okta-domain")
                .run((context) -> {
                    assertThat(context).hasNotFailed()
                })
    }

    @Test
    void "Should application context not fail to start if required properties are not present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, OktaAuthorizationCodeExchangeDefaultConfiguration))
                .run((context) -> {
                    assertThat(context).hasNotFailed()
                })
    }

    @Test
    void "Should application context fail to start if single-sign-on server client selected but no required additional properties are provided"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, OktaAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-type=SINGLE_SIGN_ON")
                .run((context) -> {
                    assertThat(context).hasFailed()
                })
    }

    @Test
    void "Should application context fail to start if authorization server client selected but no required additional properties are provided"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, OktaAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-type=AUTHORIZATION_SERVER")
                .run((context) -> {
                    assertThat(context).hasFailed()
                })
    }

    @Test
    void "Should auto-configure all components if all properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, OktaAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-type=SINGLE_SIGN_ON")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.okta-domain=some-okta-domain")
                .run((context) -> {
                    assertThat(context).hasBean('defaultOktaOAuth2Client')
                    assertThat(context).hasBean('defaultOktaTokenResponseHandler')
                    assertThat(context).hasBean('defaultOktaExchange')
                })
    }

    @Test
    void "Should not auto-configure all components if not all required properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, OktaAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .run((context) -> {
                    assertThat(context).doesNotHaveBean('defaultOktaOAuth2Client')
                    assertThat(context).doesNotHaveBean('defaultOktaTokenResponseHandler')
                    assertThat(context).doesNotHaveBean('defaultOktaExchange')
                })
    }

    @Test
    void "Should not auto-configure all components if no required properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, OktaAuthorizationCodeExchangeDefaultConfiguration))
                .run((context) -> {
                    assertThat(context).doesNotHaveBean('defaultOktaOAuth2Client')
                    assertThat(context).doesNotHaveBean('defaultOktaTokenResponseHandler')
                    assertThat(context).doesNotHaveBean('defaultOktaExchange')
                })
    }

    @Test
    void "Should auto-configure single-sign-on exchange client with proper properties values"() {
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def oktaDomain = 'some-okta-domain'
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, OktaAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-id=${clientId}")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-secret=${clientSecret}")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.redirect-uri=${redirectUri}")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-type=SINGLE_SIGN_ON")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.okta-domain=${oktaDomain}")
                .run((context) -> {
                    assertThat(context).hasBean('defaultOktaOAuth2Client')
                    assertThat(context).hasSingleBean(OktaOAuth2Client)
                    assertThat(context).hasSingleBean(OktaOAuth2SingleSignOnClient)

                    def exchangeClient = context.getBean('defaultOktaOAuth2Client', OktaOAuth2SingleSignOnClient)
                    assert exchangeClient.clientId == clientId
                    assert exchangeClient.clientSecret == clientSecret
                    assert exchangeClient.redirectUri == redirectUri
                    assert exchangeClient.oktaDomain == oktaDomain
                })
    }

    @Test
    void "Should auto-configure authorization server exchange client with proper properties values"() {
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def oktaDomain = 'some-okta-domain'
        def oktaAuthorizationServerId = 'some-okta-authorization-server-id'
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, OktaAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-id=${clientId}")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-secret=${clientSecret}")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.redirect-uri=${redirectUri}")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.client-type=AUTHORIZATION_SERVER")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.okta-domain=${oktaDomain}")
                .withPropertyValues("${OKTA_EXCHANGE_PREFIX}.okta-authorization-server-id=${oktaAuthorizationServerId}")
                .run((context) -> {
                    assertThat(context).hasBean('defaultOktaOAuth2Client')
                    assertThat(context).hasSingleBean(OktaOAuth2Client)
                    assertThat(context).hasSingleBean(OktaOAuth2AuthorizationServerClient)

                    def exchangeClient = context.getBean('defaultOktaOAuth2Client', OktaOAuth2AuthorizationServerClient)
                    assert exchangeClient.clientId == clientId
                    assert exchangeClient.clientSecret == clientSecret
                    assert exchangeClient.redirectUri == redirectUri
                    assert exchangeClient.oktaDomain == oktaDomain
                    assert exchangeClient.authorizationServerId == oktaAuthorizationServerId
                })
    }

}
