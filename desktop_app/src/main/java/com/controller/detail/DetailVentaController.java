package com.controller.detail;

import com.controller.SidebarController;
import com.controller.VentasController;
import com.model.Venta;
import com.model.VentaDetalle;
import com.service.VentaDetalleService;
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
import java.util.Arrays;

public class DetailVentaController implements ParentAware {
    private Venta venta;
    private SidebarController parentController;
    private final VentaDetalleService ventaDetalleService = new VentaDetalleService();
    private final ObservableList<VentaDetalle> detalles = FXCollections.observableArrayList();

    // Buttons
    @FXML Button btnCerrarVenta;

    // Text
    @FXML Text txtIdVenta;
    @FXML Text txtTotalVenta;
    @FXML Text txtFechaCreacionVenta;
    @FXML Hyperlink linkClienteVenta;
    @FXML Text txtUsuarioVenta;

    // TableView
    @FXML TableView<VentaDetalle> tableProductos;
    @FXML TableColumn<VentaDetalle, String> columnCodigoProducto;
    @FXML TableColumn<VentaDetalle, String> columnDescripcionProducto;
    @FXML TableColumn<VentaDetalle, String> columnPrecioCompraProducto;
    @FXML TableColumn<VentaDetalle, Integer> columnStockProducto;
    @FXML TableColumn<VentaDetalle, String> columnRubroProducto;
    @FXML TableColumn<VentaDetalle, String> columnMarcaProducto;
    @FXML TableColumn<VentaDetalle, Integer> columnCantidadProducto;
    @FXML TableColumn<VentaDetalle, Float> columnImporteProducto;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
        txtIdVenta.setText(String.valueOf(venta.getId()));
        txtTotalVenta.setText(String.valueOf(venta.getPrecioTotal()));
        txtFechaCreacionVenta.setText(String.valueOf(venta.getFechaCreacion()));
        linkClienteVenta.setText(venta.getCliente().getNombre());
        txtUsuarioVenta.setText(venta.getUsuario().getNombre());
        cargarDetalles(venta.getId());
    }

    private void cargarDetalles(int ventaId) {
        try {
            VentaDetalle[] lista = ventaDetalleService.getAllFromVentaByIdVentaDetalle(ventaId);
            detalles.clear();
            detalles.addAll(Arrays.asList(lista));
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar detalles de venta");
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

    @FXML private void handleCerrarVenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Ventas.fxml"));
            Parent root = loader.load();

            VentasController ventasController = loader.getController();
            ventasController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
