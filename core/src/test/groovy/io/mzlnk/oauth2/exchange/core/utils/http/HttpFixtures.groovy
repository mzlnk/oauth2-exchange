package io.mzlnk.oauth2.exchange.core.utils.http

import okhttp3.*

class HttpFixtures {

    private static final Request MOCK_REQUEST = new Request(
            HttpUrl.parse('http://some.domain.com'),
            '',
            Headers.of(),
            RequestBody.create([] as byte[]),
            [:]
    )

    static def jsonResponseBody(String json) {
        return ResponseBody.create(json, MediaType.parse('application/json'))
    }

    static def defaultSuccessHttpResponse(@DelegatesTo(Response.Builder) Closure customizer = {}) {
        return new Response.Builder()
                .protocol(Protocol.HTTP_2)
                .message('OK')
                .code(200)
                .request(MOCK_REQUEST)
                .tap(customizer)
                .build()
    }


}
