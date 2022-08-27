package com.example.pt_tugasakhir_agenda.dao;

import com.example.pt_tugasakhir_agenda.model.Category;
import com.example.pt_tugasakhir_agenda.model.User;
import com.example.pt_tugasakhir_agenda.utility.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements DaoInterface<User>{

    private ObservableList<User> uList;
    private Connection conn;
    @Override
    public ObservableList<User> getData() {
        uList = FXCollections.observableArrayList();
        conn = MyConnection.getConnection();
        String query = "SELECT * FROM User;";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                String id = result.getString("userName");
                String pass = result.getString("userPassword");
                User c = new User(id, pass);
                uList.add(c);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return uList;
    }

    @Override
    public int addData(User data) {
        conn = MyConnection.getConnection();
        String query = "INSERT INTO User(userName, userPassword) VALUES (?, ?);";
        PreparedStatement ps;
        int result;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, data.getUsername());
            ps.setString(2, data.getPassword());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("add user successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int updateData(User data) {
        int result;
        conn = MyConnection.getConnection();
        String query = "UPDATE User SET userPassword = ? WHERE userName = ?;";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, data.getPassword());
            ps.setString(2, data.getUsername());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("update user successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int deleteData(User data) {
        int result;
        conn = MyConnection.getConnection();
        String query = "DELETE FROM User WHERE idCategory = ?;";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, data.getUsername());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("delete user successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
