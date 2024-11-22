package com.codingland.apiservice.chat.service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
public class ChatService {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    public String getChatGPTResponse(String prompt) throws IOException {
        OkHttpClient client = createHttpClient();

        // JSON 요청 생성
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", model);
        jsonObject.put("messages", new JSONArray()
                .put(new JSONObject()
                        .put("role", "user")
                        .put("content", prompt)
                )
        );
        jsonObject.put("max_tokens", 100);
        jsonObject.put("temperature", 0.7);

        // RequestBody 생성
        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json")
        );

        // Request 생성
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        // 요청 실행 및 응답 처리
        return sendRequestWithRetry(request, client);
    }

    private OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
                .protocols(Collections.singletonList(Protocol.HTTP_1_1)) // HTTP/2 비활성화
                .build();
    }

    private String sendRequestWithRetry(Request request, OkHttpClient client) throws IOException {
        int attempts = 0;
        int backoff = 1000; // 초기 대기 시간 1초

        while (attempts < 5) {
            try (Response response = client.newCall(request).execute()) {
                // 응답 상태 코드 출력
                System.out.println("Response Code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    // 응답 로그 출력
                    String responseBodyString = response.body().string();
                    System.out.println("Response Body: " + responseBodyString);

                    // JSON 응답 파싱
                    JSONObject responseBody = new JSONObject(responseBodyString);
                    return responseBody
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                } else if (response.code() == 429) {
                    // 429 에러: 속도 제한 초과
                    System.out.println("Rate limit exceeded. Retrying in " + backoff + "ms...");
                    Thread.sleep(backoff);
                    backoff *= 2; // 지수 백오프
                } else {
                    // 다른 HTTP 오류 처리
                    throw new IOException("Unexpected response code: " + response.code() + " - " + response.message());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Retry interrupted", e);
            }
            attempts++;
        }
        throw new IOException("Exceeded maximum retry attempts.");
    }
}
