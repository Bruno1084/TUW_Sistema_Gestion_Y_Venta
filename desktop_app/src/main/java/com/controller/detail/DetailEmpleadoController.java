package com.controller.detail;

import com.controller.EmpleadosController;
import com.controller.SidebarController;
import com.controller.edit.EditEmpleadoController;
import com.model.Empleado;
import com.service.EmpleadoService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DetailEmpleadoController implements ParentAware {
    private Empleado empleado;
    private SidebarController parentController;
    private EmpleadoService empleadoService = new EmpleadoService();

    // Buttons
    @FXML Button btnEditarEmpleado;
    @FXML Button btnEliminarEmpleado;
    @FXML Button btnCerrarEmpleado;

    // Text
    @FXML Text txtTituloEmpleado;
    @FXML Text txtIdEmpleado;
    @FXML Text txtNombreEmpleado;
    @FXML Text txtDireccionEmpleado;
    @FXML Text txtTelefonoEmpleado;
    @FXML Text txtFechaCreacionEmpleado;
    @FXML Text txtFechaModificacionEmpleado;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setEmpleado(Empleado empleado) {
        txtTituloEmpleado.setText(empleado.getNombre());
        txtIdEmpleado.setText(String.valueOf(empleado.getId()));
        txtNombreEmpleado.setText(empleado.getNombre());
        txtDireccionEmpleado.setText(empleado.getDireccion());
        txtTelefonoEmpleado.setText(empleado.getTelefono());
        txtFechaCreacionEmpleado.setText(String.valueOf(empleado.getFechaCreacion()));
        txtFechaModificacionEmpleado.setText(String.valueOf(empleado.getFechaModificacion()));
        this.empleado = empleado;
    }

    // FXML Methods
    @FXML private void handleEditarEmpleado() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/edit/EditEmpleado.fxml"));
            Parent root = fxmlLoader.load();

            EditEmpleadoController editEmpleadoController = fxmlLoader.getController();
            editEmpleadoController.setParentController(parentController);
            editEmpleadoController.setEmpleado(empleado);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleEliminarEmpleado() {
        try {
            empleadoService.delete(empleado.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Empleados.fxml"));
            Parent root = loader.load();

            EmpleadosController empleadosController = loader.getController();
            empleadosController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCerrarEmpleado() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Empleados.fxml"));
            Parent empleadosView = loader.load();

            EmpleadosController empleadosController = loader.getController();
            empleadosController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(empleadosView);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
