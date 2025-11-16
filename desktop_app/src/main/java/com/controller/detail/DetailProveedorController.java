package com.controller.detail;

import com.controller.ProveedoresController;
import com.controller.SidebarController;
import com.controller.edit.EditProveedorController;
import com.model.Proveedor;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DetailProveedorController implements ParentAware {
    private Proveedor proveedor;
    private SidebarController parentController;

    // Buttons
    @FXML Button btnEditarProveedor;
    @FXML Button btnCerrarProveedor;

    // Text
    @FXML Text txtTituloProveedor;
    @FXML Text txtIdProveedor;
    @FXML Text txtNombreProveedor;
    @FXML Text txtDireccionProveedor;
    @FXML Text txtTelefonoProveedor;
    @FXML Text txtFechaCreacionProveedor;
    @FXML Text txtFechaModificacionProveedor;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        txtTituloProveedor.setText(proveedor.getNombre());
        txtIdProveedor.setText(String.valueOf(proveedor.getId()));
        txtNombreProveedor.setText(proveedor.getNombre());
        txtDireccionProveedor.setText(proveedor.getDireccion());
        txtTelefonoProveedor.setText(proveedor.getTelefono());
        txtFechaCreacionProveedor.setText(String.valueOf(proveedor.getFechaCreacion()));
        txtFechaModificacionProveedor.setText(String.valueOf(proveedor.getFechaModificacion()));
    }

    // FXML Methods
    @FXML private void handleEditarProveedor() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/edit/EditProveedor.fxml"));
            Parent root = fxmlLoader.load();

            EditProveedorController editProveedorController = fxmlLoader.getController();
            editProveedorController.setParentController(parentController);
            editProveedorController.setProveedor(proveedor);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCerrarProveedor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Proveedores.fxml"));
            Parent root = loader.load();

            ProveedoresController proveedoresController = loader.getController();
            proveedoresController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
