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

    public Producto createProducto(Producto producto) throws Exception {
        Map<String, Object> requestMap = writePlaneMap(producto);
        String requestBody = mapper.writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/create"))
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

    public Producto[] getAllProducto() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAll"))
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

    public Producto[] getAllWithDetailProducto() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getAllWithDetail"))
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

    public Producto getProductoById(String codigoBarra) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getOneById/" + codigoBarra))
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

    public Producto getOneByIdWithDetailProducto(String codigoBarra) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/getOneByIdWithDetail/" + codigoBarra))
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

    public Producto updateProducto(String codigoBarra, Producto producto) throws Exception{
        Map<String, Object> requestMap = writePlaneMap(producto);
        String requestBody = mapper.writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/update/" + codigoBarra))
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

    public boolean deleteProducto(String codigoBarra) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/delete/" + codigoBarra))
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
