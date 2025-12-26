package com.controller.report;

import com.controller.SidebarController;
import com.model.dto.VentaPorClienteDTO;
import com.service.VentaService;
import com.util.ParentAware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Calendar;
import java.util.Date;

public class VentasPorClienteController implements ParentAware {
    private SidebarController parentController;
    private final VentaService ventaService = new VentaService();
    private final ObservableList<VentaPorClienteDTO> ventas = FXCollections.observableArrayList();

    // Filtros
    @FXML private MenuButton menuButtonFechas;
    @FXML private MenuItem menuItemHoy;
    @FXML private MenuItem menuItemEstaSemana;
    @FXML private MenuItem menuItemEsteMes;

    // Buttons
    @FXML private Button btnExportar;

    // TableView
    @FXML private TableView<VentaPorClienteDTO> tableVentas;
    @FXML private TableColumn<VentaPorClienteDTO, Integer> columnIdVenta;
    @FXML private TableColumn<VentaPorClienteDTO, String> columnClienteVenta;
    @FXML private TableColumn<VentaPorClienteDTO, Integer> columnVentasTotalesVenta;
    @FXML private TableColumn<VentaPorClienteDTO, Float> columnPrecioTotalVenta;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarVentas(Date intervaloFecha) {
        try {
            VentaPorClienteDTO[] lista = ventaService.getAllByClientes(intervaloFecha);
            ventas.clear();
            ventas.addAll(lista);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar ventas");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private Date getIntervaloFecha() {
        String opcion = menuButtonFechas.getText();

        Calendar cal = Calendar.getInstance();

        switch (opcion) {
            case "Hoy":
                break;

            case "Esta Semana":
                cal.add(Calendar.DAY_OF_MONTH, -7);
                break;

            case "Este Mes":
                cal.add(Calendar.MONTH, -1);
                break;

            default:
                break;
        }

        return cal.getTime();
    }

    // FXML Methods
    @FXML private void initialize() {
        // Configurar TableView
        tableVentas.setItems(ventas);
        columnIdVenta.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
        columnClienteVenta.setCellValueFactory(new PropertyValueFactory<>("clienteNombre"));
        columnVentasTotalesVenta.setCellValueFactory(new PropertyValueFactory<>("ventasTotales"));
        columnPrecioTotalVenta.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));

        // Configurar MenuItem
        menuItemHoy.setOnAction(e -> {
            menuButtonFechas.setText("Hoy");
            Date intervalo = getIntervaloFecha();
            cargarVentas(intervalo);
        });

        menuItemEstaSemana.setOnAction(e -> {
            menuButtonFechas.setText("Esta Semana");
            Date intervalo = getIntervaloFecha();
            cargarVentas(intervalo);
        });

        menuItemEsteMes.setOnAction(e -> {
            menuButtonFechas.setText("Este Mes");
            Date intervalo = getIntervaloFecha();
            cargarVentas(intervalo);
        });

        menuButtonFechas.setText("Hoy");
        cargarVentas(new Date());
    }
}
