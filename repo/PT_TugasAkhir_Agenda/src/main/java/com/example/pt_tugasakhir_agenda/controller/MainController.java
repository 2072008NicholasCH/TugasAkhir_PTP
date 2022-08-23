package com.example.pt_tugasakhir_agenda.controller;

import com.calendarfx.view.CalendarView;
import com.example.pt_tugasakhir_agenda.dao.EventDao;
import com.example.pt_tugasakhir_agenda.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import net.sf.jasperreports.engine.export.Grid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        // action event
        tbRemind.setDisable(true);
        tbTask.setDisable(true);
        tbEvent.setDisable(true);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                eList = eDao.getEventDate(date.getValue().getMonthValue());

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
                        Label label = new Label(String.valueOf(i));
                        label.setPadding(new Insets(1));
                        label.setTextAlignment(TextAlignment.CENTER);
                        GridPane.setHalignment(label, HPos.LEFT);
                        GridPane.setValignment(label, VPos.TOP);
                        calendarView.add(label, col, row);
                        calendarView.setGridLinesVisible(true);
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
                                Label eventName = new Label(dateTime.getDayOfMonth() + "\n " + event.getEventname());
//                                eventName.setTextAlignment(TextAlignment.CENTER);
                                GridPane.setHalignment(eventName, HPos.LEFT);
                                GridPane.setValignment(eventName, VPos.TOP);
                                calendarView2.add(eventName ,cols,rows);
                                System.out.println(label.getText());
                            }
                        }
                    }
                }
                calendarView.getChildren().addAll(calendarView2.getChildren());
                tbRemind.setDisable(false);
                tbTask.setDisable(false);
                tbEvent.setDisable(false);
            }
        };
        date.setOnAction(event);


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
            }
        }
    }



}