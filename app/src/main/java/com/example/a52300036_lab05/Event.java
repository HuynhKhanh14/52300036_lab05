package com.example.a52300036_lab05;

public class Event {
    public String name;
    public String place;
    public String date;
    public String time;
    public boolean enabled;

    public Event(String name, String place, String date, String time, boolean enabled) {
        this.name = name;
        this.place = place;
        this.date = date;
        this.time = time;
        this.enabled = enabled;
    }
}