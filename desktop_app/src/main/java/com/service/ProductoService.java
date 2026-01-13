package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Producto;
import com.model.SessionManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ProductoService {
    private static final String BASE_URL = "http://localhost:8080/api/productos";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private Map<String, Object> writePlaneMap(Producto producto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("codigoBarra", producto.getCodigoBarra());
        requestMap.put("descripcion", producto.getDescripcion());
        requestMap.put("precioCompra", producto.getPrecioCompra());
        requestMap.put("precioVenta", producto.getPrecioVenta());
        requestMap.put("stock", producto.getStock());
        requestMap.put("imgUri", producto.getImgUri());
        requestMap.put("proveedorId", producto.getProveedor().getId());
        requestMap.put("marcaId", producto.getMarca().getId());
        requestMap.put("rubroId", producto.getRubro().getId());

        return  requestMap;
    }

    public Producto create(Producto producto) throws Exception {
        System.out.println("This should print create endpoint");
        Map<String, Object> requestMap = writePlaneMap(producto);
        String requestBody = mapper.writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), Producto.class);
        } else {
            throw new RuntimeException("Error al crear producto: " + response.body());
        }
    }

    public Producto[] getAll() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Producto[].class);
        } else {
            throw new RuntimeException("Error al obtener productos: " + response.body());
        }
    }

    public Producto[] getAllWithDetail() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/detail"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Producto[].class);
        } else {
            throw new RuntimeException("Error al obtener productos: " + response.body());
        }
    }

    public Producto getOneById(String codigoBarra) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + codigoBarra))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Producto.class);
        } else if (response.statusCode() == 404) {
            return null;
        } else {
            throw new RuntimeException("Error al obtener producto: " + response.body());
        }
    }

    public Producto getOneByIdWithDetail(String codigoBarra) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/detail/" + codigoBarra))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Producto.class);
        } else if (response.statusCode() == 404) {
            return null;
        } else {
            throw new RuntimeException("Error al obtener producto: " + response.body());
        }
    }

    public Producto update(String codigoBarra, Producto producto) throws Exception{
        Map<String, Object> requestMap = writePlaneMap(producto);
        String requestBody = mapper.writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + codigoBarra))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Producto.class);
        } else {
            throw new RuntimeException("Error al actualizar producto: " + response.body());
        }
    }

    public boolean delete(String codigoBarra) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + codigoBarra))
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 204) {
            return true;
        } else if (response.statusCode() == 404) {
            return false;
        } else {
            throw new RuntimeException("Error al eliminar producto: " + response.body());
        }
    }
}
