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
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/SequenciaComando.java" content='Comando ";" Comando' %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/Skip.java" content='Skip' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative2/command/ChamadaProcedimento.java" content='ChamadaProcedimento' %}

Skip ::=

Atribuicao ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/Id.java" %} ":=" {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/Expressao.java" %}

Expressao ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/Valor.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpUnaria.java" %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpBinaria.java" %} | Id

Valor ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ValorConcreto.java" %}

ValorConcreto ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ValorInteiro.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ValorBooleano.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ValorString.java" %}

ExpUnaria ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpMenos.java" content=' "-" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpNot.java" content=' "not" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpLength.java" content=' "length" Expressao ' %}

ExpBinaria ::= {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpSoma.java" content=' Expressao "+" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpSub.java" content=' Expressao "-" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpAnd.java" content=' Expressao "and" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpOr.java" content=' Expressao "or" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpEquals.java" content=' Expressao "==" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/expressions2/expression/ExpConcat.java" content=' Expressao "++" Expressao ' %}

ComandoDeclaracao ::= "{" {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/declaration/Declaracao.java" %} ";" Comando "}"

Declaracao ::= {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/declaration/DeclaracaoVariavel.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoProcedimento.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/declaration/DeclaracaoComposta.java" %}

DeclaracaoVariavel ::= "var" Id "=" Expressao

DeclaracaoComposta ::= Declaracao "," Declaracao

DeclaracaoProcedimento ::= {% include code-link.html path="Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoProcedimento.java" content='"proc" Id "(" [ ListaDeclaracaoParametro ] ")" "{" Comando "}"' %}

ListaDeclaracaoParametro ::= {% include code-link.html path="Imperativa2/src/li2/plp/imperative2/declaration/DeclaracaoParametro.java" content='Tipo Id' %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative2/declaration/ListaDeclaracaoParametro.java" content='Tipo Id "," ListaDeclaracaoParametro' %} 

Tipo ::= "string" \| "int" \| "boolean"

While ::= "while" Expressao "do" Comando

IfThenElse ::= "if" Expressao "then" Comando "else" Comando

IO ::= {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/Write.java" content='"write" "(" Expressao ")"' %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa2/src/li2/plp/imperative1/command/Read.java" content='"read" "(" Id ")"' %}

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