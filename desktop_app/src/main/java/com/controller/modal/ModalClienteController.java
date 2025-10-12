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
            String nombre = inputNombreCliente.getText().trim() + " " + inputApellidoCliente.getText().trim();
            String telefono = inputTelefonoCliente.getText().trim();
            String direccion = inputDireccionCliente.getText().trim();

            if (cliente == null) {
                // Caso crear Cliente
                Cliente nuevoCliente = new Cliente(
                        0,
                        nombre,
                        direccion,
                        telefono,
                        new Date(),
                        new Date()
                );

                Cliente creado = clienteService.createCliente(nuevoCliente);

                if (parentController != null) {
                    parentController.agregarCliente(creado);
                    parentController.displayCliente(creado);
                }

            } else {
                // Caso editar Cliente
                cliente.setNombre(nombre);
                cliente.setDireccion(direccion);
                cliente.setTelefono(telefono);
                cliente.setFechaModificacion(new Date());

                Cliente actualizado = clienteService.updateCliente(cliente.getId(), cliente);

                if (parentController != null) {
                    parentController.actualizarCliente(actualizado);
                    parentController.displayCliente(actualizado);
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