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
{% include code-link.html path="Expressoes1/src/le1/plp/expressions1/Programa.java" %} ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/Expressao.java" %}

Expressao ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/Valor.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpUnaria.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpBinaria.java" %}

Valor ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ValorConcreto.java" %}

ValorConcreto ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ValorInteiro.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ValorBooleano.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ValorString.java" %}

ExpUnaria ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpMenos.java" content='"-" Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpNot.java" content='"not" Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpLength.java" content='"length" Expressao' %} 

ExpBinaria ::= {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpSoma.java" content='Expressao "+" Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpSub.java" content='Expressao "-" Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpAnd.java" content='Expressao "and" Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpOr.java" content='Expressao "or" Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpEquals.java" content='Expressao "==" Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Expressoes1/src/le1/plp/expressions1/expression/ExpConcat.java" content='Expressao "++" Expressao' %} 

## Classes Auxiliares
{% include code-link.html path="Expressoes1/src/le1/plp/expressions1/util/Tipo.java" %}\
{% include code-link.html path="Expressoes1/src/le1/plp/expressions1/util/TipoPrimitivo.java" %}\
{% include code-link.html path="Expressoes1/src/le1/plp/expressions2/memory/Ambiente.java" %}\
{% include code-link.html path="Expressoes1/src/le1/plp/expressions2/memory/Contexto.java" %}

## Exemplos de Programas
{% include code-link.html path="Expressoes1/Exemplo.txt" %}

## Parser
{% include code-link.html path="Expressoes1/src/le1/plp/expressions1/parser/Expressoes1.jj" %}