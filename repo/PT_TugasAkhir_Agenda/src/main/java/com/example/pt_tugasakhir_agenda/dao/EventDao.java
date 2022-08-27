package com.example.pt_tugasakhir_agenda.dao;

import com.example.pt_tugasakhir_agenda.model.Category;
import com.example.pt_tugasakhir_agenda.model.Event;
import com.example.pt_tugasakhir_agenda.model.User;
import com.example.pt_tugasakhir_agenda.utility.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EventDao implements DaoInterface<Event> {
    ObservableList<Event> eList;
    Connection conn;

    @Override
    public ObservableList<Event> getData() {
        eList = FXCollections.observableArrayList();
        conn = MyConnection.getConnection();
        String query = "SELECT e.*, c.*, u.* FROM event e JOIN category c ON e.Category_idCategory = c.idCategory JOIN user u ON e.user_userName = u.userName;";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int idEvent = result.getInt("idEvent");
                String eventName = result.getString("eventName");
                String eventStart = result.getString("eventTimeStart");
                String eventEnd = result.getString("eventTimeStop");
                int eventTrash = result.getInt("eventTrash");

                int idCategory = result.getInt("Category_idCategory");
                String categoryName = result.getString("categoryName");

                String username = result.getString("userName");
                String password = result.getString("userPassword");

                Category c = new Category(idCategory, categoryName);
                User u = new User(username, password);
                Event e = new Event(idEvent, eventName, eventStart, eventEnd, eventTrash, c, u);
                eList.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return eList;
    }
    public Event getEventDetails(int id) {
        conn = MyConnection.getConnection();
        String query = "SELECT e.*, c.*, u.* FROM event e JOIN category c ON e.Category_idCategory = c.idCategory JOIN user u ON e.user_userName = u.userName WHERE e.idEvent = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int idEvent = result.getInt("idEvent");
                String eventName = result.getString("eventName");
                String eventStart = result.getString("eventTimeStart");
                String eventEnd = result.getString("eventTimeStop");
                int eventTrash = result.getInt("eventTrash");

                int idCategory = result.getInt("Category_idCategory");
                String categoryName = result.getString("categoryName");

                String username = result.getString("userName");
                String password = result.getString("userPassword");

                Category c = new Category(idCategory, categoryName);
                User u = new User(username, password);
                Event e = new Event(idEvent, eventName, eventStart, eventEnd, eventTrash, c, u);
                return e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public ObservableList<Event> getEventDate(int month, int year) {
        eList = FXCollections.observableArrayList();
        conn = MyConnection.getConnection();
        String query = "SELECT e.*, c.*, u.* FROM event e JOIN category c ON e.Category_idCategory = c.idCategory JOIN user u ON e.user_userName = u.userName WHERE MONTH(e.eventTimeStart) = ? AND YEAR(e.eventTimeStart) = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int idEvent = result.getInt("idEvent");
                String eventName = result.getString("eventName");
                String eventStart = result.getString("eventTimeStart");
                String eventEnd = result.getString("eventTimeStop");
                int eventTrash = result.getInt("eventTrash");

                int idCategory = result.getInt("Category_idCategory");
                String categoryName = result.getString("categoryName");

                String username = result.getString("userName");
                String password = result.getString("userPassword");

                Category c = new Category(idCategory, categoryName);
                User u = new User(username, password);
                Event e = new Event(idEvent, eventName, eventStart, eventEnd, eventTrash, c, u);
                eList.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return eList;
    }

    @Override
    public int addData(Event data) {
        conn = MyConnection.getConnection();
        String query = "INSERT INTO event(idEvent, eventName, eventTimeStart, eventTimeStop, eventTrash, Category_idCategory, user_userName) values(?,?,?,?,?,?,?)";
        PreparedStatement ps;
        int result;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, data.getIdevent());
            ps.setString(2, data.getEventname());
            ps.setString(3, data.getEventtimestart());
            ps.setString(4,data.getEventtimestop());
            ps.setInt(5,data.getEventtrash());
            ps.setInt(6,data.getCategory().getIdcategory());
            ps.setString(7,data.getUsername().getUsername());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("add event successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int updateData(Event data) {
        conn = MyConnection.getConnection();
        String query = "UPDATE event SET eventName = ?, eventTimeStart = ?, eventTimeStop = ?, eventTrash = ?, Category_idCategory = ?, user_userName = ? WHERE idEvent = ?";
        PreparedStatement ps;
        int result;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, data.getEventname());
            ps.setString(2, data.getEventtimestart());
            ps.setString(3,data.getEventtimestop());
            ps.setInt(4,data.getEventtrash());
            ps.setInt(5,data.getCategory().getIdcategory());
            ps.setString(6,data.getUsername().getUsername());
            ps.setInt(7, data.getIdevent());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("update event successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int deleteData(Event data) {
        conn = MyConnection.getConnection();
        String query = "DELETE FROM event WHERE idEvent = ?";
        PreparedStatement ps;
        int result;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1,data.getIdevent());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("delete event successfully");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
