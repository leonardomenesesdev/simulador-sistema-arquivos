# Milestones — Simulador de Sistema de Arquivos

Esta pasta reorganiza **todo** o projeto no formato de **milestones / user stories**, com
descrição detalhada de cada feature, dependências (bloqueadores e milestones bloqueadas),
critérios de aceitação e o que testar para validar.

> Esta pasta **não substitui** `docs/`. Os arquivos em `docs/01..08` continuam valendo como
> mini-atividades e checklists de apoio. Aqui o foco é a visão de planejamento por milestones
> e o **grafo de dependências** entre as features.

## Legenda de status

- ✅ **Concluída** — feature já implementada no código atual.
- ⏳ **Pendente** — feature ainda não implementada/finalizada.

## Mapa de milestones

| ID | Milestone | Status | Bloqueada por | Bloqueia |
| --- | --- | --- | --- | --- |
| [M01](01-modelo-de-dados.md) | Modelo de dados | ✅ Concluída | — | M03, M04 |
| [M02](02-journaling.md) | Journaling | ✅ Concluída | — | M03, M04 |
| [M03](03-persistencia-dat.md) | Persistência em `.dat` | ✅ Concluída | M01, M02 | M04, M05 |
| [M04](04-operacoes.md) | Operações de arquivo e diretório | ✅ Concluída | M01, M02, M03 | M05, M06 |
| [M05](05-modo-shell.md) | Modo Shell interativo | ✅ Concluída | M03, M04 | M06, M07 |
| [M06](06-testes.md) | Testes manuais + registro | ⏳ Pendente | M04, M05 | M07 |
| [M07](07-readme-relatorio.md) | README-relatório | ✅ Concluída | M05, M06 | M08 |
| [M08](08-entrega.md) | Entrega (GitHub + PDF + AVA) | ⏳ Pendente | M07 | — |

## Grafo de dependências

```
M01 ─┐
M02 ─┼─> M03 ──> M04 ──> M05 ──> M06 ──> M07 ──> M08
     └────────────^   ^
```

- **M01** e **M02** não têm bloqueadores e podem ser construídas em paralelo.
- **M03** depende de M01 e M02 (precisa do modelo e do journal para serializar o estado).
- **M04** depende de M01, M02 e M03 (opera sobre a árvore e persiste cada mudança).
- **M05..M08** formam a fila final: shell → testes → relatório → entrega.

## Ordem de execução das pendentes

1. **M05 — Modo Shell**: integra todas as operações num programa rodável/demonstrável.
2. **M06 — Testes**: executa e registra os cenários obrigatórios (de preferência via shell).
3. **M07 — README-relatório**: consolida o relatório com os resultados dos testes.
4. **M08 — Entrega**: publica no GitHub, gera o PDF e envia no AVA.

## Como usar cada arquivo

Cada milestone segue o mesmo gabarito:

- **Status** e **User story** (Como… quero… para…).
- **Descrição detalhada da feature** — o que é, o que faz, classes envolvidas e o "porquê".
- **Bloqueadores (o que impede iniciar)** — o que precisa estar pronto antes.
- **Milestones bloqueadas por esta** — quem depende dela.
- **Critérios de aceitação** — condições objetivas de "pronto".
- **O que testar para validar** — cenários que comprovam os critérios.
