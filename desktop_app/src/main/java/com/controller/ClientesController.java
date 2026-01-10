package com.controller;

import com.controller.add.AddClienteController;
import com.controller.detail.DetailClienteController;
import com.model.Cliente;
import com.service.ClienteService;
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

public class ClientesController implements ParentAware {
    private SidebarController parentController;
    private final ClienteService clienteService = new ClienteService();
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    private String filtroActual = "nombre";
    private final ObservableList<Cliente> clientesOriginal = FXCollections.observableArrayList();

    // Table View Clientes
    @FXML private TableView<Cliente> tableClientes;
    @FXML private TableColumn<Cliente, String> columnIdCliente;
    @FXML private TableColumn<Cliente, String> columnNombreCliente;
    @FXML private TableColumn<Cliente, String> columnDireccionCliente;
    @FXML private TableColumn<Cliente, String> columnTelefonoCliente;
    @FXML private TableColumn<Cliente, Date> columnFechaCreacionCliente;
    @FXML private TableColumn<Cliente, Date> columnFechaModificacionCliente;

    // Buttons
    @FXML private MenuButton btnFiltrarCliente;
    @FXML private Button btnAniadirCliente;
    @FXML private MenuItem menuItemId;
    @FXML private MenuItem menuItemNombre;
    @FXML private MenuItem menuItemDireccion;
    @FXML private MenuItem menuItemTelefono;

    // Search Bar Clientes
    @FXML TextField inputBuscarCliente;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarCliente() {
        try {
            Cliente[] lista = clienteService.getAll();
            clientesOriginal.clear();
            clientesOriginal.setAll(lista);

            clientes.clear();
            clientes.setAll(lista);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar clientes");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void cargarDetalleCliente(Cliente cliente) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailCliente.fxml"));
            Parent root = fxmlLoader.load();

            DetailClienteController detailClienteController = fxmlLoader.getController();
            detailClienteController.setParentController(parentController);
            detailClienteController.setCliente(cliente);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void aplicarFiltro() {
        String input = inputBuscarCliente.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            clientes.setAll(clientesOriginal);
        } else {
            List<Cliente> filtrados = clientesOriginal.stream()
                    .filter(p -> coincideFiltro(p, input))
                    .toList();

            clientes.setAll(filtrados);
        }
    }

    private boolean coincideFiltro(Cliente c, String input) {
        return switch (filtroActual) {
            case "id" -> String.valueOf(c.getId()).contains(input);
            case "nombre" -> c.getNombre() != null && c.getNombre().toLowerCase().contains(input);
            case "direccion" -> c.getDireccion() != null && c.getDireccion().toLowerCase().contains(input);
            case "telefono" -> c.getTelefono() != null && c.getTelefono().toLowerCase().contains(input);
            default -> false;
        };
    }

    // FXML Methods
    @FXML public void initialize() {
        // Configurar TableView
        tableClientes.setItems(clientes);
        columnIdCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnDireccionCliente.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnTelefonoCliente.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnFechaCreacionCliente.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        columnFechaModificacionCliente.setCellValueFactory(new PropertyValueFactory<>("fechaModificacion"));

        tableClientes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        cargarDetalleCliente(newSelection);
                }
        );

        // Configurar MenuItem
        menuItemId.setOnAction(e -> {
            filtroActual = "id";
            btnFiltrarCliente.setText("ID");
            aplicarFiltro();
        });

        menuItemNombre.setOnAction(e -> {
            filtroActual = "nombre";
            btnFiltrarCliente.setText("Nombre");
            aplicarFiltro();
        });

        menuItemDireccion.setOnAction(e -> {
            filtroActual = "direccion";
            btnFiltrarCliente.setText("Dirección");
            aplicarFiltro();
        });

        menuItemTelefono.setOnAction(e -> {
            filtroActual = "telefono";
            btnFiltrarCliente.setText("Teléfono");
            aplicarFiltro();
        });

        inputBuscarCliente.textProperty().addListener((obs, oldText, newText) -> aplicarFiltro());

        cargarCliente();
    }

    @FXML private void handleAniadirCliente() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/add/AddCliente.fxml"));
            Parent root = fxmlLoader.load();

            AddClienteController addClienteController = fxmlLoader.getController();
            addClienteController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear el cliente");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
