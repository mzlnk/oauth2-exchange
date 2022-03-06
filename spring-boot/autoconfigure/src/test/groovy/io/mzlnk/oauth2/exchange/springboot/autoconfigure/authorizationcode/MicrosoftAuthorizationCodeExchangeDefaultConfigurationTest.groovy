package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode

import io.mzlnk.oauth2.exchange.core.authorizationcode.client.MicrosoftAuthorizationCodeExchangeAzureADClient
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.MicrosoftAuthorizationCodeExchangeClient
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.MicrosoftAuthorizationCodeExchangeCommonClient
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.MicrosoftAuthorizationCodeExchangeConsumerClient
import io.mzlnk.oauth2.exchange.core.authorizationcode.client.MicrosoftAuthorizationCodeExchangeOrganizationsClient
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.OAuth2ExchangeCoreAutoConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
import org.springframework.boot.logging.LogLevel
import org.springframework.boot.test.context.runner.ApplicationContextRunner

import static org.assertj.core.api.Assertions.assertThat

class MicrosoftAuthorizationCodeExchangeDefaultConfigurationTest {

    private static final String MICROSOFT_EXCHANGE_PREFIX = 'oauth2.exchange.providers.microsoft'

    private ApplicationContextRunner contextRunner

    @BeforeEach
    void "Set up tests"() {
        def initializer = new ConditionEvaluationReportLoggingListener(LogLevel.INFO);

        this.contextRunner = new ApplicationContextRunner()
                .withInitializer(initializer)
    }

    @Test
    void "Should application context not fail to start if all properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-type=COMMON")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.scope=some-scope")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.code-verifier=some-code-verifier")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-assertion-type=some-client-assertion-type")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-assertion=some-client-assertion")
                .run((context) -> {
                    assertThat(context).hasNotFailed()
                })
    }

    @Test
    void "Should application context not fail to start if only required properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-type=COMMON")
                .run((context) -> {
                    assertThat(context).hasNotFailed()
                })
    }

    @Test
    void "Should application context not fail to start if required properties are not present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .run((context) -> {
                    assertThat(context).hasNotFailed()
                })
    }

    @Test
    void "Should application context fail to start if AzureAD client selected but no azure-ad-id provided via properties"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-type=AZURE")
                .run((context) -> {
                    assertThat(context).hasFailed()
                })
    }

    @Test
    void "Should auto-configure all components if all properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-type=COMMON")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.scope=some-scope")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.code-verifier=some-code-verifier")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-assertion-type=some-client-assertion-type")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-assertion=some-client-assertion")
                .run((context) -> {
                    assertThat(context).hasBean('defaultMicrosoftExchangeClient')
                    assertThat(context).hasBean('defaultMicrosoftResponseHandler')
                    assertThat(context).hasBean('defaultMicrosoftExchange')
                })
    }

    @Test
    void "Should auto-configure all components if only required properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-type=COMMON")
                .run((context) -> {
                    assertThat(context).hasBean('defaultMicrosoftExchangeClient')
                    assertThat(context).hasBean('defaultMicrosoftResponseHandler')
                    assertThat(context).hasBean('defaultMicrosoftExchange')
                })
    }

    @Test
    void "Should not auto-configure all components if not all required properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .run((context) -> {
                    assertThat(context).doesNotHaveBean('defaultMicrosoftExchangeClient')
                    assertThat(context).doesNotHaveBean('defaultMicrosoftResponseHandler')
                    assertThat(context).doesNotHaveBean('defaultMicrosoftExchange')
                })
    }

    @Test
    void "Should not auto-configure all components if no properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .run((context) -> {
                    assertThat(context).doesNotHaveBean('defaultMicrosoftExchangeClient')
                    assertThat(context).doesNotHaveBean('defaultMicrosoftResponseHandler')
                    assertThat(context).doesNotHaveBean('defaultMicrosoftExchange')
                })
    }

    @Test
    void "Should auto-configure common exchange client with proper properties values"() {
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'

        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-id=${clientId}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-secret=${clientSecret}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.redirect-uri=${redirectUri}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-type=COMMON")
                .run((context) -> {
                    assertThat(context).hasBean('defaultMicrosoftExchangeClient')
                    assertThat(context).hasSingleBean(MicrosoftAuthorizationCodeExchangeClient)
                    assertThat(context).hasSingleBean(MicrosoftAuthorizationCodeExchangeCommonClient)

                    def exchangeClient = context.getBean('defaultMicrosoftExchangeClient', MicrosoftAuthorizationCodeExchangeCommonClient)
                    assert exchangeClient.clientId == clientId
                    assert exchangeClient.clientSecret == clientSecret
                    assert exchangeClient.redirectUri == redirectUri
                })
    }

    @Test
    void "Should auto-configure consumer exchange client with proper properties values"() {
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'

        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-id=${clientId}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-secret=${clientSecret}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.redirect-uri=${redirectUri}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-type=CONSUMER")
                .run((context) -> {
                    assertThat(context).hasBean('defaultMicrosoftExchangeClient')
                    assertThat(context).hasSingleBean(MicrosoftAuthorizationCodeExchangeClient)
                    assertThat(context).hasSingleBean(MicrosoftAuthorizationCodeExchangeConsumerClient)

                    def exchangeClient = context.getBean('defaultMicrosoftExchangeClient', MicrosoftAuthorizationCodeExchangeConsumerClient)
                    assert exchangeClient.clientId == clientId
                    assert exchangeClient.clientSecret == clientSecret
                    assert exchangeClient.redirectUri == redirectUri
                })
    }

    @Test
    void "Should auto-configure organization exchange client with proper properties values"() {
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'

        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-id=${clientId}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-secret=${clientSecret}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.redirect-uri=${redirectUri}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-type=ORGANIZATION")
                .run((context) -> {
                    assertThat(context).hasBean('defaultMicrosoftExchangeClient')
                    assertThat(context).hasSingleBean(MicrosoftAuthorizationCodeExchangeClient)
                    assertThat(context).hasSingleBean(MicrosoftAuthorizationCodeExchangeOrganizationsClient)

                    def exchangeClient = context.getBean('defaultMicrosoftExchangeClient', MicrosoftAuthorizationCodeExchangeOrganizationsClient)
                    assert exchangeClient.clientId == clientId
                    assert exchangeClient.clientSecret == clientSecret
                    assert exchangeClient.redirectUri == redirectUri
                })
    }

    @Test
    void "Should auto-configure Azure exchange client with proper properties values"() {
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def azureADId = 'some-azure-ad-id'

        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, MicrosoftAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-id=${clientId}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-secret=${clientSecret}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.redirect-uri=${redirectUri}")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.client-type=AZURE")
                .withPropertyValues("${MICROSOFT_EXCHANGE_PREFIX}.azure-ad-id=${azureADId}")
                .run((context) -> {
                    assertThat(context).hasBean('defaultMicrosoftExchangeClient')
                    assertThat(context).hasSingleBean(MicrosoftAuthorizationCodeExchangeClient)
                    assertThat(context).hasSingleBean(MicrosoftAuthorizationCodeExchangeAzureADClient)

                    def exchangeClient = context.getBean('defaultMicrosoftExchangeClient', MicrosoftAuthorizationCodeExchangeAzureADClient)
                    assert exchangeClient.clientId == clientId
                    assert exchangeClient.clientSecret == clientSecret
                    assert exchangeClient.redirectUri == redirectUri
                    assert exchangeClient.azureADId == azureADId
                })
    }

}
