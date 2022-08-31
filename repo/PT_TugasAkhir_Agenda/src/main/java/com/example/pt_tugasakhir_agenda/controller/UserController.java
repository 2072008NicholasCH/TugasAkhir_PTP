package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.MainApplication;
import com.example.pt_tugasakhir_agenda.dao.UserDao;
import com.example.pt_tugasakhir_agenda.model.User;
import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class UserController {
    @FXML
    private ImageView btnClose;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignup;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPasswordHide;
    @FXML
    private TextField txtPasswordShown;
    @FXML
    private CheckBox checkShown;
    private Stage stage;
    private double xOffset;
    private double yOffset;

    public void initialize() {
        txtPasswordHide.textProperty().bindBidirectional(txtPasswordShown.textProperty());
        checkShown.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                txtPasswordShown.setVisible(true);
                txtPasswordShown.toFront();
                txtPasswordHide.setVisible(false);
            }
            else {
                txtPasswordHide.toFront();
                txtPasswordHide.setVisible(true);
                txtPasswordShown.setVisible(false);
            }
        });
        ColorAdjust color = new ColorAdjust();
        btnClose.setEffect(color);
        btnClose.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                color.setContrast(0.2);
            } else {
                color.setContrast(0.0);
            }
        });
        btnClose.setOnMouseClicked(event-> {
            System.exit(0);
        });
        ColorAdjust colorLogin = new ColorAdjust();
        btnLogin.setEffect(colorLogin);
        btnLogin.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                colorLogin.setContrast(-0.2);
            } else {
                colorLogin.setContrast(0.0);
            }
        });
        ColorAdjust colorSignup = new ColorAdjust();
        btnSignup.setEffect(colorSignup);
        btnSignup.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                colorSignup.setContrast(-0.2);
            } else {
                colorSignup.setContrast(0.0);
            }
        });
    }
    public void btnLogin() throws IOException {
        UserDao dao = new UserDao();
        String md5pw = enkrip(txtPasswordHide.getText());
        if (txtUsername.getText() != null && txtPasswordHide.getText() != null) {
            User u = dao.getUser(new User(txtUsername.getText(),md5pw));
            if (u == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect username or password", ButtonType.OK);
                alert.showAndWait();
            } else {
                u.setPassword("");
                BufferedWriter writer;
                String filename = "user/data.json";
                writer = new BufferedWriter(new FileWriter(filename));
                Gson g = new Gson();
                String json = g.toJson(u);
                writer.write(json);
                writer.close();
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage = new Stage();
                stage.setTitle("Agenda Application");
                stage.setScene(scene);
                stage.show();
                txtUsername.getScene().getWindow().hide();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all the field", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public String enkrip(String pass) {
        // sumber: https://www.javatpoint.com/how-to-encrypt-password-in-java
        /* Plain-text password initialization. */
        String password = pass;
        String encryptedpassword = null;
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        /* Display the unencrypted and encrypted passwords. */
        return encryptedpassword;
    }

    public void showSignup(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("user-register.fxml"));
        Parent fxml = fxmlLoader.load();
        Scene scene = new Scene(fxml);
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
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
