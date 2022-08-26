package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.MainApplication;
import com.example.pt_tugasakhir_agenda.model.Category;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddEventController {
    @FXML
    private TextField txtEventName;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateFinish;
    @FXML
    private ComboBox<Category> cbCategory;

    public void setDate(LocalDate date) {
        dateStart.setValue(date);
        dateFinish.setValue(date.plusDays(1));
        System.out.println(date);
    }

}
