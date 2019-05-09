package com.adildemokrasi.adil.Entity;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

public class BaseCreation {
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastEditedAt = LocalDateTime.now();

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastEditedAt() {
        return lastEditedAt;
    }

    public void setLastEditedAt(LocalDateTime lastEditedAt) {
        this.lastEditedAt = lastEditedAt;
    }
}
