package com.controller;

import com.controller.add.AddClienteController;
import com.controller.detail.DetailClienteController;
import com.model.Cliente;
import com.service.ClienteService;
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

public class ClientesController implements ParentAware {
    private SidebarController parentController;
    private final ClienteService clienteService = new ClienteService();
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();

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

    // Search Bar Clientes
    @FXML TextField inputBuscarCliente;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
        tableClientes.getSelectionModel().select(cliente);
        tableClientes.scrollTo(cliente);
    }

    private void cargarCliente() {
        try {
            Cliente[] lista = clienteService.getAllCliente();
            clientes.clear();
            clientes.addAll(Arrays.asList(lista));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar clientes");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void actualizarCliente(Cliente actualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == actualizado.getId()) {
                clientes.set(i, actualizado);
                break;
            }
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

    // FXML Methods
    @FXML public void initialize() {
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
        cargarCliente();
    }

    @FXML private void handleAniadirCliente(ActionEvent event) {
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
