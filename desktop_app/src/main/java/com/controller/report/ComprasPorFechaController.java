package com.controller.report;

import com.controller.SidebarController;
import com.controller.detail.DetailCompraController;
import com.model.Compra;
import com.service.CompraService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Date;

public class ComprasPorFechaController {
    private SidebarController parentController;
    private final CompraService compraService = new CompraService();
    private final ObservableList<Compra> compras = FXCollections.observableArrayList();

    // Filtros
    @FXML
    private MenuButton menuButtonFechas;
    @FXML private TextField inputCliente;

    // Buttons
    @FXML private Button btnEjecutarReporte;
    @FXML private Button btnExportar;

    // TableView
    @FXML private TableView<Compra> tableCompras;
    @FXML private TableColumn<Compra, Integer> columnIdCompra;
    @FXML private TableColumn<Compra, String> columnProveedorCompra;
    @FXML private TableColumn<Compra, String> columnUsuarioCompra;
    @FXML private TableColumn<Compra, Float> columnPrecioTotalCompra;
    @FXML private TableColumn<Compra, Date> columnFechaCreacionCompra;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarCompras() {
        try {
            Compra[] lista = compraService.getAll();
            compras.clear();
            compras.addAll(lista);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar compras");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void cargarDetalleCompra(Compra compra) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailCompra.fxml"));
            Parent root = fxmlLoader.load();

            DetailCompraController detailCompraController = fxmlLoader.getController();
            detailCompraController.setParentController(parentController);
            detailCompraController.setCompra(compra);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // FXML Methods
    @FXML private void initialize() {
        // Configurar TableView
        tableCompras.setItems(compras);
        columnIdCompra.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnUsuarioCompra.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        columnProveedorCompra.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProveedor().getNombre()));
        columnUsuarioCompra.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUsuario().getNombre()));
        columnPrecioTotalCompra.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        columnFechaCreacionCompra.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));

        tableCompras.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        cargarDetalleCompra(newSelection);
                }
        );

        cargarCompras();
    }

}
