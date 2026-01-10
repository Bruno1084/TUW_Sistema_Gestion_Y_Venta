package com.controller;

import com.controller.add.AddRubroController;
import com.controller.detail.DetailRubroController;
import com.model.Rubro;
import com.service.RubroService;
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

public class RubrosController implements ParentAware {
    private SidebarController parentController;
    private final RubroService rubroService = new RubroService();
    private final ObservableList<Rubro> rubros = FXCollections.observableArrayList();

    private String filtroActual = "nombre";
    private final ObservableList<Rubro> rubrosOriginal = FXCollections.observableArrayList();

    // TableView Marcas
    @FXML private TableView<Rubro> tableRubros;
    @FXML private TableColumn<Rubro, Integer> columnIdRubro;
    @FXML private TableColumn<Rubro, String> columnNombreRubro;
    @FXML private TableColumn<Rubro, Date> columnFechaCreacionRubro;
    @FXML private TableColumn<Rubro, Date> columnFechaModificacionRubro;

    // Buttons
    @FXML private MenuButton btnFiltrarRubro;
    @FXML private Button btnAniadirRubro;
    @FXML private MenuItem menuItemId;
    @FXML private MenuItem menuItemNombre;

    // SearchBar Rubros
    @FXML TextField inputBuscarRubro;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarRubro() {
        try {
            Rubro[] lista = rubroService.getAll();
            rubrosOriginal.clear();
            rubrosOriginal.setAll(lista);

            rubros.clear();
            rubros.setAll(lista);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar rubros");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void cargarDetalleRubro(Rubro rubro) {
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

    private void aplicarFiltro() {
        String input = inputBuscarRubro.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            rubros.setAll(rubrosOriginal);
        } else {
            List<Rubro> filtrados = rubrosOriginal.stream()
                    .filter(p -> coincideFiltro(p, input))
                    .toList();

            rubros.setAll(filtrados);
        }
    }

    private boolean coincideFiltro(Rubro r, String input) {
        return switch (filtroActual) {
            case "id" -> String.valueOf(r.getId()).contains(input);
            case "nombre" -> r.getNombre() != null && r.getNombre().toLowerCase().contains(input);
            default -> false;
        };
    }

    // FXML Methods
    @FXML public void initialize() {
        // Configurar TableView
        tableRubros.setItems(rubros);
        columnIdRubro.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNombreRubro.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnFechaCreacionRubro.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        columnFechaModificacionRubro.setCellValueFactory(new PropertyValueFactory<>("fechaModificacion"));

        tableRubros.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        cargarDetalleRubro(newSelection);
                }
        );

        // Configurar MenuButton
        menuItemId.setOnAction(e -> {
            filtroActual = "id";
            btnFiltrarRubro.setText("ID");
            aplicarFiltro();
        });

        menuItemNombre.setOnAction(e -> {
            filtroActual = "nombre";
            btnFiltrarRubro.setText("Nombre");
            aplicarFiltro();
        });

        inputBuscarRubro.textProperty().addListener((obs, oldText, newText) -> aplicarFiltro());

        cargarRubro();
    }

    @FXML private void handleAniadirRubro() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/add/AddRubro.fxml"));
            Parent root = fxmlLoader.load();

            AddRubroController addRubroController = fxmlLoader.getController();
            addRubroController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear el rubro");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

}
