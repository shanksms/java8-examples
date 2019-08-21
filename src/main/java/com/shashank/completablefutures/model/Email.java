package com.shashank.completablefutures.model;

public class Email {
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

    public Email() {
    }

    public Email(Long id) {
        this.id = id;
    }
}
