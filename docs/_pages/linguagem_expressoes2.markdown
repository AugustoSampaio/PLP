---
layout: linguagem
permalink: /linguagens/expressoes2
linguagem: Expressões 2
short: Exp2
order: 2
---

- Estende a Linguagem de Expressões 1 com identificadores (variáveis) que possuem um valor (constante)
- Durante a compilação, surge a necessidade de um contexto:mapeamento entre identificadores e tipos
- Durante a interpretação, surge a necessidade de um contexto: mapeamento entre identificadores e valores
- Na avaliação de uma expressão, a ocorrência de um identificador é substituída pelo valor associado ao identificador
- Um programa é uma expressão

## Fontes

Programa ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/Expressao.java" %}

Expressao ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/Valor.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpUnaria.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpBinaria.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpDeclaracao.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/Id.java" %}

Valor ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ValorConcreto.java" %}

ValorConcreto ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ValorInteiro.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ValorBooleano.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ValorString.java" %}

ExpUnaria ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpMenos.java" %} "-" Expressao  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpNot.java" %} "not" Expressao  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpLength.java" %} "length" Expressao

ExpBinaria ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpSoma.java" %} Expressao "+" Expressao  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpSub.java" %} Expressao "-" Expressao  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpAnd.java" %} Expressao "and" Expressao  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpOr.java" %} Expressao "or" Expressao  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpEquals.java" %} Expressao "==" Expressao  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpConcat.java" %} Expressao "++" Expressao


## Classes Auxiliares

{% include code-link.html path="Expressoes2/src/le2/plp/expressions1/util/Tipo.java" %}  
{% include code-link.html path="Expressoes2/src/le2/plp/expressions1/util/TipoPrimitivo.java" %}

{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/memory/Ambiente.java" %}  
{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/memory/AmbienteCompilacao.java" %}  
{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/memory/AmbienteExecucao.java" %}

{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/memory/Contexto.java" %}  
{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/memory/ContextoCompilacao.java" %}  
{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/memory/ContextoExecucao.java" %}

{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/memory/VariavelJaDeclaradaException.java" %}  
{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/memory/VariavelNaoDeclaradaException.java" %}  
{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/memory/IdentificadorJaDeclaradoException.java" %}  
{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/memory/IdentificadorNaoDeclaradoException.java" %}

## Exemplos de Programas

{% include code-link.html path="Expressoes2/Exemplo.txt" %}

## Parser

{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/parser/Expressoes2.jj" %}

