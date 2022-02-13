package io.mzlnk.oauth2.exchange.core.authorizationcode.client

import org.junit.jupiter.api.Test

class OktaAuthorizationCodeExchangeClientTest {

    @Test
    void "Should return client baseURL for single-sign-on client"() {
        given:
        def oktaDomain = 'https://some.domain.com'
        def oktaClient = new OktaAuthorizationCodeExchangeClient.OktaSingleSignOnClient(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri',
                oktaDomain
        )

        when:
        def clientBaseUrl = oktaClient.getClientBaseUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == "${oktaDomain}/oauth2"
    }

    @Test
    void "Should return client baseURL for authorization server client"() {
        given:
        def oktaDomain = 'https://some.domain.com'
        def oktaAuthorizationServerId = 'some-authorization-server-id'

        def oktaClient = new OktaAuthorizationCodeExchangeClient.OktaAuthorizationServerClient(
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

}
