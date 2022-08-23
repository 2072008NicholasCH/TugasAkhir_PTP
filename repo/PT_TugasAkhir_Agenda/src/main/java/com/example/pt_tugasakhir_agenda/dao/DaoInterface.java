package com.example.pt_tugasakhir_agenda.dao;

import javafx.collections.ObservableList;

public interface DaoInterface<T> {
    ObservableList<T> getData();
    int addData(T data);
    int updateData(T data);
    int deleteData(T data);
}
