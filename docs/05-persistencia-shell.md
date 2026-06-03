# 05 - Persistencia e Modo Shell

## Objetivo

Garantir que o sistema simulado funcione usando um arquivo `.dat` e, se houver tempo, rode em modo shell.

## Persistencia no `.dat`

O arquivo `.dat` deve armazenar:

- arvore de diretorios;
- arquivos simulados;
- conteudo dos arquivos simulados;
- entradas de journal.

## Mini Atividades de Persistencia

- [ ] Criar o arquivo `filesystem.dat` na primeira execucao.
- [ ] Carregar o estado do simulador ao iniciar.
- [ ] Salvar o estado apos cada operacao concluida.
- [ ] Manter arquivos simulados apenas como dados internos do `.dat`.
- [ ] Validar que nenhum arquivo simulado aparece no Windows Explorer.

## Modo Shell

O modo shell e um adicional recomendado pela descricao do trabalho.

Comandos sugeridos:

- `mkdir /caminho`
- `rmdir /caminho`
- `rename /caminho novoNome`
- `cp /origem /destino`
- `rm /caminho`
- `ls /caminho`
- `exit`

## Mini Atividades do Shell

- [ ] Criar loop de leitura de comandos.
- [ ] Interpretar comandos e parametros.
- [ ] Chamar os metodos do `FileSystemSimulator`.
- [ ] Exibir mensagens de sucesso ou erro.
- [ ] Encerrar com `exit`.

## Criterios de Aceite

- O simulador consegue salvar e carregar dados pelo `.dat`.
- O modo shell chama os mesmos metodos da API principal.
- O README informa se o shell foi implementado como modo avancado.

