---
layout: linguagem
permalink: /linguagens/funcional1
linguagem: Funcional 1
short: Func1
order: 3
---

- Estende a Linguagem de Expressões 2 com funções parametrizadas e recursivas
- O corpo de uma função é uma expressão e a aplicação da função a um argumento retorna um valor
- O contexto inclui dois componentes:
    - mapeamento de identificadores em valores
    - mapeamento de identificadores (nomes de função) em definições de função
- Um programa é uma expressão

## Fontes

{% include code-link.html path="Funcional1/src/lf1/plp/functional1/Programa.java" %} ::= {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/Expressao.java" %}

{% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/Valor.java" %} ::= {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ValorConcreto.java" %}

Expressao ::= {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ExpUnaria.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/functional1/expression/ExpDeclaracao.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/Id.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/functional1/expression/Aplicacao.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/functional1/expression/IfThenElse.java" %}

Valor ::= {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ValorConcreto.java" %}

ValorConcreto ::= {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ValorInteiro.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ValorBooleano.java" %}\
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ValorString.java" %}

ExpUnaria ::= {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ExpMenos.java" content='"-" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ExpNot.java" content='"not" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ExpLength.java" content='"length" Expressao' %}

ExpBinaria ::= {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ExpSoma.java" content='Expressao "+" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ExpSub.java" content='Expressao "-" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ExpAnd.java" content='Expressao "and" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ExpOr.java" content='Expressao "or" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ExpEquals.java" content='Expressao "==" Expressao' %}  
{% include code-indent.html %}| {% include code-link.html path="Funcional1/src/lf1/plp/expressions2/expression/ExpConcat.java" content='Expressao "++" Expressao' %}

ExpDeclaracao ::= "let" {% include code-link.html path="Funcional1/src/lf1/plp/functional1/declaration/DeclaracaoFuncional.java" %} "in" Expressao

DeclaracaoFuncional ::= {% include code-link.html path="Funcional1/src/lf1/plp/functional1/declaration/DecVariavel.java" %}  
{% include code-indent.html %} | {% include code-link.html path="Funcional1/src/lf1/plp/functional1/declaration/DecFuncao.java" %}  
{% include code-indent.html %} | {% include code-link.html path="Funcional1/src/lf1/plp/functional1/declaration/DecComposta.java" %}

DecVariavel ::= "var" Id "=" Expressao

DecFuncao ::= "fun" ListId "=" Expressao

DecComposta ::= DeclaracaoFuncional "," DeclaracaoFuncional

ListId ::= Id  \|  Id ListId

Aplicacao ::=  Id"(" ListExp ")"

ListExp ::= Expressao \| Expressao, ListExp

IfThenElse ::= "if" Expressao "then" Expressao "else" Expressao


## Classes Auxiliares

{% include code-link.html path="Funcional1/src/lf1/plp/functional1/util/DefFuncao.java" %}

{% include code-link.html path="Funcional1/src/lf1/plp/expressions1/util/Tipo.java" %}\
{% include code-link.html path="Funcional1/src/lf1/plp/functional1/util/TipoFuncao.java" %}\
{% include code-link.html path="Funcional1/src/lf1/plp/functional1/util/TipoPolimorfico.java" %}

{% include code-link.html path="Funcional1/src/lf1/plp/expressions2/memory/AmbienteCompilacao.java" %}\
{% include code-link.html path="Funcional1/src/lf1/plp/expressions2/memory/ContextoCompilacao.java" %}

{% include code-link.html path="Funcional1/src/lf1/plp/functional1/memory/AmbienteFuncional.java" %}\
{% include code-link.html path="Funcional1/src/lf1/plp/functional1/memory/AmbienteExecucaoFuncional.java" %}\
{% include code-link.html path="Funcional1/src/lf1/plp/functional1/memory/ContextoFuncional.java" %}\
{% include code-link.html path="Funcional1/src/lf1/plp/functional1/memory/ContextoExecucaoFuncional.java" %}

## Exemplos de Programas

{% include code-link.html path="Funcional1/Exemplo.txt" %}

## Parser

{% include code-link.html path="Funcional1/src/lf1/plp/functional1/parser/Functional1.jj" %}