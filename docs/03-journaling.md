# 03 - Journaling

## Objetivo

Implementar um log de operacoes para preservar a integridade do sistema simulado.

## Regra Principal

Toda operacao que altera o sistema deve ser registrada no journal antes de modificar a arvore de arquivos e diretorios.

Fluxo recomendado:

1. Validar os parametros da operacao.
2. Registrar a operacao no journal com status `PENDING`.
3. Executar a mudanca na estrutura em memoria.
4. Persistir o novo estado no `.dat`.
5. Marcar a operacao como `COMMITTED`.

## Operacoes Registradas

- `COPY_FILE`
- `DELETE_FILE`
- `RENAME_FILE`
- `CREATE_DIRECTORY`
- `DELETE_DIRECTORY`
- `RENAME_DIRECTORY`

## Mini Atividades

- [x] Definir o formato de uma entrada de journal.
- [x] Registrar operacoes com identificador unico.
- [x] Salvar entradas pendentes antes da alteracao.
- [x] Marcar entradas concluidas depois da persistencia.
- [x] Implementar recuperacao ao iniciar o programa.
- [x] Documentar no README como o journaling funciona.

## Recuperacao

Ao iniciar o simulador, o programa deve verificar se ha operacoes pendentes.

Comportamento simples recomendado:

- se a operacao estiver `COMMITTED`, considera-la concluida;
- se a operacao estiver `PENDING`, informar no console e ignorar ou reprocessar conforme a implementacao escolhida;
- registrar essa decisao no README.

## Criterios de Aceite

- O journal registra todas as operacoes que alteram dados.
- O README explica o objetivo do journaling.
- O simulador consegue iniciar mesmo com entradas pendentes no journal.
