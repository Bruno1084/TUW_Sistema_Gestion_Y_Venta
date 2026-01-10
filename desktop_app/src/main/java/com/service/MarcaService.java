package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Marca;
import com.model.SessionManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MarcaService {
    private static final String BASE_URL = "http://localhost:8080/api/marcas";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Marca create(Marca marca) throws Exception {
        String requestBody = mapper.writeValueAsString(marca);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), Marca.class);
        } else {
            throw new RuntimeException("Error al crear Marca: " + response.body());
        }
    }

    public Marca[] getAll() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Marca[].class);
        } else {
            throw new RuntimeException("Error al obtener marcas: " + response.body());
        }
    }

    public Marca getOneById(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Marca.class);
        } else if (response.statusCode() == 404){
            return null;
        } else {
            throw new RuntimeException("Error al obtener marca: " + response.body());
        }
    }

    public Marca update(int id, Marca marca) throws Exception {
        String requestBody = mapper.writeValueAsString(marca);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Marca.class);
        } else {
            throw new RuntimeException("Error al actualizar marca: " + response.body());
        }
    }

    public boolean delete(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 204) {
            return true;
        } else if (response.statusCode() == 404) {
            return false;
        } else {
            throw new RuntimeException("Error al eliminar marca: " + response.body());
        }
    }
}
