# Docs - Mini Atividades

Esta pasta organiza o trabalho "Simulador de Sistema de Arquivos" em mini atividades executaveis ate **5 de junho de 2026**.

O objetivo e guiar a dupla na implementacao do simulador em Java, garantindo que:

- o sistema de arquivos simulado fique armazenado em um arquivo `.dat`;
- nenhum arquivo criado pelo simulador apareca no gerenciador de arquivos do Windows;
- as operacoes basicas sejam implementadas por metodos;
- o journaling registre operacoes antes da aplicacao das mudancas;
- o README da raiz funcione como relatorio em Markdown e possa ser impresso em PDF para envio no AVA.

## Cronograma

| Data | Foco | Arquivos de apoio |
| --- | --- | --- |
| 03/06/2026 | Planejamento, arquitetura e modelo de dados | [01-planejamento.md](01-planejamento.md), [02-arquitetura.md](02-arquitetura.md) |
| 04/06/2026 | Operacoes, journaling e persistencia `.dat` | [03-journaling.md](03-journaling.md), [04-operacoes.md](04-operacoes.md), [05-persistencia-shell.md](05-persistencia-shell.md) |
| 05/06/2026 | Testes, README-relatorio, PDF e entrega | [06-testes.md](06-testes.md), [07-readme-relatorio.md](07-readme-relatorio.md), [08-entrega.md](08-entrega.md) |

## Ordem Recomendada

1. Definir o escopo minimo para entrega.
2. Implementar as classes centrais.
3. Criar o arquivo `.dat` como armazenamento unico do simulador.
4. Implementar journaling antes de cada operacao.
5. Implementar comandos/metodos de arquivos e diretorios.
6. Testar cenarios principais e falhas.
7. Escrever o README-relatorio na raiz.
8. Publicar no GitHub, gerar PDF do README e enviar no AVA.

## Checklist Geral

- [ ] `FileSystemSimulator` criado.
- [ ] Classe para arquivo simulado criada.
- [ ] Classe para diretorio simulado criada.
- [ ] Classe `Journal` criada.
- [ ] Arquivo `.dat` usado como armazenamento do sistema simulado.
- [ ] Operacao de copiar arquivo implementada.
- [ ] Operacao de apagar arquivo implementada.
- [ ] Operacao de renomear arquivo implementada.
- [ ] Operacao de criar diretorio implementada.
- [ ] Operacao de apagar diretorio implementada.
- [ ] Operacao de renomear diretorio implementada.
- [ ] Operacao de listar diretorio implementada.
- [ ] Modo shell implementado ou documentado como extra.
- [ ] Testes manuais registrados.
- [ ] README da raiz escrito como relatorio.
- [ ] Link do GitHub incluido no README.
- [ ] README impresso/exportado em PDF.
