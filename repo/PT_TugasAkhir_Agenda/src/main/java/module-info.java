module com.example.pt_tugasakhir_agenda {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pt_tugasakhir_agenda to javafx.fxml;
    exports com.example.pt_tugasakhir_agenda;
}