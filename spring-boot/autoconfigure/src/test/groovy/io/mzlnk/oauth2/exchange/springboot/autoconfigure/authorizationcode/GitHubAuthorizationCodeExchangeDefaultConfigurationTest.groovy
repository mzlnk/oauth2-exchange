package io.mzlnk.oauth2.exchange.springboot.autoconfigure.authorizationcode

import io.mzlnk.oauth2.exchange.core.authorizationcode.client.GitHubOAuth2Client
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.OAuth2ExchangeCoreAutoConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
import org.springframework.boot.logging.LogLevel
import org.springframework.boot.test.context.runner.ApplicationContextRunner

import static org.assertj.core.api.Assertions.assertThat

class GitHubAuthorizationCodeExchangeDefaultConfigurationTest {

    private static final String GITHUB_EXCHANGE_PREFIX = 'oauth2.exchange.providers.github'

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
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, GitHubAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${GITHUB_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${GITHUB_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${GITHUB_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .run((context) -> {
                    assertThat(context).hasNotFailed()
                })
    }

    @Test
    void "Should application context not fail to start if required properties are not present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, GitHubAuthorizationCodeExchangeDefaultConfiguration))
                .run((context) -> {
                    assertThat(context).hasNotFailed()
                })
    }

    @Test
    void "Should auto-configure all components if properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, GitHubAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${GITHUB_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${GITHUB_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .withPropertyValues("${GITHUB_EXCHANGE_PREFIX}.redirect-uri=some-redirect-uri")
                .run((context) -> {
                    assertThat(context).hasBean('defaultGitHubOAuth2Client')
                    assertThat(context).hasBean('defaultGitHubTokenResponseHandler')
                    assertThat(context).hasBean('defaultGitHubExchange')
                })
    }

    @Test
    void "Should not auto-configure all components if no properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, GitHubAuthorizationCodeExchangeDefaultConfiguration))
                .run((context) -> {
                    assertThat(context).doesNotHaveBean('defaultGitHubOAuth2Client')
                    assertThat(context).doesNotHaveBean('defaultGitHubTokenResponseHandler')
                    assertThat(context).doesNotHaveBean('defaultGitHubExchange')
                })
    }

    @Test
    void "Should not auto-configure all components if not all properties are present"() {
        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, GitHubAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${GITHUB_EXCHANGE_PREFIX}.client-id=some-client-id")
                .withPropertyValues("${GITHUB_EXCHANGE_PREFIX}.client-secret=some-client-secret")
                .run((context) -> {
                    assertThat(context).doesNotHaveBean('defaultGitHubOAuth2Client')
                    assertThat(context).doesNotHaveBean('defaultGitHubTokenResponseHandler')
                    assertThat(context).doesNotHaveBean('defaultGitHubExchange')
                })
    }

    @Test
    void "Should auto-configure exchange client with proper properties values"() {
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'

        this.contextRunner
                .withConfiguration(AutoConfigurations.of(OAuth2ExchangeCoreAutoConfiguration, GitHubAuthorizationCodeExchangeDefaultConfiguration))
                .withPropertyValues("${GITHUB_EXCHANGE_PREFIX}.client-id=${clientId}")
                .withPropertyValues("${GITHUB_EXCHANGE_PREFIX}.client-secret=${clientSecret}")
                .withPropertyValues("${GITHUB_EXCHANGE_PREFIX}.redirect-uri=${redirectUri}")
                .run((context) -> {
                    assertThat(context).hasBean('defaultGitHubOAuth2Client')

                    def exchangeClient = context.getBean('defaultGitHubOAuth2Client', GitHubOAuth2Client)
                    assert exchangeClient.clientId == clientId
                    assert exchangeClient.clientSecret == clientSecret
                    assert exchangeClient.redirectUri == redirectUri
                })
    }

}
