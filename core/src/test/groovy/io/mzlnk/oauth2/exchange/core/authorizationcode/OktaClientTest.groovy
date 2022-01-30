package io.mzlnk.oauth2.exchange.core.authorizationcode

import org.junit.jupiter.api.Test

class OktaClientTest {

    @Test
    void "Should return client baseURL for single-sign-on client"() {
        given:
        def oktaDomain = 'some.domain.com'
        def oktaClient = new OktaClient.OktaSingleSignOnClient(oktaDomain)

        when:
        def clientBaseUrl = oktaClient.getClientBaseUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == "https://${oktaDomain}/oauth2"
    }

    @Test
    void "Should return client baseURL for authorization server client"() {
        given:
        def oktaDomain = 'some.domain.com'
        def oktaAuthorizationServerId = 'some-authorization-server-id'

        def oktaClient = new OktaClient.OktaAuthorizationServerClient(oktaDomain, oktaAuthorizationServerId)

        when:
        def clientBaseUrl = oktaClient.getClientBaseUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == "https://${oktaDomain}/oauth2/${oktaAuthorizationServerId}"
    }

}
