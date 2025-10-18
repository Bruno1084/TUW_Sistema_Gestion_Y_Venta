package com.controller.modal;

import com.controller.ComprasController;
import com.model.Compra;
import com.model.CompraDetalle;
import com.model.Producto;
import com.model.Proveedor;
import com.service.CompraService;
import com.service.ProductoService;
import com.service.ProveedorService;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;

public class ModalCompraController {
    private ComprasController parentController;
    private final CompraService compraService = new CompraService();
    private final ProductoService productoService = new ProductoService();
    private final ProveedorService proveedorService = new ProveedorService();

    private final ObservableList<CompraDetalle> detalles = FXCollections.observableArrayList();

    private Compra compra;
    private Proveedor proveedorSeleccionado;

    //  TableView
    @FXML private TableView<CompraDetalle> tableProductos;
    @FXML private TableColumn<CompraDetalle, String> columnCodigoProducto;
    @FXML private TableColumn<CompraDetalle, String> columnDescripcionProducto;
    @FXML private TableColumn<CompraDetalle, Float> columnPrecioCompraProducto;
    @FXML private TableColumn<CompraDetalle, Integer> columnStockProducto;
    @FXML private TableColumn<CompraDetalle, String> columnRubroProducto;
    @FXML private TableColumn<CompraDetalle, String> columnMarcaProducto;
    @FXML private TableColumn<CompraDetalle, Integer> columnCantidadProducto;
    @FXML private TableColumn<CompraDetalle, Float> columnImporteProducto;

    // TextField
    @FXML private TextField inputIdCompra;
    @FXML private DatePicker inputFechaCompra;
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
        for (Producto producto : seleccionados) {
            CompraDetalle detalle = new CompraDetalle();
            detalle.setProducto(producto);
            detalle.setCantidad(1);
            detalle.setPrecioUnitario(producto.getPrecioCompra());
            detalle.setPrecioTotal(producto.getPrecioCompra());
            detalles.add(detalle);
        }

        actualizarTotal();
    }

    private void actualizarTotal() {
        double total = detalles.stream()
                .mapToDouble(CompraDetalle::getPrecioTotal)
                .sum();

        inputTotalProducto.setText(String.format("$ %.2f", total));
    }

    @FXML public void initialize() {
        inputIdCompra.setDisable(true);
        inputFechaCompra.setValue(LocalDate.now());

        tableProductos.setItems(detalles);

        columnCodigoProducto.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getProducto().getCodigoBarra())
        );

        columnDescripcionProducto.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getProducto().getDescripcion())
        );

        columnPrecioCompraProducto.setCellValueFactory(cellData ->
            new SimpleFloatProperty(cellData.getValue().getProducto().getPrecioCompra()).asObject()
        );

        columnStockProducto.setCellValueFactory(cellData ->
            new SimpleIntegerProperty(cellData.getValue().getProducto().getStock()).asObject()
        );

        columnRubroProducto.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getProducto().getRubro().getNombre())
        );

        columnMarcaProducto.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getProducto().getMarca().getNombre())
        );

        columnCantidadProducto.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCantidad()).asObject()
        );
        columnCantidadProducto.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnCantidadProducto.setOnEditCommit(event -> {
            CompraDetalle detalle = event.getRowValue();
            detalle.setCantidad(event.getNewValue());
            detalle.setPrecioTotal(detalle.getCantidad() * detalle.getPrecioUnitario());
            tableProductos.refresh();
            actualizarTotal();
        });

        columnImporteProducto.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getPrecioTotal()).asObject()
        );

        tableProductos.setEditable(true);
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
