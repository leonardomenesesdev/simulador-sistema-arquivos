# 06 - Testes

## Objetivo

Validar as funcionalidades principais antes da entrega em PDF no AVA.

## Testes Manuais Obrigatorios

### Diretorios

- [ ] Criar `/docs`.
- [ ] Criar `/docs/trabalho`.
- [ ] Listar `/docs`.
- [ ] Renomear `/docs/trabalho` para `/docs/relatorio`.
- [ ] Apagar `/docs/relatorio`.

### Arquivos

- [ ] Criar ou carregar um arquivo simulado dentro do `.dat`.
- [ ] Copiar arquivo de um diretorio para outro.
- [ ] Renomear arquivo.
- [ ] Apagar arquivo.
- [ ] Listar diretorio apos cada operacao.

### Journaling

- [ ] Verificar se cada operacao aparece no journal.
- [ ] Verificar se operacoes concluidas ficam como `COMMITTED`.
- [ ] Simular ou documentar o comportamento com operacao `PENDING`.

### Regra do `.dat`

- [ ] Confirmar que arquivos criados no simulador nao aparecem como arquivos reais no Windows.
- [ ] Confirmar que o arquivo real principal e apenas o `.dat`.

## Registro dos Resultados

Use esta tabela no README ou em anotacoes de teste:

| Teste | Entrada | Resultado esperado | Resultado obtido | Status |
| --- | --- | --- | --- | --- |
| Criar diretorio | `mkdir /docs` | Diretorio criado |  |  |
| Listar diretorio | `ls /` | Exibe `/docs` |  |  |
| Renomear diretorio | `rename /docs documentos` | Nome alterado |  |  |
| Apagar diretorio | `rmdir /documentos` | Diretorio removido |  |  |

## Criterios de Aceite

- As operacoes obrigatorias foram testadas.
- Os erros principais foram testados.
- O requisito de nao manipular arquivos reais do Windows foi validado.

