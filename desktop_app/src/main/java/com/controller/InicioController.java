package com.controller;

import com.controller.detail.DetailVentaController;
import com.model.Venta;
import com.service.VentaService;
import com.util.ParentAware;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InicioController implements ParentAware {
    private SidebarController parentController;
    private final VentaService ventaService = new VentaService();
    private ObservableList<Venta> ventas = FXCollections.observableArrayList();

    // Table View Ventas
    @FXML private TableView<Venta> tableVentasRecientes;
    @FXML private TableColumn<Venta, String> columnIdVenta;
    @FXML private TableColumn<Venta, String> columnClienteVenta;
    @FXML private TableColumn<Venta, String> columnUsuarioVenta;
    @FXML private TableColumn<Venta, Float> columnPrecioTotalVenta;
    @FXML private TableColumn<Venta, Date> columnFechaCreacionVenta;

    // Stats
    @FXML private Text txtIngresosTotalesPrecio;
    @FXML private Text txtProductosActivosTotal;

    // Chart Ventas Diarias
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    @FXML private BarChart<String, Number> chartVentasDiarias = new BarChart<>(xAxis, yAxis);

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarDetalleCliente(Venta venta) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailVenta.fxml"));
            Parent root = fxmlLoader.load();

            DetailVentaController detailVentaController = fxmlLoader.getController();
            detailVentaController.setParentController(parentController);
            detailVentaController.setVenta(venta);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private void cargarGraficoVentasDiarias() {
        try {
            ventas = FXCollections.observableArrayList(ventaService.getAll());

            Map<String, Float> ventasPorDia = new HashMap<>();

            for (Venta venta : ventas) {
                Date fecha = venta.getFechaCreacion();
                String dia = new SimpleDateFormat("yyyy-MM-dd").format(fecha);

                ventasPorDia.put(dia,
                        ventasPorDia.getOrDefault(dia, 0f) + venta.getPrecioTotal());
            }

            chartVentasDiarias.getData().clear();
            XYChart.Series<String, Number> serie = new XYChart.Series<>();

            for (String dia : ventasPorDia.keySet()) {
                serie.getData().add(new XYChart.Data<>(dia, ventasPorDia.get(dia)));
            }

            chartVentasDiarias.getData().add(serie);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // FXML Methods
    @FXML private void initialize() {
        cargarGraficoVentasDiarias();

        // Configurar TableView
        tableVentasRecientes.setItems(ventas);
        columnIdVenta.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnClienteVenta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCliente().getNombre()));
        columnUsuarioVenta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUsuario().getNombre()));
        columnPrecioTotalVenta.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        columnFechaCreacionVenta.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));

        tableVentasRecientes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        cargarDetalleCliente(newSelection);
                }
        );
    }

}
