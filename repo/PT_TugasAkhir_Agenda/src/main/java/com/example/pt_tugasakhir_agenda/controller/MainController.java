package com.example.pt_tugasakhir_agenda.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Iterator;

public class MainController {

    @FXML
    private DatePicker date;
    @FXML
    private GridPane calendarView;

    public void initialize() {
        // action event
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                removeGridPane();
                LocalDate today = date.getValue();
                LocalDate startOfMonth = today.minusDays(today.getDayOfMonth() - 1);

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
                        GridPane.setHalignment(label, HPos.CENTER);
                        calendarView.add(label, col, row);
                        calendarView.setGridLinesVisible(true);
                        col++;
                        i++;
                    }
                }
            }
        };
        date.setOnAction(event);
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