package com.controller;

import com.controller.modal.ModalCompraController;
import com.controller.modal.ModalDetalleCompra;
import com.model.Compra;
import com.service.CompraService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class ComprasController {
    private final CompraService compraService = new CompraService();
    private final ObservableList<Compra> compras = FXCollections.observableArrayList();

    // Table View Compras
    @FXML private TableView<Compra> tableCompras;
    @FXML private TableColumn<Compra, Integer> columnIdCompra;
    @FXML private TableColumn<Compra, String> columnProveedorCompra;
    @FXML private TableColumn<Compra, String> columnUsuarioCompra;
    @FXML private TableColumn<Compra, Float> columnPrecioTotalCompra;
    @FXML private TableColumn<Compra, Date> columnFechaCreacionCompra;
    @FXML private TableColumn<Compra, Void> columnDetalleCompra;

    // Buttons
    @FXML private MenuButton btnFiltrarCompra;
    @FXML private Button btnAniadirCompra;
    @FXML private Button btnDetalleCompra;

    // Search Bar Compra
    @FXML private TextField inputBuscarCompra;

    // Text
    @FXML private Text txtGastosTotalesPrecio;
    @FXML private Text txtComprasMensualesTotal;

    // Helper Methods
    private void agregarCompra(Compra compra) {
        compras.add(compra);
        tableCompras.getSelectionModel().select(compra);
        tableCompras.scrollTo(compra);
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

    private void agregarBotonDetalle() {
        Callback<TableColumn<Compra, Void>, TableCell<Compra, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Compra, Void> call(final TableColumn<Compra, Void> param) {
                return new TableCell<>() {

                    private final Button btnDetalle = new Button("Detalle");

                    {
                        btnDetalle.setOnAction(event -> {
                            Compra compra = getTableView().getItems().get(getIndex());
                            mostrarModalDetalle(compra);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnDetalle);
                        }
                    }
                };
            }
        };
        columnDetalleCompra.setCellFactory(cellFactory);
    }

    private void mostrarModalDetalle(Compra compra) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/modal/ModalDetalleCompra.fxml"));
            Parent root = loader.load();

            ModalDetalleCompra controller = loader.getController();
            controller.setCompra(compra);

            Stage stage = new Stage();
            stage.setTitle("Detalle de Compra #" + compra.getId());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tableCompras.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al abrir detalle");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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
        agregarBotonDetalle();

        cargarCompras();
    }

    @FXML private void handleBtnAniadir(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/modal/ModalCompra.fxml"));
            Parent root = fxmlLoader.load();

            ModalCompraController modalController = fxmlLoader.getController();
            modalController.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Añadir Compra");
            stage.setResizable(false);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear la compra");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    @FXML private void handleBtnFiltrar(ActionEvent event) {

    }
}
