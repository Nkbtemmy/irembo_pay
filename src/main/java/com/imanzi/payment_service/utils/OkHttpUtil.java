package com.imanzi.payment_service.utils;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OkHttpUtil {
    private final OkHttpClient client = new OkHttpClient();

    public Response sendRequest(String url, String method, String secretKey, RequestBody body) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("irembopay-secretKey", secretKey)
                .addHeader("User-Agent", "MyAppName");

        switch (method.toUpperCase()) {
            case "POST":
                builder.post(body);
                break;
            case "PUT":
                builder.put(body);
                break;
            case "GET":
                builder.get();
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        return client.newCall(builder.build()).execute();
    }

    public Response sendRequest(String url, String method, String secretKey) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("irembopay-secretKey", secretKey)
                .addHeader("User-Agent", "MyAppName");

        if (method.equalsIgnoreCase("GET")) {
            builder.get();
        } else {
            throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        return client.newCall(builder.build()).execute();
    }
}