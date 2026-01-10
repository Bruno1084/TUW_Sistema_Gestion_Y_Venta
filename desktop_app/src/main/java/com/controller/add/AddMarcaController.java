package com.controller.add;

import com.controller.MarcasController;
import com.controller.SidebarController;
import com.model.Marca;
import com.service.MarcaService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.Date;

public class AddMarcaController implements ParentAware {
    private SidebarController parentController;
    private final MarcaService marcaService = new MarcaService();
    private Marca marca;

    // Buttons
    @FXML private Button btnAniadirMarca;
    @FXML private Button btnCancelarMarca;

    // Text
    @FXML private Text txtTituloMarca;
    @FXML private TextField inputNombreMarca;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
        txtTituloMarca.setText(marca.getNombre());
        inputNombreMarca.setText(marca.getNombre());
    }

    // FXML Methods
    @FXML private void handleAniadirMarca() {
        try {
            Marca nuevaMarca = new Marca(
                    0,
                    inputNombreMarca.getText(),
                    new Date(),
                    new Date()
            );

            marcaService.create(nuevaMarca);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Marcas.fxml"));
            Parent root = fxmlLoader.load();

            MarcasController marcasController = fxmlLoader.getController();
            marcasController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCancelarMarca() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Marcas.fxml"));
            Parent root = fxmlLoader.load();

            MarcasController marcasController = fxmlLoader.getController();
            marcasController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}