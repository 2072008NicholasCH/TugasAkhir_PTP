package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.dao.EventDao;
import com.example.pt_tugasakhir_agenda.model.Event;
import com.example.pt_tugasakhir_agenda.model.User;
import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TrashController {
    public Button btnCancel;
    @FXML
    private TableView<Event> tbEvent;
    @FXML
    private TableColumn<String, Event> nameCol;
    @FXML
    private TableColumn<String, Event> startCol;
    @FXML
    private TableColumn<String, Event> finishCol;
    @FXML
    private TableColumn<String, Event> categoryCol;
    private ObservableList<Event> eList, mList;
    private User user;
    private EventDao eDao;
    public void initialize() {
        tbEvent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        eDao = new EventDao();
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
        getData();
    }
    public void getData() {
        eList = eDao.getTrashEvent(user);
        tbEvent.setItems(eList);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("eventname"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("eventtimestart"));
        finishCol.setCellValueFactory(new PropertyValueFactory<>("eventtimestop"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
    }
    public void emptyTrash() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to empty trash?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            for (Event e: eList) {
                eDao.deleteData(e);
            }
        }
        getData();
    }
    public void restoreAll() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to restore all events?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            for (Event e: eList) {
                e.setEventtrash(0);
                eDao.updateData(e);
            }
        }
        getData();
    }
    public void getSelectedItem() {
        mList = tbEvent.getSelectionModel().getSelectedItems();
        if(!tbEvent.getSelectionModel().getSelectedItems().isEmpty()) {
            // create a contextMenu
            ContextMenu contextMenu = new ContextMenu();
            // create MenuItem
            MenuItem delete = new MenuItem("Delete");
            delete.setOnAction((ActionEvent e) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to delete this event?", ButtonType.OK, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    for (Event m: mList) {
                        eDao.deleteData(m);
                    }
                }
                tbEvent.getSelectionModel().clearSelection();
                getData();
            });
            MenuItem restore = new MenuItem("Restore");
            restore.setOnAction((ActionEvent e) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to restore this event?", ButtonType.OK, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    for (Event m : mList) {
                        m.setEventtrash(0);
                        eDao.updateData(m);
                    }
                }
                tbEvent.getSelectionModel().clearSelection();
                getData();
            });

            // add MenuItem to contextMenu
            contextMenu.getItems().add(restore);
            contextMenu.getItems().add(delete);
            tbEvent.setContextMenu(contextMenu);
        }
    }

    public void cancel() {
        tbEvent.getScene().getWindow().hide();
    }
}
