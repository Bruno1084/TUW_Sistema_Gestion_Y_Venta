package com.controller;

import com.controller.add.AddCompraController;
import com.controller.detail.DetailCompraController;
import com.model.Compra;
import com.service.CompraService;
import com.util.ParentAware;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.util.Arrays;
import java.util.Date;

public class ComprasController implements ParentAware {
    private SidebarController parentController;
    private final CompraService compraService = new CompraService();
    private final ObservableList<Compra> compras = FXCollections.observableArrayList();

    // Table View Compras
    @FXML private TableView<Compra> tableCompras;
    @FXML private TableColumn<Compra, Integer> columnIdCompra;
    @FXML private TableColumn<Compra, String> columnProveedorCompra;
    @FXML private TableColumn<Compra, String> columnUsuarioCompra;
    @FXML private TableColumn<Compra, Float> columnPrecioTotalCompra;
    @FXML private TableColumn<Compra, Date> columnFechaCreacionCompra;

    // Buttons
    @FXML private MenuButton btnConfigCompra;
    @FXML private MenuButton btnFiltrarCompra;
    @FXML private Button btnAniadirCompra;

    // Search Bar Compra
    @FXML private TextField inputBuscarCompra;

    // Text
    @FXML private Text txtGastosTotalesPrecio;
    @FXML private Text txtComprasMensualesTotal;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    private void cargarCompras() {
        try {
            Compra[] lista = compraService.getAllWithDetailCompra();
            compras.clear();
            compras.addAll(Arrays.asList(lista));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar compras");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void cargarDetalleCliente(Compra compra) {
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
    @FXML public void initialize() {
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
                        cargarDetalleCliente(newSelection);
                }
        );

        cargarCompras();
    }

    @FXML private void handleBtnAniadir() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/add/AddCompra.fxml"));
            Parent root = fxmlLoader.load();

            AddCompraController addCompraController = fxmlLoader.getController();
            addCompraController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear la compra");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    @FXML private void handleBtnFiltrar() {

    }
}
