module com.sistema_gestion_y_venta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;
    requires javafx.base;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires com.sistema_gestion_y_venta;

    opens com to javafx.fxml;
    opens com.controller to javafx.fxml;
    opens com.controller.modal to javafx.fxml;
    opens com.controller.detail to javafx.fxml;
    opens com.controller.add to javafx.fxml;
    opens com.model to com.fasterxml.jackson.databind, javafx.base;
    opens com.model.dto to com.fasterxml.jackson.databind, javafx.base;

    exports com;
}