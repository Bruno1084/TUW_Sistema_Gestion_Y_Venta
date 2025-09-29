package com.controller;

import com.controller.modal.ModalEmpleadoController;
import com.model.Empleado;
import com.service.EmpleadoService;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class EmpleadosController {
    private final EmpleadoService empleadoService = new EmpleadoService();
    private final ObservableList<Empleado> empleados = FXCollections.observableArrayList();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm");

    // Table View Empleados
    @FXML private TableView<Empleado> tableEmpleados;
    @FXML private TableColumn<Empleado, String> columnIdEmpleado;
    @FXML private TableColumn<Empleado, String> columnNombreEmpleado;
    @FXML private TableColumn<Empleado, String> columnDireccionEmpleado;
    @FXML private TableColumn<Empleado, String> columnTelefonoEmpleado;
    @FXML private TableColumn<Empleado, Date> columnFechaCreacionEmpleado;
    @FXML private TableColumn<Empleado, Date> columnFechaModificacionEmpleado;

    // Buttons
    @FXML private MenuButton btnFiltrarEmpleado;
    @FXML private Button btnAniadirEmpleado;
    @FXML private Button btnEditarEmpleado;
    @FXML private Button btnEliminarEmpleado;

    // Search Bar Empleados
    @FXML private TextField inputBuscarEmpleado;

    // Text
    @FXML private Text txtIdEmpleado;
    @FXML private Text txtNombreEmpleado;
    @FXML private Text txtApellidoEmpleado;
    @FXML private Text txtDireccionEmpleado;
    @FXML private Text txtTelefonoEmpleado;
    @FXML private Text txtFechaCreacionEmpleado;
    @FXML private Text txtFechaModificacionEmpleado;

    // Helper Methods
    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    private void cargarEmpleados() {
        try {
            Empleado[] lista = empleadoService.getAllEmpleado();

            empleados.clear();
            empleados.addAll(Arrays.asList(lista));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar empleados");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void actualizarEmpleado(Empleado actualizado) {
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getId().equals(actualizado.getId())) {
                empleados.set(i, actualizado);
                break;
            }
        }
    }

    private void displayEmpleado(Empleado empleado) {
        String[] nombre = empleado.getNombre().split(" ");
        txtIdEmpleado.setText(empleado.getId());
        txtNombreEmpleado.setText(nombre[0]);
        txtApellidoEmpleado.setText(nombre[1]);
        txtDireccionEmpleado.setText(empleado.getDireccion());
        txtTelefonoEmpleado.setText(empleado.getTelefono());

        if (empleado.getFechaCreacion() != null) {
            txtFechaCreacionEmpleado.setText(DATE_FORMAT.format(empleado.getFechaCreacion()));
        } else {
            txtFechaCreacionEmpleado.setText("");
        }

        if (empleado.getFechaModificacion() != null) {
            txtFechaModificacionEmpleado.setText(DATE_FORMAT.format(empleado.getFechaModificacion()));
        } else {
            txtFechaModificacionEmpleado.setText("");
        }
    }

    // FXML Methods
    @FXML public void initialize() {
        tableEmpleados.setItems(empleados);
        columnIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNombreEmpleado.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnDireccionEmpleado.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnTelefonoEmpleado.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnFechaCreacionEmpleado.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        columnFechaModificacionEmpleado.setCellValueFactory(new PropertyValueFactory<>("fechaModificacion"));

        tableEmpleados.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        displayEmpleado(newSelection);
                }
        );
        cargarEmpleados();
    }

    @FXML private void handleAniadirEmpleado(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/modal/ModalEmpleado.fxml"));
            Parent root = fxmlLoader.load();

            ModalEmpleadoController modalController = fxmlLoader.getController();
            modalController.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Añadir Empleado");
            stage.setResizable(false);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear el empleado");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    @FXML private void handleEditarEmpleado(ActionEvent event) {
        try {
            Empleado seleccionado = tableEmpleados.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("No hay selección");
                alert.setContentText("Debes seleccionar un empleado para editar.");
                alert.showAndWait();
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/modal/ModalEmpleado.fxml"));
            Parent root = fxmlLoader.load();

            ModalEmpleadoController modalController = fxmlLoader.getController();
            modalController.setParentController(this);

            modalController.setEmpleado(seleccionado);
            modalController.setTxtTituloEmpleado("Editar Empleado");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Empleado");
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleEliminarEmpleado(ActionEvent event) {
        try {
            Empleado empleadoSeleccionado = tableEmpleados.getSelectionModel().getSelectedItem();

            if (empleadoSeleccionado != null) {
                empleadoService.deleteEmpleado(Integer.parseInt(empleadoSeleccionado.getId()));

                empleados.remove(empleadoSeleccionado);

                txtIdEmpleado.setText("");
                txtNombreEmpleado.setText("");
                txtApellidoEmpleado.setText("");
                txtDireccionEmpleado.setText("");
                txtTelefonoEmpleado.setText("");
                txtFechaCreacionEmpleado.setText("");
                txtFechaModificacionEmpleado.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atención");
                alert.setHeaderText("Ningún empleado seleccionado");
                alert.setContentText("Seleccione un empleado de la tabla para eliminarlo.");
                alert.showAndWait();
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo eliminar el empleado");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
