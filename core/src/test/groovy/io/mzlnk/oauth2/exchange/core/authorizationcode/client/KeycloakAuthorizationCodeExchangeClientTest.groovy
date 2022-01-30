package io.mzlnk.oauth2.exchange.core.authorizationcode.client

import org.junit.jupiter.api.Test

class KeycloakAuthorizationCodeExchangeClientTest {

    @Test
    void "Should return client base URL for host and realm"() {
        given:
        def host = 'https://www.some.domain.com'
        def realm = 'some-realm'

        def client = new KeycloakAuthorizationCodeExchangeClient(
                'some-client',
                'some-client-secret',
                'some-redirect-uri',
                host,
                realm
        )

        when:
        def clientBaseUrl = client.getClientBaseUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == "${host}/auth/realms/${realm}"
    }

}
