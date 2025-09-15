---
layout: linguagem
permalink: /linguagens/funcional3
linguagem: Funcional 3
short: Func3
order: 5
---

* Estende a Linguagem Funcional 2 com listas e compreensão de listas
* Uma Lista é um valor
* Portanto pode ser passado como parâmetro para uma função
* Um programa é uma expressão

## Fontes

{% include code-link.html path="Funcional3/src/lf3/plp/functional3/Programa.java" %} ::= {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Expressao.java" %}

Expressao ::= {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Valor.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ExpUnaria.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ExpBinaria.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional2/expression/ExpDeclaracao.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Id.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional2/expression/Aplicacao.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional1/expression/IfThenElse.java" %}

Valor ::= {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ValorConcreto.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional2/expression/ValorAbstrato.java" %}

ValorAbstrato ::= {% include code-link.html path="Funcional3/src/lf3/plp/functional2/expression/ValorFuncao.java" %}

ValorConcreto ::= {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ValorInteiro.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ValorBooleano.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ValorString.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional3/expression/ValorLista.java" %}

ValorFuncao ::= "fn" {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Id.java" %} {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Id.java" %} "." {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Expressao.java" %}

ExpUnaria ::= {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ExpMenos.java" content='"-" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ExpNot.java" content='"not" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ExpLength.java" content='"length" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional3/expression/ExpHead.java" content='head(Expressao)' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional3/expression/ExpTail.java" content='tail(Expressao)' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional3/expression/ExpCompreensaoLista.java" %}

ExpCompreensaoLista ::= Expressao {% include code-link.html path="Funcional3/src/lf3/plp/functional3/expression/Gerador.java" %}\
{% include code-indent.html %} | Expressao Gerador Filtro

Gerador ::= "for" {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Id.java" %} "in" {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Expressao.java" %}\
{% include code-indent.html %} | "for" {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Id.java" %} "in" {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Expressao.java" %} ["," Gerador]

Filtro ::= "if" {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Expressao.java" %}

ExpBinaria ::= {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ExpSoma.java" content='Expressao "+" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ExpSub.java" content='Expressao "-" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional3/expression/ExpMult.java" content='Expressao "*" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional3/expression/ExpMaiorQue.java" content='Expressao ">" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional3/expression/ExpMenorQue.java" content='Expressao "<" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ExpAnd.java" content='Expressao "and" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ExpOr.java" content='Expressao "or" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ExpEquals.java" content='Expressao "==" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/ExpConcat.java" content='Expressao "++" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional3/expression/ExpSequencia.java" content='Expressao ".." Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional3/expression/ExpCons.java" content='Expressao ":" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional3/expression/ExpConcatLista.java" content='Expressao "^^" Expressao' %}


ExpDeclaracao ::= "let" {% include code-link.html path="Funcional3/src/lf3/plp/functional1/declaration/DeclaracaoFuncional.java" %} "in" {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Expressao.java" %}

DeclaracaoFuncional ::= {% include code-link.html path="Funcional3/src/lf3/plp/functional1/declaration/DecVariavel.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional2/declaration/DecFuncao.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/functional3/declaration/DecComposta.java" %}

DecVariavel ::= "var" {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Id.java" %} "=" {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Expressao.java" %}

DecFuncao ::= "fun" ListId "=" {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Expressao.java" %}

DecComposta ::= DeclaracaoFuncional "," DeclaracaoFuncional

ListId ::= {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Id.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Funcional3/src/lf3/plp/expressions2/expression/Id.java" %}, ListId

Aplicacao ::= Expressao "(" ListExp ")"

ListExp ::= Expressao  
{% include code-indent.html %} | Expressao, ListExp


## Classes auxiliares
{% include code-link.html path="Funcional3/src/lf3/plp/expressions2/memory/Ambiente.java" %}\
{% include code-link.html path="Funcional3/src/lf3/plp/expressions2/memory/AmbienteCompilacao.java" %}\
{% include code-link.html path="Funcional3/src/lf3/plp/expressions2/memory/AmbienteExecucao.java" %}\
{% include code-link.html path="Funcional3/src/lf3/plp/functional3/util/TipoLista.java" %}\
{% include code-link.html path="Funcional3/src/lf3/plp/functional3/util/ListaVaziaException.java" %}


## Exemplos de Programas

{% include code-link.html path="Funcional3/Exemplo.txt" %}


## Parser

{% include code-link.html path="Funcional3/src/lf3/plp/functional3/parser/Funcional3.jj" %}
