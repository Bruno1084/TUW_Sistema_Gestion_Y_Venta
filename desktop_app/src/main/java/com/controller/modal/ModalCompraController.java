package com.controller.modal;

import com.controller.ComprasController;
import com.model.Compra;
import com.model.Producto;
import com.model.Proveedor;
import com.service.CompraService;
import com.service.ProductoService;
import com.service.ProveedorService;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalCompraController {
    private ComprasController parentController;
    private final CompraService compraService = new CompraService();
    private final ProductoService productoService = new ProductoService();
    private final ProveedorService proveedorService = new ProveedorService();
    private final ObservableList<Producto> productos = FXCollections.observableArrayList();

    private Compra compra;
    private Proveedor proveedorSeleccionado;

    //  TableView
    @FXML private TableView<Producto> tableProductos;
    @FXML private TableColumn<Producto, String> columnCodigoProducto;
    @FXML private TableColumn<Producto, String> columnDescripcionProducto;
    @FXML private TableColumn<Producto, Float> columnPrecioCompraProducto;
    @FXML private TableColumn<Producto, Integer> columnStockProducto;
    @FXML private TableColumn<Producto, String> columnRubroProducto;
    @FXML private TableColumn<Producto, String> columnMarcaProducto;

    // TextField
    @FXML private TextField inputIdCompra;
    @FXML private TextField inputFechaCompra;
    @FXML private TextField inputIdProveedor;
    @FXML private TextField inputNombreProveedor;
    @FXML private TextField inputDireccionProveedor;
    @FXML private TextField inputTelefonoProveedor;
    @FXML private TextField inputCodigoBarraProducto;
    @FXML private TextField inputTotalProducto;

    // Button
    @FXML private Button btnBuscarProducto;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    public void setParentController(ComprasController parentController) {
        this.parentController = parentController;
    }

    public void recibirProductosSeleccionados(ObservableList<Producto> seleccionados) {
        tableProductos.setItems(seleccionados);
    }

    @FXML public void initialize() {
        tableProductos.setItems(productos);
        columnCodigoProducto.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        columnDescripcionProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnPrecioCompraProducto.setCellValueFactory(new PropertyValueFactory<>("precioCompra"));
        columnRubroProducto.setCellValueFactory(new PropertyValueFactory<>("rubro"));
        columnMarcaProducto.setCellValueFactory(new PropertyValueFactory<>("marca"));

    }

    @FXML private void handleBtnBuscarProducto(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/modal/ModalBuscarProducto.fxml"));
            Parent root = fxmlLoader.load();

            ModalBuscarProducto modalController = fxmlLoader.getController();
            modalController.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Buscar Producto");
            stage.setResizable(false);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleBtnGuardar() {

    }

    @FXML private void handleBtnCancelar() {

    }
}
