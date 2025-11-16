package com.controller.edit;

import com.controller.SidebarController;
import com.controller.detail.DetailProveedorController;
import com.model.Proveedor;
import com.service.ProveedorService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EditProveedorController implements ParentAware {
    private SidebarController parentController;
    private final ProveedorService proveedorService = new ProveedorService();
    private Proveedor proveedor;

    // Buttons
    @FXML private Button btnEditarProveedor;
    @FXML private Button btnCancelarProveedor;

    // Text
    @FXML private Text txtTituloProveedor;
    @FXML private Text txtIdProveedor;
    @FXML private TextField inputNombreProveedor;
    @FXML private TextField inputDireccionProveedor;
    @FXML private TextField inputTelefonoProveedor;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        txtIdProveedor.setText(String.valueOf(proveedor.getId()));
        inputNombreProveedor.setText(proveedor.getNombre());
        inputDireccionProveedor.setText(proveedor.getDireccion());
        inputTelefonoProveedor.setText(proveedor.getTelefono());
    }

    // FXML Methods
    @FXML private void handleEditarProveedor() {
        try {
            proveedor.setId(Integer.parseInt(txtIdProveedor.getText()));
            proveedor.setNombre(inputNombreProveedor.getText());
            proveedor.setDireccion(inputDireccionProveedor.getText());
            proveedor.setTelefono(inputTelefonoProveedor.getText());

            Proveedor actualizado = proveedorService.updateProveedor(proveedor.getId(), proveedor);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailProveedor.fxml"));
            Parent root = fxmlLoader.load();

            DetailProveedorController detailProveedorController = fxmlLoader.getController();
            detailProveedorController.setParentController(parentController);
            detailProveedorController.setProveedor(actualizado);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCerrarProveedor() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailProveedor.fxml"));
            Parent root = fxmlLoader.load();

            DetailProveedorController detailProveedorController = fxmlLoader.getController();
            detailProveedorController.setParentController(parentController);
            detailProveedorController.setProveedor(proveedor);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
