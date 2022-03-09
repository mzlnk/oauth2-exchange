package io.mzlnk.oauth2.exchange.core.authorizationcode.client

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class MicrosoftOAuth2OrganizationsClientTest {

    @Test
    void "Should return exception if clientId parameter is null for organization client"() {
        given:
        def clientId = null
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftOAuth2OrganizationsClient(clientId, clientSecret, redirectUri)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientId` cannot be null.'
    }

    @Test
    void "Should return exception if clientSecret parameter is null for organization client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = null
        def redirectUri = 'some-redirect-uri'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftOAuth2OrganizationsClient(clientId, clientSecret, redirectUri)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientSecret` cannot be null.'
    }

    @Test
    void "Should return exception if redirectUri parameter is null for organization client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = null

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftOAuth2OrganizationsClient(clientId, clientSecret, redirectUri)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `redirectUri` cannot be null.'
    }

}
