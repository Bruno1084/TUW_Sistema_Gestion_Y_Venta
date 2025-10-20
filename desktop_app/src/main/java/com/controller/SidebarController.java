package com.controller;

import com.util.ParentAware;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class SidebarController {
    @FXML private BorderPane borderPaneMainBoard;
    @FXML private Button btnSidebarInicio;
    @FXML private Button btnSidebarProductos;
    @FXML private Button btnSidebarOrdenVentas;
    @FXML private Button btnSidebarOrdenCompras;
    @FXML private Button btnSidebarClientes;
    @FXML private Button btnSidebarProveedores;
    @FXML private Button btnSidebarEmpleados;
    @FXML private Button btnSidebarReportes;

    public BorderPane getMainBorderPane() {
        return borderPaneMainBoard;
    }

    @FXML private void loadCenterView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane view = loader.load();

            Object controller = loader.getController();
            if (controller instanceof ParentAware aware) {
                aware.setParentController(this);
            }

            borderPaneMainBoard.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleBtnSidebarInicio(ActionEvent event) {
        loadCenterView("/com/fxml/Inicio.fxml");
    }

    @FXML private void handleBtnSidebarProductos (ActionEvent event) {
        loadCenterView("/com/fxml/Productos.fxml");
    }

    @FXML private void handleBtnSidebarOrdenVentas (ActionEvent event) {
        loadCenterView("/com/fxml/Ventas.fxml");
    }

    @FXML private void handleBtnSidebarOrdenCompras(ActionEvent event) {
        loadCenterView("/com/fxml/Compras.fxml");
    }

    @FXML private void handleBtnSidebarClientes (ActionEvent event) {
        loadCenterView("/com/fxml/Clientes.fxml");
    }

    @FXML private void handleBtnSidebarProveedores (ActionEvent event) {
        loadCenterView("/com/fxml/Proveedores.fxml");
    }

    @FXML private void handleBtnSidebarEmpleados (ActionEvent event) {
        loadCenterView("/com/fxml/Empleados.fxml");
    }

    @FXML private void handleBtnSidebarReportes (ActionEvent event) {
        loadCenterView("/com/fxml/Reportes.fxml");
    }

    @FXML private void handleBtnSidebarCategorias (ActionEvent event) { }

    @FXML private void handleBtnSidebarMarcas (ActionEvent event) { }
}
