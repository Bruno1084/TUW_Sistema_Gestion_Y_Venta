package com.controller.detail;

import com.controller.MarcasController;
import com.controller.SidebarController;
import com.controller.add.AddMarcaController;
import com.controller.edit.EditMarcaController;
import com.model.Marca;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DetailMarcaController implements ParentAware {
    private Marca marca;
    private SidebarController parentController;

    // Buttons
    @FXML Button btnEditarMarca;
    @FXML Button btnCerrarMarca;

    // Text
    @FXML Text txtTituloMarca;
    @FXML Text txtIdMarca;
    @FXML Text txtNombreMarca;
    @FXML Text txtFechaCreacionMarca;
    @FXML Text txtFechaModificacionMarca;
    @FXML Text txtCantidadProductosMarca;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setMarca(Marca marca) {
        txtTituloMarca.setText(marca.getNombre());
        txtIdMarca.setText(String.valueOf(marca.getId()));
        txtNombreMarca.setText(marca.getNombre());
        txtFechaCreacionMarca.setText(String.valueOf(marca.getFechaCreacion()));
        txtFechaModificacionMarca.setText(String.valueOf(marca.getFechaModificacion()));
        this.marca = marca;
    }

    // FXML Methods
    @FXML private void handleEditarMarca() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/edit/EditMarca.fxml"));
            Parent root = fxmlLoader.load();

            EditMarcaController editMarcaController = fxmlLoader.getController();
            editMarcaController.setParentController(parentController);
            editMarcaController.setMarca(marca);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCerrarMarca() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Marcas.fxml"));
            Parent marcasView = loader.load();

            MarcasController marcasController = loader.getController();
            marcasController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(marcasView);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
