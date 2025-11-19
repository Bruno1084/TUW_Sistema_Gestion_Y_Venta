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
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class ProveedoresController implements ParentAware {
    private SidebarController parentController;
    private final ProveedorService proveedorService = new ProveedorService();
    private final ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();

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

    // Search Bar Proveedores
    @FXML TextField inputBuscarProveedor;

    // Text
    @FXML private Text txtIdProveedor;
    @FXML private Text txtNombreProveedor;
    @FXML private Text txtDireccionProveedor;
    @FXML private Text txtTelefonoProveedor;
    @FXML private Text txtFechaCreacionProveedor;
    @FXML private Text txtFechaModificacionProveedor;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarProveedor() {
        try {
            Proveedor[] lista = proveedorService.getAll();
            proveedores.clear();
            proveedores.addAll(Arrays.asList(lista));
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

    // FXML Methods
    @FXML public void initialize() {
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
