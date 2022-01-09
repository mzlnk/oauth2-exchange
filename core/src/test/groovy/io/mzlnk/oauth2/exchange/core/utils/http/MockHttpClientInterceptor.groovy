package io.mzlnk.oauth2.exchange.core.utils.http

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import org.jetbrains.annotations.NotNull

class MockHttpClientInterceptor implements Interceptor {

    private HttpResponse response

    @Override
    Response intercept(@NotNull Chain chain) throws IOException {
        def request = chain.request();

        def responseBody = ResponseBody.create(
                this.response.getResponseBody(),
                MediaType.parse(this.response.getMediaType())
        )

        return new Response.Builder()
                .protocol(this.response.getProtocol())
                .request(request)
                .body(responseBody)
                .message(this.response.getMessage())
                .code(this.response.getResponseCode())
                .build()
    }

}
