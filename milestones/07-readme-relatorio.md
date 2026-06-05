# M07 — README-relatório

**Status:** ✅ Concluída
**User story:** Como dupla responsável pela entrega, quero o `README.md` da raiz escrito como
relatório completo em Markdown, para que ele possa ser exportado em PDF e enviado no AVA
conforme exigido.

## Descrição detalhada da feature

O enunciado exige que o relatório seja o **README da raiz**, em Markdown, na primeira página do
projeto no GitHub, e que ele seja impresso em PDF para envio no AVA. O README atual é mínimo;
esta milestone o expande para um relatório completo.

Seções a incluir (baseadas em `docs/07-readme-relatorio.md` e no enunciado):

- **Título, link do GitHub e integrantes** (dupla).
- **Resumo / Introdução / Objetivo** — contexto e meta do simulador.
- **Parte 1 — Sistema de arquivos e Journaling** — o que é um sistema de arquivos, sua
  importância em SOs, conceito de journaling e tipos (write-ahead logging, log-structured, etc.).
- **Parte 2 — Arquitetura do Simulador** — estruturas de dados (classes do modelo) e como o
  journaling é implementado (estrutura do log e operações registradas).
- **Parte 3 — Implementação em Java** — `FileSystemSimulator`, classes de arquivo/diretório e
  `Journal`, com os métodos de cada operação.
- **Parte 4 — Instalação e Funcionamento** — recursos usados, passo a passo de compilação
  (`javac -d out -sourcepath src src/Main.java`) e execução (`java -cp out Main`), incluindo os
  comandos do modo shell (M05).
- **Operações Implementadas**, **Testes e Resultados** (tabela vinda da M06), **Conclusão**.

Por que existe: é o **entregável avaliado**. Sem ele não há nota — o PDF do AVA é gerado a
partir deste arquivo.

## Bloqueadores (o que impede iniciar)

- **M05 — Modo Shell** (a seção de uso descreve os comandos do shell).
- **M06 — Testes** (fornece a tabela de resultados).
- Insumos do autor: **link do repositório no GitHub** e **nomes da dupla**.

## Milestones bloqueadas por esta

- **M08 — Entrega** (o PDF é gerado a partir deste README).

## Critérios de aceitação

- [x] O README pode ser lido como relatório completo, com todas as seções do enunciado.
- [x] Explica o que é sistema de arquivos, a importância em SOs e o conceito + tipos de journaling.
- [x] Descreve a arquitetura (classes) e como o `.dat` armazena os dados.
- [x] Traz o passo a passo de compilação/execução e os comandos do modo shell.
- [x] Contém a tabela de testes/resultados (M06).
- [x] Inclui o link do GitHub visível e os integrantes.
- [x] É renderizável/exportável em PDF sem quebrar a formatação.

## O que testar para validar

- Revisar o README contra o checklist de perguntas do `docs/08-entrega.md` (o que é FS, por que
  journaling, quais classes, quais operações, como executar, onde ficam os dados, link).
- Renderizar o Markdown (preview) e exportar para PDF, conferindo seções, tabelas e o link.
