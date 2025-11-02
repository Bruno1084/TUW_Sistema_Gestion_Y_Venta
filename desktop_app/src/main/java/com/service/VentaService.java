package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Compra;
import com.model.SessionManager;
import com.model.Venta;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class VentaService {
    private static final String BASE_URL = "http://localhost:8080/api/ventas";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private Map<String, Object> writePlaneMap(Venta venta) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("id", venta.getId());
        requestMap.put("precioTotal", venta.getPrecioTotal());
        requestMap.put("fechaCreacion", venta.getFechaCreacion());
        requestMap.put("proveedorId", venta.getCliente().getId());
        requestMap.put("usuarioId", venta.getUsuario().getId());

        return  requestMap;
    }

    public Venta createVenta(Venta venta) throws Exception {
        Map<String, Object> requestMap = writePlaneMap(venta);
        String requestBody = mapper.writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/create"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), Venta.class);
        } else {
            throw new RuntimeException("Error al crear venta: " + response.body());
        }
    }

    public Venta[] getAllVenta() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAll"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Venta[].class);
        } else {
            throw new RuntimeException("Error al obtener ventas: " + response.body());
        }
    }

    public Venta[] getAllWithDetailVenta() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAllWithDetail"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Venta[].class);
        } else {
            throw new RuntimeException("Error al obtener ventas: " + response.body());
        }
    }

    public Venta getOneByIdVenta(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getOneById/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Venta.class);
        } else if (response.statusCode() == 404) {
            return null;
        } else {
            throw new RuntimeException("Error al obtener venta: " + response.body());
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
            throw new RuntimeException("Error al obtener venta: " + response.body());
        }
    }
}
