package com.controller.modal;

import com.controller.ClientesController;
import com.model.Cliente;
import com.service.ClienteService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Date;

public class ModalClienteController {
    private final ClienteService clienteService = new ClienteService();
    private ClientesController parentController;
    private Cliente cliente;

    @FXML private Text txtTituloCliente;
    @FXML private TextField inputIdCliente;
    @FXML private TextField inputNombreCliente;
    @FXML private TextField inputDireccionCliente;
    @FXML private TextField inputTelefonoCliente;

    @FXML private Button btnGuardarCliente;
    @FXML private Button btnCancelarCliente;

    public void setParentController(ClientesController parentController) {
        this.parentController = parentController;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

        inputIdCliente.setText(cliente.getId());
        inputNombreCliente.setText(cliente.getNombre());
        inputDireccionCliente.setText(cliente.getDireccion());
        inputTelefonoCliente.setText(cliente.getTelefono());
    }

    @FXML private void handleBtnGuardar(ActionEvent event) {
        try {
            if (cliente == null) {
                // Caso crear Empleado
                Cliente nuevoEmpleado = new Cliente(
                        null,
                        inputNombreCliente.getText(),
                        inputDireccionCliente.getText(),
                        inputDireccionCliente.getText(),
                        new Date(),
                        new Date(),
                        true
                );

                Cliente creado = clienteService.createCliente(nuevoEmpleado);

                if (parentController != null) {
                    parentController.agregarCliente(creado);
                }

            } else {
                // Caso editar Empleado
                cliente.setNombre(inputNombreCliente.getText());
                cliente.setDireccion(inputDireccionCliente.getText());
                cliente.setTelefono(inputTelefonoCliente.getText());
                cliente.setFechaModificacion(new Date());

                Cliente actualizado = clienteService.updateCliente(Integer.parseInt(cliente.getId()), cliente);

                if (parentController != null) {
                    parentController.actualizarCliente(actualizado);
                }
            }

            Stage currentStage = (Stage) btnGuardarCliente.getScene().getWindow();
            currentStage.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @FXML private void handleBtnCancelar(ActionEvent event) {
        Stage currentStage = (Stage) btnCancelarCliente.getScene().getWindow();
        currentStage.close();
    }
}
