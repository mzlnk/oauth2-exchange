package io.mzlnk.oauth2.exchange.core.authorizationcode.client

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class OktaAuthorizationCodeExchangeAuthorizationServerClientTest {

    @Test
    void "Should return client baseURL for authorization server client"() {
        given:
        def oktaDomain = 'https://some.domain.com'
        def oktaAuthorizationServerId = 'some-authorization-server-id'

        def oktaClient = new OktaAuthorizationCodeExchangeAuthorizationServerClient(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri',
                oktaDomain,
                oktaAuthorizationServerId
        )

        when:
        def clientBaseUrl = oktaClient.getClientBaseUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == "${oktaDomain}/oauth2/${oktaAuthorizationServerId}"
    }

    @Test
    void "Should return exception if clientId parameter is null for authorization server client"() {
        given:
        def clientId = null
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def oktaDomain = 'https://some.domain.com'
        def oktaAuthorizationServerId = 'some-authorization-server-id'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new OktaAuthorizationCodeExchangeAuthorizationServerClient(
                        clientId,
                        clientSecret,
                        redirectUri,
                        oktaDomain,
                        oktaAuthorizationServerId)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientId` cannot be null.'
    }

    @Test
    void "Should return exception if clientSecret parameter is null for authorization server client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = null
        def redirectUri = 'some-redirect-uri'
        def oktaDomain = 'https://some.domain.com'
        def oktaAuthorizationServerId = 'some-authorization-server-id'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new OktaAuthorizationCodeExchangeAuthorizationServerClient(
                        clientId,
                        clientSecret,
                        redirectUri,
                        oktaDomain,
                        oktaAuthorizationServerId)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientSecret` cannot be null.'
    }

    @Test
    void "Should return exception if redirectUri parameter is null for authorization server client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = null
        def oktaDomain = 'https://some.domain.com'
        def oktaAuthorizationServerId = 'some-authorization-server-id'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new OktaAuthorizationCodeExchangeAuthorizationServerClient(
                        clientId,
                        clientSecret,
                        redirectUri,
                        oktaDomain,
                        oktaAuthorizationServerId)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `redirectUri` cannot be null.'
    }

    @Test
    void "Should return exception if oktaDomain parameter is null for authorization server client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def oktaDomain = null
        def oktaAuthorizationServerId = 'some-authorization-server-id'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new OktaAuthorizationCodeExchangeAuthorizationServerClient(
                        clientId,
                        clientSecret,
                        redirectUri,
                        oktaDomain,
                        oktaAuthorizationServerId)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `oktaDomain` cannot be null.'
    }

    @Test
    void "Should return exception if oktaAuthorizationServerId parameter is null for authorization server client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def oktaDomain = 'https://some.domain.com'
        def oktaAuthorizationServerId = null

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new OktaAuthorizationCodeExchangeAuthorizationServerClient(
                        clientId,
                        clientSecret,
                        redirectUri,
                        oktaDomain,
                        oktaAuthorizationServerId)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `oktaAuthorizationServerId` cannot be null.'
    }

}
