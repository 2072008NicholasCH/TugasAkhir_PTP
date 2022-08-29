package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.dao.UserDao;
import com.example.pt_tugasakhir_agenda.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class UserController {
    @FXML
    private TextField txtusername;
    @FXML
    private TextField txtpassword;

    public void btnlogin(){
        UserDao dao = new UserDao();
        if (txtusername.getText() != null && txtpassword.getText() != null) {
            dao.getUser(new User(txtusername.getText(),txtpassword.getText()));
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Fill all the field", new ButtonType[]{ButtonType.OK});
            alert.showAndWait();
        }
    }
}
