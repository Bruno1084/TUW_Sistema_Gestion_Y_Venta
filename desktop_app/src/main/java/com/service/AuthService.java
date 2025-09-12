package com.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthService {
    private HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper mapper = new ObjectMapper();

    public String login(String nombre, String contrasenia) throws Exception {
        Map<String, String> data = Map.of(
                "nombre", nombre,
                "contrasenia", contrasenia
        );

        String requestBody = mapper.writeValueAsString(data);

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
