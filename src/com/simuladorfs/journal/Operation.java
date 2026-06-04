package com.simuladorfs.journal;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Operation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private final OperationType type;
    private OperationStatus status;
    private final LocalDateTime timestamp;
    private final String sourcePath;
    private final String targetPath;
    private final String payload;

    private Operation(OperationType type, String sourcePath, String targetPath, String payload) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.status = OperationStatus.PENDING;
        this.timestamp = LocalDateTime.now();
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
        this.payload = payload;
    }

    public static Operation create(OperationType type, String sourcePath, String targetPath, String payload) {
        return new Operation(type, sourcePath, targetPath, payload);
    }

    public String getId() {
        return id;
    }

    public OperationType getType() {
        return type;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public String getPayload() {
        return payload;
    }

    public boolean isPending() {
        return status == OperationStatus.PENDING;
    }

    public void markCommitted() {
        this.status = OperationStatus.COMMITTED;
    }

    public String formatForDisplay() {
        return "[" + status + "] "
                + type
                + " id=" + id
                + " origem=" + valueOrDash(sourcePath)
                + " destino=" + valueOrDash(targetPath)
                + " data=" + timestamp;
    }

    private String valueOrDash(String value) {
        if (value == null || value.isBlank()) {
            return "-";
        }
        return value;
    }
}
