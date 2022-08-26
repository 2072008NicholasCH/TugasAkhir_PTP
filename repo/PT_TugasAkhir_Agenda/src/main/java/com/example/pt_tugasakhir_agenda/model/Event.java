package com.example.pt_tugasakhir_agenda.model;


public class Event {
    private int idevent;
    private String eventname;
    private String eventtimestart;
    private String eventtimestop;
    private int eventtrash;
    private Category category;
    private User username;

    public int getIdevent() {
        return idevent;
    }

    public void setIdevent(int idevent) {
        this.idevent = idevent;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventtimestart() {
        return eventtimestart;
    }

    public void setEventtimestart(String eventtimestart) {
        this.eventtimestart = eventtimestart;
    }

    public String getEventtimestop() {
        return eventtimestop;
    }

    public void setEventtimestop(String eventtimestop) {
        this.eventtimestop = eventtimestop;
    }

    public int getEventtrash() {
        return eventtrash;
    }

    public void setEventtrash(int eventtrash) {
        this.eventtrash = eventtrash;
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

    public Event(int idevent, String eventname, String eventtimestart, String eventtimestop, int eventtrash, Category category, User username) {
        this.idevent = idevent;
        this.eventname = eventname;
        this.eventtimestart = eventtimestart;
        this.eventtimestop = eventtimestop;
        this.eventtrash = eventtrash;
        this.category = category;
        this.username = username;
    }
}
