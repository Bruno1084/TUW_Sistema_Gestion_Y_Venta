package com.controller.modal;

import com.controller.ProductosController;
import com.model.Marca;
import com.model.Producto;
import com.model.Proveedor;
import com.model.Rubro;
import com.service.MarcaService;
import com.service.ProductoService;
import com.service.ProveedorService;
import com.service.RubroService;
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
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModalProductoController {
    private final ProductoService productoService = new ProductoService();
    private ProductosController parentController;
    private Producto producto;

    private Proveedor proveedorSeleccionado = new Proveedor();
    private Marca marcaSeleccionada = new Marca();
    private Rubro rubroSeleccionado = new Rubro();

    private final ProveedorService proveedorService = new ProveedorService();
    private final MarcaService marcaService = new MarcaService();
    private final RubroService rubroService = new RubroService();

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

        proveedorSeleccionado = producto.getProveedor();
        marcaSeleccionada = producto.getMarca();
        rubroSeleccionado = producto.getRubro();
    }

    private <T> void setAutocompleteField(TextField inputField, Supplier<List<T>> dataSupplier, Function<T, String> nombreExtractor, Consumer<T> onSeleccionado) {
        try {
            List<T> lista = dataSupplier.get();

            AutoCompletionBinding<T> binding = TextFields.bindAutoCompletion(
                    inputField,
                    param -> {
                        String texto = param.getUserText().toLowerCase();
                        return lista.stream()
                                .filter(item -> nombreExtractor.apply(item).toLowerCase().contains(texto))
                                .toList();
                    },
                    new javafx.util.StringConverter<>() {
                        @Override
                        public String toString(T item) {
                            return item != null ? nombreExtractor.apply(item) : "";
                        }

                        @Override
                        public T fromString(String string) {
                            return lista.stream()
                                    .filter(i -> nombreExtractor.apply(i).equalsIgnoreCase(string))
                                    .findFirst()
                                    .orElse(null);
                        }
                    }
            );

            binding.setOnAutoCompleted(event -> onSeleccionado.accept(event.getCompletion()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setTxtTituloProducto(String tituloModal) {
        this.txtTituloProducto.setText(tituloModal);
    }

    public void disableInputCodigoBarra(boolean option) {
        if (option) inputCodigoBarraProducto.setDisable(true);
    }
    @FXML private void initialize() {
        setAutocompleteField(
                inputProveedorProducto,
                () -> {
                    try {
                        return Arrays.asList(proveedorService.getAllProveedor());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                Proveedor::getNombre,
                p -> { proveedorSeleccionado = p; if (producto != null) producto.setProveedor(p); }
        );

        setAutocompleteField(
                inputMarcaProducto,
                () -> {
                    try {
                        return Arrays.asList(marcaService.getAllMarca());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                Marca::getNombre,
                m -> { marcaSeleccionada = m; if (producto != null) producto.setMarca(m); }
        );

        setAutocompleteField(
                inputRubroProducto,
                () -> {
                    try {
                        return Arrays.asList(rubroService.getAllRubro());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                Rubro::getNombre,
                r -> { rubroSeleccionado = r; if (producto != null) producto.setRubro(r); }
        );
    }

    @FXML private void handleBtnGuardar(ActionEvent event) {
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

                // Caso crear Producto
                if (proveedorSeleccionado == null || marcaSeleccionada == null || rubroSeleccionado == null) {
                    System.out.println("Debe seleccionar proveedor, marca y rubro válidos");
                    return;
                }

                Producto nuevoProducto = new Producto(
                        inputCodigoBarraProducto.getText(),
                        inputDescripcionProducto.getText(),
                        Float.parseFloat(inputPrecioCompraProducto.getText()),
                        Float.parseFloat(inputPrecioVentaProducto.getText()),
                        Integer.parseInt(inputStockProducto.getText()),
                        inputImgUriProducto.getText(),
                        new Date(),
                        new Date(),
                        proveedorSeleccionado,
                        marcaSeleccionada,
                        rubroSeleccionado
                );

                Producto creado = productoService.createProducto(nuevoProducto);

                if (parentController != null) {
                    parentController.agregarProducto(creado);
                    parentController.displayProducto(creado);
                }
            } else {
                // Caso editar Producto
                producto.setCodigoBarra(inputCodigoBarraProducto.getText());
                producto.setDescripcion(inputDescripcionProducto.getText());
                producto.setPrecioCompra(Float.parseFloat(inputPrecioCompraProducto.getText()));
                producto.setPrecioVenta(Float.parseFloat(inputPrecioVentaProducto.getText()));
                producto.setStock(Integer.parseInt(inputStockProducto.getText()));
                producto.setImgUri(inputImgUriProducto.getText());

                Producto actualizado = productoService.updateProducto(producto.getCodigoBarra(), producto);

                if (parentController != null) {
                    parentController.actualizarProducto(actualizado);
                    parentController.displayProducto(actualizado);
                }
            }

            // Cerrar Modal
            Stage currentStage = (Stage) btnGuardar.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleBtnCancelar() {
        Stage currentStage = (Stage) btnCancelar.getScene().getWindow();
        currentStage.close();
    }
}
