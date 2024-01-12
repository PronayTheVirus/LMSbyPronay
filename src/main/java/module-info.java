module com.exmple.lmsfinalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.exmple.lmsfinalproject to javafx.fxml;
    exports com.exmple.lmsfinalproject;
}