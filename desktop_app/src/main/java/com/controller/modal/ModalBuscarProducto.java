package com.controller.modal;

import com.controller.add.AddCompraController;
import com.model.Producto;
import com.service.ProductoService;
import com.util.ProductoSeleccionable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModalBuscarProducto {
    private final ProductoService productoService = new ProductoService();
    private ProductoSeleccionable parentController;
    private final ObservableList<Producto> productos = FXCollections.observableArrayList();

    // TableView
    @FXML private TableView<Producto> tableProductos;
    @FXML private TableColumn<Producto, String> columnCodigoProducto;
    @FXML private TableColumn<Producto, String> columnDescripcionProducto;
    @FXML private TableColumn<Producto, String> columnMarcaProducto;
    @FXML private TableColumn<Producto, String> columnRubroProducto;
    @FXML private TableColumn<Producto, Long> columnPrecioVentaProducto;
    @FXML private TableColumn<Producto, Integer> columnStockProducto;

    // Search Table Field
    @FXML private TextField inputBuscarProducto;

    // Text
    @FXML private Text txtCodigoBarraProducto;
    @FXML private Text txtDescripcionProducto;
    @FXML private Text txtMarcaProducto;
    @FXML private Text txtRubroProducto;
    @FXML private Text txtPrecioVentaProducto;
    @FXML private Text txtPrecioCompraProducto;
    @FXML private Text txtStockProducto;

    // Button
    @FXML private Button btnAgregarProducto;
    @FXML private MenuButton btnFiltrarProducto;

    // Helper Methods
    public void setParentController(ProductoSeleccionable parentController) {
        this.parentController = parentController;
    }

    public void cargarProductos() {
        try {
            Producto[] lista = productoService.getAllWithDetailProducto();
            productos.clear();
            productos.addAll(Arrays.asList(lista));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar productos");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void displayProducto(Producto producto) {
        txtCodigoBarraProducto.setText(producto.getCodigoBarra());
        txtDescripcionProducto.setText(producto.getDescripcion());
        txtMarcaProducto.setText(String.valueOf(producto.getMarca().getNombre()));
        txtRubroProducto.setText(String.valueOf(producto.getRubro().getNombre()));
        txtPrecioCompraProducto.setText(String.valueOf(producto.getPrecioCompra()));
        txtPrecioVentaProducto.setText(String.valueOf(producto.getPrecioVenta()));
        txtStockProducto.setText(String.valueOf(producto.getStock()));
    }

    // FXML Methods
    @FXML public void initialize() {
        tableProductos.setItems(productos);
        columnCodigoProducto.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        columnDescripcionProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnMarcaProducto.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMarca().getNombre())
        );
        columnRubroProducto.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRubro().getNombre())
        );
        columnPrecioVentaProducto.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        columnStockProducto.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableProductos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableProductos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        displayProducto(newSelection);
                }
        );

        cargarProductos();
    }

    @FXML private void handleAgregarProductos() {
        ObservableList<Producto> seleccionados = tableProductos.getSelectionModel().getSelectedItems();

        if (seleccionados.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar al menos un producto.");
            alert.showAndWait();
            return;
        }

        List<Producto> productosSeleccionados = new ArrayList<>(tableProductos.getSelectionModel().getSelectedItems());
        parentController.recibirProductosSeleccionados(productosSeleccionados);


        parentController.recibirProductosSeleccionados(seleccionados);

        Stage stage = (Stage) tableProductos.getScene().getWindow();
        stage.close();
    }
}
