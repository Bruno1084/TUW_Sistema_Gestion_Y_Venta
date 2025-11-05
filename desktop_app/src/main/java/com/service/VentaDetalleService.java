package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.SessionManager;
import com.model.VentaDetalle;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentaDetalleService {
    private static final String BASE_URL = "http://localhost:8080/api/detalleVentas";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public VentaDetalle createVentaDetalle(VentaDetalle ventaDetalle) throws Exception {
        String requestBody = mapper.writeValueAsString(ventaDetalle);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/create"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), VentaDetalle.class);
        } else {
            throw new RuntimeException("Error al crear detalle de venta: " + response.body());
        }
    }

    public VentaDetalle[] createManyVentaDetalle(List<VentaDetalle> detalles) throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("detalles", detalles);

        String requestBody = mapper.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/createMany"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), VentaDetalle[].class);
        } else {
            throw new RuntimeException("Error al crear detalles de venta: " + response.body());
        }
    }

    public VentaDetalle[] getAllVentaDetalle() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAll"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), VentaDetalle[].class);
        } else {
            throw new RuntimeException("Error al obtener detalle de venta: " + response.body());
        }
    }

    public VentaDetalle[] getAllFromVentaByIdVentaDetalle(int ventaId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAllFromVentaById/" + ventaId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), VentaDetalle[].class);
        } else {
            throw new RuntimeException("Error al obtener detalle de venta: " + response.body());
        }
    }

    public VentaDetalle getOneByIdVentaDetalle(int ventaId, String productoCodigoBarra) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getOneById/" + ventaId + "/" + productoCodigoBarra))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), VentaDetalle.class);
        } else if (response.statusCode() == 404) {
            return null;
        } else {
            throw new RuntimeException("Error al obtener detalle de venta: " + response.body());
        }
    }
}
