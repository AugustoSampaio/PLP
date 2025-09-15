---
layout: linguagem
permalink: /linguagens/funcional2
linguagem: Funcional 2
short: Func2
order: 4
---

- Estende a Linguagem Funcional 1 com funções de alta ordem
- Uma função passa a ser um valor
- O contexto inclui um único componente:
    - mapeamento de identificadores em valores
- Portanto, o resultado da avaliação de uma expressão pode ser uma função, uma função pode ser argumento de outra função, ...
- Um programa é uma expressão


## Fontes

{% include code-link.html path="Funcional2/src/lf2/plp/functional2/Programa.java" %} ::= {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/Expressao.java" %}

Expressao ::= {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/Valor.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ExpUnaria.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/functional2/expression/ExpDeclaracao.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/Id.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/functional2/expression/Aplicacao.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/functional1/expression/IfThenElse.java" %}

Valor ::= {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ValorConcreto.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/functional2/expression/ValorAbstrato.java" %}

ValorAbstrato ::= {% include code-link.html path="Funcional2/src/lf2/plp/functional2/expression/ValorFuncao.java" %}

ValorConcreto ::= {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ValorInteiro.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ValorBooleano.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ValorString.java" %}

ValorFuncao ::= "fn" ListId "." Expressao

ExpUnaria ::= {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ExpMenos.java" content='"-" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ExpNot.java" content='"not" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ExpLength.java" content='"length" Expressao' %}

ExpBinaria ::= {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ExpSoma.java" content='Expressao "+" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ExpSub.java" content='Expressao "-" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ExpAnd.java" content='Expressao "and" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ExpOr.java" content='Expressao "or" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ExpEquals.java" content='Expressao "==" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/expressions2/expression/ExpConcat.java" content='Expressao "++" Expressao' %}

ExpDeclaracao ::= "let" {% include code-link.html path="Funcional2/src/lf2/plp/functional1/declaration/DeclaracaoFuncional.java" %} "in" Expressao

DeclaracaoFuncional ::= {% include code-link.html path="Funcional2/src/lf2/plp/functional1/declaration/DecVariavel.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/functional2/declaration/DecFuncao.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional2/src/lf2/plp/functional1/declaration/DecComposta.java" %}

DecVariavel ::= "var" Id "=" Expressao

DecFuncao ::= "fun" ListId "=" Expressao

DecComposta ::= DeclaracaoFuncional "," DeclaracaoFuncional

ListId ::= Id \| Id ListId

Aplicacao ::= Expressao "(" ListExp ")"

ListExp ::= Expressao \| Expressao ", " ListExp


## Classes auxiliares

{% include code-link.html path="Funcional2/src/lf2/plp/functional2/util/PartialInstantiatorVisitor.java" %}\
{% include code-link.html path="Funcional2/src/lf2/plp/expressions2/memory/Ambiente.java" %}\
{% include code-link.html path="Funcional2/src/lf2/plp/expressions2/memory/AmbienteCompilacao.java" %}\
{% include code-link.html path="Funcional2/src/lf2/plp/expressions2/memory/AmbienteExecucao.java" %}\
{% include code-link.html path="Funcional2/src/lf2/plp/expressions2/memory/Contexto.java" %}\
{% include code-link.html path="Funcional2/src/lf2/plp/expressions2/memory/ContextoCompilacao.java" %}\
{% include code-link.html path="Funcional2/src/lf2/plp/expressions2/memory/ContextoExecucao.java" %}

## Exemplos de Programas

{% include code-link.html path="Funcional2/Exemplo.txt" %}

## Parser

{% include code-link.html path="Funcional2/src/lf2/plp/functional2/parser/Funcional2.jj" %}