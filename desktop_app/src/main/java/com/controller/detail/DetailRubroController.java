package com.controller.detail;

import com.controller.RubrosController;
import com.controller.SidebarController;
import com.controller.edit.EditRubroController;
import com.model.Rubro;
import com.service.RubroService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DetailRubroController implements ParentAware {
    private Rubro rubro;
    private SidebarController parentController;
    private RubroService rubroService = new RubroService();

    // Buttons
    @FXML Button btnEditarRubro;
    @FXML Button btnEliminarRubro;
    @FXML Button btnCerrarRubro;

    // Text
    @FXML Text txtTituloRubro;
    @FXML Text txtIdRubro;
    @FXML Text txtNombreRubro;
    @FXML Text txtFechaCreacionRubro;
    @FXML Text txtFechaModificacionRubro;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setRubro(Rubro rubro) {
        txtTituloRubro.setText(rubro.getNombre());
        txtIdRubro.setText(String.valueOf(rubro.getId()));
        txtNombreRubro.setText(rubro.getNombre());
        txtFechaCreacionRubro.setText(String.valueOf(rubro.getFechaCreacion()));
        txtFechaModificacionRubro.setText(String.valueOf(rubro.getFechaModificacion()));
        this.rubro = rubro;
    }

    // FXML Methods
    @FXML private void handleEditarRubro() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/edit/EditRubro.fxml"));
            Parent root = fxmlLoader.load();

            EditRubroController editRubroController = fxmlLoader.getController();
            editRubroController.setParentController(parentController);
            editRubroController.setRubro(rubro);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleEliminarRubro() {
        try {
            rubroService.delete(rubro.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Rubros.fxml"));
            Parent root = loader.load();

            RubrosController rubrosController = loader.getController();
            rubrosController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCerrarRubro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Rubros.fxml"));
            Parent marcasView = loader.load();

            RubrosController rubrosController = loader.getController();
            rubrosController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(marcasView);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
