# Resumo para Apresentacao

Este arquivo resume as funcionalidades implementadas no projeto **Simulador de Sistema de Arquivos** e o objetivo de cada uma. Ele deve ser atualizado conforme novas partes forem concluidas.

## Visao Geral

O projeto simula um sistema de arquivos em Java. A ideia principal e representar arquivos e diretorios dentro de uma estrutura propria, sem manipular diretamente o sistema de arquivos do Windows.

O armazenamento real do simulador e feito no arquivo:

```text
filesystem.dat
```

Esse arquivo guarda o estado interno do sistema simulado, incluindo diretorios, arquivos simulados e o historico de operacoes do journal.

## Funcionalidades Implementadas

### Estrutura Principal do Simulador

**Classe:** `FileSystemSimulator`

**Finalidade:**  
Centralizar todas as operacoes do sistema de arquivos simulado.

**O que ela faz:**

- carrega o estado salvo no `filesystem.dat`;
- salva o estado atual no `filesystem.dat`;
- cria diretorios;
- apaga diretorios;
- renomeia diretorios;
- cria arquivos simulados;
- copia arquivos simulados;
- apaga arquivos simulados;
- renomeia arquivos simulados;
- lista o conteudo de diretorios.

**Importancia para o trabalho:**  
Essa classe representa o nucleo do simulador. Todas as operacoes obrigatorias passam por ela, evitando que o programa manipule arquivos reais do Windows.

### Arquivo Simulado

**Classe:** `SimulatedFile`

**Finalidade:**  
Representar um arquivo dentro do sistema simulado.

**O que ela guarda:**

- nome do arquivo;
- conteudo do arquivo;
- data de criacao;
- data da ultima atualizacao.

**Importancia para o trabalho:**  
Permite criar arquivos que existem apenas dentro do simulador. Eles nao aparecem como arquivos reais no gerenciador de arquivos do Windows.

### Diretorio Simulado

**Classe:** `SimulatedDirectory`

**Finalidade:**  
Representar uma pasta dentro do sistema simulado.

**O que ela guarda:**

- nome do diretorio;
- subdiretorios;
- arquivos simulados;
- data de criacao;
- data da ultima atualizacao.

**Importancia para o trabalho:**  
Permite montar uma arvore de diretorios parecida com a de um sistema operacional, usando caminhos como `/docs/trabalho`.

### Estado Persistente do Sistema

**Classe:** `FileSystemState`

**Finalidade:**  
Agrupar tudo que precisa ser salvo no `filesystem.dat`.

**O que ela guarda:**

- diretorio raiz;
- journal;
- data em que o estado foi salvo.

**Importancia para o trabalho:**  
Garante que o simulador consiga fechar e abrir novamente mantendo os dados criados anteriormente.

### Journal

**Classe:** `Journal`

**Finalidade:**  
Registrar as operacoes antes de confirmar as alteracoes no sistema.

**O que ele faz:**

- registra uma operacao como pendente;
- marca uma operacao como concluida;
- lista as entradas do journal;
- identifica operacoes pendentes ao carregar o sistema.

**Importancia para o trabalho:**  
Atende ao requisito de journaling, usado para aumentar a integridade dos dados. A ideia e deixar registrado o que o simulador tentou fazer antes de confirmar a mudanca.

### Operacao do Journal

**Classe:** `Operation`

**Finalidade:**  
Representar uma entrada individual no journal.

**O que ela guarda:**

- identificador unico;
- tipo da operacao;
- status da operacao;
- data e hora;
- caminho de origem;
- caminho de destino;
- dados adicionais da operacao.

**Importancia para o trabalho:**  
Permite explicar exatamente qual acao foi registrada no journal e se ela foi concluida.

### Tipos e Status de Operacao

**Classes:** `OperationType` e `OperationStatus`

**Finalidade:**  
Padronizar os tipos e estados das operacoes registradas no journal.

**Tipos atuais:**

- `CREATE_FILE`
- `COPY_FILE`
- `DELETE_FILE`
- `RENAME_FILE`
- `CREATE_DIRECTORY`
- `DELETE_DIRECTORY`
- `RENAME_DIRECTORY`

**Status atuais:**

- `PENDING`
- `COMMITTED`

**Importancia para o trabalho:**  
Ajuda a organizar o log e facilita a explicacao do funcionamento do journaling durante a apresentacao.

## Caminhos no Simulador

Os caminhos sao interpretados no formato semelhante ao Linux:

```text
/docs
/docs/trabalho
/docs/trabalho/arquivo.txt
```

Mesmo usando esse formato, os arquivos e diretorios nao sao criados de verdade no computador. Eles sao apenas registros dentro do `filesystem.dat`.

## Regra Mais Importante

O simulador nao deve criar arquivos reais para representar os arquivos simulados.

Exemplo:

- criar `/docs/a.txt` no simulador nao deve gerar um arquivo `a.txt` no Windows;
- essa informacao deve existir apenas dentro de `filesystem.dat`.

## Como Explicar na Apresentacao

Uma forma simples de apresentar:

1. O simulador tem uma arvore interna de diretorios e arquivos.
2. Essa arvore fica salva em `filesystem.dat`.
3. As operacoes passam pela classe `FileSystemSimulator`.
4. Antes de alterar os dados, o simulador registra a operacao no `Journal`.
5. Depois que a mudanca e salva, o journal marca a operacao como `COMMITTED`.
6. Assim, o projeto demonstra manipulacao de arquivos, diretorios, persistencia e journaling sem alterar diretamente o sistema de arquivos real.

## Proximas Funcionalidades a Documentar

- modo shell;
- comandos de entrada pelo usuario;
- exemplos completos de execucao;
- testes manuais;
- recuperacao de operacoes pendentes no journal.

