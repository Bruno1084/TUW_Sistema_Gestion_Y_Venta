package com.controller;

import com.model.dto.ProductoDetailDTO;
import com.service.ProductoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.util.Arrays;

public class ProductosController {
    private final ProductoService productoService = new ProductoService();
    private final ObservableList<ProductoDetailDTO> productos = FXCollections.observableArrayList();

    // Table View Productos
    @FXML private TableView<ProductoDetailDTO> tableProductos;
    @FXML private TableColumn<ProductoDetailDTO, String> columnCodigoBarraProducto;
    @FXML private TableColumn<ProductoDetailDTO, String> columnDescripcionProducto;
    @FXML private TableColumn<ProductoDetailDTO, String> columnMarcaProducto;
    @FXML private TableColumn<ProductoDetailDTO, String> columnRubroProducto;
    @FXML private TableColumn<ProductoDetailDTO, Float> columnPrecioVentaProducto;
    @FXML private TableColumn<ProductoDetailDTO, Integer> columnStockProducto;

    // Buttons
    @FXML private MenuButton btnFiltrarProducto;
    @FXML private Button btnAniadirProducto;
    @FXML private Button btnEditarProducto;
    @FXML private Button btnEliminarProducto;

    // Search Bar Producto
    @FXML private TextField inputBuscarProducto;

    // Text
    @FXML private Text txtCodigoBarraProducto;
    @FXML private Text txtDescripcionProducto;
    @FXML private Text txtMarcaProducto;
    @FXML private Text txtRubroProducto;
    @FXML private Text txtPrecioVentaProducto;
    @FXML private Text txtPrecioCompraProducto;
    @FXML private Text txtStockProducto;

    // Helper Methods
    public void agregarProducto(ProductoDetailDTO producto) {
        productos.add(producto);
    }

    public void cargarProductos() {
        try {
            ProductoDetailDTO[] lista = productoService.getAllWithDetailProducto();
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

    public void actualizarProducto(ProductoDetailDTO actualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigoBarra().equals(actualizado.getCodigoBarra())) {
                productos.set(i, actualizado);
                break;
            }
        }
    }

    public void displayProducto(ProductoDetailDTO producto) {
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
        columnCodigoBarraProducto.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        columnDescripcionProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnMarcaProducto.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMarca().getNombre())
        );
        columnRubroProducto.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRubro().getNombre())
        );
        columnPrecioVentaProducto.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        columnStockProducto.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableProductos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null)
                        displayProducto(newSelection);
                }
        );
        cargarProductos();
    }

    @FXML private void handleAniadirProducto(ActionEvent event) {

    }

    @FXML private void handleEditarProducto(ActionEvent event) {

    }

    @FXML private void handleEliminarProducto(ActionEvent event) {

    }
}
