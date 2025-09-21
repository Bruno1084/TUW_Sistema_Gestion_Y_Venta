package com.controller;

import com.model.Producto;
import com.service.ProductoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.text.SimpleDateFormat;

public class ProductosController {
    private final ProductoService productoService = new ProductoService();
    private ObservableList<Producto> productos = FXCollections.observableArrayList();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm");

    // Table View Productos
    @FXML private TableView<Producto> tableProductos;
    @FXML private TableColumn<Producto, String> columnCodigoBarraProducto;
    @FXML private TableColumn<Producto, String> columnDescripcionProducto;
    @FXML private TableColumn<Producto, String> columnMarcaProducto;
    @FXML private TableColumn<Producto, String> columnRubroProducto;
    @FXML private TableColumn<Producto, Float> columnPrecioVentaProducto;
    @FXML private TableColumn<Producto, Integer> columnStockProducto;

    // Buttons
    @FXML private Button btnAniadirProducto;
    @FXML private Button btnEditarProducto;
    @FXML private Button btnEliminarProducto;
    @FXML private MenuButton btnFiltrarProducto;

    // Search Bar Producto
    @FXML private TextField inputBuscarProducto;


}
