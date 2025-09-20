package com.controller;

import com.model.Venta;
import com.service.VentaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InicioController {
    private final VentaService ventaService = new VentaService();
    private ObservableList<Venta> ventas = FXCollections.observableArrayList();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm");

    // Table View Ventas
    @FXML private TableView<Venta> tableVentasRecientes;
    @FXML private TableColumn<Venta, String> columnIdVenta;
    @FXML private TableColumn<Venta, String> columnClienteVenta;
    @FXML private TableColumn<Venta, String> columnUsuarioVenta;
    @FXML private TableColumn<Venta, Float> columnPrecioTotalVenta;
    @FXML private TableColumn<Venta, Date> columnFechaCreacionVenta;
    @FXML private TableColumn<Venta, Button> columnDetalleVenta;

    // Stats
    @FXML private Text txtIngresosTotalesPrecio;
    @FXML private Text txtProductosActivosTotal;

    // Chart Ventas Diarias
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    @FXML private BarChart<String, Number> chartVentasDiarias = new BarChart<>(xAxis, yAxis);




}
