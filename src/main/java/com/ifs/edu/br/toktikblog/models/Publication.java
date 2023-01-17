package com.ifs.edu.br.toktikblog.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Publication {

    private String uuid;
    private String name;
    private String text;
    private String userUuid;
    private LocalDateTime createdAt;

    public Publication() {
        super();
        this.uuid = UUID.randomUUID().toString();
    }

    public Publication(String name, String text, String userUuid) {
        this.name = name;
        this.text = text;
        this.userUuid = userUuid;
        this.createdAt = LocalDateTime.now();
        this.uuid = UUID.randomUUID().toString();
    }

    public Publication(String uuid, String name, String text, String userUuid) {
        this.uuid = uuid;
        this.name = name;
        this.text = text;
        this.userUuid = userUuid;
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

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
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
                "], profileUuid=" + userUuid +
                ", createdAt=" + createdAt +
                "}";
    }
}
