package com.simuladorfs;

import java.util.Scanner;

/**
 * Modo Shell interativo do simulador.
 *
 * Carrega o estado uma vez no inicio, le linhas do teclado, interpreta o
 * comando + argumentos e despacha para o metodo correspondente de
 * {@link FileSystemSimulator}. Cada operacao ja persiste o estado por conta
 * propria (fluxo de journaling); o shell e apenas a camada de entrada/saida.
 */
public class Shell {
    private final FileSystemSimulator simulator;
    private boolean running;

    public Shell(FileSystemSimulator simulator) {
        this.simulator = simulator;
    }

    public void run() {
        simulator.load();
        running = true;

        System.out.println("Simulador de Sistema de Arquivos iniciado.");
        System.out.println("Arquivo de dados: filesystem.dat");
        System.out.println("Digite 'help' para ver os comandos disponiveis.");

        try (Scanner scanner = new Scanner(System.in)) {
            while (running) {
                System.out.print("> ");
                if (!scanner.hasNextLine()) {
                    // Fim da entrada (ex.: EOF ou entrada redirecionada): encerra salvando.
                    System.out.println();
                    break;
                }
                String line = scanner.nextLine();
                handleLine(line);
            }
        }

        simulator.save();
        System.out.println("Estado salvo em filesystem.dat. Ate logo!");
    }

    private void handleLine(String line) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return;
        }

        int firstSpace = trimmed.indexOf(' ');
        String command = (firstSpace == -1 ? trimmed : trimmed.substring(0, firstSpace)).toLowerCase();
        String argsLine = firstSpace == -1 ? "" : trimmed.substring(firstSpace + 1).trim();

        try {
            dispatch(command, argsLine);
        } catch (IllegalArgumentException error) {
            // Erros de validacao das operacoes: avisa, mas mantem o loop vivo.
            System.out.println("Erro: " + error.getMessage());
        }
    }

    private void dispatch(String command, String argsLine) {
        switch (command) {
            case "mkdir" -> {
                String path = requireSingleArg(command, argsLine, "<caminho>");
                simulator.createDirectory(path);
                System.out.println("Diretorio criado: " + path);
            }
            case "rmdir" -> {
                String path = requireSingleArg(command, argsLine, "<caminho>");
                simulator.deleteDirectory(path);
                System.out.println("Diretorio apagado: " + path);
            }
            case "rm" -> {
                String path = requireSingleArg(command, argsLine, "<caminho>");
                simulator.deleteFile(path);
                System.out.println("Arquivo apagado: " + path);
            }
            case "cp" -> {
                String[] parts = requireTwoArgs(command, argsLine, "<origem> <destino>");
                simulator.copyFile(parts[0], parts[1]);
                System.out.println("Arquivo copiado: " + parts[0] + " -> " + parts[1]);
            }
            case "rename", "mv" -> {
                String[] parts = requireTwoArgs(command, argsLine, "<caminho> <novoNome>");
                rename(parts[0], parts[1]);
            }
            case "touch", "create" -> {
                touch(argsLine);
            }
            case "ls" -> {
                String path = argsLine.isEmpty() ? "/" : firstToken(argsLine);
                simulator.listDirectory(path);
            }
            case "journal" -> simulator.listJournalEntries();
            case "help" -> printHelp();
            case "exit", "quit" -> running = false;
            default -> System.out.println("Comando desconhecido: " + command + " (digite 'help').");
        }
    }

    private void rename(String path, String newName) {
        if (simulator.isDirectory(path)) {
            simulator.renameDirectory(path, newName);
            System.out.println("Diretorio renomeado: " + path + " -> " + newName);
        } else if (simulator.isFile(path)) {
            simulator.renameFile(path, newName);
            System.out.println("Arquivo renomeado: " + path + " -> " + newName);
        } else {
            throw new IllegalArgumentException("Entrada nao encontrada: " + path);
        }
    }

    private void touch(String argsLine) {
        if (argsLine.isEmpty()) {
            throw new IllegalArgumentException("Uso: touch <caminho> [conteudo]");
        }
        int space = argsLine.indexOf(' ');
        String path = space == -1 ? argsLine : argsLine.substring(0, space);
        String content = space == -1 ? "" : argsLine.substring(space + 1);
        simulator.createFile(path, content);
        System.out.println("Arquivo criado: " + path);
    }

    private String requireSingleArg(String command, String argsLine, String usage) {
        if (argsLine.isEmpty()) {
            throw new IllegalArgumentException("Uso: " + command + " " + usage);
        }
        return firstToken(argsLine);
    }

    private String[] requireTwoArgs(String command, String argsLine, String usage) {
        String[] parts = argsLine.split("\\s+");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Uso: " + command + " " + usage);
        }
        return new String[] {parts[0], parts[1]};
    }

    private String firstToken(String argsLine) {
        int space = argsLine.indexOf(' ');
        return space == -1 ? argsLine : argsLine.substring(0, space);
    }

    private void printHelp() {
        System.out.println("Comandos disponiveis:");
        System.out.println("  mkdir <caminho>            cria um diretorio");
        System.out.println("  rmdir <caminho>            apaga um diretorio vazio");
        System.out.println("  touch <caminho> [texto]    cria um arquivo (conteudo opcional)");
        System.out.println("  create <caminho> [texto]   alias de touch");
        System.out.println("  cp <origem> <destino>      copia um arquivo");
        System.out.println("  rm <caminho>               apaga um arquivo");
        System.out.println("  rename <caminho> <nome>    renomeia arquivo ou diretorio");
        System.out.println("  mv <caminho> <nome>        alias de rename");
        System.out.println("  ls [caminho]               lista um diretorio (padrao: /)");
        System.out.println("  journal                    lista as entradas do journal");
        System.out.println("  help                       mostra esta ajuda");
        System.out.println("  exit                       salva o estado e encerra");
    }
}
