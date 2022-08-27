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
    @Override
    public ObservableList<Category> getData() {
        ObservableList<Category> catList;
        catList = FXCollections.observableArrayList();
        Connection conn = MyConnection.getConnection();
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
        return 0;
    }

    @Override
    public int updateData(Category data) {
        return 0;
    }

    @Override
    public int deleteData(Category data) {
        return 0;
    }
}
