package com.controller;

import com.controller.add.AddEmpleadoController;
import com.controller.detail.DetailEmpleadoController;
import com.model.Empleado;
import com.service.EmpleadoService;
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

public class EmpleadosController implements ParentAware {
    private SidebarController parentController;
    private final EmpleadoService empleadoService = new EmpleadoService();
    private final ObservableList<Empleado> empleados = FXCollections.observableArrayList();

    private String filtroActual = "nombre";
    private final ObservableList<Empleado> empleadosOriginal = FXCollections.observableArrayList();

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
    @FXML private MenuItem menuItemId;
    @FXML private MenuItem menuItemNombre;
    @FXML private MenuItem menuItemDireccion;
    @FXML private MenuItem menuItemTelefono;

    // Search Bar Empleados
    @FXML private TextField inputBuscarEmpleado;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarEmpleados() {
        try {
            Empleado[] lista = empleadoService.getAll();
            empleadosOriginal.clear();
            empleadosOriginal.setAll(lista);

            empleados.clear();
            empleados.setAll(lista);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar empleados");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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

    private void aplicarFiltro() {
        String input = inputBuscarEmpleado.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            empleados.setAll(empleadosOriginal);
        } else {
            List<Empleado> filtrados = empleadosOriginal.stream()
                    .filter(p -> coincideFiltro(p, input))
                    .toList();

            empleados.setAll(filtrados);
        }
    }

    private boolean coincideFiltro(Empleado e, String input) {
        return switch (filtroActual) {
            case "id" -> String.valueOf(e.getId()).contains(input);
            case "nombre" -> e.getNombre() != null && e.getNombre().toLowerCase().contains(input);
            case "direccion" -> e.getDireccion() != null && e.getDireccion().toLowerCase().contains(input);
            case "telefono" -> e.getTelefono() != null && e.getTelefono().toLowerCase().contains(input);
            default -> false;
        };
    }

    // FXML Methods
    @FXML public void initialize() {
        // Configurar TableView
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

        // Configurar MenuItem
        menuItemId.setOnAction(e -> {
            filtroActual = "id";
            btnFiltrarEmpleado.setText("ID");
            aplicarFiltro();
        });

        menuItemNombre.setOnAction(e -> {
            filtroActual = "nombre";
            btnFiltrarEmpleado.setText("Nombre");
            aplicarFiltro();
        });

        menuItemDireccion.setOnAction(e -> {
            filtroActual = "direccion";
            btnFiltrarEmpleado.setText("Dirección");
            aplicarFiltro();
        });

        menuItemTelefono.setOnAction(e -> {
            filtroActual = "telefono";
            btnFiltrarEmpleado.setText("Teléfono");
            aplicarFiltro();
        });

        inputBuscarEmpleado.textProperty().addListener((obs, oldText, newText) -> aplicarFiltro());

        cargarEmpleados();
    }

    @FXML private void handleAniadirEmpleado() {
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
