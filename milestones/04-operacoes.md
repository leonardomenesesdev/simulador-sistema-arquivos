# M04 — Operações de arquivo e diretório

**Status:** ✅ Concluída
**User story:** Como usuário do simulador, quero criar, copiar, apagar, renomear e listar
arquivos e diretórios, com cada mudança passando pelo journaling, para manipular o sistema de
arquivos simulado de forma segura e rastreável.

## Descrição detalhada da feature

É o conjunto de operações obrigatórias do trabalho, todas concentradas em
`FileSystemSimulator` (`src/com/simuladorfs/FileSystemSimulator.java`). Cada operação que altera
dados segue o **mesmo fluxo de journaling**:

```
validar → registrar PENDING → salvar → executar na árvore → salvar → marcar COMMITTED → salvar
```

Operações de arquivo:

- **`createFile(path, content)`** — cria um arquivo simulado; valida nome livre no diretório pai.
- **`copyFile(source, target)`** — copia um arquivo existente; resolve o destino (dentro de um
  diretório existente mantendo o nome, ou caminho novo com novo nome); guarda o conteúdo no
  `payload` da operação.
- **`deleteFile(path)`** — remove um arquivo existente.
- **`renameFile(path, newName)`** — renomeia, validando que o novo nome não conflita.

Operações de diretório:

- **`createDirectory(path)`** — cria diretório; impede recriar a raiz e valida o pai.
- **`deleteDirectory(path)`** — remove diretório; impede apagar a raiz e exige que esteja vazio.
- **`renameDirectory(path, newName)`** — renomeia, com as mesmas validações de conflito.
- **`listDirectory(path)`** — lista subdiretórios (`[DIR]`) e arquivos (`[FILE]`); mostra
  `(vazio)` quando não há entradas. É a única operação de leitura (não passa por journaling).

Apoio: normalização de caminhos (aceita `\` e `/`, remove `//` e barra final), `validateName`,
e helpers `resolveParentAndName`/`findDirectory`/`findFile`. Operações inválidas lançam
`IllegalArgumentException` **sem** alterar o estado nem criar entrada no journal.

Por que existe: é o coração funcional do simulador — o que o usuário efetivamente faz.

## Bloqueadores (o que impede iniciar)

- **M01 — Modelo de dados** (opera sobre a árvore).
- **M02 — Journaling** (registra cada operação).
- **M03 — Persistência em `.dat`** (persiste o estado em cada etapa do fluxo).

## Milestones bloqueadas por esta

- **M05 — Modo Shell** (os comandos chamam estes métodos).
- **M06 — Testes** (os cenários exercitam estas operações).

## Critérios de aceitação

- [x] As 7 operações obrigatórias (copiar, apagar, renomear arquivo; criar, apagar, renomear
  diretório) estão implementadas, além de criar arquivo e listar diretório.
- [x] Toda operação que altera dados passa pelo fluxo de journaling (PENDING → COMMITTED).
- [x] Operações inválidas exibem erro claro e não modificam o estado nem o journal.
- [x] Diretórios não vazios não podem ser apagados; a raiz não pode ser criada/apagada/renomeada.
- [x] Nenhuma operação cria arquivos reais fora do `.dat`.

## O que testar para validar

- Caminho feliz de cada operação: criar `/docs`, criar arquivo, copiar, renomear, apagar,
  listar antes/depois.
- Casos de erro: copiar de origem inexistente, criar com nome duplicado, apagar diretório não
  vazio, renomear/criar/apagar a raiz — confirmar a exceção e que o estado não mudou.
- Após cada operação concluída, confirmar a entrada `COMMITTED` correspondente no journal.
