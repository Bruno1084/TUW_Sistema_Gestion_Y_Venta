package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.CompraDetalle;
import com.model.SessionManager;
import com.model.dto.CompraDetalleDTO;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CompraDetalleService {
    private static final String BASE_URL = "http://localhost:8080/api/detalleCompras";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public CompraDetalle createCompraDetalle(CompraDetalle compraDetalle) throws Exception {
        String requestBody = mapper.writeValueAsString(compraDetalle);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/create"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), CompraDetalle.class);
        } else {
            throw new RuntimeException("Error al crear detalle de compra: " + response.body());
        }
    }

    public CompraDetalleDTO[] createManyCompraDetalle(List<CompraDetalleDTO> detalles) throws Exception {
        String requestBody = mapper.writeValueAsString(detalles);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/createMany"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), CompraDetalleDTO[].class);
        } else {
            throw new RuntimeException("Error al crear detalles de compra: " + response.body());
        }
    }

    public CompraDetalle[] getAllCompraDetalle() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAll"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), CompraDetalle[].class);
        } else {
            throw new RuntimeException("Error al obtener detalle de compra: " + response.body());
        }
    }

    public CompraDetalle[] getAllFromCompraByIdCompraDetalle(int compraId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAllFromCompraById/" + compraId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), CompraDetalle[].class);
        } else {
            throw new RuntimeException("Error al obtener detalle de compra: " + response.body());
        }
    }

    public CompraDetalle getOneByIdCompraDetalle(int compraId, String productoCodigoBarra) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getOneById/" + compraId + "/" + productoCodigoBarra))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), CompraDetalle.class);
        } else if (response.statusCode() == 404) {
            return null;
        } else {
            throw new RuntimeException("Error al obtener detalle de compra: " + response.body());
        }
    }
}
