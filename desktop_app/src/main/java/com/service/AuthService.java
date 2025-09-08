package com.service;

import com.model.Cajero;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthService {
    private HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper mapper = new ObjectMapper();

    public String login(Cajero cajero) throws Exception {
        String requestBody = mapper.writeValueAsString(cajero);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Extraer el token del JSON
            return mapper.readTree(response.body()).get("token").asText();
        } else {
            throw new RuntimeException("Login fallido: " + response.body());
        }
    }

}
