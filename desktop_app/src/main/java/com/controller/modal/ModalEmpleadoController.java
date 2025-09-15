package com.controller.modal;

import com.controller.EmpleadosController;
import com.model.Empleado;
import com.service.EmpleadoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Date;

public class ModalEmpleadoController {
    private final EmpleadoService empleadoService = new EmpleadoService();
    private EmpleadosController parentController;
    private Empleado empleado;

    @FXML private Text txtTituloEmpleado;
    @FXML private TextField inputIdEmpleado;
    @FXML private TextField inputNombreEmpleado;
    @FXML private TextField inputDireccionEmpleado;
    @FXML private TextField inputTelefonoEmpleado;

    @FXML private Button btnGuardarEmpleado;
    @FXML private Button btnCancelarEmpleado;


    public void setParentController(EmpleadosController parentController) {
        this.parentController = parentController;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;

        inputIdEmpleado.setText(empleado.getId());
        inputNombreEmpleado.setText(empleado.getNombre());
        inputDireccionEmpleado.setText(empleado.getDireccion());
        inputTelefonoEmpleado.setText(empleado.getTelefono());
    }

    @FXML private void handleBtnGuardar(ActionEvent event) {
        try {
            if (empleado == null) {
                // Caso crear Empleado
                Empleado nuevoEmpleado = new Empleado(
                        null,
                        inputNombreEmpleado.getText(),
                        inputDireccionEmpleado.getText(),
                        inputTelefonoEmpleado.getText(),
                        new Date(),
                        new Date(),
                        true
                );

                Empleado creado = empleadoService.createEmpleado(nuevoEmpleado);

                if (parentController != null) {
                    parentController.agregarEmpleado(creado);
                }

            } else {
                // Caso editar Empleado
                empleado.setNombre(inputNombreEmpleado.getText());
                empleado.setDireccion(inputDireccionEmpleado.getText());
                empleado.setTelefono(inputTelefonoEmpleado.getText());
                empleado.setFechaModificacion(new Date());

                Empleado actualizado = empleadoService.updateEmpleado(Integer.parseInt(empleado.getId()), empleado);

                if (parentController != null) {
                    parentController.actualizarEmpleado(actualizado);
                }
            }

            Stage currentStage = (Stage) btnGuardarEmpleado.getScene().getWindow();
            currentStage.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @FXML private void handleBtnCancelar(ActionEvent event) {
        Stage currentStage = (Stage) btnCancelarEmpleado.getScene().getWindow();
        currentStage.close();
    }
}
