package com.controller.edit;

import com.controller.SidebarController;
import com.controller.detail.DetailEmpleadoController;
import com.model.Empleado;
import com.service.EmpleadoService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EditEmpleadoController implements ParentAware {
    private SidebarController parentController;
    private final EmpleadoService empleadoService = new EmpleadoService();
    private Empleado empleado;

    // Buttons
    @FXML private Button btnEditarEmpleado;
    @FXML private Button btnCancelarEmpleado;

    // Text
    @FXML private Text txtTituloEmpleado;
    @FXML private Text txtIdEmpleado;
    @FXML private TextField inputNombreEmpleado;
    @FXML private TextField inputDireccionEmpleado;
    @FXML private TextField inputTelefonoEmpleado;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
        txtTituloEmpleado.setText(empleado.getNombre());
        txtIdEmpleado.setText(String.valueOf(empleado.getId()));
        inputNombreEmpleado.setText(empleado.getNombre());
        inputDireccionEmpleado.setText(empleado.getDireccion());
        inputTelefonoEmpleado.setText(empleado.getTelefono());
    }

    // FXML Methods
    @FXML private void handleEditarEmpleado() {
        try {
            empleado.setId(Integer.parseInt(txtIdEmpleado.getText()));
            empleado.setNombre(inputNombreEmpleado.getText());
            empleado.setDireccion(inputDireccionEmpleado.getText());
            empleado.setTelefono(inputTelefonoEmpleado.getText());

            Empleado actualizado = empleadoService.updateEmpleado(empleado.getId(), empleado);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailEmpleado.fxml"));
            Parent root = fxmlLoader.load();

            DetailEmpleadoController detailEmpleadoController = fxmlLoader.getController();
            detailEmpleadoController.setParentController(parentController);
            detailEmpleadoController.setEmpleado(actualizado);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCancelarEmpleado() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailEmpleado.fxml"));
            Parent root = fxmlLoader.load();

            DetailEmpleadoController detailEmpleadoController = fxmlLoader.getController();
            detailEmpleadoController.setParentController(parentController);
            detailEmpleadoController.setEmpleado(empleado);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
