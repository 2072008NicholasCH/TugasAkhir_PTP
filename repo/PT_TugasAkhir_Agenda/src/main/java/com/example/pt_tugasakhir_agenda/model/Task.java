package com.example.pt_tugasakhir_agenda.model;

public class Task {
    private int idtask;
    private String taskname;
    private String tasktime;
    private Category category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public Task(int idtask, String taskname, String tasktime, Category category, User username) {
        this.idtask = idtask;
        this.taskname = taskname;
        this.tasktime = tasktime;
        this.category = category;
        this.username = username;
    }
}
