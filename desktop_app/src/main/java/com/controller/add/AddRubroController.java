package com.controller.add;

import com.controller.RubrosController;
import com.controller.SidebarController;
import com.model.Rubro;
import com.service.RubroService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.util.Date;

public class AddRubroController implements ParentAware {
    private SidebarController parentController;
    private final RubroService rubroService = new RubroService();
    private Rubro rubro;

    // Buttons
    @FXML private Button btnAniadirRubro;
    @FXML private Button btnCancelarRubro;

    // Text
    @FXML private Text txtTituloRubro;
    @FXML private TextField inputNombreRubro;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
        txtTituloRubro.setText(rubro.getNombre());
        inputNombreRubro.setText(rubro.getNombre());
    }

    // FXML Methods
    @FXML private void handleAniadirRubro() {
        try {
            Rubro nuevoRubro = new Rubro(
                    0,
                    inputNombreRubro.getText(),
                    new Date(),
                    new Date()
            );

            rubroService.create(nuevoRubro);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Rubros.fxml"));
            Parent root = fxmlLoader.load();

            RubrosController rubrosCotroller = fxmlLoader.getController();
            rubrosCotroller.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCancelarRubro() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Rubros.fxml"));
            Parent root = fxmlLoader.load();

            RubrosController rubrosController = fxmlLoader.getController();
            rubrosController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}