package com.controller;

import com.model.Compra;
import com.service.CompraService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComprasController {
    private final CompraService compraService = new CompraService();
    private ObservableList<Compra> compras = FXCollections.observableArrayList();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm");

    // Table View Compras
    @FXML private TableView<Compra> tableCompras;
    @FXML private TableColumn<Compra, Integer> columnIdCompra;
    @FXML private TableColumn<Compra, String> columnProveedorCompra;
    @FXML private TableColumn<Compra, String> columnUsuarioCompra;
    @FXML private TableColumn<Compra, Float> columnPrecioTotalCompra;
    @FXML private TableColumn<Compra, Date> columnFechaCreacionCompra;
    @FXML private TableColumn<Compra, Button> columnDetalleCompra;

    // Buttons
    @FXML private MenuButton btnFiltrarCompra;
    @FXML private Button btnAniadirCompra;

    // Search Bar Compra
    @FXML private TextField inputBuscarCompra;

}
