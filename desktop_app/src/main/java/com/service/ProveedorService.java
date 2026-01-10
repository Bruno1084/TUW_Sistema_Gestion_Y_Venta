package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Proveedor;
import com.model.SessionManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProveedorService {
    private static final String BASE_URL = "http://localhost:8080/api/proveedores";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Proveedor create(Proveedor proveedor) throws Exception {
        String requestBody = mapper.writeValueAsString(proveedor);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), Proveedor.class);
        } else {
            throw new RuntimeException("Error al crear proveedor: " + response.body());
        }
    }

    public Proveedor[] getAll() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Proveedor[].class);
        } else {
            throw new RuntimeException("Error al obtener empleados: " + response.body());
        }
    }

    public Proveedor getOneById(int proveedorId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + proveedorId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Proveedor.class);
        } else if (response.statusCode() == 404) {
            return null;
        } else {
            throw new RuntimeException("Error al obtener proveedor: " + response.body());
        }
    }

    public Proveedor update(int proveedorId, Proveedor proveedor) throws Exception {
        String requestBody = mapper.writeValueAsString(proveedor);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + proveedorId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Proveedor.class);
        } else {
            throw new RuntimeException("Error al actualizar proveedor: " + response.body());
        }
    }

    public boolean delete(int proveedorId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + proveedorId))
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 204) {
            return true;
        } else if (response.statusCode() == 404) {
            return false;
        } else {
            throw new RuntimeException("Error al eliminar proveedor: " + response.body());
        }
    }
}
