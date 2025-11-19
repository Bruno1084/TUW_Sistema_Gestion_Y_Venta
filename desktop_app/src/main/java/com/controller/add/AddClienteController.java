package com.controller.add;

import com.controller.ClientesController;
import com.controller.SidebarController;
import com.model.Cliente;
import com.service.ClienteService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.Date;

public class AddClienteController implements ParentAware {
    private SidebarController parentController;
    private final ClienteService clienteService = new ClienteService();
    private Cliente cliente;

    // Buttons
    @FXML Button btnAniadirCliente;
    @FXML Button btnCancelarCliente;

    // Text
    @FXML Text txtTituloCliente;
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
        inputNombreCliente.setText(cliente.getNombre());
        inputDireccionCliente.setText(cliente.getDireccion());
        inputTelefonoCliente.setText(cliente.getTelefono());
    }

    // FXML Methods
    @FXML private void handleAniadirCliente() {
        try {
            Cliente nuevoCliente = new Cliente(
                    0,
                    inputNombreCliente.getText(),
                    inputDireccionCliente.getText(),
                    inputTelefonoCliente.getText(),
                    new Date(),
                    new Date()
            );

            clienteService.create(nuevoCliente);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Clientes.fxml"));
            Parent root = fxmlLoader.load();

            ClientesController clientesController = fxmlLoader.getController();
            clientesController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCancelarCliente() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Clientes.fxml"));
            Parent root = fxmlLoader.load();

            ClientesController clientesController= fxmlLoader.getController();
            clientesController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
