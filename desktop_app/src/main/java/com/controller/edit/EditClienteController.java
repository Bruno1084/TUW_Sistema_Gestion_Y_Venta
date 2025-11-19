package com.controller.edit;

import com.controller.SidebarController;
import com.controller.detail.DetailClienteController;
import com.model.Cliente;
import com.service.ClienteService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EditClienteController implements ParentAware {
    private SidebarController parentController;
    private final ClienteService clienteService = new ClienteService();
    private Cliente cliente;

    // Buttons
    @FXML Button btnEditarCliente;
    @FXML Button btnCancelarCliente;

    // Text
    @FXML Text txtTituloCliente;
    @FXML Text txtIdCliente;
    @FXML TextField inputNombreCliente;
    @FXML TextField inputDireccionCliente;
    @FXML TextField inputTelefonoCliente;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        txtTituloCliente.setText(cliente.getNombre());
        txtIdCliente.setText(String.valueOf(cliente.getId()));
        inputNombreCliente.setText(cliente.getNombre());
        inputDireccionCliente.setText(cliente.getDireccion());
        inputTelefonoCliente.setText(cliente.getTelefono());
    }

    // FXML Methods
    @FXML private void handleEditarCliente() {
        try {
            cliente.setId(Integer.parseInt(txtIdCliente.getText()));
            cliente.setNombre(inputNombreCliente.getText());
            cliente.setDireccion(inputDireccionCliente.getText());
            cliente.setTelefono(inputTelefonoCliente.getText());

            Cliente actualizado = clienteService.update(cliente.getId(), cliente);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailCliente.fxml"));
            Parent root = fxmlLoader.load();

            DetailClienteController detailClienteController = fxmlLoader.getController();
            detailClienteController.setParentController(parentController);
            detailClienteController.setCliente(actualizado);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCancelarCliente() {
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
}
