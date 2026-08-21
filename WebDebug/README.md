# WebDebug

Suporte de depuração para a WebAPI/WebUI, **isolado das linguagens**.

## Por que este módulo existe

O commit `f288186` adicionou suporte a debugger editando ~30 classes de ensino
nas 9 linguagens (`Programa`, `Contexto`, `ExpDeclaracao`, `ComandoDeclaracao`,
`DecClasseSimples`, ...). Como o professor ensina a partir dessas classes, elas
precisam permanecer exatamente como estavam. Este módulo concentra todo o
suporte de depuração para que os 9 projetos de linguagem fiquem
**byte-idênticos** ao estado anterior a `f288186`:

```
git diff --stat f288186~1 -- Expressoes1 Expressoes2 Funcional1 Funcional2 \
    Funcional3 Imperativa1 Imperativa2 Objetos1 Objetos2
```

não deve imprimir nada. Se imprimir, alguma linguagem foi alterada
indevidamente.

## Como funciona

1. **Gramáticas próprias** (`src/plp/debug/<lang>/parser/*Debug.jj`) — cópias das
   gramáticas das linguagens cujas ações constroem subclasses `*Debug` em vez
   das classes originais, passando um `InfoEscopo` com a faixa exata de
   código-fonte (`TrechoCodigoFonte`) capturada dos tokens no ponto sintático
   do escopo. É isso que torna escopo e faixa **exatos**, e não inferidos.

2. **Subclasses `*Debug`** (`src/plp/debug/<lang>/*.java`) — estendem as classes
   de AST da linguagem e sobrescrevem `checaTipo`/`getTipo` reproduzindo o corpo
   original, inserindo apenas o registro do escopo após o `incrementa()`.

3. **Ambientes `*Debug`** — implementam a interface `Ambiente*` da linguagem,
   delegam tudo ao ambiente real e observam
   `incrementa()`/`restaura()`/`map()`. Implementam `ScopeAware`, por onde as
   subclasses publicam o escopo.

4. **`core/SnapshotRecorder`** — publica um frame **somente** quando um escopo é
   registrado. Níveis criados por `incrementa()` que não registram escopo
   (unificação de aplicação de função, chamada de procedimento/método) ficam
   invisíveis, o que mantém a saída limpa. Revisitas do mesmo escopo — comuns na
   inferência de tipos, que retipa funções recursivas a cada chamada — colapsam
   por identidade exata (`InfoEscopo.chave()` = rótulo + nome + faixa).

A dependência é unidirecional: `WebDebug` → linguagens. As linguagens não
conhecem `WebDebug`. Inverter isso criaria um ciclo no Maven.

## Manutenção: risco de divergência

As gramáticas aqui são **cópias**. Se a sintaxe de uma linguagem mudar, a cópia
correspondente precisa ser atualizada à mão, senão o debugger divergirá
silenciosamente. Cada `.jj` tem um cabeçalho apontando o arquivo que espelha.
As únicas diferenças intencionais em relação ao original são:

- `package` e nome da classe do parser (sufixo `Debug`);
- imports de `plp.debug.*` no lugar de `InfoEscopo`/`TrechoCodigoFonte`;
- capturas de `Token` para compor as faixas;
- chamadas de construtor apontando para as subclasses `*Debug`.

Tokens, produções e `LOOKAHEAD`s são idênticos ao original.

## Escopos publicados por linguagem

| Linguagem | Escopos |
|---|---|
| Expressoes1 | nenhum (a linguagem não tem declarações) — a cópia de gramática existe só por uniformidade |
| Expressoes2 | `let-in` |
| Funcional1/2/3 | `let-in`, `function` (com os parâmetros formais) |
| Imperativa1 | `block` |
| Imperativa2 | `block`, `procedure` |
| Objetos1 | `block`, `class`, `procedure` |
| Objetos2 | `block`, `class`, `procedure`, `constructor` |

## Limitações conhecidas (comportamento das linguagens, não deste módulo)

- **Expressoes1** não possui `let`/variáveis, então não há escopo algum a
  mostrar — `compilationEnv` é `null`.
- **`Aplicacao` com `let` parentetizado** (ex.: `Funcional2/input`,
  `Funcional3/input`): `Aplicacao.getFuncType` só desce em `func` quando ele é
  `Id` ou `ValorFuncao`. Um `let` entre parênteses não é nenhum dos dois, então
  a própria linguagem **não verifica tipos** daquele `let` — ele é fabricado
  como `TipoFuncao(params, TipoPolimorfico)`. Sem verificação de tipos não há
  escopo a registrar. A execução (`avaliar`) desce normalmente, por isso o
  programa roda e produz o resultado correto.
- **Tipos de parâmetros formais** aparecem como `?` no instante do `map()`
  (ainda são `TipoPolimorfico` não inferidos) e são reregistrados com o tipo
  concreto após a verificação do corpo, via `ScopeAware.registraBinding`.
