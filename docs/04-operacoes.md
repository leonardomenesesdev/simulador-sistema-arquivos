# 04 - Operacoes do Sistema de Arquivos

## Objetivo

Implementar as operacoes obrigatorias do trabalho por meio de chamadas de metodos.

## Arquivos

### Copiar Arquivos

- [x] Validar se o arquivo de origem existe.
- [x] Validar se o diretorio de destino existe.
- [x] Criar uma nova entrada de arquivo no destino.
- [x] Registrar `COPY_FILE` no journal.
- [x] Persistir alteracao no `.dat`.

### Apagar Arquivos

- [x] Validar se o arquivo existe.
- [x] Remover o arquivo da estrutura simulada.
- [x] Registrar `DELETE_FILE` no journal.
- [x] Persistir alteracao no `.dat`.

### Renomear Arquivos

- [x] Validar se o arquivo existe.
- [x] Validar se o novo nome ainda nao existe no mesmo diretorio.
- [x] Atualizar o nome do arquivo.
- [x] Registrar `RENAME_FILE` no journal.
- [x] Persistir alteracao no `.dat`.

## Diretorios

### Criar Diretorios

- [x] Validar se o diretorio pai existe.
- [x] Validar se o nome ainda nao existe.
- [x] Criar a entrada de diretorio.
- [x] Registrar `CREATE_DIRECTORY` no journal.
- [x] Persistir alteracao no `.dat`.

### Apagar Diretorios

- [x] Validar se o diretorio existe.
- [x] Definir se diretorios nao vazios podem ser apagados.
- [x] Remover o diretorio da estrutura simulada.
- [x] Registrar `DELETE_DIRECTORY` no journal.
- [x] Persistir alteracao no `.dat`.

### Renomear Diretorios

- [x] Validar se o diretorio existe.
- [x] Validar se o novo nome ainda nao existe no diretorio pai.
- [x] Atualizar o nome do diretorio.
- [x] Registrar `RENAME_DIRECTORY` no journal.
- [x] Persistir alteracao no `.dat`.

### Listar Diretorio

- [x] Validar se o diretorio existe.
- [x] Exibir subdiretorios.
- [x] Exibir arquivos.
- [x] Exibir resultado no console.

## Criterios de Aceite

- Todas as operacoes obrigatorias estao implementadas.
- Operacoes invalidas exibem erro claro.
- Nenhuma operacao cria arquivos reais fora do `.dat`.

