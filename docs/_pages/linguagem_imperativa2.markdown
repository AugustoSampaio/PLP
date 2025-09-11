---
layout: linguagem
permalink: /linguagens/imperativa2
linguagem: Imperativa 2
short: Imp2
order: 7
---


* Estende a Linguagem Imperativa 1 com procedimentos parametrizados e recursivos
* O corpo de um procedimento é um comando e a chamada de um procedimento idem
* O contexto também é estendido com:
    * um mapeamento de identificadores (nomes de procedimentos) em definições de procedimentos
* Procedimentos não possuem status de valor
* Um programa é um comando


## Fontes

{% include code-link.html path="Imperativa2/src/li2/plp/imperative2/Programa.java" %} ::= {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/Comando.java" %}

Comando ::= {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/Atribuicao.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/ComandoDeclaracao.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/While.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/IfThenElse.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/IO.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/SequenciaComando.java" %} Comando ";" Comando\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/Skip.java" %} Skip \
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative2/command/ChamadaProcedimento.java" %} ChamadaProcedimento

Skip ::=

Atribuicao ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/Id.java" %} ":=" {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/Expressao.java" %}

Expressao ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/Valor.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpUnaria.java" %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpBinaria.java" %} | Id

Valor ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ValorConcreto.java" %}

ValorConcreto ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ValorInteiro.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ValorBooleano.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ValorString.java" %}

ExpUnaria ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpMenos.java" %} "-" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpNot.java" %} "not" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpLength.java" %} "length" Expressao

ExpBinaria ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpSoma.java" %} Expressao "+" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpSub.java" %} Expressao "-" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpAnd.java" %} Expressao "and" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpOr.java" %} Expressao "or" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpEquals.java" %} Expressao "==" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpConcat.java" %} Expressao "++" Expressao

ComandoDeclaracao ::= "{" {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/declaration/Declaracao.java" %} ";" Comando "}"

Declaracao ::= {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/declaration/DeclaracaoVariavel.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoProcedimento.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/declaration/DeclaracaoComposta.java" %}

DeclaracaoVariavel ::= "var" Id "=" Expressao

DeclaracaoComposta ::= Declaracao "," Declaracao

DeclaracaoProcedimento ::= {% include code-link.html path="Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoProcedimento.java" %} "proc" Id "(" [ ListaDeclaracaoParametro ] ")" "{" Comando "}"

ListaDeclaracaoParametro ::= {% include code-link.html path="Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoParametro.java" %} Tipo Id\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative2/declaration/ListaDeclaracaoParametro.java" %} Tipo Id "," ListaDeclaracaoParametro

Tipo ::= "string" \| "int" \| "boolean"

While ::= "while" Expressao "do" Comando

IfThenElse ::= "if" Expressao "then" Comando "else" Comando

IO ::= {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/Write.java" %} "write" "(" Expressao ")"\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/Read.java" %} "read" "(" Id ")"

ChamadaProcedimento ::= "call" Id "(" [ {% include code-link.html path="Imperativa2/src/li2/plp/imperative2/command/ListaExpressao.java" %} ] ")"

ListaExpressao ::= Expressao \| Expressao, ListaExpressao


## Classes Auxiliares

{% include code-link.html path="Imperativa2/src/li2/plp/imperative2/memory/AmbienteExecucaoImperativa2.java" %}\
{% include code-link.html path="Imperativa2/src/li2/plp/imperative2/memory/ContextoExecucaoImperativa2.java" %}\
{% include code-link.html path="Imperativa2/src/li2/plp/imperative1/memory/ListaValor.java" %}\
{% include code-link.html path="Imperativa2/src/li2/plp/imperative2/declaration/DefProcedimento.java" %}\
{% include code-link.html path="Imperativa2/src/li2/plp/imperative2/memory/ProcedimentoJaDeclaradoException.java" %}\
{% include code-link.html path="Imperativa2/src/li2/plp/imperative2/memory/ProcedimentoNaoDeclaradoException.java" %}


## Parser

{% include code-link.html path="Imperativa2/src/li2/plp/imperative2/parser/Imperative2.jj" %}\