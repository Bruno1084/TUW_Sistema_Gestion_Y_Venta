package com.controller.add;

import com.controller.ClientesController;
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
    @FXML TextField inputIdCliente;
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
        inputIdCliente.setText(String.valueOf(cliente.getId()));
        inputNombreCliente.setText(cliente.getNombre());
        inputDireccionCliente.setText(cliente.getDireccion());
        inputTelefonoCliente.setText(cliente.getTelefono());
    }

    // FXML Methods
    @FXML private void handleAniadirCliente() {
        try {
            if (cliente == null) {
                // CASO CREAR CLIENTE
                Cliente nuevoCliente = new Cliente(
                        Integer.parseInt(inputIdCliente.getText()),
                        inputNombreCliente.getText(),
                        inputDireccionCliente.getText(),
                        inputTelefonoCliente.getText(),
                        new Date(),
                        new Date()
                );

                clienteService.createCliente(nuevoCliente);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Clientes.fxml"));
                Parent root = fxmlLoader.load();

                ClientesController clientesController = fxmlLoader.getController();
                clientesController.setParentController(parentController);

                parentController.getMainBorderPane().setCenter(root);
            } else {
                // CASO EDITAR EMPLEADO
                cliente.setId(Integer.parseInt(inputIdCliente.getText()));
                cliente.setNombre(inputNombreCliente.getText());
                cliente.setDireccion(inputDireccionCliente.getText());
                cliente.setTelefono(inputTelefonoCliente.getText());

                Cliente actualizado = clienteService.updateCliente(cliente.getId(), cliente);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailCliente.fxml"));
                Parent root = fxmlLoader.load();

                DetailClienteController detailClienteController = fxmlLoader.getController();
                detailClienteController.setParentController(parentController);
                detailClienteController.setCliente(actualizado);

                parentController.getMainBorderPane().setCenter(root);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCancelarCliente() {
        try {
            if (cliente == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Clientes.fxml"));
                Parent root = fxmlLoader.load();

                ClientesController clientesController= fxmlLoader.getController();
                clientesController.setParentController(parentController);
                parentController.getMainBorderPane().setCenter(root);
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailCliente.fxml"));
                Parent root = fxmlLoader.load();

                DetailClienteController detailClienteController = fxmlLoader.getController();
                detailClienteController.setParentController(parentController);
                detailClienteController.setCliente(cliente);
                parentController.getMainBorderPane().setCenter(root);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
