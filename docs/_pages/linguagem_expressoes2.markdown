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

{% include code-link.html path="Expressoes2/src/le2/plp/expressions2/Programa.java" %} ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/Expressao.java" %}

Expressao ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/Valor.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpUnaria.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpBinaria.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpDeclaracao.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/Id.java" %}

Valor ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ValorConcreto.java" %}

ValorConcreto ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ValorInteiro.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ValorBooleano.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ValorString.java" %}

ExpUnaria ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpMenos.java" content='"-" Expressao' %} 
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpNot.java" content='"not" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpLength.java" content='"length" Expressao' %}

ExpBinaria ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpSoma.java" content='Expressao "+" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpSub.java" content='Expressao "-" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpAnd.java" content='Expressao "and" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpOr.java" content='Expressao "or" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpEquals.java" content='Expressao "==" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/expression/ExpConcat.java" content='Expressao "++" Expressao' %}

ExpDeclaracao ::= "let" {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/declaration/Declaracao.java" content='Declaracao' %} "in" Expressao  

Declaracao ::= {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/declaration/DecVariavel.java" content='DecVariavel' %}\
{% include code-indent.html %} | {% include code-link.html path="Expressoes2/src/le2/plp/expressions2/declaration/DecComposta.java" content='DecComposta' %}

DecVariavel ::= "var" Id "=" Expressao  

DecComposta ::= Declaracao "," Declaracao



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

