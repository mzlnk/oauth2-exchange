package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class OktaAuthorizationCodeExchangeResponseTest {

    @Test
    void "Should return exception when create response from null values"() {
        given:
        def values = null as Map<String, Object>

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> OktaOAuth2TokenResponse.from(values)
        )

        then:
        assert exception != null
        assert exception.message == 'Cannot create response from null values.'
    }

}
