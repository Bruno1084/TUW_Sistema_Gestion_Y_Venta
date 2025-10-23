package com.controller.add;

import com.controller.ProductosController;
import com.controller.SidebarController;
import com.controller.detail.DetailProductoController;
import com.model.Marca;
import com.model.Producto;
import com.model.Proveedor;
import com.model.Rubro;
import com.service.MarcaService;
import com.service.ProductoService;
import com.service.ProveedorService;
import com.service.RubroService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.util.Date;

public class AddProductoController implements ParentAware {
    private SidebarController parentController;
    private final ProductoService productoService = new ProductoService();
    private final ProveedorService proveedorService = new ProveedorService();
    private final MarcaService marcaService = new MarcaService();
    private final RubroService rubroService = new RubroService();

    private Producto producto;
    private Proveedor proveedorSeleccionado = new Proveedor();
    private Marca marcaSeleccionada = new Marca();
    private Rubro rubroSeleccionado = new Rubro();

    // Buttons
    @FXML Button btnAniadirProducto;
    @FXML Button btnCancelarProducto;

    // Text
    @FXML Text txtTituloProducto;
    @FXML TextField inputDescripcionProducto;
    @FXML TextField inputCodigoProducto;
    @FXML TextField inputRubroProducto;
    @FXML TextField inputMarcaProducto;
    @FXML TextField inputStockProducto;
    @FXML TextField inputPrecioCompraProducto;
    @FXML TextField inputProveedorProducto;
    @FXML TextField inputPrecioVentaProducto;

    // ImageView
    @FXML private ImageView imgProducto;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        this.proveedorSeleccionado = producto.getProveedor();
        this.marcaSeleccionada = producto.getMarca();
        this.rubroSeleccionado = producto.getRubro();

        txtTituloProducto.setText(producto.getDescripcion());
        inputDescripcionProducto.setText(producto.getDescripcion());
        inputCodigoProducto.setText(producto.getCodigoBarra());
        inputRubroProducto.setText(producto.getRubro().getNombre());
        inputMarcaProducto.setText(producto.getMarca().getNombre());
        inputStockProducto.setText(String.valueOf(producto.getStock()));
        inputPrecioCompraProducto.setText(String.valueOf(producto.getPrecioCompra()));
        inputProveedorProducto.setText(producto.getProveedor().getNombre());
        inputPrecioVentaProducto.setText(String.valueOf(producto.getPrecioVenta()));

    }

    // FXML Methods
    @FXML private void handleAniadirProducto() {
        try {
            if (producto == null) {
                if(!inputProveedorProducto.getText().isEmpty() && !inputProveedorProducto.getText().equals(proveedorSeleccionado.getNombre())) {
                    proveedorSeleccionado.setNombre(inputProveedorProducto.getText());
                    proveedorSeleccionado = proveedorService.createProveedor(proveedorSeleccionado);
                }

                if(!inputMarcaProducto.getText().isEmpty() && !inputMarcaProducto.getText().equals(marcaSeleccionada.getNombre())) {
                    marcaSeleccionada.setNombre(inputMarcaProducto.getText());
                    marcaSeleccionada = marcaService.createMarca(marcaSeleccionada);
                }

                if (!inputRubroProducto.getText().isEmpty() && !inputRubroProducto.getText().equals(rubroSeleccionado.getNombre())) {
                    rubroSeleccionado.setNombre(inputRubroProducto.getText());
                    rubroSeleccionado = rubroService.createRubro(rubroSeleccionado);
                }

                // CASO CREAR PRODUCTO
                if (proveedorSeleccionado == null || marcaSeleccionada == null || rubroSeleccionado == null) {
                    System.out.println("Debe seleccionar proveedor, marca y rubro válidos");
                    return;
                }

                Producto nuevoProducto = new Producto(
                        inputCodigoProducto.getText(),
                        inputDescripcionProducto.getText(),
                        Float.parseFloat(inputPrecioCompraProducto.getText()),
                        Float.parseFloat(inputPrecioVentaProducto.getText()),
                        Integer.parseInt(inputStockProducto.getText()),
                        "img",
                        new Date(),
                        new Date(),
                        proveedorSeleccionado,
                        marcaSeleccionada,
                        rubroSeleccionado
                );

                Producto creado = productoService.createProducto(nuevoProducto);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Productos.fxml"));
                Parent root = fxmlLoader.load();

                ProductosController productosController = fxmlLoader.getController();
                productosController.setParentController(parentController);
                productosController.agregarProducto(creado);

                parentController.getMainBorderPane().setCenter(root);
            } else {
                // CASO EDITAR PRODUCTO
                producto.setCodigoBarra(inputCodigoProducto.getText());
                producto.setDescripcion(inputDescripcionProducto.getText());
                producto.setPrecioCompra(Float.parseFloat(inputPrecioCompraProducto.getText()));
                producto.setPrecioVenta(Float.parseFloat(inputPrecioVentaProducto.getText()));
                producto.setStock(Integer.parseInt(inputStockProducto.getText()));

                Producto actualizado = productoService.updateProducto(producto.getCodigoBarra(), producto);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailProducto.fxml"));
                Parent root = fxmlLoader.load();

                DetailProductoController detailProductoController = fxmlLoader.getController();
                detailProductoController.setParentController(parentController);
                detailProductoController.setProducto(actualizado);

                parentController.getMainBorderPane().setCenter(root);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCancelarProducto() {
        try {
            if (producto == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Productos.fxml"));
                Parent root = fxmlLoader.load();

                ProductosController productosController = fxmlLoader.getController();
                productosController.setParentController(parentController);
                parentController.getMainBorderPane().setCenter(root);
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailProducto.fxml"));
                Parent root = fxmlLoader.load();

                DetailProductoController detailProductoController = fxmlLoader.getController();
                detailProductoController.setParentController(parentController);
                detailProductoController.setProducto(producto);
                parentController.getMainBorderPane().setCenter(root);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
