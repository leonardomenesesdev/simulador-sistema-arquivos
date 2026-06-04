# M01 — Modelo de dados

**Status:** ✅ Concluída
**User story:** Como desenvolvedor do simulador, quero representar arquivos e diretórios como
objetos Java serializáveis, para montar uma árvore de sistema de arquivos em memória sem tocar
no sistema de arquivos real do computador.

## Descrição detalhada da feature

O modelo é a base de todo o simulador: define as peças que descrevem a árvore de diretórios e
arquivos. Tudo vive apenas em memória (e, depois, dentro do `filesystem.dat`), nunca como
arquivo real no disco.

Classes envolvidas (`src/com/simuladorfs/model/`):

- **`SimulatedFile`** — representa um arquivo simulado. Guarda `name`, `content`, `createdAt`
  e `updatedAt`. Expõe `rename()` e a atualização de datas. É a unidade de conteúdo do sistema.
- **`SimulatedDirectory`** — representa uma pasta. Guarda o `name`, um mapa de subdiretórios e
  um mapa de arquivos, além de datas. Oferece operações de árvore: `addDirectory`,
  `removeDirectory`, `getDirectory`, `hasDirectory`, `addFile`, `removeFile`, `getFile`,
  `hasFile`, `rename`, `touch`, `isEmpty`. Possui a fábrica `root()` para o diretório raiz `/`.

Por que existe: separa **o que é** o sistema de arquivos (dados) de **como ele é operado**
(M04) e **como é salvo** (M03). Todas as classes implementam `Serializable` para que o estado
possa ser persistido de uma vez.

## Bloqueadores (o que impede iniciar)

Nenhum. É uma das milestones-base do projeto.

## Milestones bloqueadas por esta

- **M03 — Persistência em `.dat`** (precisa serializar a árvore).
- **M04 — Operações** (operam sobre `SimulatedFile`/`SimulatedDirectory`).

## Critérios de aceitação

- [x] Existe classe para arquivo simulado com nome, conteúdo e datas.
- [x] Existe classe para diretório simulado com subdiretórios, arquivos e datas.
- [x] O diretório suporta adicionar, remover, buscar e renomear arquivos e subdiretórios.
- [x] Todas as classes do modelo são `Serializable`.
- [x] Nenhum arquivo real é criado no sistema operacional ao manipular o modelo.

## O que testar para validar

- Criar um `SimulatedDirectory.root()`, adicionar subdiretórios e arquivos e confirmar que
  `getDirectory`/`getFile`/`hasFile` retornam o esperado.
- Renomear um arquivo e um diretório e verificar que o nome muda e `isEmpty` reflete o estado.
- Confirmar, ao final, que nenhum arquivo ou pasta com esses nomes aparece no explorador de
  arquivos do sistema operacional.
