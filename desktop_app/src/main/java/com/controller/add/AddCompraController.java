package com.controller.add;

import com.controller.ComprasController;
import com.controller.SidebarController;
import com.controller.modal.ModalBuscarProducto;
import com.model.*;
import com.model.dto.CompraDetalleDTO;
import com.service.CompraDetalleService;
import com.service.CompraService;
import com.service.ProveedorService;
import com.util.ParentAware;
import com.util.ProductoSeleccionable;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AddCompraController implements ParentAware, ProductoSeleccionable {
    private SidebarController parentController;
    private final CompraService compraService = new CompraService();
    private final ProveedorService proveedorService = new ProveedorService();
    private final CompraDetalleService compraDetalleService = new CompraDetalleService();

    private final ObservableList<CompraDetalle> detalles = FXCollections.observableArrayList();
    private Compra compra;
    private Proveedor proveedorSeleccionado = new Proveedor();

    // Buttons
    @FXML Button btnAniadirCompra;
    @FXML Button btnCancelarCompra;
    @FXML Button btnBuscarCompra;

    // Text
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

    public void recibirProductosSeleccionados(List<Producto> productos) {
        for (Producto producto : productos) {
            boolean yaExiste = detalles.stream()
                    .anyMatch(detalle -> detalle.getProducto().getCodigoBarra().equals(producto.getCodigoBarra()));

            if (!yaExiste) {
                CompraDetalle detalle = new CompraDetalle();
                detalle.setProducto(producto);
                detalle.setCantidad(1);
                detalle.setPrecioUnitario(producto.getPrecioCompra());
                detalle.setPrecioTotal(producto.getPrecioCompra());
                detalles.add(detalle);
            }
        }
        actualizarTotal();
    }

    private void actualizarTotal() {
        double total = detalles.stream()
                .mapToDouble(CompraDetalle::getPrecioTotal)
                .sum();

        inputTotalCompra.setText(String.format("$ %.2f", total));
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
        columnCantidadProducto.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnCantidadProducto.setOnEditCommit(event -> {
            CompraDetalle detalle = event.getRowValue();
            detalle.setCantidad(event.getNewValue());
            detalle.recalcularTotal();
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

            float total = Float.parseFloat(inputTotalCompra.getText().replace("$", "").replace(",", "."));
            Compra nuevaCompra = new Compra(
                    0,
                    total,
                    new Date(),
                    proveedorSeleccionado,
                    SessionManager.getInstance().getCurrentUsuario()
            );

            Compra compraCreada = compraService.create(nuevaCompra);

            List<CompraDetalleDTO> detallesDTO = new ArrayList<>();
            detalles.forEach(detalle -> {
                CompraDetalleDTO compraDTO = new CompraDetalleDTO(
                        compraCreada.getId(),
                        detalle.getProducto().getCodigoBarra(),
                        detalle.getCantidad(),
                        detalle.getPrecioTotal(),
                        detalle.getPrecioUnitario()
                );

                detallesDTO.add(compraDTO);
            });

            compraDetalleService.createManyCompraDetalle(detallesDTO);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Compras.fxml"));
            Parent root = fxmlLoader.load();

            ComprasController comprasController = fxmlLoader.getController();
            comprasController.setParentController(parentController);

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
