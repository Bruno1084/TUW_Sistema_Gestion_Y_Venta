package com.controller;

import com.controller.report.ComprasPorFechaController;
import com.controller.report.ComprasPorProductoController;
import com.controller.report.ComprasPorProveedorController;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class ReportesController implements ParentAware {
    private SidebarController parentController;

    // SearchBar Reportes
    @FXML private TextField inputBuscarReporte;

    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    @FXML private void handleComprasPorProveedor() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/report/ComprasPorProveedor.fxml"));
            Parent root = fxmlLoader.load();

            ComprasPorProveedorController comprasPorProveedorController = fxmlLoader.getController();
            comprasPorProveedorController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleComprasPorProducto() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/report/ComprasPorProducto.fxml"));
            Parent root = fxmlLoader.load();

            ComprasPorProductoController comprasPorProductoController = fxmlLoader.getController();
            comprasPorProductoController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleComprasPorFecha() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/report/ComprasPorProducto.fxml"));
            Parent root = fxmlLoader.load();

            ComprasPorFechaController comprasPorFechaController = fxmlLoader.getController();
            comprasPorFechaController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleVentasPorCliente() {

    }

    @FXML private void handleVentasPorProducto() {

    }

    @FXML private void handleVentasPorFecha() {

    }

}
