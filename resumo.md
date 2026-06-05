# Resumo para Apresentacao

Este arquivo resume as funcionalidades implementadas no projeto **Simulador de Sistema de Arquivos** e o objetivo de cada uma. Ele deve ser atualizado conforme novas partes forem concluidas.

## Visao Geral

O projeto simula um sistema de arquivos em Java. A ideia principal e representar arquivos e diretorios dentro de uma estrutura propria, sem manipular diretamente o sistema de arquivos do Windows.

O armazenamento real do simulador e feito no arquivo:

```text
filesystem.dat
```

Esse arquivo guarda o estado interno do sistema simulado, incluindo diretorios, arquivos simulados e o historico de operacoes do journal.

## Termos Importantes (Glossario)

Antes de entrar nos detalhes, vale conhecer algumas palavras que aparecem o tempo todo no projeto:

- **Serializacao:** transformar objetos Java (a arvore de diretorios e o journal) em uma sequencia de bytes que pode ser gravada em arquivo. Ao abrir o programa de novo, esses bytes viram objetos outra vez. E o que permite "salvar" o sistema simulado no `filesystem.dat`.
- **Journal (diario de operacoes):** uma lista que registra cada acao que altera dados. Funciona como um caderno onde anotamos "vou fazer isto" e, depois, "feito".
- **PENDING (pendente):** estado de uma operacao que ja foi anotada no journal, mas ainda nao foi confirmada.
- **COMMITTED (confirmada):** estado de uma operacao que ja foi aplicada e salva com sucesso.
- **Arvore de diretorios:** a estrutura em forma de galhos onde cada diretorio pode conter arquivos e outros diretorios, comecando pela raiz `/`.
- **Caminho (path):** o endereco de um arquivo ou diretorio dentro da arvore, como `/docs/trabalho/arquivo.txt`.

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
- salva a entrada pendente no `filesystem.dat` antes da alteracao;
- marca uma operacao como concluida;
- lista as entradas do journal;
- identifica operacoes pendentes ao carregar o sistema.

**Importancia para o trabalho:**  
Atende ao requisito de journaling, usado para aumentar a integridade dos dados. A ideia e deixar registrado o que o simulador tentou fazer antes de confirmar a mudanca.

Uma forma simples de entender: imagine um caixa de banco que, antes de transferir dinheiro, anota no caderno "vou transferir X". Se faltar luz no meio da operacao, ao voltar ele olha o caderno e sabe exatamente o que estava em andamento. O journal faz esse papel: anota a intencao (`PENDING`) antes de mexer nos dados e so marca como concluida (`COMMITTED`) depois que a mudanca foi salva. Esse estilo de "registrar antes de agir" e chamado de write-ahead logging.

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
5. O estado com a operacao `PENDING` e salvo no `filesystem.dat`.
6. Depois que a mudanca e salva, o journal marca a operacao como `COMMITTED`.
7. Se o programa iniciar com operacoes pendentes, elas sao exibidas no console para revisao. O simulador nao refaz nem desfaz a operacao automaticamente: ele apenas mantem o registro no log para que a pessoa entenda o que ficou incompleto.
8. Assim, o projeto demonstra manipulacao de arquivos, diretorios, persistencia e journaling sem alterar diretamente o sistema de arquivos real.

## Operacoes do Sistema de Arquivos

Cada operacao segue o mesmo fluxo de seguranca:

1. validacao dos dados de entrada;
2. registro da operacao como `PENDING` no journal;
3. salvamento do estado no `filesystem.dat`;
4. execucao da mudanca na estrutura em memoria;
5. salvamento do estado atualizado;
6. marcacao da operacao como `COMMITTED` no journal;
7. salvamento final.

Esse fluxo garante que, se o programa for interrompido entre os passos, o journal preserva o registro da operacao incompleta para revisao.

### Exemplo Passo a Passo (criar o diretorio /docs)

Para deixar o fluxo concreto, veja o que acontece ao chamar `createDirectory("/docs")`:

1. **Validacao:** o simulador confere que o nome `docs` ainda nao existe na raiz e que nao se trata da propria raiz.
2. **Registro PENDING:** cria uma operacao `CREATE_DIRECTORY` com status `PENDING` e a guarda no journal.
3. **Salvamento:** grava o estado no `filesystem.dat` ja com a operacao pendente. Se o programa cair exatamente aqui, ao reabrir veremos essa operacao listada como pendente.
4. **Execucao:** adiciona de fato o diretorio `docs` dentro da raiz, em memoria.
5. **Salvamento:** grava o novo estado (agora com `/docs` existindo) no `filesystem.dat`.
6. **COMMITTED:** marca a operacao como concluida no journal.
7. **Salvamento final:** grava o estado com a operacao confirmada.

No fim, o journal guarda uma linha parecida com `[COMMITTED] CREATE_DIRECTORY ... origem=/docs ...`, e o diretorio passa a existir apenas dentro do `.dat`, nunca como pasta real no computador.

### Operacoes de Arquivo

**Criar arquivo** (`createFile`)

- valida se ja existe uma entrada com o mesmo nome no diretorio pai;
- registra `CREATE_FILE` no journal;
- adiciona o arquivo ao diretorio pai.

**Copiar arquivo** (`copyFile`)

- valida se o arquivo de origem existe;
- resolve o destino: se o caminho de destino for um diretorio existente, copia para dentro dele mantendo o nome original; se for um caminho novo, usa o ultimo segmento como novo nome;
- valida que o nome de destino nao conflita com outra entrada;
- registra `COPY_FILE` no journal com o conteudo original como dado adicional.

**Apagar arquivo** (`deleteFile`)

- valida se o arquivo existe no diretorio pai;
- registra `DELETE_FILE` no journal;
- remove o arquivo da estrutura.

**Renomear arquivo** (`renameFile`)

- valida se o arquivo existe;
- valida se o novo nome nao conflita com outro arquivo ou diretorio no mesmo local;
- registra `RENAME_FILE` no journal com o novo nome como destino;
- atualiza o nome dentro do objeto `SimulatedFile`.

### Operacoes de Diretorio

**Criar diretorio** (`createDirectory`)

- impede criar o diretorio raiz (`/`), que ja existe;
- valida se o diretorio pai existe no caminho informado;
- valida se o nome nao conflita com outra entrada;
- registra `CREATE_DIRECTORY` no journal;
- adiciona o novo `SimulatedDirectory` ao pai.

**Apagar diretorio** (`deleteDirectory`)

- impede apagar o diretorio raiz;
- valida se o diretorio existe;
- impede apagar diretorios nao vazios (a operacao exige que o diretorio esteja vazio);
- registra `DELETE_DIRECTORY` no journal;
- remove o diretorio do pai.

**Renomear diretorio** (`renameDirectory`)

- impede renomear o diretorio raiz;
- valida se o diretorio existe;
- valida se o novo nome nao conflita com outra entrada no mesmo pai;
- registra `RENAME_DIRECTORY` no journal;
- atualiza o nome dentro do objeto `SimulatedDirectory`.

**Listar diretorio** (`listDirectory`)

- valida se o diretorio existe;
- exibe cada subdiretorio com o prefixo `[DIR]`;
- exibe cada arquivo com o prefixo `[FILE]`;
- se o diretorio estiver vazio, exibe `(vazio)` no console.

### Erros de Operacao

Todas as operacoes invalidas lancam `IllegalArgumentException` com uma mensagem explicando o motivo. Nenhuma operacao invalida modifica o estado do sistema ou cria entradas no journal.

## Modo Shell (Interativo)

**Classe:** `Shell`

**Finalidade:**  
Dar ao usuario uma forma pratica de operar o simulador digitando comandos, sem precisar recompilar o programa a cada acao. E o "Modo Avancado" do enunciado: a camada de entrada/saida que fica por cima das operacoes ja existentes em `FileSystemSimulator`.

**O que ela faz:**

- carrega o estado salvo uma unica vez no inicio (`load`);
- mostra um prompt (`>`) e fica num laco lendo linhas digitadas pelo usuario;
- interpreta cada linha como um **comando** seguido de **argumentos** e chama o metodo correto do `FileSystemSimulator`;
- ao receber `exit` (ou quando a entrada termina), salva o estado e encerra.

**Comandos disponiveis:**

- `mkdir <caminho>` → cria diretorio (`createDirectory`);
- `rmdir <caminho>` → apaga diretorio vazio (`deleteDirectory`);
- `touch <caminho> [conteudo]` (ou `create`) → cria arquivo (`createFile`); o conteudo e opcional e pode ter espacos;
- `cp <origem> <destino>` → copia arquivo (`copyFile`);
- `rm <caminho>` → apaga arquivo (`deleteFile`);
- `rename <caminho> <novoNome>` (ou `mv`) → renomeia arquivo **ou** diretorio (`renameFile`/`renameDirectory`);
- `ls [caminho]` → lista um diretorio (`listDirectory`); sem argumento, lista a raiz `/`;
- `journal` → lista as entradas do journal (`listJournalEntries`);
- `help` → mostra a lista de comandos;
- `exit` (ou `quit`) → salva o estado e encerra o programa.

**Como o `rename` decide o tipo:**  
Como existe um unico comando para renomear arquivo e diretorio, o shell primeiro pergunta ao simulador se o caminho aponta para um diretorio (`isDirectory`) ou para um arquivo (`isFile`) e so entao chama o metodo correspondente. Esses dois metodos sao apenas de leitura (nao passam pelo journaling).

**Tratamento de erros:**  
Cada comando roda dentro de um `try/catch`. Se a operacao for invalida (arquivo inexistente, nome duplicado, diretorio nao vazio, argumento faltando, etc.), o shell captura a `IllegalArgumentException`, imprime a mensagem de erro e **continua** no laco — um comando errado nunca derruba o programa. Comandos desconhecidos tambem apenas exibem um aviso.

**Importancia para o trabalho:**  
Transforma a API de metodos numa experiencia de uso real. E o que permite demonstrar o simulador na apresentacao e executar os testes manuais (M06) digitando uma sequencia de comandos. Como cada operacao do `FileSystemSimulator` ja persiste o estado por conta propria (fluxo de journaling), o shell apenas garante um `save` final ao sair.

**Exemplo de sessao completa:**

```text
> mkdir /docs
Diretorio criado: /docs
> touch /docs/a.txt Ola
Arquivo criado: /docs/a.txt
> ls /docs
[FILE] a.txt
> cp /docs/a.txt /docs/b.txt
Arquivo copiado: /docs/a.txt -> /docs/b.txt
> rename /docs/b.txt c.txt
Arquivo renomeado: /docs/b.txt -> c.txt
> rm /docs/c.txt
Arquivo apagado: /docs/c.txt
> rmdir /docs
Erro: Diretorio nao esta vazio: /docs
> rm /docs/a.txt
Arquivo apagado: /docs/a.txt
> rmdir /docs
Diretorio apagado: /docs
> exit
Estado salvo em filesystem.dat. Ate logo!
```

Mesmo digitando comandos como se fosse um terminal de verdade, **nada** e criado no sistema de arquivos real: tudo continua vivendo apenas dentro do `filesystem.dat`.

## Proximas Funcionalidades a Documentar

- testes manuais;
- cenarios de falha para demonstrar operacoes pendentes no journal.
