package com.kit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OKhttpTest {


    @Before
    public void setUp() throws IOException {

        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.



//        Request request = new Request.Builder()
//                .url(baseUrl)
//                .build();
//
//        Response execute = client.newCall(request).execute();
//
//        System.out.println(execute.body().string());

    }


    @Test
    public void testHttp() throws IOException {

        MockWebServer server = new MockWebServer();

        // Schedule some responses.
        server.enqueue(new MockResponse().setBody("hello, world!"));
        server.enqueue(new MockResponse().setBody("sup, bra?"));
//        server.enqueue(new MockResponse().setBody("yo dog"));
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody("{name:\"ming\"}");
//        response.throttleBody(1024, 100, TimeUnit.SECONDS);
        response.setResponseCode(404);
//        response.setBodyDelay(10,TimeUnit.SECONDS);
        server.enqueue(response);

        server.start();
        HttpUrl baseUrl = server.url("/testUrl");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(baseUrl)
                .build();

        System.out.println(client.newCall(request).execute().body().string());
        System.out.println(client.newCall(request).execute().body().string());
        System.out.println(client.newCall(request).execute().code());

    }


}
