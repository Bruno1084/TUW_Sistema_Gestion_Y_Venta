package com.controller;

import com.model.Producto;
import com.service.ProductoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ProductosController {
    private final ProductoService productoService = new ProductoService();
    private ObservableList<Producto> productos = FXCollections.observableArrayList();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm");

    // Table View Productos
    @FXML private TableView<Producto> tableProductos;
    @FXML private TableColumn<Producto, String> columnCodigoBarraProducto;
    @FXML private TableColumn<Producto, String> columnDescripcionProducto;
    @FXML private TableColumn<Producto, String> columnMarcaProducto;
    @FXML private TableColumn<Producto, String> columnRubroProducto;
    @FXML private TableColumn<Producto, Float> columnPrecioVentaProducto;
    @FXML private TableColumn<Producto, Integer> columnStockProducto;

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
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void cargarProductos() {
        try {
            Producto[] lista = productoService.getAllProducto();

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

    public void actualizarProducto(Producto actualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigoBarra().equals(actualizado.getCodigoBarra())) {
                productos.set(i, actualizado);
                break;
            }
        }
    }

    public void displayProducto(Producto producto) {
        txtCodigoBarraProducto.setText(String.valueOf(producto.getCodigoBarra()));
        txtDescripcionProducto.setText(producto.getDescripcion());
        txtMarcaProducto.setText(String.valueOf(producto.getIdMarca()));
        txtRubroProducto.setText(String.valueOf(producto.getIdRubro()));
        txtPrecioCompraProducto.setText(String.valueOf(producto.getPrecioCompra()));
        txtPrecioVentaProducto.setText(String.valueOf(producto.getPrecioVenta()));
        txtStockProducto.setText(String.valueOf(producto.getStock()));
    }

    // FXML Methods
    @FXML public void initialize() {
        tableProductos.setItems(productos);
        columnCodigoBarraProducto.setCellValueFactory(new PropertyValueFactory<>("Código de Barra"));
        columnDescripcionProducto.setCellValueFactory(new PropertyValueFactory<>("Descripción"));
        columnMarcaProducto.setCellValueFactory(new PropertyValueFactory<>("Marca"));
        columnRubroProducto.setCellValueFactory(new PropertyValueFactory<>("Rubro"));
        columnPrecioVentaProducto.setCellValueFactory(new PropertyValueFactory<>("Precio de Venta"));

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
