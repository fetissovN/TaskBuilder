package com.example.nick.taskbuilder.entity;


import java.io.Serializable;

public class Task implements Serializable {
    private long id;

    private String name;

    private String description;

    private String startes;

    private String expires;

    private String priority;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartes() {
        return startes;
    }

    public void setStartes(String startes) {
        this.startes = startes;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
