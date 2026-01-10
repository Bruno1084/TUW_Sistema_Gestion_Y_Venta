package com.controller;

import com.controller.report.ComprasPorProductoController;
import com.controller.report.ComprasPorProveedorController;
import com.controller.report.VentasPorClienteController;
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

    @FXML private void handleVentasPorCliente() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/report/VentasPorCliente.fxml"));
            Parent root = fxmlLoader.load();

            VentasPorClienteController ventasPorClienteController = fxmlLoader.getController();
            ventasPorClienteController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleVentasPorProducto() {
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
}
