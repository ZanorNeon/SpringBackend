package com.example.SpringBackend.model;

import jakarta.persistence.Id;

public class ToDo {
    @Id
    private long id;
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ToDo(long id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }


}
