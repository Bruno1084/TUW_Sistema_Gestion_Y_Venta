package com.controller.add;

import com.controller.EmpleadosController;
import com.controller.SidebarController;
import com.model.Empleado;
import com.service.EmpleadoService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.Date;

public class AddEmpleadoController implements ParentAware {
    private SidebarController parentController;
    private final EmpleadoService empleadoService = new EmpleadoService();
    private Empleado empleado;

    // Buttons
    @FXML private Button btnAniadirEmpleado;
    @FXML private Button btnCancelarEmpleado;

    // Text
    @FXML private Text txtTituloEmpleado;
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
        inputNombreEmpleado.setText(empleado.getNombre());
        inputDireccionEmpleado.setText(empleado.getDireccion());
        inputTelefonoEmpleado.setText(empleado.getTelefono());
    }

    // FXML Methods
    @FXML private void handleAniadirEmpleado() {
        try {
            Empleado nuevoEmpleado = new Empleado(
                    0,
                    inputNombreEmpleado.getText(),
                    inputDireccionEmpleado.getText(),
                    inputTelefonoEmpleado.getText(),
                    new Date(),
                    new Date()
            );

            empleadoService.create(nuevoEmpleado);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Empleados.fxml"));
            Parent root = fxmlLoader.load();

            EmpleadosController empleadosController = fxmlLoader.getController();
            empleadosController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCancelarEmpleado() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Empleados.fxml"));
            Parent root = fxmlLoader.load();

            EmpleadosController empleadosController= fxmlLoader.getController();
            empleadosController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
