package com.controller;

import com.model.Empleado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EmpleadosController {
    @FXML private TextField inputSearchIdEmpleado;
    @FXML private TextField inputSearchNombreEmpleado;
    @FXML private TextField inputSearchDireccionEmpleado;
    @FXML private TextField inputSearchTelefonoEmpleado;

    @FXML private TableView<Empleado> tableEmpleados;
    @FXML private TableColumn<Empleado, String> columnIdEmpleado;
    @FXML private TableColumn<Empleado, String> columnNombreEmpleado;
    @FXML private TableColumn<Empleado, String> columnDireccionEmpleado;
    @FXML private TableColumn<Empleado, String> columnTelefonoEmpleado;

    @FXML private Button btnAniadirEmpleado;


    @FXML
    private void handleAniadirEmpleado(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/ModalEmpleado.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Añadir Empleado");
            stage.setResizable(false);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
