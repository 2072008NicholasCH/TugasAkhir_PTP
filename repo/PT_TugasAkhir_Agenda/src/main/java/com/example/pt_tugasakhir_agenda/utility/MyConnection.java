package com.example.pt_tugasakhir_agenda.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection getConnection(){
        // 13.214.43.159 admindbs
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://13.214.43.159:3306/PT_TA_Agenda",
                    "admindbs",
                    ""
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);

        }
        return conn;
    }
}
