package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.MainApplication;
import com.example.pt_tugasakhir_agenda.dao.EventDao;
import com.example.pt_tugasakhir_agenda.dao.ReminderDao;
import com.example.pt_tugasakhir_agenda.dao.TaskDao;
import com.example.pt_tugasakhir_agenda.model.Event;
import com.example.pt_tugasakhir_agenda.model.Reminder;
import com.example.pt_tugasakhir_agenda.model.Task;
import com.example.pt_tugasakhir_agenda.model.User;
import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class MainController {

    public ToggleButton tbEvent;
    public ToggleButton tbRemind;
    public ToggleButton tbTask;
    public Button btnTrash;
    public Button btnToday;
    public ImageView imgTrash;
    public ImageView imgEvent;
    public ImageView imgReminder;
    public ImageView imgTask;
    public MenuItem itmAbout;
    public MenuItem itmInfo;
    @FXML
    private Label lbYear;
    @FXML
    private Label lbMonth;
    @FXML
    private DatePicker date;
    @FXML
    private GridPane calendarView;
    private EventDao eDao;
    private TaskDao tDao;
    private ReminderDao rDao;
    private ObservableList<Event> eList;
    private ObservableList<Task> tList;
    private ObservableList<Reminder> rList;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private User user;
    @FXML
    private CheckBox filterEvent;
    @FXML
    private CheckBox filterReminder;
    @FXML
    private CheckBox filterTask;
    @FXML
    private Label name;
    private double xOffset;
    private double yOffset;

    public void initialize() {
        filterEvent.setSelected(true);
        filterEvent.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            changeDate();
        });
        filterReminder.setSelected(true);
        filterReminder.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            changeDate();
        });
        filterTask.setSelected(true);
        filterTask.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            changeDate();
        });
        eDao = new EventDao();
        tDao = new TaskDao();
        rDao = new ReminderDao();
        LocalDate now = LocalDate.now();
        date.setValue(now);
        btnToday.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            setToday();
        });
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
        name.setText(user.getName());
        changeDate();
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                changeDate();
            }
        };
        date.setOnAction(event);

    }

    public void changeDate() {
        eList = eDao.getEventDate(date.getValue().getMonthValue(), date.getValue().getYear(), user.getUsername());
        tList = tDao.getTaskDate(date.getValue().getMonthValue(), date.getValue().getYear(), user.getUsername());
        rList = rDao.getReminderDate(date.getValue().getMonthValue(), date.getValue().getYear(), user.getUsername());

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
        if (filterEvent.isSelected()) {
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
                            sPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
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
                                    getEvent(btn);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            vbox.getChildren().add(btn);
                            calendarView2.add(sPane, cols, rows);
                        }
                    }
                }
                calendarView.getChildren().addAll(calendarView2.getChildren());
            }
        }
        if (filterTask.isSelected()) {
            GridPane calendarView3 = new GridPane();
            for (Task task : tList) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(task.getTasktime(), formatter);
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
                            sPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                                label.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 10; -fx-padding: 5");
                                date.setValue(LocalDate.of(date.getValue().getYear(), date.getValue().getMonthValue(), Integer.parseInt(label.getText())));
                            });
                            if (label.getText().equals(String.valueOf(today.getDayOfMonth()))) {
                                labelDate.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 10; -fx-padding: 5");
                            }
                            Button btn = new Button();
                            btn.setStyle("-fx-background-color: #c8c8c8; -fx-cursor: hand");
                            btn.setText(task.getTaskname());
                            btn.setId(String.valueOf(task.getIdtask()));
                            btn.setOnAction(test -> {
                                try {
                                    getTask(btn);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            vbox.getChildren().add(btn);
                            calendarView3.add(sPane, cols, rows);
                        }
                    }
                }
                calendarView.getChildren().addAll(calendarView3.getChildren());
            }
        }
        if (filterReminder.isSelected()) {
            GridPane calendarView4 = new GridPane();
            for (Reminder reminder : rList) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(reminder.getRemindertime(), formatter);
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
                            sPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                                label.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 10; -fx-padding: 5");
                                date.setValue(LocalDate.of(date.getValue().getYear(), date.getValue().getMonthValue(), Integer.parseInt(label.getText())));
                            });
                            if (label.getText().equals(String.valueOf(today.getDayOfMonth()))) {
                                labelDate.setStyle("-fx-background-color: #FFFF00; -fx-background-radius: 10; -fx-padding: 5");
                            }
                            Button btn = new Button();
                            btn.setStyle("-fx-background-color: #e8a7b1; -fx-cursor: hand");
                            btn.setText(reminder.getRemindername());
                            btn.setId(String.valueOf(reminder.getIdreminder()));
                            btn.setOnAction(test -> {
                                try {
                                    getReminder(btn);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            vbox.getChildren().add(btn);
                            calendarView4.add(sPane, cols, rows);
                        }
                    }
                }
                calendarView.getChildren().addAll(calendarView4.getChildren());
            }
        }
    }

    public void getReminder(Button btn) throws IOException {
        Reminder reminder = rDao.getReminderDetails(Integer.parseInt(btn.getId()));
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType update = new ButtonType("Update");
        ButtonType delete = new ButtonType("Delete");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ok, update, delete);
        String reminderName = reminder.getRemindername();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateStart = LocalDateTime.parse(reminder.getRemindertime(), formatter);
        LocalTime timeStart = dateStart.toLocalTime();
        String details = dateStart.getDayOfMonth() + " " + dateStart.getMonth() + " " + dateStart.getYear() + " " + timeStart;
        alert.setTitle("Reminder Details");
        alert.setHeaderText(reminderName);
        alert.setContentText(details);
        alert.showAndWait();
        if (alert.getResult() == update) {
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("reminder-view.fxml"));
            Parent fxml = fxmlLoader.load();
            fxml.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                }
            });
            fxml.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setX(mouseEvent.getScreenX() - xOffset);
                    stage.setY(mouseEvent.getScreenY() - yOffset);
                }
            });
            Scene scene = new Scene(fxml, 500, 300);
            ReminderController rController = fxmlLoader.getController();
            rController.setDate(date.getValue());
            rController.setData("Update Reminder", reminder, timeStart);
            stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            changeDate();
        } else if (alert.getResult() == delete) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to delete this reminder?", ButtonType.OK, ButtonType.CANCEL);
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {
                rDao.deleteData(reminder);
            }
            changeDate();
        }
    }

    public void getEvent(Button btn) throws IOException {
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
        String details = dateStart.getDayOfMonth() + " " + dateStart.getMonth() + " " + dateStart.getYear() + " " + timeStart + " - " +
                dateStop.getDayOfMonth() + " " + dateStop.getMonth() + " " + dateStop.getYear() + " " + timeStop + "\n" +
                "Category: " + event.getCategory().getCategoryname();
        alert.setTitle("Event Details");
        alert.setHeaderText(eventName);
        alert.setContentText(details);
        alert.showAndWait();
        if (alert.getResult() == update) {
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("event-view.fxml"));
            Parent fxml = fxmlLoader.load();
            fxml.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                }
            });
            fxml.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setX(mouseEvent.getScreenX() - xOffset);
                    stage.setY(mouseEvent.getScreenY() - yOffset);
                }
            });
            Scene scene = new Scene(fxml, 500, 300);
            EventController aeController = fxmlLoader.getController();
            aeController.setDate(date.getValue());
            aeController.setData("Update Event", event, timeStart, timeStop);
            stage = new Stage();
            stage.setTitle("Update Event");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            changeDate();
        } else if (alert.getResult() == trash) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to move to trash?", ButtonType.OK, ButtonType.CANCEL);
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {
                Event e = new Event(event.getIdevent(), event.getEventname(), event.getEventtimestart(), event.getEventtimestop(), 1, event.getCategory(), event.getUsername());
                eDao.updateData(e);
            }
            changeDate();
        }
    }
    public void getTask(Button btn) throws IOException {
        Task task = tDao.getTaskDetails(Integer.parseInt(btn.getId()));
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType update = new ButtonType("Update");
        ButtonType delete = new ButtonType("Delete");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ok, update, delete);
        String taskName = task.getTaskname();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateStart = LocalDateTime.parse(task.getTasktime(), formatter);
        LocalTime timeStart = dateStart.toLocalTime();
        String details = dateStart.getDayOfMonth() + " " + dateStart.getMonth() + " " + dateStart.getYear() + " " + timeStart + "\n" +
                "Category: " + task.getCategory().getCategoryname();
        alert.setTitle("Task Details");
        alert.setHeaderText(taskName);
        alert.setContentText(details);
        alert.showAndWait();
        if (alert.getResult() == update) {
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("task-view.fxml"));
            Parent fxml = fxmlLoader.load();
            fxml.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                }
            });
            fxml.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setX(mouseEvent.getScreenX() - xOffset);
                    stage.setY(mouseEvent.getScreenY() - yOffset);
                }
            });
            Scene scene = new Scene(fxml, 500, 300);
            TaskController tController = fxmlLoader.getController();
            tController.setDate(date.getValue());
            tController.setData("Update Task", task, timeStart);
            stage = new Stage();
            stage.setTitle("Update Task");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            changeDate();
        } else if (alert.getResult() == delete) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to delete this task?", ButtonType.OK, ButtonType.CANCEL);
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {
                tDao.deleteData(task);
            }
            changeDate();
        }
    }
    public void setToday() {
        LocalDate now = LocalDate.now();
        date.setValue(now);
    }
    public void removeGridPane() {
        ObservableList<Node> children = calendarView.getChildren();
        children.removeIf(node -> node instanceof ScrollPane);
    }
    public void showTrash() throws IOException {
        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("trash-view.fxml"));
        Parent fxml = fxmlLoader.load();
        fxml.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });
        fxml.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });
        Scene scene = new Scene(fxml);
        stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        changeDate();
    }
    public void showEvent() throws IOException {
        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("event-view.fxml"));
        Parent fxml = fxmlLoader.load();
        fxml.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });
        fxml.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });
        Scene scene = new Scene(fxml, 500, 300);
        EventController eController = fxmlLoader.getController();
        eController.setDate(date.getValue());
        stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        changeDate();
    }
    public void showTask() throws IOException {
        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("task-view.fxml"));
        Parent fxml = fxmlLoader.load();
        fxml.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });
        fxml.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });
        Scene scene = new Scene(fxml, 500, 300);
        TaskController tController = fxmlLoader.getController();
        tController.setDate(date.getValue());
        stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        changeDate();
    }
    public void showReminder() throws IOException {
        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("reminder-view.fxml"));
        Parent fxml = fxmlLoader.load();
        fxml.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });
        fxml.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });
        Scene scene = new Scene(fxml, 500, 300);
        ReminderController rController = fxmlLoader.getController();
        rController.setDate(date.getValue());
        stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        changeDate();
    }
    public void showCategory() throws IOException {
        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("category-view.fxml"));
        Parent fxml = fxmlLoader.load();
        fxml.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });
        fxml.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });
        Scene scene = new Scene(fxml, 600, 400);
        stage = new Stage();
        stage.setTitle("Category Management");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }
    public void logOut() throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to log out?", ButtonType.OK, ButtonType.CANCEL);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.OK) {
            date.getScene().getWindow().hide();
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("user-login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        }
    }
    public void increaseMonth() {
        date.setValue(date.getValue().plusMonths(1));
    }
    public void decreaseMonth() {
        date.setValue(date.getValue().minusMonths(1));
    }
    public void increaseYear() {
        date.setValue(date.getValue().plusYears(1));
    }
    public void decreaseYear() {
        date.setValue(date.getValue().minusYears(1));
    }

    public void tentang(ActionEvent actionEvent) throws IOException {
        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("about-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void inpoh(ActionEvent actionEvent) {
        Alert alertI = new Alert(Alert.AlertType.INFORMATION, "Agenda 1.0.1 Version 0.0.1 ", ButtonType.OK);
        alertI.setTitle("Version Info");
        alertI.setHeaderText("Agenda 1.0.1 Application");
        alertI.showAndWait();
    }
}