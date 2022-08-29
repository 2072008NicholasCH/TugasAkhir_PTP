package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.dao.CategoryDao;
import com.example.pt_tugasakhir_agenda.dao.EventDao;
import com.example.pt_tugasakhir_agenda.model.Category;
import com.example.pt_tugasakhir_agenda.model.Event;
import com.example.pt_tugasakhir_agenda.model.User;
import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventController {
    public Button btnCancel;
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
    private Button btnEvent;
    @FXML
    private ComboBox<Category> cbCategory;
    private ObservableList<Category> cList;
    private CategoryDao cDao;
    private EventDao eDao;
    private User user;
    public void initialize() {
        cDao = new CategoryDao();
        eDao = new EventDao();
        cList = cDao.getData();
        cbCategory.setItems(cList);
        cbCategory.getSelectionModel().select(0);

        BufferedReader reader;
        String filename = "user/data.json";
        try {
            reader = new BufferedReader(new FileReader(filename));
            String json = reader.readLine();
            Gson g = new Gson();
            user = g.fromJson(json, User.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setDate(LocalDate date) {
        dateStart.setValue(date);
        dateFinish.setValue(date.plusDays(1));
    }
    public void addEvent() {
        if (txtEventName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill the event name field", ButtonType.OK);
            alert.showAndWait();
        } else {
            if (dateStart.getValue().isEqual(dateFinish.getValue())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime start = LocalTime.parse(timeStart.getText(), formatter);
                LocalTime finish = LocalTime.parse(timeFinish.getText(), formatter);
                if (start.isBefore(finish)) {
                    String dateTimeStart = dateStart.getValue() + " " + timeStart.getText();
                    String dateTimeFinish = dateFinish.getValue() + " " + timeFinish.getText();

                    User u = new User(user.getUsername(), user.getPassword(), user.getName());
                    Event e = new Event(0, txtEventName.getText(), dateTimeStart, dateTimeFinish, 0, cbCategory.getSelectionModel().getSelectedItem(), u);
                    int res = eDao.addData(e);
                    if (res > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Event added successfully", ButtonType.OK);
                        alert.showAndWait();
                        txtEventName.getScene().getWindow().hide();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error on add event", ButtonType.OK);
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Finish event time is before Start event time!", ButtonType.OK);
                    alert.showAndWait();
                }
            } else if (dateStart.getValue().isBefore(dateFinish.getValue())) {
                String dateTimeStart = dateStart.getValue() + " " + timeStart.getText();
                String dateTimeFinish = dateFinish.getValue() + " " + timeFinish.getText();

                User u = new User(user.getUsername(), user.getPassword(), user.getName());
                Event e = new Event(0, txtEventName.getText(), dateTimeStart, dateTimeFinish, 0, cbCategory.getSelectionModel().getSelectedItem(), u);
                int res = eDao.addData(e);
                if (res > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Event added successfully", ButtonType.OK);
                    alert.showAndWait();
                    txtEventName.getScene().getWindow().hide();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error on add event", ButtonType.OK);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Finish event date is before Start event date!", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
    public void setData (String text, Event event, LocalTime startTime, LocalTime stopTime) {
        btnEvent.setText(text);
        btnEvent.setOnAction(actionEvent -> {
            updateEvent(event);
        });
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate startDate = LocalDate.parse(event.getEventtimestart(), formatter);
        LocalDate finishDate = LocalDate.parse(event.getEventtimestop(), formatter);
        txtEventName.setText(event.getEventname());
        dateStart.setValue(startDate);
        timeStart.setText(String.valueOf(startTime));
        dateFinish.setValue(finishDate);
        timeFinish.setText(String.valueOf(stopTime));
        cbCategory.setValue(event.getCategory());
    }
    public void updateEvent(Event event) {
        if (txtEventName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill the event name field", ButtonType.OK);
            alert.showAndWait();
        } else {
            if (dateStart.getValue().isEqual(dateFinish.getValue())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime start = LocalTime.parse(timeStart.getText(), formatter);
                LocalTime finish = LocalTime.parse(timeFinish.getText(), formatter);
                if (start.isBefore(finish)) {
                    String dateTimeStart = dateStart.getValue() + " " + timeStart.getText();
                    String dateTimeFinish = dateFinish.getValue() + " " + timeFinish.getText();

                    User u = new User(user.getUsername(), user.getPassword(), user.getName());
                    Event e = new Event(event.getIdevent(), txtEventName.getText(), dateTimeStart, dateTimeFinish, 0, cbCategory.getSelectionModel().getSelectedItem(), u);
                    int res = eDao.updateData(e);
                    if (res > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Event updated successfully", ButtonType.OK);
                        alert.showAndWait();
                        txtEventName.getScene().getWindow().hide();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error on update event", ButtonType.OK);
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Finish event time is before Start event time!", ButtonType.OK);
                    alert.showAndWait();
                }
            } else if (dateStart.getValue().isBefore(dateFinish.getValue())) {
                String dateTimeStart = dateStart.getValue() + " " + timeStart.getText();
                String dateTimeFinish = dateFinish.getValue() + " " + timeFinish.getText();

                User u = new User(user.getUsername(), user.getPassword(), user.getName());
                Event e = new Event(event.getIdevent(), txtEventName.getText(), dateTimeStart, dateTimeFinish, 0, cbCategory.getSelectionModel().getSelectedItem(), u);
                int res = eDao.updateData(e);
                if (res > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Event updated successfully", ButtonType.OK);
                    alert.showAndWait();
                    txtEventName.getScene().getWindow().hide();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error on update event", ButtonType.OK);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Finish event date is before Start event date!", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void cancel() {
        txtEventName.clear();
        timeFinish.clear();
        timeStart.clear();
        txtEventName.getScene().getWindow().hide();
    }
}
