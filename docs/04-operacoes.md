# 04 - Operacoes do Sistema de Arquivos

## Objetivo

Implementar as operacoes obrigatorias do trabalho por meio de chamadas de metodos.

## Arquivos

### Copiar Arquivos

- [ ] Validar se o arquivo de origem existe.
- [ ] Validar se o diretorio de destino existe.
- [ ] Criar uma nova entrada de arquivo no destino.
- [ ] Registrar `COPY_FILE` no journal.
- [ ] Persistir alteracao no `.dat`.

### Apagar Arquivos

- [ ] Validar se o arquivo existe.
- [ ] Remover o arquivo da estrutura simulada.
- [ ] Registrar `DELETE_FILE` no journal.
- [ ] Persistir alteracao no `.dat`.

### Renomear Arquivos

- [ ] Validar se o arquivo existe.
- [ ] Validar se o novo nome ainda nao existe no mesmo diretorio.
- [ ] Atualizar o nome do arquivo.
- [ ] Registrar `RENAME_FILE` no journal.
- [ ] Persistir alteracao no `.dat`.

## Diretorios

### Criar Diretorios

- [ ] Validar se o diretorio pai existe.
- [ ] Validar se o nome ainda nao existe.
- [ ] Criar a entrada de diretorio.
- [ ] Registrar `CREATE_DIRECTORY` no journal.
- [ ] Persistir alteracao no `.dat`.

### Apagar Diretorios

- [ ] Validar se o diretorio existe.
- [ ] Definir se diretorios nao vazios podem ser apagados.
- [ ] Remover o diretorio da estrutura simulada.
- [ ] Registrar `DELETE_DIRECTORY` no journal.
- [ ] Persistir alteracao no `.dat`.

### Renomear Diretorios

- [ ] Validar se o diretorio existe.
- [ ] Validar se o novo nome ainda nao existe no diretorio pai.
- [ ] Atualizar o nome do diretorio.
- [ ] Registrar `RENAME_DIRECTORY` no journal.
- [ ] Persistir alteracao no `.dat`.

### Listar Diretorio

- [ ] Validar se o diretorio existe.
- [ ] Exibir subdiretorios.
- [ ] Exibir arquivos.
- [ ] Exibir resultado no console.

## Criterios de Aceite

- Todas as operacoes obrigatorias estao implementadas.
- Operacoes invalidas exibem erro claro.
- Nenhuma operacao cria arquivos reais fora do `.dat`.

