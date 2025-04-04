---
layout: linguagem
permalink: /linguagens/expressoes1
linguagem: Expressões 1
short: Exp1
order: 1
---

- Inclui apenas constantes (valores) e operações sobre valores
- Valores e operações sobre inteiros, booleanos e string são admitidos
- Um programa é uma expressão

## Fontes
Programa ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/Expressao.java" %}

Expressao ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/Valor.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpUnaria.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpBinaria.java" %}

Valor ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ValorConcreto.java" %}

ValorConcreto ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ValorInteiro.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ValorBooleano.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ValorString.java" %}

ExpUnaria ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpMenos.java" %} "-" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpNot.java" %} "not" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpLength.java" %} "length" Expressao

ExpBinaria ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpSoma.java" %} Expressao "+" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpSub.java" %} Expressao "-" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpAnd.java" %} Expressao "and" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpOr.java" %} Expressao "or" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpEquals.java" %} Expressao "==" Expressao\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpConcat.java" %} Expressao "++" Expressao

## Classes Auxiliares
{% include code-link.html path="Expressoes1/src/le1/plp/expressions1/util/Tipo.java" %}\
{% include code-link.html path="Expressoes1/src/le1/plp/expressions1/util/TipoPrimitivo.java" %}\
{% include code-link.html path="Expressoes1/src/le1/plp/expressions2/memory/Ambiente.java" %}\
{% include code-link.html path="Expressoes1/src/le1/plp/expressions2/memory/Contexto.java" %}

## Exemplos de Programas
{% include code-link.html path="Expressoes1/Exemplo.txt" %}

## Parser
{% include code-link.html path="Expressoes1/src/le1/plp/expressions1/parser/Expressoes1.jj" %}