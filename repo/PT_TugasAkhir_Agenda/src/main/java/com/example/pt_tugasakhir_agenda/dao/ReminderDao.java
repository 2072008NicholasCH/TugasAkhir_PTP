package com.example.pt_tugasakhir_agenda.dao;

import com.example.pt_tugasakhir_agenda.model.Reminder;
import com.example.pt_tugasakhir_agenda.model.User;
import com.example.pt_tugasakhir_agenda.utility.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReminderDao implements DaoInterface<Reminder>{
    ObservableList<Reminder> rList;
    Connection conn;

    @Override
    public ObservableList<Reminder> getData() {
        rList = FXCollections.observableArrayList();
        conn = MyConnection.getConnection();
        String query = "SELECT r.*, u.* FROM reminder r JOIN user u ON r.user_userName = u.userName;";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int idRemind = result.getInt("idReminder");
                String rName = result.getString("reminderName");
                String rTime = result.getString("reminderTime");
                String username = result.getString("userName");
                String password = result.getString("userPassword");
                User u = new User(username, password);
                Reminder r = new Reminder(idRemind, rName, rTime, u);
                rList.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rList;
    }
    public ObservableList<Reminder> getReminderDate(int month, int year, String user) {
        rList = FXCollections.observableArrayList();
        conn = MyConnection.getConnection();
        String query = "SELECT r.*, u.* FROM reminder r JOIN user u ON r.user_userName = u.userName WHERE MONTH(r.reminderTime) = ? AND YEAR(r.reminderTime) = ? AND r.user_userName = ?;";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, month);
            ps.setInt(2, year);
            ps.setString(3, user);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int idRemind = result.getInt("idReminder");
                String rName = result.getString("reminderName");
                String rTime = result.getString("reminderTime");
                String username = result.getString("userName");
                String password = result.getString("userPassword");
                User u = new User(username, password);
                Reminder r = new Reminder(idRemind, rName, rTime, u);
                rList.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rList;
    }
    public Reminder getReminderDetails(int id) {
        conn = MyConnection.getConnection();
        String query = "SELECT r.*, u.* FROM reminder r JOIN user u ON r.user_userName = u.userName WHERE r.idReminder = ?;";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int idRemind = result.getInt("idReminder");
                String rName = result.getString("reminderName");
                String rTime = result.getString("reminderTime");
                String username = result.getString("userName");
                String password = result.getString("userPassword");
                User u = new User(username, password);
                Reminder r = new Reminder(idRemind, rName, rTime, u);
                return r;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public int addData(Reminder data) {
        int result;
        conn = MyConnection.getConnection();
        String query = "INSERT INTO Reminder(idReminder, reminderName, reminderTime, user_userName) values(?, ?, ?, ?);";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, data.getIdreminder());
            ps.setString(2, data.getRemindername());
            ps.setString(3, data.getRemindertime());
            ps.setString(4, data.getUsername().getUsername());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("add reminder successfully");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int updateData(Reminder data) {
        int result;
        conn = MyConnection.getConnection();
        String query = "UPDATE Reminder SET reminderName = ?, reminderTime = ?, user_userName = ? WHERE idReminder = ?;";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, data.getRemindername());
            ps.setString(2, data.getRemindertime());
            ps.setString(3, data.getUsername().getUsername());
            ps.setInt(4, data.getIdreminder());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("update reminder successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int deleteData(Reminder data) {
        int result;
        conn = MyConnection.getConnection();
        String query = "DELETE FROM Reminder WHERE idReminder = ?;";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, data.getIdreminder());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("delete reminder successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
