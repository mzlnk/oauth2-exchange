package io.mzlnk.oauth2.exchange.core.authorizationcode.client

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class KeycloakOAuth2ClientTest {

    @Test
    void "Should return client base URL for host and realm"() {
        given:
        def host = 'https://www.some.domain.com'
        def realm = 'some-realm'

        def client = new KeycloakOAuth2Client(
                'some-client',
                'some-client-secret',
                'some-redirect-uri',
                host,
                realm
        )

        when:
        def clientBaseUrl = client.getTokenUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == "${host}/auth/realms/${realm}"
    }

    @Test
    void "Should return exception if clientId parameter is null"() {
        given:
        def clientId = null
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def host = 'https://www.some.domain.com'
        def realm = 'some-realm'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new KeycloakOAuth2Client(clientId, clientSecret, redirectUri, host, realm)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientId` cannot be null.'
    }

    @Test
    void "Should return exception if clientSecret parameter is null"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = null
        def redirectUri = 'some-redirect-uri'
        def host = 'https://www.some.domain.com'
        def realm = 'some-realm'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new KeycloakOAuth2Client(clientId, clientSecret, redirectUri, host, realm)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientSecret` cannot be null.'
    }

    @Test
    void "Should return exception if redirectUri parameter is null"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = null
        def host = 'https://www.some.domain.com'
        def realm = 'some-realm'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new KeycloakOAuth2Client(clientId, clientSecret, redirectUri, host, realm)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `redirectUri` cannot be null.'
    }

    @Test
    void "Should return exception if host parameter is null"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def host = null
        def realm = 'some-realm'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new KeycloakOAuth2Client(clientId, clientSecret, redirectUri, host, realm)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `host` cannot be null.'
    }

    @Test
    void "Should return exception if realm parameter is null"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def host = 'https://www.some.domain.com'
        def realm = null

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new KeycloakOAuth2Client(clientId, clientSecret, redirectUri, host, realm)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `realm` cannot be null.'
    }

}
