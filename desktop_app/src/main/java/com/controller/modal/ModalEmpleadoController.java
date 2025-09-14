package com.controller.modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ModalEmpleadoController {
    @FXML private Text txtTituloEmpleado;
    @FXML private TextField inputIdEmpleado;
    @FXML private TextField inputNombreEmpleado;
    @FXML private TextField inputDireccionEmpleado;
    @FXML private TextField inputTelefonoEmpleado;

    @FXML private Button btnGuardarEmpleado;
    @FXML private Button btnCancelarEmpleado;

    @FXML private void handleBtnGuardar(ActionEvent event) {

    }

    @FXML private void handleBtnCancelar(ActionEvent event) {
        
    }
}
