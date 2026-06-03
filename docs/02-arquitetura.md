# 02 - Arquitetura do Simulador

## Objetivo

Definir as classes principais e a responsabilidade de cada uma.

## Classes Sugeridas

### FileSystemSimulator

Responsavel por coordenar o sistema de arquivos simulado.

Metodos esperados:

- `copyFile(String sourcePath, String targetPath)`
- `deleteFile(String path)`
- `renameFile(String path, String newName)`
- `createDirectory(String path)`
- `deleteDirectory(String path)`
- `renameDirectory(String path, String newName)`
- `listDirectory(String path)`
- `load()`
- `save()`

### SimulatedFile

Representa um arquivo dentro do `.dat`.

Atributos sugeridos:

- `name`
- `content`
- `createdAt`
- `updatedAt`

### SimulatedDirectory

Representa um diretorio dentro do `.dat`.

Atributos sugeridos:

- `name`
- `directories`
- `files`
- `createdAt`
- `updatedAt`

### Journal

Gerencia o registro das operacoes antes da execucao definitiva.

Metodos esperados:

- `record(Operation operation)`
- `markCommitted(String operationId)`
- `recover()`
- `listEntries()`

### Operation

Representa uma operacao registrada no journal.

Atributos sugeridos:

- `id`
- `type`
- `status`
- `timestamp`
- `sourcePath`
- `targetPath`
- `payload`

## Mini Atividades

- [x] Criar o pacote principal do projeto.
- [x] Criar a classe `FileSystemSimulator`.
- [x] Criar a classe `SimulatedFile`.
- [x] Criar a classe `SimulatedDirectory`.
- [x] Criar a classe `Journal`.
- [x] Criar a classe ou enum para tipos de operacao.
- [x] Definir como os caminhos serao interpretados, por exemplo `/docs/a.txt`.
- [x] Definir como o conteudo sera serializado dentro do `.dat`.

## Criterios de Aceite

- Cada classe tem uma responsabilidade clara.
- As operacoes publicas ficam centralizadas no `FileSystemSimulator`.
- A estrutura permite salvar e carregar o estado completo pelo `.dat`.
