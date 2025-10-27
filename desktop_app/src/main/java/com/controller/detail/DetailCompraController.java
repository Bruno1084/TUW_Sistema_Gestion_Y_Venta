package com.controller.detail;

import com.controller.ComprasController;
import com.controller.SidebarController;
import com.model.Compra;
import com.model.CompraDetalle;
import com.util.ParentAware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class DetailCompraController implements ParentAware {
    private Compra compra;
    private SidebarController parentController;
    private final ObservableList<CompraDetalle> detalles = FXCollections.observableArrayList();

    // Buttons
    @FXML Button btnCerrarCompra;
    @FXML Hyperlink linkProveedorCompra;

    // Text
    @FXML Text txtIdCompra;
    @FXML Text txtTotalCompra;
    @FXML Text txtFechaCreacionCompra;
    @FXML Text txtProveedorCompra;
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
        txtIdCompra.setText(String.valueOf(compra.getId()));
        txtTotalCompra.setText(String.valueOf(compra.getPrecioTotal()));
        txtFechaCreacionCompra.setText(String.valueOf(compra.getFechaCreacion()));
        txtProveedorCompra.setText(compra.getProveedor().getNombre());
        txtUsuarioCompra.setText(compra.getUsuario().getNombre());
        this.compra = compra;

    }

    // FXML Methods
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
