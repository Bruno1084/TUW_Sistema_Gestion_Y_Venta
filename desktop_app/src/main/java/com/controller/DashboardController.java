package com.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class DashboardController {
    @FXML private BorderPane borderPaneMainBoard;
    @FXML private Button btnSideBarProductos;
    @FXML private Button btnSideBarVentas;
    @FXML private Button btnSideBarCompras;
    @FXML private Button btnSideBarClientes;
    @FXML private Button btnSideBarProveedores;
    @FXML private Button btnSideBarReportes;

    @FXML private void loadCenterView(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/" + fxmlFile));
            AnchorPane newView = fxmlLoader.load();
            borderPaneMainBoard.setCenter(newView);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleBtnSideBarProductos (ActionEvent event) {
        loadCenterView("Productos.fxml");
    }

    @FXML private void handleBtnSideBarVentas (ActionEvent event) {
        loadCenterView("Ventas.fxml");
    }

    @FXML private void handleBtnSideBarCompras (ActionEvent event) {
        loadCenterView("Compras.fxml");
    }

    @FXML private void handleBtnSideBarClientes (ActionEvent event) {
        loadCenterView("Clientes.fxml");
    }

    @FXML private void handleBtnSideBarProveedores (ActionEvent event) {
        loadCenterView("Proveedores.fxml");
    }

    @FXML private void handleBtnSideBarReportes (ActionEvent event) {
        loadCenterView("Reportes.fxml");
    }
}
