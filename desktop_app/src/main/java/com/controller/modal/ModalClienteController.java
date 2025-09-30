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
    @FXML private TextField inputNombreCliente;
    @FXML private TextField inputApellidoCliente;
    @FXML private TextField inputDireccionCliente;
    @FXML private TextField inputTelefonoCliente;

    @FXML private Button btnGuardarCliente;
    @FXML private Button btnCancelarCliente;

    public void setParentController(ClientesController parentController) {
        this.parentController = parentController;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

        String[] nombre = cliente.getNombre().split(" ");
        inputNombreCliente.setText(nombre[0]);
        inputApellidoCliente.setText(nombre[1]);
        inputDireccionCliente.setText(cliente.getDireccion());
        inputTelefonoCliente.setText(cliente.getTelefono());
    }

    public void setTxtTituloCliente(String titulo) {
        this.txtTituloCliente.setText(titulo);
    }

    @FXML private void handleBtnGuardar(ActionEvent event) {
        try {
            if (cliente == null) {
                // Caso crear Cliente
                Cliente nuevoCliente = new Cliente(
                        null,
                        inputNombreCliente.getText() + " " + inputApellidoCliente.getText(),
                        inputDireccionCliente.getText(),
                        inputTelefonoCliente.getText(),
                        new Date(),
                        new Date(),
                        true
                );

                Cliente creado = clienteService.createCliente(nuevoCliente);

                if (parentController != null) {
                    parentController.agregarCliente(creado);
                }

            } else {
                // Caso editar Cliente
                cliente.setNombre(inputNombreCliente.getText() + " " + inputApellidoCliente.getText());
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
