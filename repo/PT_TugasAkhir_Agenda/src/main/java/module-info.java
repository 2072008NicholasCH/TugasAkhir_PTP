module com.example.pt_tugasakhir_agenda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;
    requires com.calendarfx.view;
    requires com.google.gson;

    exports com.example.pt_tugasakhir_agenda;
    exports com.example.pt_tugasakhir_agenda.controller;
    opens com.example.pt_tugasakhir_agenda.controller;
    opens com.example.pt_tugasakhir_agenda.model;
    exports com.example.pt_tugasakhir_agenda.model;
    opens com.example.pt_tugasakhir_agenda.dao to javafx.fxml;
    exports com.example.pt_tugasakhir_agenda.dao;
}