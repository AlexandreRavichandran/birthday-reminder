package com.birthdayreminder.birthdayreminder.controller;


import com.birthdayreminder.birthdayreminder.domain.Birthday;
import com.birthdayreminder.birthdayreminder.service.BirthdayServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BirthdayController {

    BirthdayServiceImpl birthdayService = new BirthdayServiceImpl();

    @FXML
    DatePicker birthdayDate;

    @FXML
    TextField birthdayName;

    Long selectedBirthdayId;

    public void onAddBirthday(ActionEvent event) {
        Birthday birthday = new Birthday();
        birthday.setName(birthdayName.getText());
        birthday.setDate(birthdayDate.getValue().toString());

        this.birthdayService.addNew(birthday);
        this.onClose(event);
    }

    public void onClose(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onEditBirthday(ActionEvent event) {
        Birthday birthday = new Birthday();
        birthday.setId(this.selectedBirthdayId);
        birthday.setName(birthdayName.getText());
        birthday.setDate(birthdayDate.getValue().toString());

        this.birthdayService.edit(birthday);
        this.onClose(event);
    }

}
