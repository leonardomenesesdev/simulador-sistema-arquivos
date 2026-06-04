# Simulador de Sistema de Arquivos

Projeto em Java para simular operacoes basicas de um sistema de arquivos usando um arquivo `.dat` como disco virtual.

## Journaling

O simulador usa journaling para registrar operacoes antes de aplicar alteracoes na arvore de arquivos e diretorios.

Fluxo usado nas operacoes:

1. O simulador valida os parametros.
2. A operacao e registrada no journal com status `PENDING`.
3. O estado com a entrada pendente e salvo no `filesystem.dat`.
4. A alteracao e aplicada em memoria.
5. O estado alterado e salvo no `filesystem.dat`.
6. A operacao e marcada como `COMMITTED`.
7. O estado final e salvo novamente no `filesystem.dat`.

Ao iniciar, o simulador verifica se existem operacoes `PENDING`. A decisao atual e manter essas entradas no log e exibi-las no console para revisao, sem reprocessar automaticamente.

## Arquivo `.dat`

Os arquivos e diretorios simulados nao sao criados no Windows. Eles ficam serializados dentro do arquivo `filesystem.dat`.

