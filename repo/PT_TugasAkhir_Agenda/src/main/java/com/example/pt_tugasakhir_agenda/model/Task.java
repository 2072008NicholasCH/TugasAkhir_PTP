package com.example.pt_tugasakhir_agenda.model;

public class Task {
    private int idtask;
    private String taskname;
    private String tasktime;
    private Category idcategory;
    private User username;

    public int getIdtask() {
        return idtask;
    }

    public void setIdtask(int idtask) {
        this.idtask = idtask;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTasktime() {
        return tasktime;
    }

    public void setTasktime(String tasktime) {
        this.tasktime = tasktime;
    }

    public Category getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(Category idcategory) {
        this.idcategory = idcategory;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public Task(int idtask, String taskname, String tasktime, Category idcategory, User username) {
        this.idtask = idtask;
        this.taskname = taskname;
        this.tasktime = tasktime;
        this.idcategory = idcategory;
        this.username = username;
    }
}
