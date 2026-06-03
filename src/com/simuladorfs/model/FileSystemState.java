package com.simuladorfs.model;

import com.simuladorfs.journal.Journal;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class FileSystemState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final SimulatedDirectory root;
    private final Journal journal;
    private final LocalDateTime savedAt;

    public FileSystemState(SimulatedDirectory root, Journal journal, LocalDateTime savedAt) {
        this.root = root;
        this.journal = journal;
        this.savedAt = savedAt;
    }

    public SimulatedDirectory getRoot() {
        return root;
    }

    public Journal getJournal() {
        return journal;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }
}
