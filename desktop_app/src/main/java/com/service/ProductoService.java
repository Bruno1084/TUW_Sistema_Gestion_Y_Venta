package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Producto;
import java.net.http.HttpClient;

public class ProductoService {
    private static final String BASE_URL = "http://localhost:8080/api/productos";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Producto createProducto(Producto producto) throws Exception {

    }

    public Producto[] getAllProducto() throws Exception {

    }

    public Producto getOneByIdProducto(String codigoBarra) throws Exception {

    }

    public Producto updateProducto(String codigoBarra, Producto producto) throws Exception{

    }

    public boolean deleteProducto(String codigoBarra) throws Exception {

    }
}
