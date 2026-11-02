# ImpSQL — Extensão da Linguagem Imperativa 1 para Persistência e Consulta de Dados

## Universidade Federal de Pernambuco

**Centro de Informática**  
**Disciplina:** IN1007-2026.2 - **Paradigmas de Linguagens de Programação**

## Equipe

- **Aline Franciele Correia da Silva** - <afcs@cin.ufpe.br>
- **Pedro Mesquita Brasil** - <pmb2@cin.ufpe.br>

Este repositório organiza o projeto da disciplina de Paradigmas de Linguagens de Programação com o código-fonte do fork organizado em `PLP_project/`.

## Introdução

O projeto consiste na extensão da linguagem Imperativa 1 com comandos inspirados em SQL para persistência e consulta de dados estruturados. A extensão permite definir as tabelas usadas por um programa, inserir registros e realizar consultas sobre os dados armazenados. Os dados são persistidos em um banco de dados SQLite, acessado por uma camada de persistência própria, e continuam existindo após o término do programa.

## Objetivos

### Objetivo Geral

Estender a linguagem Imperativa 1 com comandos para definição de tabelas, inserção e consulta de dados persistentes, incluindo seleção e filtragem de registros.

### Objetivos Específicos

- Definir, no início do programa, as tabelas utilizadas, criando-as caso ainda não existam.
- Verificar, antes da execução, se as tabelas definidas são compatíveis com as já existentes no banco.
- Inserir registros nas tabelas.
- Consultar dados armazenados nas tabelas.
- Selecionar colunas específicas nas consultas.
- Filtrar registros utilizando condições baseadas nas expressões da linguagem.
- Persistir os dados em um banco de dados SQLite.

## Estrutura do Repositório

- `PLP_project/`: código-fonte do fork com os módulos e a documentação técnica original.
- `README.md`: visão geral do projeto, proposta, BNF e especificação semântica.
- `Documents/`: espaço reservado para documentação e apresentações.

## Resumo do Projeto

A extensão adiciona à linguagem Imperativa 1 a definição de tabelas e dois comandos:

- `create-use` — definição de uma tabela no início do programa: cria a tabela se ela não existir, ou usa a existente se o esquema for compatível.
- `insert into` — inserção de um registro na tabela.
- `select [... where Expressao]` — consulta de dados, com filtragem opcional.

## Exemplos de Uso

A seguir são apresentados exemplos simples das novas construções da linguagem.

### Definir tabelas

Todas as tabelas usadas pelo programa são definidas no início, com suas colunas e respectivos tipos. Cada `create-use` termina com `;`, e depois da lista vem sempre o comando do programa:

```text
create-use clientes (nome string, idade int, ativo boolean);
create-use pedidos (produto string, cliente string);
insert into clientes values ("Alice", 25, true)
```

Se a tabela não existir no banco, ela é criada. Se já existir com o mesmo esquema, ela é usada como está. Assim, o mesmo programa pode ser executado várias vezes.

### Inserir um registro

O comando `insert into` permite inserir valores, literais ou expressões, em uma tabela:

```text
create-use clientes (nome string, idade int, ativo boolean);
{ var n = "Bob", var i = 30;
  insert into clientes values ("Alice", 25, true);
  insert into clientes values (n, i + 1, false)
}
```

### Consultar todos os dados

O comando `select` permite consultar todos os registros e colunas de uma tabela:

```text
create-use clientes (nome string, idade int, ativo boolean);
select * from clientes
```

### Consultar colunas específicas

Também é possível selecionar apenas algumas colunas:

```text
create-use clientes (nome string, idade int, ativo boolean);
select nome from clientes
```

### Filtrar registros

O comando `select` pode utilizar uma condição para filtrar os registros:

```text
create-use clientes (nome string, idade int, ativo boolean);
select nome from clientes where idade == 25
```

## BNF

<pre>
<a href="PLP_project/Imperativa1/src/li1/plp/imperative1/Programa.java">Programa</a> ::= [<a href="PLP_project/Imperativa1/src/li1/plp/imperative1/declaration/ListaCreateUse.java">ListaCreateUse</a> ";"] <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/Comando.java">Comando</a>                   [ALTERADO]

Comando ::= <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/Atribuicao.java">Atribuicao</a>
          | <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/ComandoDeclaracao.java">ComandoDeclaracao</a>
          | <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/While.java">While</a>
          | <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/IfThenElse.java">IfThenElse</a>
          | <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/IO.java">IO</a>
          | <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/SequenciaComando.java">Comando ";" Comando</a>
          | <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/Skip.java">Skip</a>
          | <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/Insert.java">Insert</a>                                          [NOVO]
          | <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/Select.java">Select</a>                                          [NOVO]

Skip ::=

Atribuicao ::= <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/Id.java">Id</a> ":=" <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/Expressao.java">Expressao</a>

Expressao ::= <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/Valor.java">Valor</a>
            | <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ExpUnaria.java">ExpUnaria</a>
            | <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ExpBinaria.java">ExpBinaria</a>
            | Id

Valor ::= <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ValorConcreto.java">ValorConcreto</a>

ValorConcreto ::= <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ValorInteiro.java">ValorInteiro</a>
                | <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ValorBooleano.java">ValorBooleano</a>
                | <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ValorString.java">ValorString</a>

ExpUnaria ::= <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ExpMenos.java">"-" Expressao</a>
            | <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ExpNot.java">"not" Expressao</a>
            | <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ExpLength.java">"length" Expressao</a>

ExpBinaria ::= <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ExpSoma.java">Expressao "+" Expressao</a>
             | <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ExpSub.java">Expressao "-" Expressao</a>
             | <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ExpAnd.java">Expressao "and" Expressao</a>
             | <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ExpOr.java">Expressao "or" Expressao</a>
             | <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ExpEquals.java">Expressao "==" Expressao</a>
             | <a href="PLP_project/Imperativa1/src/li1/plp/expressions2/expression/ExpConcat.java">Expressao "++" Expressao</a>

ComandoDeclaracao ::= "{" <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/declaration/Declaracao.java">Declaracao</a> ";" Comando "}"

Declaracao ::= <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/declaration/DeclaracaoVariavel.java">DeclaracaoVariavel</a>
             | <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/declaration/DeclaracaoComposta.java">DeclaracaoComposta</a>

DeclaracaoVariavel ::= "var" Id "=" Expressao

DeclaracaoComposta ::= Declaracao "," Declaracao

While ::= "while" Expressao "do" Comando

IfThenElse ::= "if" Expressao "then" Comando
               "else" Comando

IO ::= <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/Write.java">"write" "(" Expressao ")"</a>
     | <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/Read.java">"read" "(" Id ")"</a>

;-------------------------------------------------------
; Definição de tabelas (somente no início do programa)
;-------------------------------------------------------

ListaCreateUse ::= <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/declaration/CreateUse.java">CreateUse</a>                                 [NOVO]
                 | CreateUse ";" ListaCreateUse

CreateUse ::= "create-use" Id "(" ListaColunas ")"           [NOVO]

;-------------------------------------------------------
; Comandos de manipulação de dados
;-------------------------------------------------------

Insert ::= "insert" "into" Id                                [NOVO]
           "values" "(" <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/command/ListaExpressao.java">ListaExpressao</a> ")"

Select ::= "select" "*" "from" Id ["where" Expressao]        [NOVO]
         | "select" ListaId "from" Id ["where" Expressao]

;-------------------------------------------------------
; Definições auxiliares
;-------------------------------------------------------

ListaColunas ::= <a href="PLP_project/Imperativa1/src/li1/plp/imperative1/util/Coluna.java">Coluna</a>
               | Coluna "," ListaColunas

Coluna ::= Id <a href="PLP_project/Imperativa1/src/li1/plp/expressions1/util/Tipo.java">Tipo</a>

Tipo ::= "int"
       | "boolean"
       | "string"

ListaExpressao ::= Expressao
                 | Expressao "," ListaExpressao

ListaId ::= Id
          | Id "," ListaId
</pre>


Na BNF, `[ ... ]` indica um elemento opcional.

### Classes Auxiliares

- [AmbienteCompilacaoImperativa](PLP_project/Imperativa1/src/li1/plp/imperative1/memory/AmbienteCompilacaoImperativa.java) [ALTERADO]
- [AmbienteExecucaoImperativa](PLP_project/Imperativa1/src/li1/plp/imperative1/memory/AmbienteExecucaoImperativa.java)
- [ContextoCompilacaoImperativa](PLP_project/Imperativa1/src/li1/plp/imperative1/memory/ContextoCompilacaoImperativa.java) [ALTERADO]
- [ContextoExecucaoImperativa](PLP_project/Imperativa1/src/li1/plp/imperative1/memory/ContextoExecucaoImperativa.java)
- [EntradaVaziaException](PLP_project/Imperativa1/src/li1/plp/imperative1/memory/EntradaVaziaException.java)
- [ListaValor](PLP_project/Imperativa1/src/li1/plp/imperative1/memory/ListaValor.java)
- [Lista](PLP_project/Imperativa1/src/li1/plp/imperative1/util/Lista.java): também representa `ListaColunas` e `ListaId`
- [SqliteManager](PLP_project/Imperativa1/src/li1/plp/imperative1/util/SqliteManager.java) [NOVO]
- [SqliteHelper](PLP_project/Imperativa1/src/li1/plp/imperative1/util/SqliteHelper.java) [NOVO]

### Parser

- [Imperative1](PLP_project/Imperativa1/src/li1/plp/imperative1/parser/Imperative1.jj) [ALTERADO]


## Decisões de Projeto

### Definição das tabelas no início do programa

As tabelas têm escopo global: elas existem no banco independentemente dos blocos do programa e sobrevivem ao seu término. Para que isso fique claro na própria sintaxe, as tabelas só podem ser definidas no início do programa, por meio de uma lista de `create-use`, antes do comando do programa.

A lista de `create-use` faz parte da regra `Programa`, e não de `Comando`: `Programa ::= [ListaCreateUse ";"] Comando`. Como as produções `While`, `IfThenElse` e `ComandoDeclaracao` usam `Comando`, um `create-use` não pode aparecer dentro de blocos ou laços. Assim, a definição de uma tabela nunca aparenta ter escopo local.

Na implementação, a classe `Programa` guarda a lista de `create-use` (classe `ListaCreateUse`) e o comando, espelhando a regra `Programa ::= [ListaCreateUse ";"] Comando`. Quando o programa não tem `create-use`, a lista é vazia. A `ListaCreateUse` segue o formato da `ListaDeclaracaoOO` da linguagem Orientada a Objetos 2.

O `create-use` é uma definição de tabela. Na verificação de tipos, ele registra o esquema da tabela no mapa de tabelas. Na execução, ele cria a tabela no banco, caso ela ainda não exista. Como seu efeito é persistente e global, ele só pode aparecer no início do programa.

Além disso, o programa precisa definir todas as tabelas que utiliza, inclusive as que já existem no banco. Com isso, as tabelas usadas são conhecidas desde o início, sem a necessidade de percorrer o restante do programa.

Cada `create-use` termina com `;`, inclusive o último, que o separa do comando do programa:

```text
create-use clientes (nome string, idade int);
create-use pedidos (produto string, cliente string);
insert into clientes values ("Alice", 25)
```

Depois da lista vem sempre um comando: um programa que apenas define tabelas não é aceito.

### Semântica do `create-use`

O `create-use` significa "criar a tabela se ela não existir, ou usar a existente". Por isso, o mesmo programa pode ser executado várias vezes sem erro, e tabelas criadas por outros programas podem ser usadas, desde que sejam definidas no início do programa.

Para evitar que o programa acredite em um esquema diferente do real, o esquema definido é comparado com o esquema da tabela no banco, quando ela já existe. Se forem diferentes, há erro de tipo, antes da execução.

### Representação das tabelas no ambiente de compilação

O esquema de cada tabela definida (nome, colunas e tipos) é registrado em um **mapa de tabelas**, atributo do contexto de compilação, mantido fora da pilha de escopos das variáveis. É esse mapa que permite ao `insert` e ao `select` verificar tipos estaticamente.

- **Espaço de nomes separado:** tabelas e variáveis não compartilham nomes. `var clientes = 1` e `create-use clientes (...)` podem coexistir: depois de `into` e `from`, um nome é sempre uma tabela; em expressões, é sempre uma variável. Dentro do `where`, as colunas escondem variáveis de mesmo nome. É o mesmo comportamento da PL/SQL, do Oracle, em que uma coluna tem precedência sobre uma variável local de mesmo nome dentro de um comando SQL.

- **Escopo global:** como o mapa não faz parte da pilha, ele não é afetado pela entrada e saída de blocos.

O mapa é usado apenas na verificação de tipos. Durante a execução, os comandos acessam diretamente o banco.

### Mapeamento de tipos para o SQLite

| ImpSQL    | SQLite    | Valor armazenado |
|-----------|-----------|------------------|
| `int`     | `INTEGER` | inteiro          |
| `string`  | `TEXT`    | texto            |
| `boolean` | `BOOLEAN` | `0` ou `1`       |

O SQLite não possui uma classe de armazenamento específica para valores booleanos. Entretanto, permite declarar uma coluna como `BOOLEAN`, mantendo esse tipo declarado no esquema da tabela. Os valores booleanos são armazenados como inteiros, com `false` representado por `0` e `true` por `1`. Isso permite recuperar o tipo `boolean` ao ler o esquema de uma tabela existente, necessário para compará-lo com o esquema definido no `create-use`.

A conversão entre `ValorBooleano` e `0`/`1` é realizada na camada de persistência, enquanto a linguagem continua tratando esses valores como booleanos.

### Avaliação do `where`

A condição do `where` é avaliada pelo interpretador da Imp1, e não traduzida para SQL. Os registros são lidos do banco e, para cada um, a expressão é avaliada em um ambiente em que as colunas estão associadas aos valores do registro. Assim, o filtro segue a semântica da ImpSQL.

### Ausência de valores nulos

A linguagem não possui `null`: todo registro tem um valor para cada coluna.

## Semântica Estática (`checaTipo`)

Os erros de esquema são detectados na verificação de tipos, antes da execução.

### Programa com `create-use`

Um programa com `create-use` tem duas partes: a lista de `create-use` e o comando `C` que vem em seguida. O comando pode ser qualquer comando da linguagem, inclusive blocos com declarações de variáveis, mas não pode conter `create-use`.

```text
create-use T1 (...); ...; create-use Tk (...);   ← lista de create-use
C                                                ← comando
```

A verificação de tipos é feita em duas etapas:

1. Cada `create-use` é verificado, na ordem em que aparece. Cada um que for bem tipado registra sua tabela no mapa de tabelas.
2. O comando `C` é verificado usando o mapa já preenchido. Assim, os `insert` e `select` só podem usar tabelas definidas na lista.

O programa é bem tipado se todos os `create-use` e o comando forem bem tipados.

### `create-use T (c1 t1, ..., cn tn)`

É bem tipado se:

1. Se a tabela `T` já foi definida anteriormente na mesma lista, o esquema `(c1 t1, ..., cn tn)` é igual ao da definição anterior.
2. Os nomes `c1, ..., cn` são distintos entre si.
3. Os tipos `t1, ..., tn` são válidos, o que já é garantido pela gramática.
4. Se `T` já existe no banco, seu esquema é igual a `(c1 t1, ..., cn tn)`: mesmas colunas, na mesma ordem, com os mesmos tipos.

Efeito: registra `T ↦ (c1 t1, ..., cn tn)` no mapa de tabelas.

### `insert into T values (e1, ..., em)`

É bem tipado se:

1. A tabela `T` existe no mapa de tabelas, ou seja, foi definida por um `create-use` no início do programa.
2. O número de expressões é igual ao número de colunas de `T` (se `T` tem `n` colunas, `m = n`).
3. Cada `ei` é bem tipada, e seu tipo é igual ao da i-ésima coluna (correspondência posicional).

### `select L from T [where e]`

É bem tipado se:

1. A tabela `T` existe no mapa de tabelas, ou seja, foi definida por um `create-use` no início do programa.
2. Se `L` é uma lista de identificadores, cada um é uma coluna de `T`. Se `L` é `*`, todas as colunas são selecionadas.
3. Se houver `where e`: em um escopo temporário em que cada coluna de `T` é mapeada para seu tipo, `e` é bem tipada e tem tipo `boolean`. Nesse escopo, uma coluna esconde uma variável de mesmo nome (sombreamento). O escopo é descartado ao fim da verificação.

## Semântica Dinâmica (`executar`)

### Programa com `create-use`

1. Cada `create-use` é executado, na ordem em que aparece.
2. Em seguida, o comando `C` é executado.

### `create-use`

Cria a tabela no SQLite com as colunas e os tipos definidos, conforme o mapeamento de tipos, caso ela ainda não exista. Se já existir, nada é feito, e a tabela existente é usada. A compatibilidade do esquema já foi garantida pela verificação de tipos.

### `insert into`

Avalia as expressões `e1, ..., em` no ambiente de execução atual e grava um novo registro na tabela, convertendo cada valor para o tipo correspondente do SQLite. As expressões são avaliadas no momento da inserção, e apenas os valores resultantes são armazenados.

### `select`

1. Lê os registros da tabela, convertendo cada valor de volta para `ValorInteiro`, `ValorString` ou `ValorBooleano`, de acordo com o tipo da coluna.
2. Se houver `where e`, avalia `e` para cada registro, em um escopo temporário em que cada coluna está associada ao seu valor, e mantém os registros em que o resultado é `true`.
3. Escreve na saída cada valor selecionado, como um valor da linguagem, da mesma forma que o comando `write`: na ordem dos registros e, dentro de cada registro, na ordem das colunas pedidas. Se nenhum registro for selecionado, nada é escrito.

   Exemplo: executando uma vez o programa da seção de exemplos de inserção, com a tabela inicialmente vazia, o comando `select * from clientes` produz a saída `"Alice" 25 true "Bob" 31 false`, e `select nome from clientes where idade == 25` produz `"Alice"`.

O `select` não produz um valor utilizável pelo programa: o resultado vai apenas para a saída, pois ainda não há um tipo capaz de representar uma coleção de registros.

### Erros de execução

Falhas do banco (arquivo inacessível, erro de SQL) interrompem a execução com uma exceção. O banco fica no arquivo `banco_impSQL.db`, no diretório em que o interpretador é executado.

## Como Executar

O interpretador pode ser executado pela interface gráfica (`AppUI`) ou pelo terminal. Em ambos os casos, o banco é o arquivo `banco_impSQL.db`, criado no diretório em que o interpretador é executado.

### Pela interface gráfica

Dentro de `PLP_project/`:

```bash
mvn -q -pl AppUI -am compile
```

```bash
java -cp "AppUI/target/classes;Expressoes1/target/classes;Expressoes2/target/classes;Funcional1/target/classes;Funcional2/target/classes;Funcional3/target/classes;Imperativa1/target/classes;Imperativa2/target/classes;Objetos1/target/classes;Objetos2/target/classes;%USERPROFILE%/.m2/repository/org/xerial/sqlite-jdbc/3.45.1.0/sqlite-jdbc-3.45.1.0.jar;%USERPROFILE%/.m2/repository/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar" gui.applet.SwingInterpretadorPLP
```

O comando acima é para o Prompt de Comando (cmd) do Windows. No PowerShell, troque `%USERPROFILE%` por `$env:USERPROFILE`. Na janela, selecione a linguagem Imperativa 1. As mensagens da camada de persistência (`[ImpSQL] ...`) aparecem no terminal; na janela aparece o resultado do programa.

O `package` da `AppUI` não é usado porque ele também gera a versão web da interface, com TeaVM, que não suporta JDBC.

### Pelo terminal

Para executar o programa do arquivo `input`, dentro de `PLP_project/Imperativa1/`:

```bash
mvn clean generate-sources compile exec:java
```

Para executar outro arquivo, como os exemplos da pasta `exemplos/`, compile uma vez e passe o arquivo em `-Dfile`:

```bash
mvn -q clean generate-sources compile
```

```bash
mvn -q exec:java "-Dfile=exemplos/exemplo1.txt"
```

No terminal, a saída aparece sem o prefixo `resultado =`, e os erros de tipo aparecem como `Erro de tipo`.

## Exemplos de Execução

Os exemplos abaixo foram executados em sequência na `AppUI`, começando com o banco vazio. A saída é a mostrada na janela.

### 1. Criar a tabela e inserir registros

```text
create-use clientes (nome string, idade int, ativo boolean);
{ var n = "Bob", var i = 30;
  insert into clientes values ("Alice", 25, true);
  insert into clientes values (n, i + 1, false)
}
```

Saída: `resultado =` (vazia). No terminal: `Tabela 'clientes' criada no SQLite.` e um aviso para cada registro inserido. A expressão `i + 1` é avaliada antes da inserção, e o valor gravado é `31`.

### 2. Consultar em outra execução (persistência)

```text
create-use clientes (nome string, idade int, ativo boolean);
select * from clientes
```

Saída: `resultado = "Alice" 25 true "Bob" 31 false`. Os registros inseridos pelo programa anterior continuam no banco.

### 3. Selecionar colunas e filtrar

```text
create-use clientes (nome string, idade int, ativo boolean);
select nome, idade from clientes where ativo
```

Saída: `resultado = "Alice" 25`

### 4. Coluna e variável com o mesmo nome

```text
create-use clientes (nome string, idade int, ativo boolean);
{ var idade = 99;
  select nome from clientes where idade == 25;
  write(idade)
}
```

Saída: `resultado = "Alice" 99`. No `where`, `idade` é a coluna; no `write`, é a variável.

### 5. Tabela e variável com o mesmo nome

```text
create-use clientes (nome string, idade int, ativo boolean);
{ var clientes = 10;
  insert into clientes values ("Carla", clientes, true);
  select nome from clientes where idade == clientes
}
```

Saída: `resultado = "Carla"`. Depois de `into` e `from`, `clientes` é a tabela; nas expressões, é a variável.

### 6. Erros de tipo

Todos os programas abaixo são rejeitados antes da execução, com `erro de tipos!`, e o banco não é alterado:

| Programa | Motivo |
|---|---|
| `create-use clientes (nome string, idade string, ativo boolean); select * from clientes` | o esquema é diferente do que está no banco |
| `create-use clientes (nome string, idade int, ativo boolean); insert into clientes values ("Ana", "vinte", true)` | `"vinte"` não é `int` |
| `create-use clientes (nome string, idade int, ativo boolean); insert into clientes values ("Ana", 20)` | faltam valores |
| `create-use clientes (nome string, idade int, ativo boolean); select email from clientes` | `email` não é coluna de `clientes` |
| `create-use clientes (nome string, idade int, ativo boolean); select nome from clientes where idade` | a condição não é `boolean` |
| `insert into produtos values ("caneta")` | `produtos` não foi definida por um `create-use` |

Os programas desta seção estão em exemplos/erro1.txt a erro7.txt.

### 7. Erro de sintaxe

```text
{ var x = 1;
  create-use t (a int);
  write(x)
}
```

O parser rejeita o programa: um `create-use` só pode aparecer no início. 

## Limitações e Extensões Futuras

- `update`: atualização de registros existentes.
- `delete`: remoção de registros.
- `drop table`: remoção de tabelas.
- Iteração sobre o resultado de uma consulta (`for each ... in T where e do Comando`): o corpo é executado uma vez para cada registro, com as colunas visíveis como variáveis. Isso permite operar sobre os registros com os comandos da linguagem sem introduzir novos tipos, reaproveitando o escopo temporário usado na avaliação do `where`.
- Valores nulos.
- Execução na IDE web: a IDE web compila o interpretador para JavaScript com TeaVM, que não suporta JDBC. Executar a ImpSQL no navegador exigiria uma implementação das tabelas em memória, sem persistência entre execuções.

## Status da Implementação

As seções de especificação acima descrevem a linguagem ImpSQL como definida pela equipe. A implementação está em andamento:

- [x] Criação de tabela no SQLite (`CREATE TABLE IF NOT EXISTS`)
- [x] Gramática e parser do `create-use` no início do programa (`Programa ::= [ListaCreateUse ";"] Comando`)
- [x] Mapa de tabelas no ambiente de compilação
- [x] Verificação de tipos do `create-use`, incluindo a comparação com o esquema do banco
- [x] `insert into`
- [x] `select`
- [x] Tratamento de erros do banco com exceções