package com.simuladorfs;

import com.simuladorfs.journal.Journal;
import com.simuladorfs.journal.Operation;
import com.simuladorfs.journal.OperationType;
import com.simuladorfs.model.FileSystemState;
import com.simuladorfs.model.SimulatedDirectory;
import com.simuladorfs.model.SimulatedFile;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileSystemSimulator {
    private static final Path DEFAULT_DATA_FILE = Path.of("filesystem.dat");

    private final Path dataFile;
    private SimulatedDirectory root;
    private Journal journal;

    public FileSystemSimulator() {
        this(DEFAULT_DATA_FILE);
    }

    public FileSystemSimulator(Path dataFile) {
        this.dataFile = Objects.requireNonNull(dataFile, "dataFile");
        this.root = SimulatedDirectory.root();
        this.journal = new Journal();
    }

    public void copyFile(String sourcePath, String targetPath) {
        String normalizedSource = normalizePath(sourcePath);
        String normalizedTarget = normalizePath(targetPath);
        SimulatedFile source = findFile(normalizedSource);
        TargetFile target = resolveTargetFile(normalizedTarget, source.getName());

        Operation operation = Operation.create(OperationType.COPY_FILE, normalizedSource, normalizedTarget, source.getContent());
        journal.record(operation);

        SimulatedFile copy = new SimulatedFile(target.fileName(), source.getContent());
        target.directory().addFile(copy);
        target.directory().touch();

        saveAndCommit(operation.getId());
    }

    public void deleteFile(String path) {
        String normalizedPath = normalizePath(path);
        ParentAndName parentAndName = resolveParentAndName(normalizedPath);

        if (!parentAndName.parent().hasFile(parentAndName.name())) {
            throw new IllegalArgumentException("Arquivo nao encontrado: " + normalizedPath);
        }

        Operation operation = Operation.create(OperationType.DELETE_FILE, normalizedPath, null, null);
        journal.record(operation);

        parentAndName.parent().removeFile(parentAndName.name());
        parentAndName.parent().touch();

        saveAndCommit(operation.getId());
    }

    public void renameFile(String path, String newName) {
        validateName(newName);
        String normalizedPath = normalizePath(path);
        ParentAndName parentAndName = resolveParentAndName(normalizedPath);

        SimulatedFile file = parentAndName.parent().getFile(parentAndName.name());
        if (file == null) {
            throw new IllegalArgumentException("Arquivo nao encontrado: " + normalizedPath);
        }
        if (parentAndName.parent().hasFile(newName) || parentAndName.parent().hasDirectory(newName)) {
            throw new IllegalArgumentException("Ja existe uma entrada com o nome: " + newName);
        }

        Operation operation = Operation.create(OperationType.RENAME_FILE, normalizedPath, newName, null);
        journal.record(operation);

        parentAndName.parent().removeFile(parentAndName.name());
        file.rename(newName);
        parentAndName.parent().addFile(file);
        parentAndName.parent().touch();

        saveAndCommit(operation.getId());
    }

    public void createDirectory(String path) {
        String normalizedPath = normalizePath(path);
        if ("/".equals(normalizedPath)) {
            throw new IllegalArgumentException("O diretorio raiz ja existe.");
        }

        ParentAndName parentAndName = resolveParentAndName(normalizedPath);
        if (parentAndName.parent().hasFile(parentAndName.name()) || parentAndName.parent().hasDirectory(parentAndName.name())) {
            throw new IllegalArgumentException("Ja existe uma entrada com o nome: " + parentAndName.name());
        }

        Operation operation = Operation.create(OperationType.CREATE_DIRECTORY, normalizedPath, null, null);
        journal.record(operation);

        parentAndName.parent().addDirectory(new SimulatedDirectory(parentAndName.name()));
        parentAndName.parent().touch();

        saveAndCommit(operation.getId());
    }

    public void deleteDirectory(String path) {
        String normalizedPath = normalizePath(path);
        if ("/".equals(normalizedPath)) {
            throw new IllegalArgumentException("Nao e permitido apagar o diretorio raiz.");
        }

        ParentAndName parentAndName = resolveParentAndName(normalizedPath);
        SimulatedDirectory directory = parentAndName.parent().getDirectory(parentAndName.name());
        if (directory == null) {
            throw new IllegalArgumentException("Diretorio nao encontrado: " + normalizedPath);
        }
        if (!directory.isEmpty()) {
            throw new IllegalArgumentException("Diretorio nao esta vazio: " + normalizedPath);
        }

        Operation operation = Operation.create(OperationType.DELETE_DIRECTORY, normalizedPath, null, null);
        journal.record(operation);

        parentAndName.parent().removeDirectory(parentAndName.name());
        parentAndName.parent().touch();

        saveAndCommit(operation.getId());
    }

    public void renameDirectory(String path, String newName) {
        validateName(newName);
        String normalizedPath = normalizePath(path);
        if ("/".equals(normalizedPath)) {
            throw new IllegalArgumentException("Nao e permitido renomear o diretorio raiz.");
        }

        ParentAndName parentAndName = resolveParentAndName(normalizedPath);
        SimulatedDirectory directory = parentAndName.parent().getDirectory(parentAndName.name());
        if (directory == null) {
            throw new IllegalArgumentException("Diretorio nao encontrado: " + normalizedPath);
        }
        if (parentAndName.parent().hasFile(newName) || parentAndName.parent().hasDirectory(newName)) {
            throw new IllegalArgumentException("Ja existe uma entrada com o nome: " + newName);
        }

        Operation operation = Operation.create(OperationType.RENAME_DIRECTORY, normalizedPath, newName, null);
        journal.record(operation);

        parentAndName.parent().removeDirectory(parentAndName.name());
        directory.rename(newName);
        parentAndName.parent().addDirectory(directory);
        parentAndName.parent().touch();

        saveAndCommit(operation.getId());
    }

    public List<String> listDirectory(String path) {
        SimulatedDirectory directory = findDirectory(normalizePath(path));
        List<String> entries = new ArrayList<>();

        directory.getDirectories().values().forEach(child -> entries.add("[DIR] " + child.getName()));
        directory.getFiles().values().forEach(file -> entries.add("[FILE] " + file.getName()));

        if (entries.isEmpty()) {
            System.out.println("(vazio)");
            return entries;
        }

        entries.forEach(System.out::println);
        return entries;
    }

    public void createFile(String path, String content) {
        String normalizedPath = normalizePath(path);
        ParentAndName parentAndName = resolveParentAndName(normalizedPath);
        if (parentAndName.parent().hasFile(parentAndName.name()) || parentAndName.parent().hasDirectory(parentAndName.name())) {
            throw new IllegalArgumentException("Ja existe uma entrada com o nome: " + parentAndName.name());
        }

        Operation operation = Operation.create(OperationType.CREATE_FILE, normalizedPath, null, content);
        journal.record(operation);

        parentAndName.parent().addFile(new SimulatedFile(parentAndName.name(), content));
        parentAndName.parent().touch();

        saveAndCommit(operation.getId());
    }

    public void load() {
        if (!Files.exists(dataFile)) {
            save();
            return;
        }

        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(dataFile))) {
            FileSystemState state = (FileSystemState) input.readObject();
            this.root = state.getRoot();
            this.journal = state.getJournal();
            this.journal.recover();
        } catch (IOException | ClassNotFoundException exception) {
            throw new IllegalStateException("Nao foi possivel carregar o arquivo de dados: " + dataFile, exception);
        }
    }

    public void save() {
        FileSystemState state = new FileSystemState(root, journal, LocalDateTime.now());
        try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(dataFile))) {
            output.writeObject(state);
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel salvar o arquivo de dados: " + dataFile, exception);
        }
    }

    public Journal getJournal() {
        return journal;
    }

    public SimulatedDirectory getRoot() {
        return root;
    }

    private void saveAndCommit(String operationId) {
        save();
        journal.markCommitted(operationId);
        save();
    }

    private SimulatedFile findFile(String path) {
        ParentAndName parentAndName = resolveParentAndName(path);
        SimulatedFile file = parentAndName.parent().getFile(parentAndName.name());
        if (file == null) {
            throw new IllegalArgumentException("Arquivo nao encontrado: " + path);
        }
        return file;
    }

    private SimulatedDirectory findDirectory(String path) {
        if ("/".equals(path)) {
            return root;
        }

        SimulatedDirectory current = root;
        for (String part : pathParts(path)) {
            SimulatedDirectory next = current.getDirectory(part);
            if (next == null) {
                throw new IllegalArgumentException("Diretorio nao encontrado: " + path);
            }
            current = next;
        }
        return current;
    }

    private TargetFile resolveTargetFile(String targetPath, String defaultFileName) {
        try {
            SimulatedDirectory existingDirectory = findDirectory(targetPath);
            if (existingDirectory.hasFile(defaultFileName) || existingDirectory.hasDirectory(defaultFileName)) {
                throw new IllegalArgumentException("Ja existe uma entrada com o nome: " + defaultFileName);
            }
            return new TargetFile(existingDirectory, defaultFileName);
        } catch (IllegalArgumentException ignored) {
            ParentAndName parentAndName = resolveParentAndName(targetPath);
            if (parentAndName.parent().hasFile(parentAndName.name()) || parentAndName.parent().hasDirectory(parentAndName.name())) {
                throw new IllegalArgumentException("Ja existe uma entrada com o nome: " + parentAndName.name());
            }
            return new TargetFile(parentAndName.parent(), parentAndName.name());
        }
    }

    private ParentAndName resolveParentAndName(String path) {
        if ("/".equals(path)) {
            throw new IllegalArgumentException("Caminho deve apontar para uma entrada, nao para a raiz.");
        }

        List<String> parts = pathParts(path);
        String name = parts.get(parts.size() - 1);
        validateName(name);

        SimulatedDirectory parent = root;
        for (int index = 0; index < parts.size() - 1; index++) {
            SimulatedDirectory next = parent.getDirectory(parts.get(index));
            if (next == null) {
                throw new IllegalArgumentException("Diretorio pai nao encontrado: " + path);
            }
            parent = next;
        }

        return new ParentAndName(parent, name);
    }

    private String normalizePath(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Caminho nao pode ser vazio.");
        }

        String normalized = path.trim().replace('\\', '/');
        if (!normalized.startsWith("/")) {
            normalized = "/" + normalized;
        }
        while (normalized.contains("//")) {
            normalized = normalized.replace("//", "/");
        }
        if (normalized.length() > 1 && normalized.endsWith("/")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }
        return normalized;
    }

    private List<String> pathParts(String path) {
        String normalized = normalizePath(path);
        if ("/".equals(normalized)) {
            return List.of();
        }

        String[] rawParts = normalized.substring(1).split("/");
        List<String> parts = new ArrayList<>();
        for (String rawPart : rawParts) {
            validateName(rawPart);
            parts.add(rawPart);
        }
        return parts;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome nao pode ser vazio.");
        }
        if (name.contains("/") || name.contains("\\")) {
            throw new IllegalArgumentException("Nome nao pode conter separador de caminho: " + name);
        }
    }

    private record ParentAndName(SimulatedDirectory parent, String name) {
    }

    private record TargetFile(SimulatedDirectory directory, String fileName) {
    }
}
