package com.shashank.completablefutures.model;

public class User {
    Long id;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }
}
