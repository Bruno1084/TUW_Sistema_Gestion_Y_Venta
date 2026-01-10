package com.controller;

import com.controller.add.AddProveedorController;
import com.controller.detail.DetailProveedorController;
import com.model.Proveedor;
import com.service.ProveedorService;
import com.util.ParentAware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ProveedoresController implements ParentAware {
    private SidebarController parentController;
    private final ProveedorService proveedorService = new ProveedorService();
    private final ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();

    private String filtroActual = "nombre";
    private final ObservableList<Proveedor> proveedoresOriginal = FXCollections.observableArrayList();

    // Table View Proveedores
    @FXML private TableView<Proveedor> tableProveedores;
    @FXML private TableColumn<Proveedor, String> columnIdProveedor;
    @FXML private TableColumn<Proveedor, String> columnNombreProveedor;
    @FXML private TableColumn<Proveedor, String> columnDireccionProveedor;
    @FXML private TableColumn<Proveedor, String> columnTelefonoProveedor;
    @FXML private TableColumn<Proveedor, Date> columnFechaCreacionProveedor;
    @FXML private TableColumn<Proveedor, Date> columnFechaModificacionProveedor;

    // Buttons
    @FXML private MenuButton btnFiltrarProveedor;
    @FXML private Button btnAniadirProveedor;
    @FXML private MenuItem menuItemId;
    @FXML private MenuItem menuItemNombre;
    @FXML private MenuItem menuItemDireccion;
    @FXML private MenuItem menuItemTelefono;

    // Search Bar Proveedores
    @FXML TextField inputBuscarProveedor;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarProveedor() {
        try {
            Proveedor[] lista = proveedorService.getAll();
            proveedoresOriginal.clear();
            proveedoresOriginal.setAll(lista);

            proveedores.clear();
            proveedores.setAll(lista);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar proveedores");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void cargarDetalleProveedor(Proveedor proveedor) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailProveedor.fxml"));
            Parent root = fxmlLoader.load();

            DetailProveedorController detailProveedorController = fxmlLoader.getController();
            detailProveedorController.setParentController(parentController);
            detailProveedorController.setProveedor(proveedor);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void aplicarFiltro() {
        String input = inputBuscarProveedor.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            proveedores.setAll(proveedoresOriginal);
        } else {
            List<Proveedor> filtrados = proveedoresOriginal.stream()
                    .filter(p -> coincideFiltro(p, input))
                    .toList();

            proveedores.setAll(filtrados);
        }
    }

    private boolean coincideFiltro(Proveedor p, String input) {
        return switch (filtroActual) {
            case "id" -> String.valueOf(p.getId()).contains(input);
            case "nombre" -> p.getNombre() != null && p.getNombre().toLowerCase().contains(input);
            case "direccion" -> p.getDireccion() != null && p.getDireccion().toLowerCase().contains(input);
            case "telefono" -> p.getTelefono() != null && p.getTelefono().toLowerCase().contains(input);
            default -> false;
        };
    }

    // FXML Methods
    @FXML public void initialize() {
        // Configurar TableView
        tableProveedores.setItems(proveedores);
        columnIdProveedor.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNombreProveedor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnDireccionProveedor.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnTelefonoProveedor.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnFechaCreacionProveedor.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        columnFechaModificacionProveedor.setCellValueFactory(new PropertyValueFactory<>("fechaModificacion"));

        tableProveedores.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        cargarDetalleProveedor(newSelection);
                }
        );

        // Configurar MenuButton
        menuItemId.setOnAction(e -> {
            filtroActual = "id";
            btnFiltrarProveedor.setText("ID");
            aplicarFiltro();
        });

        menuItemNombre.setOnAction(e -> {
            filtroActual = "nombre";
            btnFiltrarProveedor.setText("Nombre");
            aplicarFiltro();
        });

        menuItemDireccion.setOnAction(e -> {
            filtroActual = "direccion";
            btnFiltrarProveedor.setText("Dirección");
            aplicarFiltro();
        });

        menuItemTelefono.setOnAction(e -> {
            filtroActual = "telefono";
            btnFiltrarProveedor.setText("Teléfono");
            aplicarFiltro();
        });

        inputBuscarProveedor.textProperty().addListener((obs, oldText, newText) -> aplicarFiltro());

        cargarProveedor();
    }

    @FXML private void handleAniadirProveedor() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/add/AddProveedor.fxml"));
            Parent root = fxmlLoader.load();

            AddProveedorController addProveedorController = fxmlLoader.getController();
            addProveedorController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear el proveedor");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
