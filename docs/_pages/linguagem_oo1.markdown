---
layout: linguagem
permalink: /linguagens/oo1
linguagem: Orientada a Objetos 1
short: OO1
order: 8
---


* Estende Linguagem Imperativa 2 com classes e objetos
* Procedimentos passam a ocorrer exclusivamente como métodos das classes
* O contexto também é estendido com:
    * um mapeamento de identificadores (nomes de classes) em definições de classes
* Um programa é um comando

## Fontes

{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/Programa.java" %} ::= "{" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/classe/DecClasse.java" %} ";" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Comando.java" %} "}"

Comando ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Atribuicao.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/ComDeclaracao.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/While.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/IfThenElse.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/IO.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Sequencial.java" content='Comando ";" Comando' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Skip.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/New.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/ChamadaMetodo.java" %}

Skip ::=

ComDeclaracao ::= "{" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/variavel/DecVariavel.java" %} ";" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Comando.java" %} "}"

While ::= "while" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/Expressao.java" %} "do" "{" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Comando.java" %} "}"

IfThenElse ::= "if" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/Expressao.java" %} "then" "{" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Comando.java" %} "}"\
{% include code-indent.html %} | "if" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/Expressao.java" %} "then" "{" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Comando.java" %} "}" "else" "{" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Comando.java" %} "}"

ChamadaMetodo ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/Expressao.java" %} "." {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/leftExpression/Id.java" %} "(" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/ListaExpressao.java" %} ")" \| {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/Expressao.java" %} "." {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/leftExpression/Id.java" %} "("")"

ListaExpressao ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/Expressao.java" %} \| {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/Expressao.java" %} "," {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/ListaExpressao.java" %}

New ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/leftExpression/LeftExpression.java" %} ":=" "new" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/leftExpression/Id.java" %}

Atribuicao ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/leftExpression/LeftExpression.java" %} ":=" {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/Expressao.java" %}

IO ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Write.java" %} \| {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Read.java" %}

Expressao ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/valor/Valor.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/unaria/ExpUnaria.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/binaria/ExpBinaria.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/leftExpression/LeftExpression.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/This.java" %}

Valor ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/valor/ValorConcreto.java" %}

ValorConcreto ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/valor/ValorInteiro.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/valor/ValorBooleano.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/valor/ValorString.java" %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/valor/ValorNull.java" %}

ExpUnaria ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/unaria/ExpMenos.java" content='"-" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/unaria/ExpNot.java" content='"not" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/unaria/ExpLength.java" content='"length" Expressao' %}

ExpBinaria ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/binaria/ExpSoma.java" content='Expressao "+" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/binaria/ExpSub.java" content='Expressao "-" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/binaria/ExpAnd.java" content='Expressao "and" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/binaria/ExpOr.java" content='Expressao "or" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/binaria/ExpEquals.java" content='Expressao "==" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/binaria/ExpConcat.java" content='Expressao "++" Expressao' %}


LeftExpression ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/leftExpression/Id.java" %} \| {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/leftExpression/AcessoAtributo.java" %}

AcessoAtributo ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/leftExpression/AcessoAtributoId.java" content='LeftExpression.Id' %} \| {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/leftExpression/AcessoAtributoThis.java" content='this.Id' %}

DecClasse ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/classe/DecClasseSimples.java" content='"classe" Id "{" DecVariavel ";" DecProcedimento "}" '%}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/classe/DecClasseComposta.java" content='DecClasse "," DecClasse' %}

DecVariavel ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/variavel/SimplesDecVariavel.java" content='Tipo Id "=" Expressao' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/variavel/CompostaDecVariavel.java" content='DecVariavel "," DecVariavel' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/variavel/DecVariavelObjeto.java" content='Tipo Id ":=" "new" Id' %}

{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/DecProcedimento.java" %} ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/procedimento/DecProcedimento.java" content='"proc" Id "(" ListaDeclaracaoParametro ")" "{" Comando "}"' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/procedimento/DecProcedimentoComposta.java" content='DecProcedimento "," DecProcedimento' %}

ListaDeclaracaoParametro ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/procedimento/DecParametro.java" content='Tipo Id' %} \| {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/procedimento/ListaDeclaracaoParametro.java" content='Tipo Id "," ListaDeclaracaoParametro' %}

Tipo ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/util/TipoClasse.java" %} \| {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/util/TipoPrimitivo.java" %}

TipoClasse ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/leftExpression/Id.java" %}

TipoPrimitivo ::= {% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/util/TipoPrimitivo.java" %}

## Parser

{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/parser/OO1.jj" %}\
{% include code-link.html path="bnfOrientadaObjetos1Real.html" %}

## Classes Auxiliares

{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/Procedimento.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/comando/ChamadaProcedimento.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/declaracao/Declaracao.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/excecao/declaracao/ClasseJaDeclaradaException.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/excecao/declaracao/ClasseNaoDeclaradaException.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/excecao/declaracao/ObjetoJaDeclaradoException.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/excecao/declaracao/ObjetoNaoDeclaradoException.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/excecao/declaracao/ProcedimentoJaDeclaradoException.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/excecao/declaracao/ProcedimentoNaoDeclaradoException.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/excecao/declaracao/VariavelJaDeclaradaException.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/excecao/declaracao/VariavelNaoDeclaradaException.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/excecao/execucao/EntradaNaoFornecidaException.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/excecao/execucao/EntradaInvalidaException.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/expressao/valor/ValorRef.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/memoria/AmbienteOO1.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/memoria/AmbienteCompilacaoOO1.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/memoria/AmbienteExecucaoOO1.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/memoria/ContextoCompilacaoOO1.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/memoria/ContextoExecucaoOO1.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/memoria/DefClasse.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/memoria/Objeto.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/memoria/ContextoObjeto.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/memoria/colecao/ListaValor.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/util/ListaTipo.java" %}


## Testes com o JUnit


{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/testes/AllTests.java" %}\
{% include code-link.html path="Objetos1/src/loo1/plp/orientadaObjetos1/testes/OO1ParserTest.java" %}

## Programas utilizados nos testes

{% include code-link.html path="testes/teste1.txt" %}\
{% include code-link.html path="testes/teste2.txt" %}\
{% include code-link.html path="testes/teste3.txt" %}\
{% include code-link.html path="testes/teste4.txt" %}\
{% include code-link.html path="testes/teste5.txt" %}\
{% include code-link.html path="testes/teste6.txt" %}\
{% include code-link.html path="testes/teste7.txt" %}