package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.dao.UserDao;
import com.example.pt_tugasakhir_agenda.model.User;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpController {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPasswordShown;
    @FXML
    private TextField txtName;
    @FXML
    private PasswordField txtPasswordHide;
    @FXML
    private CheckBox checkShown;
    @FXML
    private ImageView btnClose;
    @FXML
    private Button btnSignup;
    public void initialize() {
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
            txtName.getScene().getWindow().hide();
        });
        txtPasswordHide.textProperty().bindBidirectional(txtPasswordShown.textProperty());
        checkShown.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                txtPasswordShown.toFront();
                txtPasswordShown.setVisible(true);
                txtPasswordHide.setVisible(false);
            }
            else {
                txtPasswordHide.toFront();
                txtPasswordShown.setVisible(false);
                txtPasswordHide.setVisible(true);
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
    public void signUp(ActionEvent actionEvent) {
        UserDao dao = new UserDao();
        String md5pw = enkrip(txtPasswordHide.getText());
        if (txtUsername.getText() != null && txtPasswordHide.getText() != null && txtName.getText() != null) {
            User u = dao.checkUser(new User(txtUsername.getText(),md5pw, txtName.getText()));
            if (u == null) {
                int res = dao.addData(new User(txtUsername.getText(), md5pw, txtName.getText()));
                if (res > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sign up successfully, please login again.", ButtonType.OK);
                    alert.showAndWait();
                    txtUsername.getScene().getWindow().hide();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username \"" + txtUsername.getText() + "\" is exists, please choose other username.", ButtonType.OK);
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Fill all the field", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public String enkrip(String pass) {
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
}
