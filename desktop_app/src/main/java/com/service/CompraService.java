package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Compra;
import com.model.SessionManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class CompraService {
    private static final String BASE_URL = "http://localhost:8080/api/compras";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private Map<String, Object> writePlaneMap(Compra compra) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("id", compra.getId());
        requestMap.put("precioTotal", compra.getPrecioTotal());
        requestMap.put("fechaCreacion", compra.getFechaCreacion());
        requestMap.put("proveedorId", compra.getProveedor().getId());
        requestMap.put("usuarioId", compra.getUsuario().getId());

        return  requestMap;
    }

    public Compra createCompra(Compra compra) throws Exception {
        Map<String, Object> requestMap = writePlaneMap(compra);
        String requestBody = mapper.writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/create"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), Compra.class);
        } else {
            throw new RuntimeException("Error al crear compra: " + response.body());
        }
    }

    public Compra[] getAllCompra() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAll"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Compra[].class);
        } else {
            throw new RuntimeException("Error al obtener compras: " + response.body());
        }
    }

    public Compra[] getAllWithDetailCompra() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAllWithDetail"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Compra[].class);
        } else {
            throw new RuntimeException("Error al obtener compras: " + response.body());
        }
    }

    public Compra getOneByIdCompra(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getOneById/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Compra.class);
        } else if (response.statusCode() == 404) {
            return null;
        } else {
            throw new RuntimeException("Error al obtener compra: " + response.body());
        }
    }

    public Compra getOneByIdWithDetailCompra(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getOneByIdWithDetail/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Compra.class);
        } else if (response.statusCode() == 404) {
            return null;
        } else {
            throw new RuntimeException("Error al obtener compra: " + response.body());
        }
    }
}
