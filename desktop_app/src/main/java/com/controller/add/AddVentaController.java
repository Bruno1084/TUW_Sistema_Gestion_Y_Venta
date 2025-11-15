package com.controller.add;

import com.controller.ComprasController;
import com.controller.SidebarController;
import com.controller.VentasController;
import com.controller.modal.ModalBuscarProducto;
import com.model.*;
import com.model.dto.VentaDetalleDTO;
import com.service.ClienteService;
import com.service.VentaService;
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

public class AddVentaController implements ParentAware, ProductoSeleccionable {
    private SidebarController parentController;
    private final VentaService ventaService = new VentaService();
    private final ClienteService clienteService = new ClienteService();

    private final ObservableList<VentaDetalle> detalles = FXCollections.observableArrayList();
    private Venta venta;
    private Cliente clienteSeleccionado = new Cliente();

    // Buttons
    @FXML Button btnAniadirVenta;
    @FXML Button btnCancelarVenta;
    @FXML Button btnBuscarVenta;

    // Text
    @FXML TextField inputTotalVenta;
    @FXML TextField inputClienteVenta;
    @FXML TextField inputCodigoVenta;

    // TextView
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
        this.clienteSeleccionado = venta.getCliente();

        inputTotalVenta.setText(String.valueOf(venta.getPrecioTotal()));
        inputClienteVenta.setText(venta.getCliente().getNombre());
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
                VentaDetalle detalle = new VentaDetalle();
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
                .mapToDouble(VentaDetalle::getPrecioTotal)
                .sum();

        inputTotalVenta.setText(String.format("$ %.2f", total));
    }

    // FXML Methods
    @FXML private void initialize() {
        setAutocompleteField(
                inputClienteVenta,
                () -> {
                    try {
                        return Arrays.asList(clienteService.getAllCliente());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                Cliente::getNombre,
                c -> { clienteSeleccionado = c; if (venta != null) venta.setCliente(c); }
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
            VentaDetalle detalle = event.getRowValue();
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

    @FXML private void handleAniadirCompra() {
        try {
            if(!inputClienteVenta.getText().isEmpty() && !inputClienteVenta.getText().equals(clienteSeleccionado.getNombre())) {
                clienteSeleccionado.setNombre(inputClienteVenta.getText());
                clienteSeleccionado = clienteService.createCliente(clienteSeleccionado);
            }

            if (clienteSeleccionado == null) {
                System.out.println("Debe seleccionar un cliente válido");
                return;
            }

            float total = Float.parseFloat(inputTotalVenta.getText().replace("$", "").replace(",", "."));
            Venta nuevaVenta = new Venta(
                    0,
                    total,
                    new Date(),
                    clienteSeleccionado,
                    SessionManager.getInstance().getCurrentUsuario()
            );

            List<VentaDetalleDTO> detallesDTO = new ArrayList<>();
            detalles.forEach(detalle -> {
                VentaDetalleDTO ventaDTO = new VentaDetalleDTO(
                        0,
                        detalle.getProducto().getCodigoBarra(),
                        detalle.getCantidad(),
                        detalle.getPrecioTotal(),
                        detalle.getPrecioUnitario()
                );

                detallesDTO.add(ventaDTO);
            });

            ventaService.create(nuevaVenta, detallesDTO);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/Ventas.fxml"));
            Parent root = fxmlLoader.load();

            VentasController ventasController = fxmlLoader.getController();
            ventasController.setParentController(parentController);

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
