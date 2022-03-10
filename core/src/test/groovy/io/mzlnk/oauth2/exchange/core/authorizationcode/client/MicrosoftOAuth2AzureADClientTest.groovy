package io.mzlnk.oauth2.exchange.core.authorizationcode.client

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class MicrosoftOAuth2AzureADClientTest {

    @Test
    void "Should return token URL for AzureAD client"() {
        given:
        def azureADId = 'some-azure-ad-id'

        def client = new MicrosoftOAuth2AzureADClient(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri',
                azureADId
        )

        when:
        def clientBaseUrl = client.getTokenUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == "https://login.microsoftonline.com/${azureADId}/oauth2/v2.0/token"
    }

    @Test
    void "Should return exception if clientId parameter is null for AzureAD client"() {
        given:
        def clientId = null
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def azureADId = 'some-azure-ad-id'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftOAuth2AzureADClient(clientId, clientSecret, redirectUri, azureADId)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientId` cannot be null.'
    }

    @Test
    void "Should return exception if clientSecret parameter is null for AzureAD client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = null
        def redirectUri = 'some-redirect-uri'
        def azureADId = 'some-azure-ad-id'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftOAuth2AzureADClient(clientId, clientSecret, redirectUri, azureADId)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientSecret` cannot be null.'
    }

    @Test
    void "Should return exception if redirectUri parameter is null for AzureAD client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = null
        def azureADId = 'some-azure-ad-id'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftOAuth2AzureADClient(clientId, clientSecret, redirectUri, azureADId)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `redirectUri` cannot be null.'
    }

    @Test
    void "Should return exception if azureADId parameter is null for AzureAD client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'
        def azureADId = null

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftOAuth2AzureADClient(clientId, clientSecret, redirectUri, azureADId)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `azureADId` cannot be null.'
    }

}
