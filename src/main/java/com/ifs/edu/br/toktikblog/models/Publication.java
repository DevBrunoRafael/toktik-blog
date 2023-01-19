package com.ifs.edu.br.toktikblog.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Publication {

    private String uuid;
    private String name;
    private String text;
    private User user;
    private LocalDateTime createdAt;

    public Publication() {
        super();
        this.uuid = UUID.randomUUID().toString();
    }

    public Publication(String name, String text, User user) {
        this.name = name;
        this.text = text;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.uuid = UUID.randomUUID().toString();
    }

    public Publication(String uuid, String name, String text, User user) {
        this.uuid = uuid;
        this.name = name;
        this.text = text;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "{" +
                "uuid=" + uuid +
                ", name=" + name +
                ", text=[" + text +
                "], profileUuid=" + user +
                ", createdAt=" + createdAt +
                "}";
    }
}
