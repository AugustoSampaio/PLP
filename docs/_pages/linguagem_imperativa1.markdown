---
layout: linguagem
permalink: /linguagens/imperativa1
linguagem: Imperativa 1
short: Imp1
order: 6
---


* Estende a Linguagem de Expressões 1 com identificadores (variáveis) e comandos de atribuição, entrada/saída e controle de fluxo
* O contexto inclui três componentes:
    * um mapeamento (dinâmico) de identificadores em valores
    * uma lista de valores de entrada
    * uma lista de valores de saída
* Um programa é um comando

## Fontes

{% include code-link.html path="Imperativa1/src/li1/plp/imperative1/Programa.java" %} ::= {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/command/Comando.java" %}

Comando ::= {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/command/Atribuicao.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/command/ComandoDeclaracao.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/command/While.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/command/IfThenElse.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/command/IO.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/command/SequenciaComando.java" content='Comando ";" Comando' %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/command/Skip.java" %}

Skip ::=

Atribuicao ::= {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/Id.java" %} ":=" {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/Expressao.java" %}

Expressao ::= {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/Valor.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ExpUnaria.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ExpBinaria.java" %}\
{% include code-indent.html %} | Id

Valor ::= {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ValorConcreto.java" %}

ValorConcreto ::= {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ValorInteiro.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ValorBooleano.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ValorString.java" %}

ExpUnaria ::= {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ExpMenos.java" content=' "-" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ExpNot.java" content=' "not" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ExpLength.java" content=' "length" Expressao ' %}

ExpBinaria ::= {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ExpSoma.java" content=' Expressao "+" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ExpSub.java" content=' Expressao "-" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ExpAnd.java" content=' Expressao "and" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ExpOr.java" content=' Expressao "or" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ExpEquals.java" content=' Expressao "==" Expressao ' %} \
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/expressions2/expression/ExpConcat.java" content=' Expressao "++" Expressao ' %}

ComandoDeclaracao ::= "{" {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/declaration/Declaracao.java" %} ";" Comando "}"

Declaracao ::= {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/declaration/DeclaracaoVariavel.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/declaration/DeclaracaoComposta.java" %}

DeclaracaoVariavel ::= "var" Id "=" Expressao

DeclaracaoComposta ::= Declaracao "," Declaracao

While ::= "while" Expressao "do" Comando

IfThenElse ::= "if" Expressao "then" Comando "else" Comando

IO ::= {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/command/Write.java" content='"write" "(" Expressao ")"' %}\
{% include code-indent.html %} | {% include code-link.html path="Imperativa1/src/li1/plp/imperative1/command/Read.java" content='"read" "(" Id ")"'%}

## Classes Auxiliares

{% include code-link.html path="Imperativa1/src/li1/plp/imperative1/memory/AmbienteCompilacaoImperativa.java" %}\
{% include code-link.html path="Imperativa1/src/li1/plp/imperative1/memory/AmbienteExecucaoImperativa.java" %}\
{% include code-link.html path="Imperativa1/src/li1/plp/imperative1/memory/ContextoCompilacaoImperativa.java" %}\
{% include code-link.html path="Imperativa1/src/li1/plp/imperative1/memory/ContextoExecucaoImperativa.java" %}\
{% include code-link.html path="Imperativa1/src/li1/plp/imperative1/memory/EntradaVaziaException.java" %}\
{% include code-link.html path="Imperativa1/src/li1/plp/imperative1/memory/ListaValor.java" %}\
{% include code-link.html path="Imperativa1/src/li1/plp/imperative1/util/Lista.java" %}

## Parser

{% include code-link.html path="Imperativa1/src/li1/plp/imperative1/parser/Imperative1.jj" %}