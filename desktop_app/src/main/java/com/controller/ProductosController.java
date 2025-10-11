package com.controller;

import com.controller.modal.ModalProductoController;
import com.model.Producto;
import com.service.ProductoService;
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
import java.io.IOException;
import java.util.Arrays;

public class ProductosController {
    private final ProductoService productoService = new ProductoService();
    private final ObservableList<Producto> productos = FXCollections.observableArrayList();

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
        tableProductos.getSelectionModel().select(producto);
        tableProductos.scrollTo(producto);
        displayProducto(producto);
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

    public void actualizarProducto(Producto actualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigoBarra().equals(actualizado.getCodigoBarra())) {
                productos.set(i, actualizado);
                break;
            }
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/modal/ModalProducto.fxml"));
            Parent root = fxmlLoader.load();

            ModalProductoController modalController = fxmlLoader.getController();
            modalController.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Añadir Producto");
            stage.setResizable(false);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo crear el producto");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    @FXML private void handleEditarProducto(ActionEvent event) {
        try {
            Producto seleccionado = tableProductos.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("No hay selección");
                alert.setContentText("Debes seleccionar un producto para editar.");
                alert.showAndWait();
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/modal/ModalProducto.fxml"));
            Parent root = fxmlLoader.load();

            ModalProductoController modalController = fxmlLoader.getController();
            modalController.setParentController(this);

            modalController.setProducto(seleccionado);
            modalController.setTxtTituloProducto("Editar Producto");
            modalController.disableInputCodigoBarra(true);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Producto");
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleEliminarProducto(ActionEvent event) {
        try {
            Producto productoSeleccionado = tableProductos.getSelectionModel().getSelectedItem();

            if (productoSeleccionado != null) {
                productoService.deleteProducto(productoSeleccionado.getCodigoBarra());
                productos.remove(productoSeleccionado);

                txtCodigoBarraProducto.setText("");
                txtDescripcionProducto.setText("");
                txtMarcaProducto.setText("");
                txtRubroProducto.setText("");
                txtPrecioCompraProducto.setText("");
                txtPrecioVentaProducto.setText("");
                txtStockProducto.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atención");
                alert.setHeaderText("Ningún producto seleccionado");
                alert.setContentText("Seleccione un producto de la tabla para eliminarlo.");
                alert.showAndWait();
            }
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo eliminar el producto");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
