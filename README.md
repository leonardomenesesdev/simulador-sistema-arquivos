# Simulador de Sistema de Arquivos

## Identificação

**Projeto no GitHub:** <https://github.com/leonardomenesesdev/simulador-sistema-arquivos>

**Integrantes:**

- Leonardo Meneses Cavalcante
- Kaio Damasceno Mendes
## Resumo

Este trabalho apresenta o desenvolvimento de um simulador de sistema de arquivos em Java.
O programa representa arquivos e diretórios em uma estrutura hierárquica, permite executar
operações comuns de um sistema operacional e mantém um journal das alterações. Todo o estado
simulado é persistido no arquivo `filesystem.dat`, sem criar no Windows os arquivos e
diretórios manipulados dentro do simulador.

## Introdução

O gerenciamento eficiente de arquivos é essencial para o funcionamento de um sistema
operacional. Um sistema de arquivos define como os dados são nomeados, organizados,
armazenados, localizados e protegidos em um dispositivo.

Compreender sua organização ajuda a entender responsabilidades importantes de um sistema
operacional, como manter uma hierarquia de diretórios, controlar alterações e preservar a
consistência dos dados após falhas. Este simulador apresenta esses conceitos de maneira
simplificada e observável por meio de um shell interativo.

## Objetivo

Desenvolver um simulador de sistema de arquivos em Java com funcionalidades básicas de
manipulação de arquivos e diretórios e suporte a journaling. O programa permite criar, copiar,
apagar, renomear e listar entradas simuladas, registrando as operações antes de alterar o
estado persistido.

## Metodologia

O simulador foi desenvolvido em Java com classes responsáveis pelo modelo de dados, pelas
operações, pelo journal, pela persistência e pela interface de linha de comando. Os comandos
digitados no shell são convertidos em chamadas de métodos da classe `FileSystemSimulator`.

Antes de uma alteração, o simulador valida seus parâmetros e registra uma operação com status
`PENDING`. Em seguida, salva o estado, aplica a alteração, salva novamente, marca a operação
como `COMMITTED` e realiza uma última persistência. As mensagens necessárias são exibidas no
terminal.

## Parte 1: Introdução ao Sistema de Arquivos com Journaling

### Sistema de arquivos

Um sistema de arquivos é o mecanismo utilizado para organizar e acessar dados armazenados. Ele
normalmente oferece arquivos, diretórios, caminhos, metadados e regras para operações como
criação, leitura, cópia, renomeação e remoção.

Sua importância está em fornecer uma visão organizada e confiável do armazenamento. Sem esse
mecanismo, cada aplicação precisaria conhecer diretamente a posição física de seus dados e
seria muito mais difícil manter consistência, segurança e compartilhamento.

### Journaling

Journaling é uma técnica usada para registrar alterações planejadas em um log antes de
considerá-las concluídas. Se uma falha ocorrer durante uma operação, o journal oferece
informações para identificar alterações incompletas e apoiar a recuperação da consistência.

Algumas abordagens relacionadas são:

- **Write-ahead logging (WAL):** a intenção da alteração é gravada no log antes da alteração
  principal. Depois de concluída, a operação é marcada como confirmada.
- **Metadata journaling:** registra principalmente mudanças estruturais e metadados, reduzindo
  o volume do log, mas sem necessariamente proteger o conteúdo dos arquivos.
- **Full data journaling:** registra metadados e conteúdo, oferecendo maior proteção com maior
  custo de armazenamento e escrita.
- **Ordered journaling:** garante que os dados sejam gravados antes dos metadados que passam a
  referenciá-los.
- **Log-structured file system:** utiliza o log como estrutura principal de armazenamento,
  escrevendo mudanças sequencialmente em vez de apenas manter um journal auxiliar.

O projeto adota uma forma simplificada de **write-ahead logging**: registra a operação
`PENDING` antes da mudança e a marca como `COMMITTED` após a persistência. Ao iniciar, entradas
pendentes são detectadas, mantidas no log e exibidas para revisão. A versão atual não executa
replay nem rollback automático.

## Parte 2: Arquitetura do Simulador

### Estrutura de dados

O estado é uma árvore cuja raiz representa `/`. Cada diretório mantém mapas ordenados de seus
subdiretórios e arquivos, permitindo localizar entradas pelo nome e preservar a ordem de
inserção na listagem.

| Classe | Responsabilidade |
| --- | --- |
| `SimulatedFile` | Representa nome, conteúdo e datas de criação e atualização de um arquivo. |
| `SimulatedDirectory` | Representa um diretório e mantém seus arquivos e subdiretórios. |
| `FileSystemState` | Agrupa a raiz, o journal e a data da última gravação. |
| `FileSystemSimulator` | Valida caminhos, executa operações e controla a persistência. |
| `Shell` | Lê comandos, chama o simulador e apresenta resultados. |
| `Journal` | Armazena, confirma, lista e localiza operações pendentes. |
| `Operation` | Representa uma entrada do journal. |

### Persistência no arquivo `.dat`

O `FileSystemState` e todos os objetos alcançados por ele implementam `Serializable`. A classe
`FileSystemSimulator` usa `ObjectOutputStream` para serializar o estado completo no arquivo
`filesystem.dat` e `ObjectInputStream` para carregá-lo.

Assim, `/docs/nota.txt` é apenas uma entrada dentro da árvore serializada. Ele não se torna um
arquivo real chamado `nota.txt` no sistema operacional hospedeiro. O arquivo real criado pelo
simulador é o `filesystem.dat`, que funciona como disco virtual.

### Estrutura e fluxo do journal

Cada `Operation` contém:

- identificador UUID;
- tipo da operação;
- status `PENDING` ou `COMMITTED`;
- data e hora;
- caminho de origem;
- caminho ou nome de destino, quando aplicável;
- conteúdo auxiliar, quando aplicável.

Fluxo utilizado nas operações que alteram o estado:

1. Validar parâmetros e regras da operação.
2. Criar e registrar a operação como `PENDING`.
3. Persistir o estado com a entrada pendente.
4. Aplicar a alteração na árvore em memória.
5. Persistir a árvore alterada.
6. Marcar a entrada como `COMMITTED`.
7. Persistir o estado final.

As operações registradas são `CREATE_FILE`, `COPY_FILE`, `DELETE_FILE`, `RENAME_FILE`,
`CREATE_DIRECTORY`, `DELETE_DIRECTORY` e `RENAME_DIRECTORY`. A listagem não altera o estado e,
por isso, não gera uma entrada no journal.

## Parte 3: Implementação em Java

### Classe `FileSystemSimulator`

Esta é a classe central do projeto. Seus principais métodos públicos são:

| Método | Função |
| --- | --- |
| `createFile(path, content)` | Cria um arquivo simulado com conteúdo opcional. |
| `copyFile(sourcePath, targetPath)` | Copia um arquivo para outro caminho ou diretório. |
| `deleteFile(path)` | Apaga um arquivo. |
| `renameFile(path, newName)` | Renomeia um arquivo. |
| `createDirectory(path)` | Cria um diretório. |
| `deleteDirectory(path)` | Apaga um diretório vazio. |
| `renameDirectory(path, newName)` | Renomeia um diretório. |
| `listDirectory(path)` | Lista arquivos e subdiretórios. |
| `listJournalEntries()` | Lista as operações registradas. |
| `load()` / `save()` | Carrega e salva o estado em `filesystem.dat`. |

Os caminhos aceitam `/` ou `\`, são normalizados para começar com `/` e não podem conter
componentes vazios. A raiz não pode ser apagada ou renomeada, nomes duplicados são rejeitados e
diretórios só podem ser apagados quando estão vazios.

### Classes de arquivos e diretórios

`SimulatedFile` armazena nome, conteúdo, data de criação e data de atualização.
`SimulatedDirectory` armazena os mesmos metadados básicos e dois `LinkedHashMap`: um para
arquivos e outro para diretórios filhos.

### Classe `Journal`

`Journal` mantém uma lista de objetos `Operation`. Ele registra novas entradas, procura uma
operação pelo identificador para marcá-la como confirmada, retorna entradas pendentes durante a
recuperação e disponibiliza uma visualização somente leitura do histórico.

## Parte 4: Instalação e Funcionamento

### Requisitos

- JDK 17 ou superior instalado;
- terminal PowerShell, Prompt de Comando ou terminal integrado de uma IDE;
- Git, apenas para clonar o projeto.

### Obter o projeto

```bash
git clone https://github.com/leonardomenesesdev/simulador-sistema-arquivos.git
cd simulador-sistema-arquivos
```

### Compilar no PowerShell

Na raiz do projeto:

```powershell
$sources = Get-ChildItem -Path src -Recurse -Filter *.java |
    ForEach-Object { $_.FullName }
javac -d out $sources
```

### Executar

```powershell
java -cp out Main
```

Na primeira execução, o programa cria `filesystem.dat`. Nas execuções seguintes, o estado
anterior é carregado automaticamente. Digite `exit` para salvar e encerrar.

### Comandos do shell

| Comando | Descrição |
| --- | --- |
| `mkdir <caminho>` | Cria um diretório. |
| `rmdir <caminho>` | Apaga um diretório vazio. |
| `touch <caminho> [texto]` | Cria um arquivo com conteúdo opcional. |
| `create <caminho> [texto]` | Alias de `touch`. |
| `cp <origem> <destino>` | Copia um arquivo. |
| `rm <caminho>` | Apaga um arquivo. |
| `rename <caminho> <novoNome>` | Renomeia arquivo ou diretório. |
| `mv <caminho> <novoNome>` | Alias de `rename`. |
| `ls [caminho]` | Lista um diretório; sem caminho, lista `/`. |
| `journal` | Lista as entradas do journal. |
| `help` | Exibe a ajuda. |
| `exit` ou `quit` | Salva o estado e encerra. |

Exemplo de uso:

```text
> mkdir /docs
Diretorio criado: /docs
> touch /docs/nota.txt conteudo de teste
Arquivo criado: /docs/nota.txt
> cp /docs/nota.txt /copia.txt
Arquivo copiado: /docs/nota.txt -> /copia.txt
> ls /
[DIR] docs
[FILE] copia.txt
> journal
[COMMITTED] CREATE_DIRECTORY ...
[COMMITTED] CREATE_FILE ...
[COMMITTED] COPY_FILE ...
```

## Operações Implementadas

- criação, cópia, exclusão e renomeação de arquivos;
- criação, exclusão e renomeação de diretórios;
- listagem do conteúdo de diretórios;
- criação de arquivo com conteúdo textual;
- persistência da árvore e do journal em `filesystem.dat`;
- detecção e exibição de operações pendentes na inicialização;
- shell interativo com mensagens de sucesso e validação.

## Testes e Resultados

Os testes manuais abaixo foram executados em **5 de junho de 2026**, usando OpenJDK 17.0.18 no
Windows e o modo shell do projeto.

| Teste | Entrada principal | Resultado esperado | Resultado obtido | Status |
| --- | --- | --- | --- | --- |
| Criar diretórios | `mkdir /docs` e `mkdir /docs/trabalho` | Estrutura criada | Comandos confirmaram a criação | OK |
| Listar diretório | `ls /docs` | Exibir `trabalho` | Exibiu `[DIR] trabalho` | OK |
| Renomear diretório | `rename /docs/trabalho relatorio` | Alterar o nome | Renomeou para `relatorio` | OK |
| Apagar diretório vazio | `rmdir /docs/relatorio` | Remover o diretório | Comando confirmou a remoção | OK |
| Criar arquivo | `touch /docs/nota.txt conteudo de teste` | Criar entrada simulada | Exibiu `[FILE] nota.txt` em `/docs` | OK |
| Copiar arquivo | `cp /docs/nota.txt /backup` | Criar cópia em `/backup` | Exibiu `[FILE] nota.txt` em `/backup` | OK |
| Renomear arquivo | `rename /backup/nota.txt copia.txt` | Alterar o nome | Exibiu `[FILE] copia.txt` | OK |
| Apagar arquivo | `rm /backup/copia.txt` | Remover a cópia | `/backup` passou a exibir `(vazio)` | OK |
| Verificar journal | `journal` | Operações concluídas como `COMMITTED` | Nove alterações listadas como `COMMITTED` | OK |
| Verificar persistência | Encerrar, executar novamente e usar `ls` | Recuperar a árvore e o journal | `/docs`, `/backup`, `nota.txt` e journal foram carregados | OK |
| Verificar disco virtual | Inspecionar a raiz do projeto | Não criar arquivos simulados no Windows | Apenas `filesystem.dat` foi criado pelo simulador | OK |
| Documentar recuperação | Carregar estado com entrada `PENDING` | Detectar alteração incompleta | O código lista e mantém pendências para revisão, sem replay automático | Documentado |

Os testes demonstraram que as operações obrigatórias e a persistência funcionam no cenário
avaliado. O journal registra as alterações realizadas e permite observar o ciclo de confirmação.

## Resultados Esperados

Espera-se que o simulador forneça uma visão prática da organização hierárquica de arquivos e
diretórios, da persistência de estado e do uso de journaling para acompanhar alterações. A
implementação permite relacionar operações comuns de um shell com métodos Java e estruturas de
dados internas.

## Limitações

- A recuperação detecta operações `PENDING`, mas não realiza replay ou rollback automático.
- O simulador não implementa permissões, usuários, cotas ou blocos físicos de armazenamento.
- O conteúdo dos arquivos é textual e só pode ser definido durante sua criação.
- Diretórios não vazios precisam ter seu conteúdo removido antes de serem apagados.

## Conclusão

O projeto atingiu o objetivo de simular funcionalidades básicas de um sistema de arquivos em
Java. A separação entre modelo, serviço, journal, persistência e shell facilita a compreensão
do funcionamento do programa. A serialização em `filesystem.dat` garante que os dados
simulados permaneçam separados dos arquivos reais do Windows, enquanto o journal registra e
confirma cada alteração persistente.
