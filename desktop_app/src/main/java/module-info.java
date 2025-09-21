module com.sistema_gestion_y_venta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;
    requires javafx.base;
    requires com.sistema_gestion_y_venta;

    opens com.controller to javafx.fxml;
    opens com.controller.modal to javafx.fxml;
    opens com.model to com.fasterxml.jackson.databind, javafx.base;

    opens com to javafx.fxml;
    exports com;
}