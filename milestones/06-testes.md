# M06 — Testes manuais + registro

**Status:** ⏳ Pendente
**User story:** Como dupla responsável pela entrega, quero executar e registrar os testes
manuais obrigatórios, para comprovar que o simulador funciona e atender ao requisito de
validação do trabalho.

## Descrição detalhada da feature

Esta milestone não adiciona código de produção: ela **valida** o que já existe e produz
evidências para o relatório (M07). Os testes são executados manualmente (de preferência pelo
modo shell da M05) e seus resultados são anotados numa tabela esperado × obtido.

Grupos de testes (baseados em `docs/06-testes.md`):

- **Diretórios:** criar `/docs`, criar `/docs/trabalho`, listar `/docs`, renomear
  `/docs/trabalho` para `relatorio`, apagar `/docs/relatorio`.
- **Arquivos:** criar arquivo simulado, copiar entre diretórios, renomear, apagar, listar após
  cada operação.
- **Journaling:** verificar que cada operação aparece no journal; que operações concluídas
  ficam `COMMITTED`; documentar (ou simular) o comportamento de uma operação `PENDING`.
- **Regra do `.dat`:** confirmar que os arquivos simulados **não** aparecem como arquivos reais
  no sistema operacional e que o único arquivo de dados real é o `filesystem.dat`.

Saída esperada: a tabela de resultados preenchida (entrada, resultado esperado, resultado
obtido, status), pronta para entrar no README (M07).

Por que existe: o enunciado pede validação das funcionalidades antes da entrega; é o que dá
credibilidade ao relatório.

## Bloqueadores (o que impede iniciar)

- **M04 — Operações** (precisa das operações implementadas para testá-las).
- **M05 — Modo Shell** (recomendado para executar os testes de forma interativa).

## Milestones bloqueadas por esta

- **M07 — README-relatório** (usa a tabela de resultados destes testes).

## Critérios de aceitação

- [ ] Todos os cenários obrigatórios de diretórios e arquivos foram executados e registrados.
- [ ] As entradas do journal foram verificadas (presença e status `COMMITTED`).
- [ ] O comportamento de operação `PENDING` foi testado ou documentado.
- [ ] Foi confirmado que nenhum arquivo simulado aparece no sistema operacional real.
- [ ] A tabela de resultados (esperado × obtido + status) está preenchida.

## O que testar para validar

- Executar a sequência de diretórios e a de arquivos descritas acima, anotando cada resultado.
- Rodar o comando de listagem do journal e conferir os status.
- Inspecionar o diretório do projeto e confirmar que apenas o `filesystem.dat` foi criado.
- Revisar a tabela final: cada linha com entrada, esperado, obtido e status (OK/Falhou).
