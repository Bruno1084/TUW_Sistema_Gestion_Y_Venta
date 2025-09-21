package com.controller;

import com.model.Venta;
import com.service.VentaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentasController {
    private final VentaService ventasService = new VentaService();
    private ObservableList<Venta> ventas = FXCollections.observableArrayList();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm");

    // Table View Ventas
    @FXML private TableView<Venta> tableVentas;
    @FXML private TableColumn<Venta, Integer> columnIdVenta;
    @FXML private TableColumn<Venta, String> columnClienteVenta;
    @FXML private TableColumn<Venta, String> columnUsuarioVenta;
    @FXML private TableColumn<Venta, Float> columnPrecioTotalVenta;
    @FXML private TableColumn<Venta, Date> columnFechaCreacionVenta;
    @FXML private TableColumn<Venta, Button> columnDetalleVenta;

    // Buttons
    @FXML private MenuButton btnFiltrarVenta;
    @FXML private Button btnAniadirVenta;

    // Search Bar Venta
    @FXML private TextField inputBuscarVenta;

}
