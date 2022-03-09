package io.mzlnk.oauth2.exchange.core.authorizationcode.client

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class OktaOAuth2SingleSignOnClienTest {

    @Test
    void "Should return token URL for single-sign-on client"() {
        given:
        def oktaDomain = 'https://some.domain.com'
        def oktaClient = new OktaOAuth2SingleSignOnClient(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri',
                oktaDomain
        )

        when:
        def clientBaseUrl = oktaClient.getTokenUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == "${oktaDomain}/oauth2/v1/token"
    }

    @Test
    void "Should return exception if clientId parameter is null for single-sign-on client"() {
        given:
        def clientId = null
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def oktaDomain = 'https://some.domain.com'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new OktaOAuth2SingleSignOnClient(clientId, clientSecret, redirectUri, oktaDomain)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientId` cannot be null.'
    }

    @Test
    void "Should return exception if clientSecret parameter is null for single-sign-on client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = null
        def redirectUri = 'some-redirect-uri'
        def oktaDomain = 'https://some.domain.com'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new OktaOAuth2SingleSignOnClient(clientId, clientSecret, redirectUri, oktaDomain)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientSecret` cannot be null.'
    }

    @Test
    void "Should return exception if redirectUri parameter is null for single-sign-on client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = null
        def oktaDomain = 'https://some.domain.com'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new OktaOAuth2SingleSignOnClient(clientId, clientSecret, redirectUri, oktaDomain)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `redirectUri` cannot be null.'
    }

    @Test
    void "Should return exception if oktaDomain parameter is null for single-sign-on client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def oktaDomain = null

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new OktaOAuth2SingleSignOnClient(clientId, clientSecret, redirectUri, oktaDomain)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `oktaDomain` cannot be null.'
    }

}
