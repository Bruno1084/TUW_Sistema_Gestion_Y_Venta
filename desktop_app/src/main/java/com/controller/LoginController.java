package com.controller;

import javafx.fxml.FXML;
import com.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField inputNombre;
    @FXML private TextField inputContrasenia;
    @FXML private Button btnIniciarSesion;
    @FXML private Hyperlink linkOlvidoContrasenia;

    @FXML private void handleLogin(ActionEvent event) {
        AuthService authService = new AuthService();

        try {
            if (!inputNombre.getText().isBlank() && !inputContrasenia.getText().isBlank()) {
//                String token = authService.login(inputNombre.getText(), inputContrasenia.getText());
//                SessionManager.setToken(token);

                // Redireccionar a Main
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/sidebar.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Sistema Gestión y Venta");
                stage.show();

                Stage currentStage = (Stage) btnIniciarSesion.getScene().getWindow();
                currentStage.close();
            }
         } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}