package com.controller;

import com.controller.add.AddProductoController;
import com.controller.detail.DetailProductoController;
import com.model.Producto;
import com.service.ProductoService;
import com.util.ParentAware;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class ProductosController implements ParentAware {
    private SidebarController parentController;
    private final ProductoService productoService = new ProductoService();
    private final ObservableList<Producto> productos = FXCollections.observableArrayList();

    private String filtroActual = "descripcion";
    private final ObservableList<Producto> productosOriginal = FXCollections.observableArrayList();

    // Table View Productos
    @FXML private TableView<Producto> tableProductos;
    @FXML private TableColumn<Producto, String> columnCodigoBarraProducto;
    @FXML private TableColumn<Producto, String> columnDescripcionProducto;
    @FXML private TableColumn<Producto, String> columnMarcaProducto;
    @FXML private TableColumn<Producto, String> columnRubroProducto;
    @FXML private TableColumn<Producto, Float> columnPrecioVentaProducto;
    @FXML private TableColumn<Producto, Integer> columnStockProducto;

    // Buttons
    @FXML private MenuButton btnFiltrarProducto;
    @FXML private Button btnAniadirProducto;
    @FXML private MenuItem menuItemCodigoBarra;
    @FXML private MenuItem menuItemDescripcion;
    @FXML private MenuItem menuItemMarca;
    @FXML private MenuItem menuItemRubro;
    @FXML private MenuItem menuItemPrecioVenta;
    @FXML private MenuItem menuItemStock;

    // Search Bar Producto
    @FXML private TextField inputBuscarProducto;

    // Helper Methods
    public void setParentController(SidebarController parentController) {
        this.parentController = parentController;
    }

    public void cargarProductos() {
        try {
            Producto[] lista = productoService.getAllWithDetail();
            productosOriginal.clear();
            productosOriginal.setAll(lista);

            productos.clear();
            productos.addAll(lista);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al cargar productos");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void cargarDetalleProducto(Producto producto) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/detail/DetailProducto.fxml"));
            Parent root = fxmlLoader.load();

            DetailProductoController detailProductoController = fxmlLoader.getController();
            detailProductoController.setParentController(parentController);
            detailProductoController.setProducto(producto);

            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void aplicarFiltro() {
        String input = inputBuscarProducto.getText().toLowerCase().trim();

        if (input.isEmpty()) {
            productos.setAll(productosOriginal);
        } else {
            List<Producto> filtrados = productosOriginal.stream()
                    .filter(p -> coincideFiltro(p, input))
                    .toList();

            productos.setAll(filtrados);
        }
    }

    private boolean coincideFiltro(Producto p, String input) {
        return switch (filtroActual) {
            case "codigo barra" -> String.valueOf(p.getCodigoBarra()).contains(input);
            case "descripcion" -> p.getDescripcion() != null && p.getDescripcion().toLowerCase().contains(input);
            case "marca" -> p.getMarca().getNombre() != null && p.getMarca().getNombre().toLowerCase().contains(input);
            case "rubro" -> p.getRubro().getNombre() != null && p.getRubro().getNombre().toLowerCase().contains(input);
            case "precio venta" -> String.valueOf(p.getPrecioVenta()).contains(input);
            case "stock" -> String.valueOf(p.getStock()).contains(input);
            default -> false;
        };
    }

    // FXML Methods
    @FXML public void initialize() {
        // Configurar TableView
        tableProductos.setItems(productos);
        columnCodigoBarraProducto.setCellValueFactory(new PropertyValueFactory<>("codigoBarra"));
        columnDescripcionProducto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnMarcaProducto.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMarca().getNombre())
        );
        columnRubroProducto.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRubro().getNombre())
        );
        columnPrecioVentaProducto.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        columnStockProducto.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableProductos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        cargarDetalleProducto(newSelection);
                    }
                }
        );

        // Configurar MenuItem
        menuItemCodigoBarra.setOnAction(e -> {
            filtroActual = "codigo barra";
            btnFiltrarProducto.setText("Código Barra");
            aplicarFiltro();
        });

        menuItemDescripcion.setOnAction(e -> {
            filtroActual = "descripcion";
            btnFiltrarProducto.setText("Descripcion");
            aplicarFiltro();
        });

        menuItemMarca.setOnAction(e -> {
            filtroActual = "marca";
            btnFiltrarProducto.setText("Marca");
            aplicarFiltro();
        });

        menuItemRubro.setOnAction(e -> {
            filtroActual = "rubro";
            btnFiltrarProducto.setText("Rubro");
            aplicarFiltro();
        });

        menuItemPrecioVenta.setOnAction(e -> {
            filtroActual = "precio venta";
            btnFiltrarProducto.setText("Precio de Venta");
            aplicarFiltro();
        });

        menuItemStock.setOnAction(e -> {
            filtroActual = "stock";
            btnFiltrarProducto.setText("Stock");
            aplicarFiltro();
        });

        inputBuscarProducto.textProperty().addListener((obs, oldText, newText) -> aplicarFiltro());

        cargarProductos();
    }

    @FXML private void handleAniadirProducto() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/add/AddProducto.fxml"));
            Parent root = fxmlLoader.load();

            AddProductoController addProductoController = fxmlLoader.getController();
            addProductoController.setParentController(parentController);
            parentController.getMainBorderPane().setCenter(root);
        } catch (Exception exception) {
            exception.printStackTrace();
            if (exception.getCause() != null) {
                System.err.println("CAUSA REAL:");
                exception.getCause().printStackTrace();
            }
        }
    }
}
