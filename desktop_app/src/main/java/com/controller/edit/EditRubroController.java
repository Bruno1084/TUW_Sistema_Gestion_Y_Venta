package com.controller.edit;

import com.controller.SidebarController;
import com.controller.detail.DetailRubroController;
import com.model.Rubro;
import com.service.RubroService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EditRubroController implements ParentAware {
    private SidebarController parentController;
    private final RubroService rubroService = new RubroService();
    private Rubro rubro;

    // Buttons
    @FXML
    private Button btnEditarRubro;
    @FXML private Button btnCancelarRubro;

    // Text
    @FXML private Text txtTituloRubro;
    @FXML private Text txtIdRubro;
    @FXML private TextField inputNombreRubro;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
        txtTituloRubro.setText(rubro.getNombre());
        txtIdRubro.setText(String.valueOf(rubro.getId()));
        inputNombreRubro.setText(rubro.getNombre());
    }

    // FXML Methods
    @FXML private void handleEditarRubro() {
        try {
            rubro.setId(Integer.parseInt(txtIdRubro.getText()));
            rubro.setNombre(inputNombreRubro.getText());

            Rubro actualizado = rubroService.update(rubro.getId(), rubro);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailRubro.fxml"));
            Parent root = fxmlLoader.load();

            DetailRubroController detailRubroController = fxmlLoader.getController();
            detailRubroController.setParentController(parentController);
            detailRubroController.setRubro(actualizado);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCancelarRubro() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailRubro.fxml"));
            Parent root = fxmlLoader.load();

            DetailRubroController detailRubroController = fxmlLoader.getController();
            detailRubroController.setParentController(parentController);
            detailRubroController.setRubro(rubro);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
