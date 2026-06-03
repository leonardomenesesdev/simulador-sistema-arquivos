package com.simuladorfs.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class SimulatedFile implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SimulatedFile(String name, String content) {
        this.name = name;
        this.content = content == null ? "" : content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void rename(String newName) {
        this.name = newName;
        touch();
    }

    public void updateContent(String newContent) {
        this.content = newContent == null ? "" : newContent;
        touch();
    }

    public void touch() {
        this.updatedAt = LocalDateTime.now();
    }
}
