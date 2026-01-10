package com.controller.add;

import com.controller.ProveedoresController;
import com.controller.SidebarController;
import com.model.Proveedor;
import com.service.ProveedorService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.Date;

public class AddProveedorController implements ParentAware {
    private SidebarController parentController;
    private final ProveedorService proveedorService = new ProveedorService();
    private Proveedor proveedor;

    // Buttons
    @FXML private Button btnAniadirProveedor;
    @FXML private Button btnCancelarProveedor;

    // Text
    @FXML private Text txtTituloProveedor;
    @FXML private TextField inputNombreProveedor;
    @FXML private TextField inputDireccionProveedor;
    @FXML private TextField inputTelefonoProveedor;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        inputNombreProveedor.setText(proveedor.getNombre());
        inputDireccionProveedor.setText(proveedor.getDireccion());
        inputTelefonoProveedor.setText(proveedor.getTelefono());
    }

    // FXML Methods
    @FXML private void handleAniadirProveedor() {
        try {
            Proveedor nuevoProveedor = new Proveedor(
                    0,
                    inputNombreProveedor.getText(),
                    inputDireccionProveedor.getText(),
                    inputTelefonoProveedor.getText(),
                    new Date(),
                    new Date()
            );

            proveedorService.create(nuevoProveedor);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Proveedores.fxml"));
            Parent root = fxmlLoader.load();

            ProveedoresController proveedoresController = fxmlLoader.getController();
            proveedoresController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCerrarProveedor() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Proveedores.fxml"));
            Parent root = fxmlLoader.load();

            ProveedoresController proveedoresController= fxmlLoader.getController();
            proveedoresController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
