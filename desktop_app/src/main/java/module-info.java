module com.sistema_gestion_y_venta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.controller to javafx.fxml;

    opens com to javafx.fxml;
    exports com;
}