# M02 — Journaling

**Status:** ✅ Concluída
**User story:** Como usuário do simulador, quero que cada operação seja registrada num log
antes de ser aplicada, para que o sistema demonstre integridade e permita identificar
operações que ficaram incompletas.

## Descrição detalhada da feature

O journaling é o diferencial pedido no enunciado. A ideia é **write-ahead logging**: registrar
a intenção da operação como `PENDING` antes de alterar os dados e, só depois de aplicada e
salva, marcá-la como `COMMITTED`. Se o programa for interrompido no meio, a entrada `PENDING`
permanece no log e pode ser exibida na próxima inicialização.

Classes envolvidas (`src/com/simuladorfs/journal/`):

- **`OperationType`** — enum com os tipos: `CREATE_FILE`, `COPY_FILE`, `DELETE_FILE`,
  `RENAME_FILE`, `CREATE_DIRECTORY`, `DELETE_DIRECTORY`, `RENAME_DIRECTORY`.
- **`OperationStatus`** — enum com `PENDING` e `COMMITTED`.
- **`Operation`** — uma entrada do journal: UUID, tipo, status, timestamp, `sourcePath`,
  `targetPath` e `payload` (dados extras, como o conteúdo de um arquivo copiado). Tem
  `formatForDisplay()` para exibição legível.
- **`Journal`** — a lista de operações: `record()` (registra como `PENDING`),
  `markCommitted()` (promove a `COMMITTED`), `recover()` (retorna as pendentes) e
  `listEntries()` (lista tudo).

Por que existe: é o mecanismo que dá **integridade** ao simulador e serve de base para explicar
o conceito de journaling no relatório (M07).

## Bloqueadores (o que impede iniciar)

Nenhum. Pode ser construída em paralelo com M01.

## Milestones bloqueadas por esta

- **M03 — Persistência em `.dat`** (o journal é salvo junto do estado).
- **M04 — Operações** (toda operação registra e confirma uma entrada).

## Critérios de aceitação

- [x] Existe um enum de tipos cobrindo as 7 operações obrigatórias.
- [x] Existe um enum de status com `PENDING` e `COMMITTED`.
- [x] Uma operação registrada nasce como `PENDING` e pode ser promovida a `COMMITTED`.
- [x] O journal consegue listar todas as entradas e retornar apenas as pendentes.
- [x] As classes do journal são `Serializable` (salvas junto do estado).

## O que testar para validar

- Registrar uma operação e verificar que entra como `PENDING`.
- Chamar `markCommitted` para o mesmo id e confirmar a transição para `COMMITTED`.
- Inserir uma pendente, chamar `recover()` e confirmar que ela aparece na lista de pendentes.
- Conferir `formatForDisplay()` produz uma linha legível (tipo, status, caminhos).
