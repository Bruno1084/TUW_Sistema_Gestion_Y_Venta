package com.controller;

import com.controller.modal.ModalProveedorController;
import com.model.Proveedor;
import com.service.ProveedorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ProveedoresController {
    private final ProveedorService proveedorService = new ProveedorService();
    private final ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm");

    // Table View Proveedores
    @FXML
    private TableView<Proveedor> tableProveedores;
    @FXML private TableColumn<Proveedor, String> columnIdProveedor;
    @FXML private TableColumn<Proveedor, String> columnNombreProveedor;
    @FXML private TableColumn<Proveedor, String> columnDireccionProveedor;
    @FXML private TableColumn<Proveedor, String> columnTelefonoProveedor;
    @FXML private TableColumn<Proveedor, Date> columnFechaCreacionProveedor;
    @FXML private TableColumn<Proveedor, VBox> columnOpcionProveedor;

    // Buttons
    @FXML private MenuButton btnFiltrarProveedor;
    @FXML private Button btnAniadirProveedor;
    @FXML private Button btnEditarProveedor;
    @FXML private Button btnEliminarProveedor;

    // Search Bar Proveedores
    @FXML
    TextField inputBuscarProveedor;

    // Text
    @FXML private Text txtIdProveedor;
    @FXML private Text txtNombreProveedor;
    @FXML private Text txtDireccionProveedor;
    @FXML private Text txtTelefonoProveedor;
    @FXML private Text txtFechaCreacionProveedor;
    @FXML private Text txtFechaModificacionProveedor;

    // Helper Methods
    public void agregarProveedor(Proveedor proveedor) {
        proveedores.add(proveedor);
    }

    private void cargarProveedor() {
        try {
            Proveedor[] lista = proveedorService.getAllProveedor();

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

    public void actualizarProveedor(Proveedor actualizado) {
        for (int i = 0; i < proveedores.size(); i++) {
            if (proveedores.get(i).getId().equals(actualizado.getId())) {
                proveedores.set(i, actualizado);
                break;
            }
        }
    }

    private void displayProveedor(Proveedor proveedor) {
        txtIdProveedor.setText(proveedor.getId());
        txtNombreProveedor.setText(proveedor.getNombre());
        txtDireccionProveedor.setText(proveedor.getDireccion());
        txtTelefonoProveedor.setText(proveedor.getTelefono());

        if (proveedor.getFechaCreacion() != null) {
            txtFechaCreacionProveedor.setText(DATE_FORMAT.format(proveedor.getFechaCreacion()));
        } else {
            txtFechaCreacionProveedor.setText("");
        }

        if (proveedor.getFechaModificacion() != null) {
            txtFechaModificacionProveedor.setText(DATE_FORMAT.format(proveedor.getFechaModificacion()));
        } else {
            txtFechaModificacionProveedor.setText("");
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

        tableProveedores.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        displayProveedor(newSelection);
                }
        );
        cargarProveedor();
    }

    @FXML private void handleAniadirProveedor(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/ModalProveedor.fxml"));
            Parent root = fxmlLoader.load();

            ModalProveedorController modalController = fxmlLoader.getController();
            modalController.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Añadir Proveedor");
            stage.setResizable(false);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear el proveedor");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    @FXML private void handleEditarProveedor(ActionEvent event) {
        try {
            Proveedor seleccionado = tableProveedores.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("No hay selección");
                alert.setContentText("Debes seleccionar un proveedor para editar.");
                alert.showAndWait();
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/ModalProveedor.fxml"));
            Parent root = fxmlLoader.load();

            ModalProveedorController modalController = fxmlLoader.getController();
            modalController.setParentController(this);

            modalController.setProveedor(seleccionado);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Proveedor");
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleEliminarProveedor(ActionEvent event) {
        try {
            Proveedor proveedorSeleccionado = tableProveedores.getSelectionModel().getSelectedItem();

            if (proveedorSeleccionado != null) {
                proveedorService.deleteProveedor(Integer.parseInt(proveedorSeleccionado.getId()));

                proveedores.remove(proveedorSeleccionado);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atención");
                alert.setHeaderText("Ningún proveedor seleccionado");
                alert.setContentText("Seleccione un proveedor de la tabla para eliminarlo.");
                alert.showAndWait();
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo eliminar el proveedor");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
