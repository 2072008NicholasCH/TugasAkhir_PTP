package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.dao.CategoryDao;
import com.example.pt_tugasakhir_agenda.dao.EventDao;
import com.example.pt_tugasakhir_agenda.model.Category;
import com.example.pt_tugasakhir_agenda.model.Event;
import com.example.pt_tugasakhir_agenda.model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class AddEventController {
    @FXML
    private TextField txtEventName;
    @FXML
    private DatePicker dateStart;
    @FXML
    private TextField timeStart;
    @FXML
    private DatePicker dateFinish;
    @FXML
    private TextField timeFinish;
    @FXML
    private ComboBox<Category> cbCategory;
    private ObservableList<Category> cList;
    private CategoryDao cDao;
    private EventDao eDao;
    public void initialize() {
        cDao = new CategoryDao();
        eDao = new EventDao();
        cList = cDao.getData();
        cbCategory.setItems(cList);
        cbCategory.getSelectionModel().select(0);
    }
    public void setDate(LocalDate date) {
        dateStart.setValue(date);
        dateFinish.setValue(date.plusDays(1));
    }
    public void addEvent() {
        String dateTimeStart = dateStart.getValue() + " " + timeStart.getText();
        String dateTimeFinish = dateFinish.getValue() + " " + timeFinish.getText();
        User u = new User("user1", "user");
        Event e = new Event(0, txtEventName.getText(), dateTimeStart, dateTimeFinish, 0, cbCategory.getSelectionModel().getSelectedItem(), u);
        int res = eDao.addData(e);
        if (res > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Event Added Successfully", ButtonType.OK);
            alert.showAndWait();
            txtEventName.getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Error on add event",ButtonType.OK);
            alert.showAndWait();
        }
    }

}
