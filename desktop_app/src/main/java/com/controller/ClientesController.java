package com.controller;

import com.controller.modal.ModalClienteController;
import com.model.Cliente;
import com.service.ClienteService;
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

public class ClientesController {
    private final ClienteService clienteService = new ClienteService();
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm");

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
    @FXML private Button btnEditarCliente;
    @FXML private Button btnEliminarCliente;

    // Search Bar Clientes
    @FXML TextField inputBuscarCliente;

    // Text
    @FXML private Text txtIdCliente;
    @FXML private Text txtNombreCliente;
    @FXML private Text txtApellidoCliente;
    @FXML private Text txtDireccionCliente;
    @FXML private Text txtTelefonoCliente;
    @FXML private Text txtFechaCreacionCliente;
    @FXML private Text txtFechaModificacionCliente;

    // Helper Methods
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
        tableClientes.getSelectionModel().select(cliente);
        tableClientes.scrollTo(cliente);
        displayCliente(cliente);
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

    public void displayCliente(Cliente cliente) {
        if(cliente == null) return;

        String[] partesNombre = cliente.getNombre() != null? cliente.getNombre().split(" ", 2): new String[]{""};
        String nombre = partesNombre.length > 0 ? partesNombre[0] : "";
        String apellido = partesNombre.length > 1 ? partesNombre[1] : "";

        txtIdCliente.setText(String.valueOf(cliente.getId()));
        txtNombreCliente.setText(nombre);
        txtApellidoCliente.setText(apellido);
        txtDireccionCliente.setText(cliente.getDireccion());
        txtTelefonoCliente.setText(cliente.getTelefono());

        if (cliente.getFechaCreacion() != null) {
            txtFechaCreacionCliente.setText(DATE_FORMAT.format(cliente.getFechaCreacion()));
        } else {
            txtFechaCreacionCliente.setText("");
        }

        if (cliente.getFechaModificacion() != null) {
            txtFechaModificacionCliente.setText(DATE_FORMAT.format(cliente.getFechaModificacion()));
        } else {
            txtFechaModificacionCliente.setText("");
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
                        displayCliente(newSelection);
                }
        );
        cargarCliente();
    }

    @FXML private void handleAniadirCliente(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/modal/ModalCliente.fxml"));
            Parent root = fxmlLoader.load();

            ModalClienteController modalController = fxmlLoader.getController();
            modalController.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Añadir Cliente");
            stage.setResizable(false);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear el cliente");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    @FXML private void handleEditarCliente(ActionEvent event) {
        try {
            Cliente seleccionado = tableClientes.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("No hay selección");
                alert.setContentText("Debes seleccionar un cliente para editar.");
                alert.showAndWait();
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/modal/ModalCliente.fxml"));
            Parent root = fxmlLoader.load();

            ModalClienteController modalController = fxmlLoader.getController();
            modalController.setParentController(this);

            modalController.setCliente(seleccionado);
            modalController.setTxtTituloCliente("Editar Cliente");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Cliente");
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleEliminarCliente(ActionEvent event) {
        try {
            Cliente clienteSeleccionado = tableClientes.getSelectionModel().getSelectedItem();

            if (clienteSeleccionado != null) {
                clienteService.deleteCliente(clienteSeleccionado.getId());
                clientes.remove(clienteSeleccionado);

                txtIdCliente.setText("");
                txtNombreCliente.setText("");
                txtApellidoCliente.setText("");
                txtDireccionCliente.setText("");
                txtTelefonoCliente.setText("");
                txtFechaCreacionCliente.setText("");
                txtFechaModificacionCliente.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atención");
                alert.setHeaderText("Ningún cliente seleccionado");
                alert.setContentText("Seleccione un cliente de la tabla para eliminarlo.");
                alert.showAndWait();
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo eliminar el cliente");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
