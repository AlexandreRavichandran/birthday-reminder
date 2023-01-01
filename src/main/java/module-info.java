module com.birthdayreminder.birthdayreminder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.birthdayreminder.birthdayreminder to javafx.fxml;
    exports com.birthdayreminder.birthdayreminder;
}