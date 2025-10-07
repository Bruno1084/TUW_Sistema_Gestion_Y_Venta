package com.controller.modal;

import com.controller.ProveedoresController;
import com.model.Proveedor;
import com.service.ProveedorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Date;

public class ModalProveedorController {
    private final ProveedorService proveedorService = new ProveedorService();
    private ProveedoresController parentController;
    private Proveedor proveedor;

    @FXML
    private Text txtTituloProveedor;
    @FXML private TextField inputIdProveedor;
    @FXML private TextField inputNombreProveedor;
    @FXML private TextField inputDireccionProveedor;
    @FXML private TextField inputTelefonoProveedor;

    @FXML private Button btnGuardarProveedor;
    @FXML private Button btnCancelarProveedor;

    public void setParentController(ProveedoresController parentController) {
        this.parentController = parentController;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;

        inputIdProveedor.setText(String.valueOf(proveedor.getId()));
        inputNombreProveedor.setText(proveedor.getNombre());
        inputDireccionProveedor.setText(proveedor.getDireccion());
        inputTelefonoProveedor.setText(proveedor.getTelefono());
    }

    @FXML private void handleBtnGuardar(ActionEvent event) {
        try {
            if (proveedor == null) {
                // Caso crear Empleado
                Proveedor nuevoProveedor = new Proveedor(
                    0,
                    inputNombreProveedor.getText(),
                    inputDireccionProveedor.getText(),
                    inputDireccionProveedor.getText(),
                    new Date(),
                    new Date(),
                    true
                );

                Proveedor creado = proveedorService.createProveedor(nuevoProveedor);

                if (parentController != null) {
                    parentController.agregarProveedor(creado);
                }

            } else {
                // Caso editar Empleado
                proveedor.setNombre(inputNombreProveedor.getText());
                proveedor.setDireccion(inputDireccionProveedor.getText());
                proveedor.setTelefono(inputTelefonoProveedor.getText());
                proveedor.setFechaModificacion(new Date());

                Proveedor actualizado = proveedorService.updateProveedor(Integer.parseInt(String.valueOf(proveedor.getId())), proveedor);

                if (parentController != null) {
                    parentController.actualizarProveedor(actualizado);
                }
            }

            Stage currentStage = (Stage) btnGuardarProveedor.getScene().getWindow();
            currentStage.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @FXML private void handleBtnCancelar(ActionEvent event) {
        Stage currentStage = (Stage) btnCancelarProveedor.getScene().getWindow();
        currentStage.close();
    }
}
