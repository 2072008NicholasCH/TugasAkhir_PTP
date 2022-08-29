package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.dao.CategoryDao;
import com.example.pt_tugasakhir_agenda.dao.EventDao;
import com.example.pt_tugasakhir_agenda.dao.TaskDao;
import com.example.pt_tugasakhir_agenda.model.Category;
import com.example.pt_tugasakhir_agenda.model.Event;
import com.example.pt_tugasakhir_agenda.model.User;
import com.example.pt_tugasakhir_agenda.model.Task;
import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TaskController {
    public Button btnCancel;
    @FXML
    private TextField txtTaskName;
    @FXML
    private TextField timeTask;
    @FXML
    private DatePicker dateTask;
    @FXML
    private ComboBox<Category> cbCategory;
    @FXML
    private Button btnTask;
    private CategoryDao cDao;
    private TaskDao tDao;
    private ObservableList<Category> cList;
    private User user;

    public void initialize() {
        cDao = new CategoryDao();
        tDao = new TaskDao();
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
    public void addTask() {
        String dateTime = dateTask.getValue() + " " + timeTask.getText();

        User u = new User(user.getUsername(), user.getPassword(), user.getName());
        Task t = new Task(0, txtTaskName.getText(), dateTime, cbCategory.getSelectionModel().getSelectedItem(), u);
        int res = tDao.addData(t);
        if (res > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Task added successfully", ButtonType.OK);
            alert.showAndWait();
            txtTaskName.getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error on add task", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void updateTask(Task task) {
        String dateTime = dateTask.getValue() + " " + timeTask.getText();

        User u = new User(user.getUsername(), user.getPassword(), user.getName());
        Task t = new Task(task.getIdtask(), txtTaskName.getText(), dateTime, cbCategory.getSelectionModel().getSelectedItem(), u);
        int res = tDao.updateData(t);
        if (res > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Task updated successfully", ButtonType.OK);
            alert.showAndWait();
            txtTaskName.getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error on update task", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void setDate(LocalDate date) {
        dateTask.setValue(date);
    }

    public void setData(String text, Task task, LocalTime timeStart) {
        btnTask.setText(text);
        btnTask.setOnAction(actionEvent -> {
            updateTask(task);
        });
        txtTaskName.setText(task.getTaskname());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate startDate = LocalDate.parse(task.getTasktime(), formatter);
        dateTask.setValue(startDate);
        timeTask.setText(String.valueOf(timeStart));
        cbCategory.setValue(task.getCategory());
    }

    public void cancel() {
        txtTaskName.clear();
        timeTask.clear();
        txtTaskName.getScene().getWindow().hide();
    }
}
