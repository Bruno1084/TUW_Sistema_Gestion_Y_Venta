package com.controller.edit;

import com.controller.SidebarController;
import com.controller.detail.DetailMarcaController;
import com.model.Marca;
import com.service.MarcaService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EditMarcaController implements ParentAware {
    private SidebarController parentController;
    private final MarcaService marcaService = new MarcaService();
    private Marca marca;

    // Buttons
    @FXML
    private Button btnEditarMarca;
    @FXML private Button btnCancelarMarca;

    // Text
    @FXML private Text txtTituloMarca;
    @FXML private Text txtIdMarca;
    @FXML private TextField inputNombreMarca;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
        txtTituloMarca.setText(marca.getNombre());
        txtIdMarca.setText(String.valueOf(marca.getId()));
        inputNombreMarca.setText(marca.getNombre());
    }

    // FXML Methods
    @FXML private void handleEditarMarca() {
        try {
            marca.setId(Integer.parseInt(txtIdMarca.getText()));
            marca.setNombre(inputNombreMarca.getText());

            Marca actualizado = marcaService.update(marca.getId(), marca);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailMarca.fxml"));
            Parent root = fxmlLoader.load();

            DetailMarcaController detailMarcaController = fxmlLoader.getController();
            detailMarcaController.setParentController(parentController);
            detailMarcaController.setMarca(actualizado);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCancelarMarca() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailMarca.fxml"));
            Parent root = fxmlLoader.load();

            DetailMarcaController detailMarcaController = fxmlLoader.getController();
            detailMarcaController.setParentController(parentController);
            detailMarcaController.setMarca(marca);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
