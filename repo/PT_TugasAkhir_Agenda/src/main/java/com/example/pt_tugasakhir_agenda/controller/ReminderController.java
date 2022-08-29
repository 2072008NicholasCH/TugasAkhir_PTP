package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.dao.ReminderDao;
import com.example.pt_tugasakhir_agenda.model.Reminder;
import com.example.pt_tugasakhir_agenda.model.Task;
import com.example.pt_tugasakhir_agenda.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReminderController {
    public Button btnCancel;
    @FXML
    private TextField txtReminderName;
    @FXML
    private DatePicker dateReminder;
    @FXML
    private TextField timeReminder;
    @FXML
    private Button btnReminder;
    private ReminderDao rDao;

    public void initialize() {
        rDao = new ReminderDao();
    }
    public void addReminder() {
        String dateTime = dateReminder.getValue() + " " + timeReminder.getText();

        User u = new User("user1", "user");
        Reminder r = new Reminder(0, txtReminderName.getText(), dateTime, u);
        int res = rDao.addData(r);
        if (res > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reminder added successfully", ButtonType.OK);
            alert.showAndWait();
            txtReminderName.getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error on add reminder", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void updateReminder(Reminder reminder) {
        String dateTime = dateReminder.getValue() + " " + timeReminder.getText();

        User u = new User("user1", "user");
        Reminder r = new Reminder(reminder.getIdreminder(), txtReminderName.getText(), dateTime, u);
        int res = rDao.updateData(r);
        if (res > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reminder updated successfully", ButtonType.OK);
            alert.showAndWait();
            txtReminderName.getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error on update reminder", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void setDate(LocalDate date) {
        dateReminder.setValue(date);
    }

    public void setData(String text, Reminder reminder, LocalTime timeStart) {
        btnReminder.setText(text);
        btnReminder.setOnAction(actionEvent -> {
            updateReminder(reminder);
        });
        txtReminderName.setText(reminder.getRemindername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate startDate = LocalDate.parse(reminder.getRemindertime(), formatter);
        dateReminder.setValue(startDate);
        timeReminder.setText(String.valueOf(timeStart));
    }

    public void cancel(MouseEvent mouseEvent) {
        txtReminderName.clear();
        timeReminder.clear();
        txtReminderName.getScene().getWindow().hide();
    }
}
