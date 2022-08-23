package com.example.pt_tugasakhir_agenda.dao;

import com.example.pt_tugasakhir_agenda.model.Task;
import javafx.collections.ObservableList;

public class TaskDao implements DaoInterface<Task>{
    @Override
    public ObservableList<Task> getData() {
        return null;
    }

    @Override
    public int addData(Task data) {
        return 0;
    }

    @Override
    public int updateData(Task data) {
        return 0;
    }

    @Override
    public int deleteData(Task data) {
        return 0;
    }
}
