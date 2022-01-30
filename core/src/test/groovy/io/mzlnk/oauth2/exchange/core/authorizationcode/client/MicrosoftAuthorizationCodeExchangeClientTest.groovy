package io.mzlnk.oauth2.exchange.core.authorizationcode.client

import org.junit.jupiter.api.Test

class MicrosoftAuthorizationCodeExchangeClientTest {

    @Test
    void "Should return client base URL for common client"() {
        given:
        def client = new MicrosoftAuthorizationCodeExchangeClient.MicrosoftCommonClient(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri'
        )

        when:
        def clientBaseUrl = client.getClientBaseUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == 'https://login.microsoftonline.com/common'
    }

    @Test
    void "Should return client base URL for organization client"() {
        given:
        def client = new MicrosoftAuthorizationCodeExchangeClient.MicrosoftOrganizationClient(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri'
        )

        when:
        def clientBaseUrl = client.getClientBaseUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == 'https://login.microsoftonline.com/organizations'
    }

    @Test
    void "Should return client base URL for consumer client"() {
        given:
        def client = new MicrosoftAuthorizationCodeExchangeClient.MicrosoftConsumerClient(
                'some-client-id',
                'some-client-secret',
                'some-redirect-uri'
        )

        when:
        def clientBaseUrl = client.getClientBaseUrl()

        then:
        assert clientBaseUrl != null
        assert clientBaseUrl == 'https://login.microsoftonline.com/consumers'
    }

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

}
