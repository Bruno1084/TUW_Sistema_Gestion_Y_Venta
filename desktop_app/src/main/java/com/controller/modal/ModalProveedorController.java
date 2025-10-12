package com.controller.modal;

import com.controller.ProveedoresController;
import com.model.Proveedor;
import com.service.ProveedorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    @FXML private TextField inputNombreProveedor;
    @FXML private TextField inputTelefonoProveedor;
    @FXML private TextField inputDireccionProveedor;

    @FXML private Button btnGuardarProveedor;
    @FXML private Button btnCancelarProveedor;

    public void setParentController(ProveedoresController parentController) {
        this.parentController = parentController;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        inputNombreProveedor.setText(proveedor.getNombre());
        inputTelefonoProveedor.setText(proveedor.getTelefono());
        inputDireccionProveedor.setText(proveedor.getDireccion());
    }

    public void setTxtTituloProveedor(String titulo) {
        this.txtTituloProveedor.setText(titulo);
    }

    @FXML private void handleBtnGuardar(ActionEvent event) {
        try {
            String nombre = inputNombreProveedor.getText().trim();
            String telefono = inputTelefonoProveedor.getText().trim();
            String direccion = inputDireccionProveedor.getText().trim();

            // Validación de campo obligatorio
            if (nombre.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campo obligatorio");
                alert.setHeaderText("Nombre requerido");
                alert.setContentText("Debes ingresar un nombre para el proveedor.");
                alert.showAndWait();
                return;
            }

            if (proveedor == null) {
                // Caso crear Proveedor
                Proveedor nuevoProveedor = new Proveedor(
                        0,
                        nombre,
                        direccion,
                        telefono,
                        new Date(),
                        new Date()
                );

                Proveedor creado = proveedorService.createProveedor(nuevoProveedor);

                if (parentController != null) {
                    parentController.agregarProveedor(creado);
                    parentController.displayProveedor(creado);
                }

            } else {
                // Caso editar Empleado
                proveedor.setNombre(nombre);
                proveedor.setTelefono(direccion);
                proveedor.setDireccion(telefono);
                proveedor.setFechaModificacion(new Date());

                Proveedor actualizado = proveedorService.updateProveedor(proveedor.getId(), proveedor);

                if (parentController != null) {
                    parentController.actualizarProveedor(actualizado);
                    parentController.displayProveedor(actualizado);
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
