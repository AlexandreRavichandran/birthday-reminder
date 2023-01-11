package com.birthdayreminder.birthdayreminder.controller;

import com.birthdayreminder.birthdayreminder.HelloApplication;
import com.birthdayreminder.birthdayreminder.domain.Birthday;
import com.birthdayreminder.birthdayreminder.service.BirthdayServiceImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


public class HomeController implements Initializable {

    BirthdayServiceImpl birthdayService = new BirthdayServiceImpl();

    private static LocalDate getLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }

    @FXML
    private TableView<Birthday> tableView;

    @FXML
    public TableColumn<Birthday, String> name;

    @FXML
    public TableColumn<Birthday, String> date;

    @FXML
    public Button editButton;

    @FXML
    public Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editButton.setVisible(Boolean.FALSE);
        deleteButton.setVisible(Boolean.FALSE);
        this.name.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
        this.date.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDate()));
        this.updateBirthdayList();

    }

    public void delete() {
        this.birthdayService.delete(this.tableView.getSelectionModel().getSelectedItem());
        this.updateBirthdayList();
    }

    public void onCloseApp(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
    }

    public void onAddBirthday() {
        Birthday birthday = new Birthday();
        birthday.setName("Added");
        birthday.setDate(new Date().toString());

        this.birthdayService.addNew(birthday);
        this.updateBirthdayList();
    }


    private void updateBirthdayList() {
        this.tableView.setItems(FXCollections.observableArrayList(birthdayService.browse()));
    }

    public void openAddWindow() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("birthday-add-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 395, 400);
        stage.setTitle("Add a new birthday");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        this.updateBirthdayList();
    }

    public void showSelected() {
        if (nonNull(this.tableView.getSelectionModel().getSelectedItem())) {
            this.deleteButton.setVisible(Boolean.TRUE);
            this.editButton.setVisible(Boolean.TRUE);
        } else {
            this.deleteButton.setVisible(Boolean.FALSE);
            this.editButton.setVisible(Boolean.FALSE);
        }

    }

    public void openEditWindow() throws IOException {
        Birthday selectedBirthday = this.tableView.getSelectionModel().getSelectedItem();
        if(isNull(selectedBirthday)) {
            return;
        }

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("birthday-edit-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 395, 400);
        stage.setTitle("Edit a birthday");



        BirthdayController birthdayController = fxmlLoader.getController();
        birthdayController.birthdayName.setText(selectedBirthday.getName());
        birthdayController.birthdayDate.setValue(getLocalDate(selectedBirthday.getDate()));
        birthdayController.selectedBirthdayId = selectedBirthday.getId();
        fxmlLoader.setController(birthdayController);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        this.updateBirthdayList();
    }

}