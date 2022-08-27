package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.MainApplication;
import com.example.pt_tugasakhir_agenda.dao.EventDao;
import com.example.pt_tugasakhir_agenda.model.Event;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private Button today;
    private EventDao eDao;
    private ObservableList<Event> eList;
    private FXMLLoader fxmlLoader;
    private Stage stage;

    public void initialize() {
        eDao = new EventDao();
        LocalDate now = LocalDate.now();
        date.setValue(now);
        today.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            setToday();
        });
        changeDate();
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                changeDate();
            }
        };
        date.setOnAction(event);
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
                ScrollPane sPane = new ScrollPane();
                sPane.setStyle("-fx-background-color: transparent");
                VBox vbox = new VBox();
                Label label = new Label(String.valueOf(i));
                vbox.getChildren().add(label);
                sPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
                    label.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 10; -fx-padding: 5");
                    date.setValue(LocalDate.of(date.getValue().getYear(), date.getValue().getMonthValue(), Integer.parseInt(label.getText())));
                });
                if (today.getDayOfMonth() == i) {
                    label.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 10; -fx-padding: 5");
                }
                GridPane.setHalignment(label, HPos.LEFT);
                GridPane.setValignment(label, VPos.TOP);
                sPane.setContent(vbox);
                calendarView.add(sPane ,col,row);
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
                if (node instanceof ScrollPane) {
                    ScrollPane sPane = (ScrollPane) node;
                    VBox vbox = (VBox) sPane.getContent();
                    Label label = (Label) vbox.getChildren().get(0);
                    if (label.getText().equals(String.valueOf(dateTime.getDayOfMonth()))) {
                        children.remove();
                        int rows = GridPane.getRowIndex(node);
                        int cols = GridPane.getColumnIndex(node);
                        Label labelDate = new Label(String.valueOf(dateTime.getDayOfMonth()));
                        vbox.setSpacing(5);
                        sPane.setStyle("-fx-background-color: transparent");
                        sPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
                            label.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 10; -fx-padding: 5");
                            date.setValue(LocalDate.of(date.getValue().getYear(), date.getValue().getMonthValue(), Integer.parseInt(label.getText())));
                        });
                        if (label.getText().equals(String.valueOf(today.getDayOfMonth()))) {
                            labelDate.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 10; -fx-padding: 5");
                        }
                        Button btn = new Button();
                        btn.setStyle("-fx-background-color: #86c5db; -fx-cursor: hand");
                        btn.setText(event.getEventname());
                        btn.setId(String.valueOf(event.getIdevent()));
                        btn.setOnAction(test -> {
                            try {
                                getData(btn);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        vbox.getChildren().add(btn);
                        calendarView2.add(sPane,cols,rows);
                    }
                }
            }
            calendarView.getChildren().addAll(calendarView2.getChildren());
        }
    }
    public void getData(Button btn) throws IOException {
        Event event = eDao.getEventDetails(Integer.parseInt(btn.getId()));
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType update = new ButtonType("Update");
        ButtonType trash = new ButtonType("Move to Trash");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ok, update, trash);
        String eventName = event.getEventname();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateStart = LocalDateTime.parse(event.getEventtimestart(), formatter);
        LocalDateTime dateStop = LocalDateTime.parse(event.getEventtimestop(), formatter);
        LocalTime timeStart = dateStart.toLocalTime();
        LocalTime timeStop = dateStop.toLocalTime();
        System.out.println(dateStart);
        System.out.println(dateStop);
        String details = dateStart.getDayOfMonth() + " " + dateStart.getMonth() + " " + dateStart.getYear() + " " + timeStart + " - " +
                dateStop.getDayOfMonth() + " " + dateStop.getMonth() + " " + dateStop.getYear() + " " + timeStop + "\n" +
                "Category: " + event.getCategory().getCategoryname();
        alert.setTitle("Event Details");
        alert.setHeaderText(eventName);
        alert.setContentText(details);
        alert.showAndWait();
        if (alert.getResult() == update) {
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("add-event-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 300);
            AddEventController aeController = fxmlLoader.getController();
            aeController.setDate(date.getValue());
            aeController.setData("Update Event", event, timeStart, timeStop);
            stage = new Stage();
            stage.setTitle("Update Event");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            changeDate();
        } else if (alert.getResult() == trash) {
            Event e = new Event(event.getIdevent(), event.getEventname(), event.getEventtimestart(), event.getEventtimestop(), 1, event.getCategory(), event.getUsername());
            eDao.updateData(e);
            changeDate();
        }
    }
    public void setToday() {
        LocalDate now = LocalDate.now();
        date.setValue(now);
    }
    public void addReminder(){
        System.out.println("itil");
    }

    public void addTask(){
        System.out.println("Alek Jancok");
    }

    public void removeGridPane() {
        ObservableList<Node> children = calendarView.getChildren();
        children.removeIf(node -> node instanceof ScrollPane);
    }
    public void showAddEvent() throws IOException {
        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("add-event-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        AddEventController aeController = fxmlLoader.getController();
        aeController.setDate(date.getValue());
        stage = new Stage();
        stage.setTitle("Add Event");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        changeDate();
    }
    public void showCategory() throws IOException {
        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("category.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage = new Stage();
        stage.setTitle("Category Management");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}