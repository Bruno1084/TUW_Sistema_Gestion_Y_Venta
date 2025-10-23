package com.controller;

import com.controller.add.AddEmpleadoController;
import com.controller.detail.DetailEmpleadoController;
import com.model.Empleado;
import com.service.EmpleadoService;
import com.util.ParentAware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class EmpleadosController implements ParentAware {
    private SidebarController parentController;
    private final EmpleadoService empleadoService = new EmpleadoService();
    private final ObservableList<Empleado> empleados = FXCollections.observableArrayList();

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

    // Search Bar Empleados
    @FXML private TextField inputBuscarEmpleado;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }


    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
        tableEmpleados.getSelectionModel().select(empleado);
        tableEmpleados.scrollTo(empleado);
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
            if (empleados.get(i).getId() == actualizado.getId()) {
                empleados.set(i, actualizado);
                break;
            }
        }
    }

    private void cargarDetalleEmpleado(Empleado empleado) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailEmpleado.fxml"));
            Parent root = fxmlLoader.load();

            DetailEmpleadoController detailEmpleadoController = fxmlLoader.getController();
            detailEmpleadoController.setParentController(parentController);
            detailEmpleadoController.setEmpleado(empleado);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
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
                        cargarDetalleEmpleado(newSelection);
                }
        );
        cargarEmpleados();
    }

    @FXML private void handleAniadirEmpleado(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/add/AddEmpleado.fxml"));
            Parent root = fxmlLoader.load();

            AddEmpleadoController addEmpleadoController = fxmlLoader.getController();
            addEmpleadoController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear el empleado");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
