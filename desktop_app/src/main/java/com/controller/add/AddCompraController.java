package com.controller.add;

import com.controller.ComprasController;
import com.controller.SidebarController;
import com.model.Compra;
import com.model.CompraDetalle;
import com.model.Proveedor;
import com.model.SessionManager;
import com.service.CompraService;
import com.service.ProveedorService;
import com.util.ParentAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AddCompraController implements ParentAware {
    private SidebarController parentController;
    private final CompraService compraService = new CompraService();
    private final ProveedorService proveedorService = new ProveedorService();

    private Compra compra;
    private Proveedor proveedorSeleccionado = new Proveedor();

    // Buttons
    @FXML Button btnAniadirCompra;
    @FXML Button btnCancelarCompra;
    @FXML Button btnBuscarCompra;

    // Text
    @FXML TextField inputIdCompra;
    @FXML TextField inputTotalCompra;
    @FXML TextField inputProveedorCompra;
    @FXML TextField inputCodigoCompra;

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
        this.proveedorSeleccionado = compra.getProveedor();

        inputIdCompra.setText(String.valueOf(compra.getId()));
        inputTotalCompra.setText(String.valueOf(compra.getPrecioTotal()));
        inputProveedorCompra.setText(compra.getProveedor().getNombre());
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

    // FXML Methods
    @FXML private void initialize() {
        setAutocompleteField(
                inputProveedorCompra,
                () -> {
                    try {
                        return Arrays.asList(proveedorService.getAllProveedor());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                Proveedor::getNombre,
                p -> { proveedorSeleccionado = p; if (compra != null) compra.setProveedor(p); }
        );
    }

    @FXML private void handleBuscarCompra() {

    }

    @FXML private void handleAniadirCompra() {
        try {
            if(!inputProveedorCompra.getText().isEmpty() && !inputProveedorCompra.getText().equals(proveedorSeleccionado.getNombre())) {
                proveedorSeleccionado.setNombre(inputProveedorCompra.getText());
                proveedorSeleccionado = proveedorService.createProveedor(proveedorSeleccionado);
            }

            if (proveedorSeleccionado == null) {
                System.out.println("Debe seleccionar un proveedor válido");
                return;
            }

            System.out.println("Usuario: " + SessionManager.getInstance().getCurrentUsuario().getNombre());
            System.out.println("ID: " + SessionManager.getInstance().getCurrentUsuario().getId());

            Compra nuevaCompra = new Compra(
                    Integer.parseInt(inputIdCompra.getText()),
                    Float.parseFloat(inputTotalCompra.getText()),
                    new Date(),
                    proveedorSeleccionado,
                    SessionManager.getInstance().getCurrentUsuario()
            );

            Compra creado = compraService.createCompra(nuevaCompra);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Compras.fxml"));
            Parent root = fxmlLoader.load();

            ComprasController comprasController = fxmlLoader.getController();
            comprasController.setParentController(parentController);
            comprasController.agregarCompra(creado);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML private void handleCancelarCompra() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Compras.fxml"));
            Parent root = fxmlLoader.load();

            ComprasController comprasController = fxmlLoader.getController();
            comprasController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
