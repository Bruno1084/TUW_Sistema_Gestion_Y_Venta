package com.controller.report;

import com.controller.SidebarController;
import com.model.dto.CompraPorProveedorDTO;
import com.service.CompraService;
import com.util.ParentAware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Calendar;
import java.util.Date;

public class ComprasPorProveedorController implements ParentAware {
    private SidebarController parentController;
    private final CompraService compraService = new CompraService();
    private final ObservableList<CompraPorProveedorDTO> compras = FXCollections.observableArrayList();

    // Filtros
    @FXML private MenuButton menuButtonFechas;
    @FXML private MenuItem menuItemHoy;
    @FXML private MenuItem menuItemEstaSemana;
    @FXML private MenuItem menuItemEsteMes;

    // Buttons
    @FXML private Button btnExportar;

    // TableView
    @FXML private TableView<CompraPorProveedorDTO> tableCompras;
    @FXML private TableColumn<CompraPorProveedorDTO, Integer> columnIdCompra;
    @FXML private TableColumn<CompraPorProveedorDTO, String> columnProveedorCompra;
    @FXML private TableColumn<CompraPorProveedorDTO, Integer> columnComprasTotalesCompra;
    @FXML private TableColumn<CompraPorProveedorDTO, Float> columnPrecioTotalCompra;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarCompras(Date intervaloFecha) {
        try {
            CompraPorProveedorDTO[] lista = compraService.getAllByProveedores(intervaloFecha);
            compras.clear();
            compras.addAll(lista);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar compras");
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
        tableCompras.setItems(compras);
        columnIdCompra.setCellValueFactory(new PropertyValueFactory<>("proveedorId"));
        columnProveedorCompra.setCellValueFactory(new PropertyValueFactory<>("proveedorNombre"));
        columnComprasTotalesCompra.setCellValueFactory(new PropertyValueFactory<>("comprasTotales"));
        columnPrecioTotalCompra.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));

        // Configurar MenuItem
        menuItemHoy.setOnAction(e -> {
            menuButtonFechas.setText("Hoy");
            Date intervalo = getIntervaloFecha();
            cargarCompras(intervalo);
        });

        menuItemEstaSemana.setOnAction(e -> {
            menuButtonFechas.setText("Esta Semana");
            Date intervalo = getIntervaloFecha();
            cargarCompras(intervalo);
        });

        menuItemEsteMes.setOnAction(e -> {
            menuButtonFechas.setText("Este Mes");
            Date intervalo = getIntervaloFecha();
            cargarCompras(intervalo);
        });

        menuButtonFechas.setText("Hoy");
        cargarCompras(new Date());
    }
}
