package com.example.pt_tugasakhir_agenda.dao;

import com.example.pt_tugasakhir_agenda.model.User;
import javafx.collections.ObservableList;

public class UserDao implements DaoInterface<User>{
    @Override
    public ObservableList<User> getData() {
        return null;
    }

    @Override
    public int addData(User data) {
        return 0;
    }

    @Override
    public int updateData(User data) {
        return 0;
    }

    @Override
    public int deleteData(User data) {
        return 0;
    }
}
