package com.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.AuthResponse;
import com.model.Usuario;

public class AuthService {
    private static final String BASE_URL = "http://localhost:8080/api/usuarios";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public AuthResponse login(String nombre, String contrasenia) throws Exception {
        Map<String, String> loginData = Map.of(
                "nombre", nombre,
                "contrasenia", contrasenia
        );
        String requestBody = mapper.writeValueAsString(loginData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), AuthResponse.class);
        } else {
            throw new RuntimeException("Error al iniciar sesión: " + response.body());
        }
    }

    public Usuario register(Usuario usuario) throws Exception {
        String requestBody = mapper.writeValueAsString(usuario);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), Usuario.class);
        } else {
            throw new RuntimeException("Error al registrar usuario: " + response.body());
        }
    }
}
