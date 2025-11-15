package com.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.*;
import com.model.dto.VentaDetailResponseDTO;
import com.model.dto.VentaDetalleResponseDTO;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public Venta create(Venta venta) throws Exception {
        Map<String, Object> requestMap = writePlaneMap(venta);
        String requestBody = mapper.writeValueAsString(requestMap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
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

    public Venta[] getAll() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
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

    public VentaDetailResponseDTO getOneById(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode root = mapper.readTree(response.body());
            return parseVentaDetail(root);
        } else if (response.statusCode() == 404) {
            return null;
        } else {
            throw new RuntimeException("Error al obtener venta: " + response.body());
        }
    }

    private VentaDetailResponseDTO parseVentaDetail(JsonNode root) {
        VentaDetailResponseDTO venta = new VentaDetailResponseDTO();

        venta.setId(root.get("id").asInt());
        venta.setPrecioTotal((float) root.get("precioTotal").asDouble());
        venta.setFechaCreacion(root.get("fechaCreacion").asText());

        JsonNode provNode = root.get("cliente");
        Cliente cliente = new Cliente();
        cliente.setId(provNode.get("id").asInt());
        cliente.setNombre(provNode.get("nombre").asText());
        venta.setCliente(cliente);

        JsonNode userNode = root.get("usuario");
        Usuario usuario = new Usuario();
        usuario.setId(userNode.get("id").asInt());
        usuario.setNombre(userNode.get("nombre").asText());
        venta.setUsuario(usuario);

        List<VentaDetalleResponseDTO> detalles = new ArrayList<>();
        for (JsonNode detNode : root.get("detalles")) {
            detalles.add(parseDetalle(detNode));
        }
        venta.setDetalles(detalles);

        return venta;
    }

    private VentaDetalleResponseDTO parseDetalle(JsonNode detNode) {
        VentaDetalleResponseDTO dto = new VentaDetalleResponseDTO();

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
