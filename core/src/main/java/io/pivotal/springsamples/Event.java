package io.pivotal.springsamples;

import java.time.LocalDate;

public class Event {
    private String id;
    private String title;
    private LocalDate date;

    public Event(String title, LocalDate date) {
        this.title = title;
        this.date = date;
    }

    public Event(String id, String title, LocalDate date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }
}
