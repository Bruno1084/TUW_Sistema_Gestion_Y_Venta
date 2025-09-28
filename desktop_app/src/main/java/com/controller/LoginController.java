package com.controller;

import com.model.SessionManager;
import com.model.AuthResponse;
import javafx.fxml.FXML;
import com.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML private Text txtHeaderTitle;
    @FXML private TextField inputNombre;
    @FXML private TextField inputContrasenia;
    @FXML private Button btnIniciarSesion;
    @FXML private Hyperlink linkOption;

    @FXML
    private void initialize() {
        btnIniciarSesion.setOnAction(this::handleAction);
    }

    @FXML
    private void handleAction(ActionEvent event) {
        AuthService authService = new AuthService();

        try {
            if (!inputNombre.getText().isBlank() && !inputContrasenia.getText().isBlank()) {
                if (txtHeaderTitle.getText().equals("Iniciar Sesión")) {
                    AuthResponse authResponse = authService.login(
                            inputNombre.getText(),
                            inputContrasenia.getText()
                    );
                    SessionManager.getInstance().setToken(authResponse.getToken());
                    redirectToMain();

                } else {
                    authService.register(
                            inputNombre.getText(),
                            inputContrasenia.getText()
                    );
                    showInfo("Usuario registrado con éxito. Ahora puede iniciar sesión.");

                    txtHeaderTitle.setText("Iniciar Sesión");
                    btnIniciarSesion.setText("Iniciar Sesión");
                    linkOption.setText("Registrar Usuario");
                    inputNombre.clear();
                    inputContrasenia.clear();
                }
            } else {
                showError("Debe completar todos los campos.");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void handleLinkAction(ActionEvent event) {
        if (txtHeaderTitle.getText().equals("Iniciar Sesión")) {
            txtHeaderTitle.setText("Registrar Usuario");
            btnIniciarSesion.setText("Registrar");
            linkOption.setText("Iniciar Sesión");
        } else {
            txtHeaderTitle.setText("Iniciar Sesión");
            btnIniciarSesion.setText("Iniciar Sesión");
            linkOption.setText("Registrar Usuario");
        }
    }

    private void redirectToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/sidebar.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Sistema Gestión y Venta");
        stage.show();

        Stage currentStage = (Stage) btnIniciarSesion.getScene().getWindow();
        currentStage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
