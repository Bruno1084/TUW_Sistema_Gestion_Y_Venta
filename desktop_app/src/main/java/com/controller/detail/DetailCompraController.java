package com.controller.detail;

import com.controller.ComprasController;
import com.controller.SidebarController;
import com.model.Compra;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DetailCompraController implements ParentAware {
    private Compra compra;
    private SidebarController parentController;

    // Buttons
    @FXML Button btnCerrarCompra;

    // Text
    @FXML Text txtIdCompra;
    @FXML Text txtPrecioTotalCompra;
    @FXML Text txtFechaCreacionCompra;
    @FXML Text txtProveedorCompra;
    @FXML Text txtUsuarioCompra;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setCompra(Compra compra) {
        txtIdCompra.setText(String.valueOf(compra.getId()));
        txtPrecioTotalCompra.setText(String.valueOf(compra.getPrecioTotal()));
        txtFechaCreacionCompra.setText(String.valueOf(compra.getFechaCreacion()));
        txtProveedorCompra.setText(compra.getProveedor().getNombre());
        txtUsuarioCompra.setText(compra.getUsuario().getNombre());
        this.compra = compra;
    }

    // FXML Methods
    @FXML private void habdleCerrarCompra() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Compras.fxml"));
            Parent root = loader.load();

            ComprasController comprasController = loader.getController();
            comprasController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
