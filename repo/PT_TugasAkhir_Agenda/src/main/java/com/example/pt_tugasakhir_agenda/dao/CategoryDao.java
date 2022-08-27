package com.example.pt_tugasakhir_agenda.dao;

import com.example.pt_tugasakhir_agenda.model.Category;
import com.example.pt_tugasakhir_agenda.utility.MyConnection;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDao implements DaoInterface<Category>{
    private ObservableList<Category> catList;
    private Connection conn;
    @Override
    public ObservableList<Category> getData() {
        catList = FXCollections.observableArrayList();
        conn = MyConnection.getConnection();
        String query = "SELECT * FROM Category;";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int id = result.getInt("idCategory");
                String kategori = result.getString("categoryName");
                Category c = new Category(id, kategori);
                catList.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return catList;
    }

    @Override
    public int addData(Category data) {
        conn = MyConnection.getConnection();
        String query = "INSERT INTO Category(idCategory, categoryName) VALUES (?, ?);";
        PreparedStatement ps;
        int result;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, data.getIdcategory());
            ps.setString(2, data.getCategoryname());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("add item successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public int updateData(Category data) {
        conn = MyConnection.getConnection();
        String query = "UPDATE Category SET categoryName = ? WHERE idCategory = ?;";
        PreparedStatement ps;
        int result;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, data.getCategoryname());
            ps.setInt(2, data.getIdcategory());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("update item successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public int deleteData(Category data) {
        conn = MyConnection.getConnection();
        String query = "DELETE FROM Category WHERE idCategory = ?;";
        PreparedStatement ps;
        int result;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, data.getIdcategory());
            result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("delete item successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
