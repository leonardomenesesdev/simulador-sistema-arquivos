package com.simuladorfs.journal;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Journal implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Operation> entries = new ArrayList<>();

    public void record(Operation operation) {
        entries.add(operation);
    }

    public void markCommitted(String operationId) {
        entries.stream()
                .filter(operation -> operation.getId().equals(operationId))
                .findFirst()
                .ifPresent(Operation::markCommitted);
    }

    public List<Operation> recover() {
        return entries.stream()
                .filter(Operation::isPending)
                .toList();
    }

    public List<Operation> listEntries() {
        return Collections.unmodifiableList(entries);
    }
}
