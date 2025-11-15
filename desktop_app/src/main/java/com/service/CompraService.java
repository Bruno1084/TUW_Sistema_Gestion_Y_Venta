package com.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.*;
import com.model.dto.CompraDetailResponseDTO;
import com.model.dto.CompraDetalleDTO;
import com.model.dto.CompraDetalleResponseDTO;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public CompraDetailResponseDTO create(Compra compra, List<CompraDetalleDTO> detalles) throws Exception {
        Map<String, Object> requestMap = writePlaneMap(compra);
        requestMap.put("detalles", detalles);

        String requestBody = mapper.writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return mapper.readValue(response.body(), CompraDetailResponseDTO.class);
        } else {
            throw new RuntimeException("Error al crear compra: " + response.body());
        }
    }

    public Compra[] getAll() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
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

    public CompraDetailResponseDTO getOneById(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode root = mapper.readTree(response.body());
            return parseCompraDetail(root);
        } else if (response.statusCode() == 404) {
            return null;
        } else {
            throw new RuntimeException("Error al obtener compra: " + response.body());
        }
    }

    private CompraDetailResponseDTO parseCompraDetail(JsonNode root) {
        CompraDetailResponseDTO compra = new CompraDetailResponseDTO();

        compra.setId(root.get("id").asInt());
        compra.setPrecioTotal((float) root.get("precioTotal").asDouble());
        compra.setFechaCreacion(root.get("fechaCreacion").asText());

        JsonNode provNode = root.get("proveedor");
        Proveedor prov = new Proveedor();
        prov.setId(provNode.get("id").asInt());
        prov.setNombre(provNode.get("nombre").asText());
        compra.setProveedor(prov);

        JsonNode userNode = root.get("usuario");
        Usuario usuario = new Usuario();
        usuario.setId(userNode.get("id").asInt());
        usuario.setNombre(userNode.get("nombre").asText());
        compra.setUsuario(usuario);

        List<CompraDetalleResponseDTO> detalles = new ArrayList<>();
        for (JsonNode detNode : root.get("detalles")) {
            detalles.add(parseDetalle(detNode));
        }
        compra.setDetalles(detalles);

        return compra;
    }

    private CompraDetalleResponseDTO parseDetalle(JsonNode detNode) {
        CompraDetalleResponseDTO dto = new CompraDetalleResponseDTO();

        dto.setCantidad(detNode.get("cantidad").asInt());
        dto.setPrecioUnitario((float) detNode.get("precioUnitario").asDouble());
        dto.setPrecioTotal((float) detNode.get("precioTotal").asDouble());

        JsonNode prodNode = detNode.get("producto");
        Producto producto = new Producto();
        producto.setCodigoBarra(prodNode.get("codigoBarra").asText());
        producto.setDescripcion(prodNode.get("descripcion").asText());
        producto.setPrecioCompra((float) prodNode.get("precioCompra").asDouble());
        producto.setPrecioVenta((float) prodNode.get("precioVenta").asDouble());
        producto.setStock(prodNode.get("stock").asInt());
        producto.setImgUri(prodNode.get("imgUri").asText());

        Rubro rubro = new Rubro();
        rubro.setNombre(prodNode.get("rubro").asText());
        producto.setRubro(rubro);

        Marca marca = new Marca();
        marca.setNombre(prodNode.get("marca").asText());
        producto.setMarca(marca);

        dto.setProducto(producto);
        return dto;
    }
}
