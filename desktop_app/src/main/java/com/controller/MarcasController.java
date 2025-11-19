package com.controller;

import com.controller.add.AddMarcaController;
import com.controller.detail.DetailMarcaController;
import com.model.Marca;
import com.service.MarcaService;
import com.util.ParentAware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MarcasController implements ParentAware {
    private SidebarController parentController;
    private final MarcaService marcaService = new MarcaService();
    private final ObservableList<Marca> marcas = FXCollections.observableArrayList();

    private String filtroActual = "nombre";
    private final ObservableList<Marca> marcasOriginal = FXCollections.observableArrayList();

    // TableView Marcas
    @FXML private TableView<Marca> tableMarcas;
    @FXML private TableColumn<Marca, Integer> columnIdMarca;
    @FXML private TableColumn<Marca, String> columnNombreMarca;
    @FXML private TableColumn<Marca, Date> columnFechaCreacionMarca;
    @FXML private TableColumn<Marca, Date> columnFechaModificacionMarca;

    // Buttons
    @FXML private MenuButton btnFiltrarMarca;
    @FXML private Button btnAniadirMarca;
    @FXML private MenuItem menuItemId;
    @FXML private MenuItem menuItemNombre;

    // SearchBar Marcas
    @FXML TextField inputBuscarMarca;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarMarca() {
        try {
            Marca[] lista = marcaService.getAll();
            marcasOriginal.clear();
            marcasOriginal.setAll(lista);

            marcas.clear();
            marcas.setAll(lista);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar marcas");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void cargarDetalleMarca(Marca marca) {
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

    private void aplicarFiltro() {
        String input = inputBuscarMarca.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            marcas.setAll(marcasOriginal);
        } else {
            List<Marca> filtrados = marcasOriginal.stream()
                    .filter(p -> coincideFiltro(p, input))
                    .toList();

            marcas.setAll(filtrados);
        }
    }

    private boolean coincideFiltro(Marca m, String input) {
        return switch (filtroActual) {
            case "id" -> String.valueOf(m.getId()).contains(input);
            case "nombre" -> m.getNombre() != null && m.getNombre().toLowerCase().contains(input);
            default -> false;
        };
    }

    // FXML Methods
    @FXML public void initialize() {
        // Configurar TableView
        tableMarcas.setItems(marcas);
        columnIdMarca.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNombreMarca.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnFechaCreacionMarca.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        columnFechaModificacionMarca.setCellValueFactory(new PropertyValueFactory<>("fechaModificacion"));

        tableMarcas.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        cargarDetalleMarca(newSelection);
                }
        );

        // Configurar MenuButton
        menuItemId.setOnAction(e -> {
            filtroActual = "id";
            btnFiltrarMarca.setText("ID");
            aplicarFiltro();
        });

        menuItemNombre.setOnAction(e -> {
            filtroActual = "nombre";
            btnFiltrarMarca.setText("Nombre");
            aplicarFiltro();
        });

        inputBuscarMarca.textProperty().addListener((obs, oldText, newText) -> aplicarFiltro());

        cargarMarca();
    }

    @FXML private void handleAniadirMarca() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/add/AddMarca.fxml"));
            Parent root = fxmlLoader.load();

            AddMarcaController addMarcaController = fxmlLoader.getController();
            addMarcaController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear la marca");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

}
