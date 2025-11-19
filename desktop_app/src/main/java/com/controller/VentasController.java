package com.controller;

import com.controller.add.AddVentaController;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.util.Date;
import java.util.List;

public class VentasController implements ParentAware {
    private SidebarController parentController;
    private final VentaService ventasService = new VentaService();
    private final ObservableList<Venta> ventas = FXCollections.observableArrayList();

    private String filtroActual = "id";
    private final ObservableList<Venta> ventasOriginal = FXCollections.observableArrayList();

    // Table View Ventas
    @FXML private TableView<Venta> tableVentas;
    @FXML private TableColumn<Venta, Integer> columnIdVenta;
    @FXML private TableColumn<Venta, String> columnClienteVenta;
    @FXML private TableColumn<Venta, String> columnUsuarioVenta;
    @FXML private TableColumn<Venta, Float> columnPrecioTotalVenta;
    @FXML private TableColumn<Venta, Date> columnFechaCreacionVenta;

    // Buttons
    @FXML private MenuButton btnFiltrarVenta;
    @FXML private Button btnAniadirVenta;
    @FXML private MenuItem menuItemId;
    @FXML private MenuItem menuItemCliente;
    @FXML private MenuItem menuItemUsuario;
    @FXML private MenuItem menuItemPrecioTotal;

    // Search Bar Venta
    @FXML private TextField inputBuscarVenta;

    // Text
    @FXML private Text txtGastosTotalesPrecio;
    @FXML private Text txtComprasMensualesTotal;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarVentas() {
        try {
            Venta[] lista = ventasService.getAll();
            ventasOriginal.clear();
            ventasOriginal.setAll(lista);

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

    private void aplicarFiltro() {
        String input = inputBuscarVenta.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            ventas.setAll(ventasOriginal);
        } else {
            List<Venta> filtrados = ventasOriginal.stream()
                    .filter(v -> coincideFiltro(v, input))
                    .toList();

            ventas.setAll(filtrados);
        }
    }

    private boolean coincideFiltro(Venta v, String input) {
        return switch (filtroActual) {
            case "id" -> String.valueOf(v.getId()).contains(input);
            case "cliente" -> v.getCliente().getNombre() != null && v.getCliente().getNombre().toLowerCase().contains(input);
            case "usuario" -> v.getUsuario().getNombre() != null && v.getUsuario().getNombre().toLowerCase().contains(input);
            case "precio total" -> String.valueOf(v.getPrecioTotal()).contains(input);
            default -> false;
        };
    }

    // FXML Methods
    @FXML public void initialize() {
        // Configurar TableView
        tableVentas.setItems(ventas);
        columnIdVenta.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnUsuarioVenta.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        columnClienteVenta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCliente().getNombre()));
        columnUsuarioVenta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUsuario().getNombre()));
        columnPrecioTotalVenta.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        columnFechaCreacionVenta.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));

        tableVentas.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        cargarDetalleCliente(newSelection);
                }
        );

        // Configurar MenuItem
        menuItemId.setOnAction(e -> {
            filtroActual = "id";
            btnFiltrarVenta.setText("ID");
            aplicarFiltro();
        });

        menuItemCliente.setOnAction(e -> {
            filtroActual = "cliente";
            btnFiltrarVenta.setText("Cliente");
            aplicarFiltro();
        });

        menuItemUsuario.setOnAction(e -> {
            filtroActual = "usuario";
            btnFiltrarVenta.setText("Usuario");
            aplicarFiltro();
        });

        menuItemPrecioTotal.setOnAction(e -> {
            filtroActual = "precio total";
            btnFiltrarVenta.setText("Precio Total");
            aplicarFiltro();
        });

        inputBuscarVenta.textProperty().addListener((obs, oldText, newText) -> aplicarFiltro());

        cargarVentas();
    }

    @FXML private void handleBtnAniadir() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/add/AddVenta.fxml"));
            Parent root = fxmlLoader.load();

            AddVentaController addVentaController = fxmlLoader.getController();
            addVentaController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear la venta");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
