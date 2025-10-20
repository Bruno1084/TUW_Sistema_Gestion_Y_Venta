package com.controller;

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

    @FXML private void loadCenterView(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/" + fxmlFile));
            AnchorPane newView = fxmlLoader.load();
            borderPaneMainBoard.setCenter(newView);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleBtnSidebarInicio(ActionEvent event) {
        loadCenterView("Inicio.fxml");
    }

    @FXML private void handleBtnSidebarProductos (ActionEvent event) {
        loadCenterView("Productos.fxml");
    }

    @FXML private void handleBtnSidebarOrdenVentas (ActionEvent event) {
        loadCenterView("Ventas.fxml");
    }

    @FXML private void handleBtnSidebarOrdenCompras(ActionEvent event) {
        loadCenterView("Compras.fxml");
    }

    @FXML private void handleBtnSidebarClientes (ActionEvent event) {
        loadCenterView("Clientes.fxml");
    }

    @FXML private void handleBtnSidebarProveedores (ActionEvent event) {
        loadCenterView("Proveedores.fxml");
    }

    @FXML private void handleBtnSidebarEmpleados (ActionEvent event) {
        loadCenterView("Empleados.fxml");
    }

    @FXML private void handleBtnSidebarReportes (ActionEvent event) {
        loadCenterView("Reportes.fxml");
    }

    @FXML private void handleBtnSidebarCategorias (ActionEvent event) { }

    @FXML private void handleBtnSidebarMarcas (ActionEvent event) { }
}
