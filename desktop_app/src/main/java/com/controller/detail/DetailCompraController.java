package com.controller.detail;

import com.controller.ComprasController;
import com.controller.SidebarController;
import com.model.Compra;
import com.model.CompraDetalle;
import com.model.dto.CompraDetailResponseDTO;
import com.service.CompraDetalleService;
import com.service.CompraService;
import com.util.ParentAware;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.util.List;

public class DetailCompraController implements ParentAware {
    private Compra compra;
    private SidebarController parentController;
    private final CompraService compraService = new CompraService();
    private final CompraDetalleService compraDetalleService = new CompraDetalleService();
    private final ObservableList<CompraDetalle> detalles = FXCollections.observableArrayList();

    // Buttons
    @FXML Button btnCerrarCompra;

    // Text
    @FXML Text txtIdCompra;
    @FXML Text txtTotalCompra;
    @FXML Text txtFechaCreacionCompra;
    @FXML Hyperlink linkProveedorCompra;
    @FXML Text txtUsuarioCompra;

    // TableView
    @FXML TableView<CompraDetalle> tableProductos;
    @FXML TableColumn<CompraDetalle, String> columnCodigoProducto;
    @FXML TableColumn<CompraDetalle, String> columnDescripcionProducto;
    @FXML TableColumn<CompraDetalle, String> columnPrecioCompraProducto;
    @FXML TableColumn<CompraDetalle, Integer> columnStockProducto;
    @FXML TableColumn<CompraDetalle, String> columnRubroProducto;
    @FXML TableColumn<CompraDetalle, String> columnMarcaProducto;
    @FXML TableColumn<CompraDetalle, Integer> columnCantidadProducto;
    @FXML TableColumn<CompraDetalle, Float> columnImporteProducto;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
        txtIdCompra.setText(String.valueOf(compra.getId()));
        txtTotalCompra.setText(String.valueOf(compra.getPrecioTotal()));
        txtFechaCreacionCompra.setText(String.valueOf(compra.getFechaCreacion()));
        linkProveedorCompra.setText(compra.getProveedor().getNombre());
        txtUsuarioCompra.setText(compra.getUsuario().getNombre());
        cargarDetalles(compra.getId());
    }

    private void cargarDetalles(int compraId) {
        try {
            CompraDetailResponseDTO lista = compraService.getOneById(compraId);

            detalles.clear();
            List<CompraDetalle> detallesConvertidos = lista.getDetalles().stream()
                    .map(dto -> {
                        CompraDetalle d = new CompraDetalle();
                        d.setProducto(dto.getProducto());
                        d.setCantidad(dto.getCantidad());
                        d.setPrecioUnitario(dto.getPrecioUnitario());
                        d.setPrecioTotal(dto.getPrecioTotal());
                        return d;
                    })
                    .toList();

            detalles.addAll(detallesConvertidos);
        } catch (Exception exception) {
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar detalles de compra");
            alert.setHeaderText(null);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    // FXML Methods
    @FXML private void initialize() {
        tableProductos.setItems(detalles);

        columnCodigoProducto.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProducto().getCodigoBarra())
        );

        columnDescripcionProducto.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProducto().getDescripcion())
        );

        columnPrecioCompraProducto.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getProducto().getPrecioCompra()).asObject().asString()
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

        columnImporteProducto.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getPrecioTotal()).asObject()
        );
    }

    @FXML private void handleCerrarCompra() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Compras.fxml"));
            Parent root = loader.load();

            ComprasController comprasController = loader.getController();
            comprasController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
