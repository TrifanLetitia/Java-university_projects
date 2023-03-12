module com.example.socialnetworkjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.socialnetworkjava.controller;
    exports com.example.socialnetworkjava;

    exports com.example.socialnetworkjava.domain;
    exports com.example.socialnetworkjava.controller;
    opens com.example.socialnetworkjava;
}