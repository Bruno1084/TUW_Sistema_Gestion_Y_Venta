package com.controller;

import com.controller.add.AddCompraController;
import com.controller.detail.DetailCompraController;
import com.model.Compra;
import com.service.CompraService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ComprasController implements ParentAware {
    private SidebarController parentController;
    private final CompraService compraService = new CompraService();
    private final ObservableList<Compra> compras = FXCollections.observableArrayList();

    private String filtroActual = "id";
    private final ObservableList<Compra> comprasOriginal = FXCollections.observableArrayList();

    // Table View Compras
    @FXML private TableView<Compra> tableCompras;
    @FXML private TableColumn<Compra, Integer> columnIdCompra;
    @FXML private TableColumn<Compra, String> columnProveedorCompra;
    @FXML private TableColumn<Compra, String> columnUsuarioCompra;
    @FXML private TableColumn<Compra, Float> columnPrecioTotalCompra;
    @FXML private TableColumn<Compra, Date> columnFechaCreacionCompra;

    // Buttons
    @FXML private MenuButton btnFiltrarCompra;
    @FXML private Button btnAniadirCompra;
    @FXML private MenuItem menuItemId;
    @FXML private MenuItem menuItemProveedor;
    @FXML private MenuItem menuItemUsuario;
    @FXML private MenuItem menuItemPrecioTotal;

    // Search Bar Compra
    @FXML private TextField inputBuscarCompra;

    // Text
    @FXML private Text txtGastosTotalesPrecio;
    @FXML private Text txtComprasMensualesTotal;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarCompras() {
        try {
            Compra[] lista = compraService.getAll();
            comprasOriginal.clear();
            comprasOriginal.setAll(lista);

            compras.clear();
            compras.addAll(lista);

            calcularEstadisticas();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar compras");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void cargarDetalleCompra(Compra compra) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailCompra.fxml"));
            Parent root = fxmlLoader.load();

            DetailCompraController detailCompraController = fxmlLoader.getController();
            detailCompraController.setParentController(parentController);
            detailCompraController.setCompra(compra);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void aplicarFiltro() {
        String input = inputBuscarCompra.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            compras.setAll(comprasOriginal);
        } else {
            List<Compra> filtrados = comprasOriginal.stream()
                    .filter(p -> coincideFiltro(p, input))
                    .toList();

            compras.setAll(filtrados);
        }
    }

    private boolean coincideFiltro(Compra c, String input) {
        return switch (filtroActual) {
            case "id" -> String.valueOf(c.getId()).contains(input);
            case "proveedor" -> c.getProveedor().getNombre() != null && c.getProveedor().getNombre().toLowerCase().contains(input);
            case "usuario" -> c.getUsuario().getNombre() != null && c.getUsuario().getNombre().toLowerCase().contains(input);
            case "precio total" -> String.valueOf(c.getPrecioTotal()).contains(input);
            default -> false;
        };
    }

    private void calcularEstadisticas() {
        float ingresosTotales = 0;
        int ventasDelMes = 0;

        LocalDate hoy = LocalDate.now();
        int mesActual = hoy.getMonthValue();
        int anioActual = hoy.getYear();

        for (Compra c : comprasOriginal) {
            ingresosTotales += c.getPrecioTotal();

            LocalDate fechaVenta = c.getFechaCreacion().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (fechaVenta.getMonthValue() == mesActual && fechaVenta.getYear() == anioActual) {
                ventasDelMes++;
            }
        }

        txtGastosTotalesPrecio.setText("$ " + ingresosTotales);
        txtComprasMensualesTotal.setText(String.valueOf(ventasDelMes));
    }

    // FXML Methods
    @FXML public void initialize() {
        // Configurar TableView
        tableCompras.setItems(compras);
        columnIdCompra.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnUsuarioCompra.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        columnProveedorCompra.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProveedor().getNombre()));
        columnUsuarioCompra.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUsuario().getNombre()));
        columnPrecioTotalCompra.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        columnFechaCreacionCompra.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));

        tableCompras.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        cargarDetalleCompra(newSelection);
                }
        );

        // Configurar MenuItem
        menuItemId.setOnAction(e -> {
            filtroActual = "id";
            btnFiltrarCompra.setText("ID");
            aplicarFiltro();
        });

        menuItemProveedor.setOnAction(e -> {
            filtroActual = "proveedor";
            btnFiltrarCompra.setText("Proveedor");
            aplicarFiltro();
        });

        menuItemUsuario.setOnAction(e -> {
            filtroActual = "usuario";
            btnFiltrarCompra.setText("Usuario");
            aplicarFiltro();
        });

        menuItemPrecioTotal.setOnAction(e -> {
            filtroActual = "precio total";
            btnFiltrarCompra.setText("Precio Total");
            aplicarFiltro();
        });

        inputBuscarCompra.textProperty().addListener((obs, oldText, newText) -> aplicarFiltro());

        cargarCompras();
    }

    @FXML private void handleBtnAniadir() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/add/AddCompra.fxml"));
            Parent root = fxmlLoader.load();

            AddCompraController addCompraController = fxmlLoader.getController();
            addCompraController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear la compra");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
