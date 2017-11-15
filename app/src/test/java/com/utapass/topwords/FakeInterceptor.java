package com.utapass.topwords;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by vanessatsai on 2017/11/9.
 */

public class FakeInterceptor implements Interceptor {

    private static final MediaType MEDIA_JSON = MediaType.parse("application/json");
    private final int fakeCode;
    private final String fakeMessage;
    private HttpLoggingInterceptor.Level level;

    public FakeInterceptor(int i, String s) {
        this.fakeCode = i;
        this.fakeMessage = s;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = new Response.Builder()
        /* Return "hello" to the api call */
                .body(ResponseBody.create(MEDIA_JSON, ""))
        /* Additional methods to satisfy OkHttp */
                .request(chain.request())
                .protocol(Protocol.HTTP_2)
                .code(fakeCode)
                .message(fakeMessage)
                .build();
        return response;
    }

    public void setLevel(HttpLoggingInterceptor.Level level) {
        this.level = level;
    }
}
