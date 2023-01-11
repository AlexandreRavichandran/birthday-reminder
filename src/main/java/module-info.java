module com.birthdayreminder.birthdayreminder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;

    opens com.birthdayreminder.birthdayreminder to javafx.fxml;
    exports com.birthdayreminder.birthdayreminder;
    exports com.birthdayreminder.birthdayreminder.service;
    exports com.birthdayreminder.birthdayreminder.controller;
    exports com.birthdayreminder.birthdayreminder.domain;
    opens com.birthdayreminder.birthdayreminder.controller to javafx.fxml;
}