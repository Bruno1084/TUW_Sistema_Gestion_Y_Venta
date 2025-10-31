package com.controller;

import com.controller.add.AddProductoController;
import com.controller.detail.DetailProductoController;
import com.model.Producto;
import com.service.ProductoService;
import com.util.ParentAware;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.util.Arrays;

public class ProductosController implements ParentAware {
    private SidebarController parentController;
    private final ProductoService productoService = new ProductoService();
    private final ObservableList<Producto> productos = FXCollections.observableArrayList();

    // Root AnchorPane
    @FXML private AnchorPane rootPane;

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
    @FXML private MenuButton btnConfigProducto;
    @FXML private Button btnAniadirProducto;

    // Menu Items
    @FXML private MenuItem menuItemImportarProducto;
    @FXML private MenuItem menuItemExportarProducto;

    // Search Bar Producto
    @FXML private TextField inputBuscarProducto;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
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

    private void cargarDetalleProducto(Producto producto) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailProducto.fxml"));
            Parent root = fxmlLoader.load();

            DetailProductoController detailProductoController = fxmlLoader.getController();
            detailProductoController.setParentController(parentController);
            detailProductoController.setProducto(producto);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
                    if (newSelection != null) {
                        cargarDetalleProducto(newSelection);
                    }
                }
        );
        cargarProductos();
    }

    @FXML private void handleAniadirProducto(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/add/AddProducto.fxml"));
            Parent root = fxmlLoader.load();

            AddProductoController addProductoController = fxmlLoader.getController();
            addProductoController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear el producto");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();        }
    }

}
