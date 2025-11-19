package com.controller.detail;

import com.controller.ClientesController;
import com.controller.SidebarController;
import com.controller.edit.EditClienteController;
import com.model.Cliente;
import com.service.ClienteService;
import com.util.ParentAware;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DetailClienteController implements ParentAware {
    private Cliente cliente;
    private SidebarController parentController;
    private ClienteService clienteService = new ClienteService();

    // Buttons
    @FXML Button btnEditarCliente;
    @FXML Button btnEliminarCliente;
    @FXML Button btnCerrarCliente;

    // Text
    @FXML Text txtTituloCliente;
    @FXML Text txtIdCliente;
    @FXML Text txtNombreCliente;
    @FXML Text txtDireccionCliente;
    @FXML Text txtTelefonoCliente;
    @FXML Text txtFechaCreacionCliente;
    @FXML Text txtFechaModificacionCliente;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setCliente(Cliente cliente) {
        txtTituloCliente.setText(cliente.getNombre());
        txtIdCliente.setText(String.valueOf(cliente.getId()));
        txtNombreCliente.setText(cliente.getNombre());
        txtDireccionCliente.setText(cliente.getDireccion());
        txtTelefonoCliente.setText(cliente.getTelefono());
        txtFechaCreacionCliente.setText(String.valueOf(cliente.getFechaCreacion()));
        txtFechaModificacionCliente.setText(String.valueOf(cliente.getFechaModificacion()));
        this.cliente = cliente;
    }

    // FXML Methods
    @FXML private void handleEditarCliente() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/edit/EditCliente.fxml"));
            Parent root = fxmlLoader.load();

            EditClienteController editClienteController = fxmlLoader.getController();
            editClienteController.setParentController(parentController);
            editClienteController.setCliente(cliente);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleEliminarCliente() {
        try {
            clienteService.delete(cliente.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Clientes.fxml"));
            Parent root = loader.load();

            ClientesController clientesController = loader.getController();
            clientesController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCerrarCliente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Clientes.fxml"));
            Parent root = loader.load();

            ClientesController clientesController = loader.getController();
            clientesController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
