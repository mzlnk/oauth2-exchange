package io.mzlnk.oauth2.exchange.core.utils

import com.google.common.io.Resources
import io.mzlnk.oauth2.exchange.core.utils.http.HttpResponse
import okhttp3.Protocol

import java.nio.charset.StandardCharsets

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class TestUtils {

    static def loadResourceAsString(String resource) {
        return Resources.toString(Resources.getResource(resource), StandardCharsets.UTF_8)
    }

    static def defaultSuccessHttpResponse() {
        def response = mock HttpResponse
        when(response.getMediaType()).thenReturn('application/json')
        when(response.getResponseCode()).thenReturn(200)
        when(response.getMessage()).thenReturn('OK')
        when(response.getProtocol()).thenReturn(Protocol.HTTP_2)
        when(response.getResponseBody()).thenReturn(null)

        return response
    }

}