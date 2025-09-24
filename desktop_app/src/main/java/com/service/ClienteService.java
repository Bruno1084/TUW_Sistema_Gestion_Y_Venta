package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Cliente;
import com.model.SessionManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteService {
    private static final String BASE_URL = "http://localhost:8080/api/empleados";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Cliente createCliente(Cliente cliente) throws Exception {
        String requestBody = mapper.writeValueAsString(cliente);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/clientes"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer" + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), Cliente.class);
        } else {
            throw new RuntimeException("Error al crear cliente: " + response.body());
        }
    }

    public Cliente[] getAllCliente() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAll"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Cliente[].class);
        } else {
            throw new RuntimeException("Error al obtener clientes: " + response.body());
        }
    }

    public Cliente getClienteById(int clienteId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getOneById/" + clienteId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Cliente.class);
        } else if (response.statusCode() == 404) {
            return null;
        } else {
            throw new RuntimeException("Error al obtener cliente: " + response.body());
        }
    }

    public Cliente updateCliente(int clienteId, Cliente cliente) throws Exception {
        String requestBody = mapper.writeValueAsString(cliente);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/update/" + clienteId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Cliente.class);
        } else {
            throw new RuntimeException("Error al actualizar cliente: " + response.body());
        }
    }

    public boolean deleteCliente(int clienteId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/delete/" + clienteId))
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 204) {
            return true;
        } else if (response.statusCode() == 404) {
            return false;
        } else {
            throw new RuntimeException("Error al eliminar cliente: " + response.body());
        }
    }
}
