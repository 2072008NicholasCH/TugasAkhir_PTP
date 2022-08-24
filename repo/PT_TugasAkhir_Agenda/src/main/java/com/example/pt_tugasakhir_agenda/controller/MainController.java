package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.dao.EventDao;
import com.example.pt_tugasakhir_agenda.model.Event;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class MainController {

    @FXML
    private Label lbYear;
    @FXML
    private Label lbMonth;
    @FXML
    private DatePicker date;
    @FXML
    private GridPane calendarView;
    @FXML
    private ToggleButton tbRemind;
    @FXML
    private ToggleButton tbTask;
    @FXML
    private ToggleButton tbEvent;
    private EventDao eDao;
    private ObservableList<Event> eList;

    public void initialize() {
        eDao = new EventDao();
        LocalDate now = LocalDate.now();
        date.setValue(now);
        changeDate();
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                changeDate();
            }
        };
        date.setOnAction(event);
        System.out.println(now);
    }
    public void changeDate() {
        eList = eDao.getEventDate(date.getValue().getMonthValue(), date.getValue().getYear());

        removeGridPane();
        LocalDate today = date.getValue();
        LocalDate startOfMonth = today.minusDays(today.getDayOfMonth() - 1);

        lbMonth.setText(String.valueOf(date.getValue().getMonth()));
        lbYear.setText(String.valueOf(date.getValue().getYear()));

        int month = date.getValue().getMonthValue();
        YearMonth yearMonthObject = YearMonth.of(2022, month);
        int daysInMonth = yearMonthObject.lengthOfMonth(); //28
        int i = 1;
        int row = 1;
        int col;
        if (startOfMonth.getDayOfWeek().getValue() == 7) {
            col = 0;
        } else {
            col = startOfMonth.getDayOfWeek().getValue();
        }
        while (i <= daysInMonth) {
            if (col > 6) {
                col = 0;
                row += 1;
            } else {
                if (today.getDayOfMonth() == i) {
                    Label label = new Label(String.valueOf(i));
                    label.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 10; -fx-padding: 5");
                    GridPane.setHalignment(label, HPos.LEFT);
                    GridPane.setValignment(label, VPos.TOP);
                    calendarView.add(label ,col,row);
                } else {
                    Label label = new Label(String.valueOf(i));
                    GridPane.setHalignment(label, HPos.LEFT);
                    GridPane.setValignment(label, VPos.TOP);
                    calendarView.add(label, col, row);
                }
                col++;
                i++;
            }
        }
        GridPane calendarView2 = new GridPane();
        for (Event event : eList) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(event.getEventtimestart(), formatter);
            Iterator<Node> children = calendarView.getChildren().iterator();
            while (children.hasNext()) {
                Node node = children.next();
                if (node instanceof Label) {
                    Label label = (Label) node;
                    if (label.getText().equals(String.valueOf(dateTime.getDayOfMonth()))) {
                        children.remove();
                        int rows = GridPane.getRowIndex(node);
                        int cols = GridPane.getColumnIndex(node);
                        VBox vBox = new VBox();
                        Label eventName = new Label(String.valueOf(dateTime.getDayOfMonth()));
                        if (label.getText().equals(String.valueOf(today.getDayOfMonth()))) {
                            eventName.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 10; -fx-padding: 5");
                        }
                        Button btn = new Button();
                        btn.setStyle("-fx-background-color: #00FF00; -fx-cursor: hand");
                        btn.setText(event.getEventname());
                        btn.setId(String.valueOf(event.getIdevent()));
                        btn.setOnAction(test -> getData(btn));

                        vBox.getChildren().add(eventName);
                        vBox.getChildren().add(btn);
                        GridPane.setHalignment(eventName, HPos.LEFT);
                        GridPane.setValignment(eventName, VPos.TOP);
                        calendarView2.add(vBox,cols,rows);
                    }
                }
            }
            calendarView.getChildren().addAll(calendarView2.getChildren());
        }
    }
    public void getData(Button btn) {
        Event test = eDao.getEventDetails(Integer.parseInt(btn.getId()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        String eventName = test.getEventname();
        String details = test.getEventtimestart() + " - " + test.getEventtimestop() + "\n" +
                "Category: " + test.getIdcategory().getCategoryname();
        alert.setTitle("Event Details");
        alert.setHeaderText(eventName);
        alert.setContentText(details);
        alert.showAndWait();
        System.out.println(test.getEventname());
    }
    public void addEvent() {
        System.out.println("test");
    }

    public void addReminder(){
        System.out.println("itil");
    }

    public void addTask(){
        System.out.println("Alek Jancok");
    }

    public void removeGridPane() {
        ObservableList<Node> children = calendarView.getChildren();
        Iterator<Node> iter = children.iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node instanceof Label) {
                iter.remove();
            } else if (node instanceof VBox) {
                iter.remove();
            }
        }
    }



}