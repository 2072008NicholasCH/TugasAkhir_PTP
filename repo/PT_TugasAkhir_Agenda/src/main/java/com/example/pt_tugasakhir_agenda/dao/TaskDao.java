package com.example.pt_tugasakhir_agenda.dao;

import com.example.pt_tugasakhir_agenda.model.Category;
import com.example.pt_tugasakhir_agenda.model.Event;
import com.example.pt_tugasakhir_agenda.model.Task;
import com.example.pt_tugasakhir_agenda.model.User;
import com.example.pt_tugasakhir_agenda.utility.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskDao implements DaoInterface<Task>{
    private ObservableList<Task> tList;
    private Connection conn;

    @Override
    public ObservableList<Task> getData() {
        tList = FXCollections.observableArrayList();
        conn = MyConnection.getConnection();
        PreparedStatement ps;
        String query = "SELECT t.*, c.*, u.* FROM task t JOIN category c ON t.Category_idCategory = c.idCategory JOIN user u ON t.user_userName = u.userName ;";
        try {
            ps = conn.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int idTask = result.getInt("idTask");
                String taskName = result.getString("taskName");
                String taskStart = result.getString("taskTime");

                int idCategory = result.getInt("Category_idCategory");
                String categoryName = result.getString("categoryName");

                String username = result.getString("userName");
                String password = result.getString("userPassword");

                Category c = new Category(idCategory, categoryName);
                User u = new User(username, password);
                Task t = new Task(idTask, taskName, taskStart, c, u);

                tList.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tList;
    }
    public ObservableList<Task> getTaskDate(int month, int year, String user) {
        tList = FXCollections.observableArrayList();
        conn = MyConnection.getConnection();
        PreparedStatement ps;
        String query = "SELECT t.*, c.*, u.* FROM task t JOIN category c ON t.Category_idCategory = c.idCategory JOIN user u ON t.user_userName = u.userName WHERE MONTH(t.taskTime) = ? AND YEAR(t.taskTime) = ? AND t.user_userName = ?;";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, month);
            ps.setInt(2, year);
            ps.setString(3, user);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int idTask = result.getInt("idTask");
                String taskName = result.getString("taskName");
                String taskStart = result.getString("taskTime");

                int idCategory = result.getInt("Category_idCategory");
                String categoryName = result.getString("categoryName");

                String username = result.getString("userName");
                String password = result.getString("userPassword");

                Category c = new Category(idCategory, categoryName);
                User u = new User(username, password);
                Task t = new Task(idTask, taskName, taskStart, c, u);

                tList.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tList;
    }
    public Task getTaskDetails(int id) {
        conn = MyConnection.getConnection();
        PreparedStatement ps;
        String query = "SELECT t.*, c.*, u.* FROM task t JOIN category c ON t.Category_idCategory = c.idCategory JOIN user u ON t.user_userName = u.userName WHERE idTask = ? ";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int idTask = result.getInt("idTask");
                String taskName = result.getString("taskName");
                String taskStart = result.getString("taskTime");

                int idCategory = result.getInt("Category_idCategory");
                String categoryName = result.getString("categoryName");

                String username = result.getString("userName");
                String password = result.getString("userPassword");

                Category c = new Category(idCategory, categoryName);
                User u = new User(username, password);
                Task t = new Task(idTask, taskName, taskStart, c, u);
                return t;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    @Override
    public int addData(Task data) {
        conn = MyConnection.getConnection();
        String query = "INSERT INTO Task(idtask, taskName, taskTime, Category_idCategory, user_userName) values(?,?,?,?,?)";
        PreparedStatement ps;
        int result;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, data.getIdtask());
            ps.setString(2, data.getTaskname());
            ps.setString(3, data.getTasktime());
            ps.setInt(4,data.getCategory().getIdcategory());
            ps.setString(5,data.getUsername().getUsername());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("add Task successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int updateData(Task data) {
        conn = MyConnection.getConnection();
        String query = "UPDATE Task SET taskName = ?, taskTime = ?, Category_idCategory = ?, user_userName = ? WHERE idTask = ?";
        PreparedStatement ps;
        int result;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, data.getTaskname());
            ps.setString(2, data.getTasktime());
            ps.setInt(3,data.getCategory().getIdcategory());
            ps.setString(4,data.getUsername().getUsername());
            ps.setInt(5, data.getIdtask());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("update Task successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int deleteData(Task data) {
        conn = MyConnection.getConnection();
        String query = "DELETE FROM Task WHERE idTask = ?";
        PreparedStatement ps;
        int result;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1,data.getIdtask());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("delete Task successfully");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
