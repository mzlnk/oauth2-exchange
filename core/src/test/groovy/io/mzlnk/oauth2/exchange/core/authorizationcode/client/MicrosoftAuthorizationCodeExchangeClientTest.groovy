package io.mzlnk.oauth2.exchange.core.authorizationcode.client

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class MicrosoftAuthorizationCodeExchangeClientTest {

    @Test
    void "Should return client base URL for AzureAD client"() {
        given:
        def azureADId = 'some-azure-ad-id'

        def client = new MicrosoftAuthorizationCodeExchangeClient.MicrosoftAzureADClient(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri',
                azureADId
        )

        when:
        def clientBaseUrl = client.getClientBaseUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == "https://login.microsoftonline.com/${azureADId}"
    }

    @Test
    void "Should return exception if clientId parameter is null for common client"() {
        given:
        def clientId = null
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftCommonClient(clientId, clientSecret, redirectUri)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientId` cannot be null.'
    }

    @Test
    void "Should return exception if clientSecret parameter is null for common client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = null
        def redirectUri = 'some-redirect-uri'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftCommonClient(clientId, clientSecret, redirectUri)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientSecret` cannot be null.'
    }

    @Test
    void "Should return exception if redirectUri parameter is null for common client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = null

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftCommonClient(clientId, clientSecret, redirectUri)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `redirectUri` cannot be null.'
    }

    @Test
    void "Should return exception if clientId parameter is null for organization client"() {
        given:
        def clientId = null
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftOrganizationClient(clientId, clientSecret, redirectUri)
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
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftOrganizationClient(clientId, clientSecret, redirectUri)
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
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftOrganizationClient(clientId, clientSecret, redirectUri)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `redirectUri` cannot be null.'
    }

    @Test
    void "Should return exception if clientId parameter is null for consumer client"() {
        given:
        def clientId = null
        def clientSecret = 'some-client-secret'
        def redirectUri = 'some-redirect-uri'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftConsumerClient(clientId, clientSecret, redirectUri)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientId` cannot be null.'
    }

    @Test
    void "Should return exception if clientSecret parameter is null for consumer client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = null
        def redirectUri = 'some-redirect-uri'

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftConsumerClient(clientId, clientSecret, redirectUri)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `clientSecret` cannot be null.'
    }

    @Test
    void "Should return exception if redirectUri parameter is null for consumer client"() {
        given:
        def clientId = 'some-client-id'
        def clientSecret = 'some-client-secret'
        def redirectUri = null

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftConsumerClient(clientId, clientSecret, redirectUri)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `redirectUri` cannot be null.'
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
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftAzureADClient(clientId, clientSecret, redirectUri, azureADId)
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
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftAzureADClient(clientId, clientSecret, redirectUri, azureADId)
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
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftAzureADClient(clientId, clientSecret, redirectUri, azureADId)
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
                () -> new MicrosoftAuthorizationCodeExchangeClient.MicrosoftAzureADClient(clientId, clientSecret, redirectUri, azureADId)
        )

        then:
        assert exception != null
        assert exception.message == 'Parameter `azureADId` cannot be null.'
    }

}
