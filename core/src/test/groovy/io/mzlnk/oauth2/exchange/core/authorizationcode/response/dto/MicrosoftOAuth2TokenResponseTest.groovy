package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class MicrosoftOAuth2TokenResponseTest {

    @Test
    void "Should return exception when create response from null values"() {
        given:
        def factory = new MicrosoftOAuth2TokenResponse.Factory()
        def values = null as Map<String, Object>

        when:
        def exception = assertThrows(
                NullPointerException,
                () -> factory.create(values)
        )

        then:
        assert exception != null
        assert exception.message == 'Cannot create response from null values.'
    }

}
