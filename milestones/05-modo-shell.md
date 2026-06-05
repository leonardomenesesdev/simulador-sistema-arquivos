# M05 — Modo Shell interativo

**Status:** ✅ Concluída
**User story:** Como usuário do simulador, quero digitar comandos num prompt interativo
(`mkdir`, `cp`, `rm`, etc.), para operar o sistema de arquivos de forma prática, sem precisar
recompilar o programa a cada ação.

## Descrição detalhada da feature

É o **Modo Avançado** citado no enunciado e a peça que **integra** todas as operações já
existentes (M04) num programa rodável e demonstrável. Hoje o `Main.java` apenas faz `load()` e
lista a raiz; esta milestone substitui isso por um **loop de leitura de comandos**.

Escopo proposto:

- Loop em `Main.java` (ou nova classe `com.simuladorfs.Shell`) que: carrega o estado uma vez no
  início, lê linhas do `Scanner(System.in)`, faz o parsing de comando + argumentos e despacha
  para o método correspondente de `FileSystemSimulator`.
- Comandos sugeridos:
  - `mkdir <caminho>` → `createDirectory`
  - `rmdir <caminho>` → `deleteDirectory`
  - `rename <caminho> <novoNome>` → `renameFile`/`renameDirectory` (decidir por tipo ou ter
    `mv`/`rename` separados)
  - `cp <origem> <destino>` → `copyFile`
  - `rm <caminho>` → `deleteFile`
  - `ls [caminho]` → `listDirectory` (padrão `/`)
  - `touch <caminho> [conteúdo]` (ou `create`) → `createFile` — **necessário** para que `cp`
    tenha um arquivo de origem, já que o `.dat` começa vazio
  - `journal` → `listJournalEntries`
  - `help` → lista de comandos
  - `exit` → encerra o loop salvando o estado
- Tratamento de erro: capturar `IllegalArgumentException` (e argumentos faltando) e imprimir a
  mensagem **sem derrubar o loop**.
- Mensagens de sucesso claras após cada operação.

Por que existe: transforma a API de métodos numa experiência de uso real, facilita os testes
(M06) e a demonstração na apresentação. As operações já existem — esta milestone é a camada de
entrada/saída por cima delas.

## Bloqueadores (o que impede iniciar)

- **M03 — Persistência em `.dat`** (o shell carrega no início e salva ao sair).
- **M04 — Operações** (os comandos só chamam métodos já prontos).

## Milestones bloqueadas por esta

- **M06 — Testes** (cenários executados preferencialmente via shell).
- **M07 — README-relatório** (a seção de execução/uso descreve os comandos do shell).

## Critérios de aceitação

- [x] O programa entra num loop interativo após iniciar, com prompt e lista de comandos (`help`).
- [x] Cada comando mapeia corretamente para o método de `FileSystemSimulator`.
- [x] Existe comando para criar arquivo (`touch`/`create`), permitindo usar `cp` em seguida.
- [x] Comandos inválidos ou com erro exibem mensagem e **não** encerram o loop.
- [x] `exit` encerra o programa garantindo o estado salvo no `.dat`.
- [x] Nenhum comando cria arquivo real fora do `.dat`.

## O que testar para validar

- Sessão completa: `mkdir /docs` → `touch /docs/a.txt Ola` → `ls /docs` → `cp /docs/a.txt
  /docs/b.txt` → `rename /docs/b.txt c.txt` → `rm /docs/c.txt` → `rmdir /docs` → `exit`.
- Comando inválido (ex.: `rm /naoexiste`, `comando_inexistente`) e confirmar que o loop segue.
- Reabrir o programa após `exit` e confirmar, com `ls`, que o estado persistiu.
- Rodar `journal` e ver as entradas `COMMITTED` das operações realizadas.
