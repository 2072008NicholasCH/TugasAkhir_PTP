package com.example.pt_tugasakhir_agenda.model;

public class Reminder {
    private int idreminder;
    private String remindername;
    private String remindertime;
    private User username;

    public int getIdreminder() {
        return idreminder;
    }

    public void setIdreminder(int idreminder) {
        this.idreminder = idreminder;
    }

    public String getRemindername() {
        return remindername;
    }

    public void setRemindername(String remindername) {
        this.remindername = remindername;
    }

    public String getRemindertime() {
        return remindertime;
    }

    public void setRemindertime(String remindertime) {
        this.remindertime = remindertime;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public Reminder(int idreminder, String remindername, String remindertime, User username) {
        this.idreminder = idreminder;
        this.remindername = remindername;
        this.remindertime = remindertime;
        this.username = username;
    }
}
