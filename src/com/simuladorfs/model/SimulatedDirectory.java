package com.simuladorfs.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimulatedDirectory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private final Map<String, SimulatedDirectory> directories;
    private final Map<String, SimulatedFile> files;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SimulatedDirectory(String name) {
        this.name = name;
        this.directories = new LinkedHashMap<>();
        this.files = new LinkedHashMap<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public static SimulatedDirectory root() {
        return new SimulatedDirectory("/");
    }

    public String getName() {
        return name;
    }

    public Map<String, SimulatedDirectory> getDirectories() {
        return directories;
    }

    public Map<String, SimulatedFile> getFiles() {
        return files;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isEmpty() {
        return directories.isEmpty() && files.isEmpty();
    }

    public boolean hasDirectory(String directoryName) {
        return directories.containsKey(directoryName);
    }

    public boolean hasFile(String fileName) {
        return files.containsKey(fileName);
    }

    public SimulatedDirectory getDirectory(String directoryName) {
        return directories.get(directoryName);
    }

    public SimulatedFile getFile(String fileName) {
        return files.get(fileName);
    }

    public void addDirectory(SimulatedDirectory directory) {
        directories.put(directory.getName(), directory);
        touch();
    }

    public void addFile(SimulatedFile file) {
        files.put(file.getName(), file);
        touch();
    }

    public void removeDirectory(String directoryName) {
        directories.remove(directoryName);
        touch();
    }

    public void removeFile(String fileName) {
        files.remove(fileName);
        touch();
    }

    public void rename(String newName) {
        this.name = newName;
        touch();
    }

    public void touch() {
        this.updatedAt = LocalDateTime.now();
    }
}
