package com.hsl.springbootjdbctemplate.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Aymood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;

    @Override
    public String toString() {
        return "Aymood{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    public Aymood() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Aymood(int id, String content) {
        this.id = id;
        this.content = content;
    }
}