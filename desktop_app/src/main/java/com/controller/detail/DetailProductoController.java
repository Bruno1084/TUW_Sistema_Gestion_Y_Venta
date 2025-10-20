package com.controller.detail;

import com.controller.ProductosController;
import com.controller.SidebarController;
import com.model.Producto;
import com.service.ProductoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DetailProductoController {
    private SidebarController parentController;
    private final ProductoService productoService = new ProductoService();
    private Producto producto;

    // Buttons
    @FXML Button btnEditarProducto;
    @FXML Button btnCerrarProducto;

    // Text
    @FXML Text txtTituloProducto;
    @FXML Text txtDescripcionProducto;
    @FXML Text txtCodigoProducto;
    @FXML Text txtRubroProducto;
    @FXML Text txtMarcaProducto;
    @FXML Text txtStockProducto;
    @FXML Text txtFechaCreacionProducto;
    @FXML Text txtFechaModificacionProducto;
    @FXML Text txtPrecioCompraProducto;
    @FXML Text txtProveedorProducto;
    @FXML Text txtPrecioVentaProducto;

    // ImageView
    @FXML private ImageView imgProducto;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setProducto(Producto producto) {
        txtTituloProducto.setText(producto.getDescripcion());
        txtDescripcionProducto.setText(producto.getDescripcion());
        txtCodigoProducto.setText(producto.getCodigoBarra());
        txtRubroProducto.setText(producto.getRubro().getNombre());
        txtMarcaProducto.setText(producto.getMarca().getNombre());
        txtStockProducto.setText(String.valueOf(producto.getStock()));
        txtFechaCreacionProducto.setText(String.valueOf(producto.getFechaCreacion()));
        txtFechaModificacionProducto.setText(String.valueOf(producto.getFechaModificacion()));
        txtPrecioCompraProducto.setText(String.valueOf(producto.getPrecioCompra()));
        txtProveedorProducto.setText(producto.getProveedor().getNombre());
        txtPrecioCompraProducto.setText(String.valueOf(producto.getPrecioVenta()));
    }

    // FXML Methods
    @FXML public void initialize() {

    }

    @FXML private void handleEditarProducto() {

    }

    @FXML private void handleCerrarProducto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fxml/Productos.fxml"));
            Parent productosView = loader.load();

            ProductosController productosController = loader.getController();
            productosController.setParentController(parentController);

            parentController.getMainBorderPane().setCenter(productosView);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
