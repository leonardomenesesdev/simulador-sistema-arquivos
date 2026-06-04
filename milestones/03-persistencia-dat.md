# M03 — Persistência em `.dat`

**Status:** ✅ Concluída
**User story:** Como usuário do simulador, quero que todo o sistema de arquivos simulado seja
salvo e recarregado a partir de um único arquivo `filesystem.dat`, para que meus dados
sobrevivam ao fechamento do programa sem criar arquivos reais no sistema operacional.

## Descrição detalhada da feature

A persistência serializa **todo o estado** (árvore + journal) num único arquivo `.dat`, usando
`ObjectOutputStream`/`ObjectInputStream`. É o que garante a regra mais importante do projeto:
nada do simulador existe como arquivo real — tudo fica dentro de `filesystem.dat`.

Peças envolvidas:

- **`FileSystemState`** (`src/com/simuladorfs/model/`) — snapshot serializável que agrupa
  `root` (diretório raiz), `journal` e `savedAt` (timestamp do salvamento).
- **`FileSystemSimulator.load()`** — se o `.dat` não existe, cria-o (primeira execução); se
  existe, desserializa o estado e, ao final, chama `journal.recover()` para exibir operações
  `PENDING` encontradas, mantendo-as no log para revisão (sem reprocessar automaticamente).
- **`FileSystemSimulator.save()`** — monta um novo `FileSystemState` e o grava no `.dat`.

Por que existe: dá **durabilidade** ao simulador e é o ponto onde o journaling se torna útil —
o estado é salvo em vários momentos do fluxo de cada operação (ver M04).

## Bloqueadores (o que impede iniciar)

- **M01 — Modelo de dados** (precisa da árvore serializável).
- **M02 — Journaling** (o journal é salvo junto do estado).

## Milestones bloqueadas por esta

- **M04 — Operações** (cada operação persiste o estado).
- **M05 — Modo Shell** (o shell carrega no início e salva ao sair).

## Critérios de aceitação

- [x] `filesystem.dat` é criado automaticamente na primeira execução.
- [x] O estado (árvore + journal + timestamp) é serializado num único arquivo `.dat`.
- [x] Ao reabrir o programa, os dados salvos anteriormente são restaurados.
- [x] Operações `PENDING` encontradas na carga são exibidas no console e mantidas no log.
- [x] O único arquivo real do projeto referente aos dados é o `filesystem.dat`.

## O que testar para validar

- Apagar o `.dat`, rodar o programa e confirmar que ele é recriado.
- Criar um diretório/arquivo, encerrar e reabrir; confirmar que o conteúdo persiste.
- Forçar/duplicar uma entrada `PENDING` no journal e verificar que a próxima inicialização a
  exibe e a mantém.
- Confirmar que, fora o `filesystem.dat`, nada novo foi criado no diretório do projeto.
