package com.controller.modal;

import com.controller.ProductosController;
import com.model.Producto;
import com.model.Proveedor;
import com.service.MarcaService;
import com.service.ProductoService;
import com.service.ProveedorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import java.util.Arrays;
import java.util.List;

public class ModalProductoController {
    private final ProductoService productoService = new ProductoService();
    private ProductosController parentController;
    private com.model.Producto producto;

    private final ProveedorService proveedorService = new ProveedorService();
    private final MarcaService marcaService = new MarcaService();

    private AutoCompletionBinding<Proveedor> autoCompleteProveedor;
    private AutoCompletionBinding<String> autoCompleteMarca;
    private AutoCompletionBinding<String> autoCompleteRubro;

    @FXML private Text txtTituloProducto;
    @FXML private TextField inputCodigoBarraProducto;
    @FXML private TextArea inputDescripcionProducto;
    @FXML private TextField inputPrecioCompraProducto;
    @FXML private TextField inputPrecioVentaProducto;
    @FXML private TextField inputProveedorProducto;
    @FXML private TextField inputMarcaProducto;
    @FXML private TextField inputRubroProducto;
    @FXML private TextField inputStockProducto;
    @FXML private TextField inputImgUriProducto;

    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    public void setParentController(ProductosController parentController) {
        this.parentController = parentController;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;

        inputCodigoBarraProducto.setText(producto.getCodigoBarra());
        inputDescripcionProducto.setText(producto.getDescripcion());
        inputPrecioCompraProducto.setText(String.valueOf(producto.getPrecioCompra()));
        inputPrecioVentaProducto.setText(String.valueOf(producto.getPrecioVenta()));
        inputProveedorProducto.setText(producto.getProveedor().getNombre());
        inputMarcaProducto.setText(producto.getMarca().getNombre());
        inputRubroProducto.setText(producto.getRubro().getNombre());
        inputStockProducto.setText(String.valueOf(producto.getStock()));
        inputImgUriProducto.setText(producto.getImgUri());
    }

    public void setTxtTituloProducto(String tituloModal) {
        this.txtTituloProducto.setText(tituloModal);
    }

    @FXML
    private void initialize() {
        try {
            Proveedor[] proveedores = proveedorService.getAllProveedor();
            List<Proveedor> listaProveedores = Arrays.asList(proveedores);

            autoCompleteProveedor = TextFields.bindAutoCompletion(
                    inputProveedorProducto,
                    param -> {
                        // Filtramos dinámicamente según lo que escribe el usuario
                        String userText = param.getUserText().toLowerCase();
                        return listaProveedores.stream()
                                .filter(p -> p.getNombre().toLowerCase().contains(userText))
                                .toList();
                    },
                    new javafx.util.StringConverter<Proveedor>() {
                        @Override
                        public String toString(Proveedor proveedor) {
                            return proveedor != null ? proveedor.getNombre() : "";
                        }

                        @Override
                        public Proveedor fromString(String string) {
                            return listaProveedores.stream()
                                    .filter(p -> p.getNombre().equalsIgnoreCase(string))
                                    .findFirst()
                                    .orElse(null);
                        }
                    }
            );


            autoCompleteProveedor.setOnAutoCompleted(event -> {
                Proveedor proveedorSeleccionado = event.getCompletion();
                inputProveedorProducto.setText(proveedorSeleccionado.getNombre());

                // ✅ Guardás el proveedor en tu producto actual o donde necesites
                if (producto != null) {
                    producto.setProveedor(proveedorSeleccionado);
                }

                System.out.println("Proveedor seleccionado: "
                        + proveedorSeleccionado.getNombre()
                        + " (ID: " + proveedorSeleccionado.getId() + ")");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBtnGuardar(ActionEvent event) {

    }

    @FXML private void handleBtnCancelar() {
        Stage currentStage = (Stage) btnCancelar.getScene().getWindow();
        currentStage.close();
    }
}
