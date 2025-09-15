---
layout: linguagem
permalink: /linguagens/oo2
linguagem: Orientada a Objetos 2
short: OO2
order: 9
---


* Estende a Linguagem OO1 com construtores e herança
* Construtores são métodos especiais, com o mesmo nome da classe e sem valor de retorno, chamados automaticamente a cada criação de um objeto (através do comando new)
* Herança simples
* Não permite overloading, redefinição de métodos, super e nem modificadores de acesso

## Fontes

{% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/Programa.java" %} ::= "{" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/declaracao/ListaDeclaracaoOO.java" %} ";" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/Comando.java" %} "}"

Comando ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/comando/AtribuicaoOO2.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/ComDeclaracao.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/While.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/IfThenElse.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/IO.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/Sequencial.java" content='Comando ";" Comando'%} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/Skip.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/comando/NewOO2.java" content="New" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/comando/ChamadaMetodoOO2.java" content="ChamadaMetodo" %}

Skip ::=

ComDeclaracao ::= "{" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/declaracao/variavel/DecVariavel.java" %} ";" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/Comando.java" %} "}"

While ::= "while" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/Expressao.java" %} "do" "{" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/Comando.java" %} "}"

IfThenElse ::= "if" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/Expressao.java" %} "then" "{" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/Comando.java" %} "}" \
{% include code-indent.html %} | "if" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/Expressao.java" %} "then" "{" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/Comando.java" %} "}" "else" "{" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/Comando.java" %} "}"

ChamadaMetodo ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/Expressao.java" %} "." {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/leftExpression/Id.java" %} "(" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/ListaExpressao.java" %} ")" \| {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/Expressao.java" %} "." {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/leftExpression/Id.java" %} "("")"

ListaExpressao ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/Expressao.java" %} \| {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/Expressao.java" %} "," {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/ListaExpressao.java" %}

New ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/leftExpression/LeftExpression.java" %} ":=" "new" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/leftExpression/Id.java" %} "(" [ListaExpressao] ")"

Atribuicao ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/leftExpression/LeftExpression.java" %} ":=" {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/Expressao.java" %}

IO ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/Write.java" content='"write" "(" Expressao ")"'%} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/comando/Read.java" content='"read" "(" Id ")"'%}

Expressao ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/valor/Valor.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/unaria/ExpUnaria.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/binaria/ExpBinaria.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/leftExpression/LeftExpression.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/This.java" %}

Valor ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/valor/ValorConcreto.java" %}

ValorConcreto ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/valor/ValorInteiro.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/valor/ValorBooleano.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/valor/ValorString.java" %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/valor/ValorNull.java" %}

ExpUnaria ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/unaria/ExpMenos.java" content='- Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/unaria/ExpNot.java" content='not Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/unaria/ExpLength.java" content='length Expressao' %}

ExpBinaria ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/binaria/ExpSoma.java" content='Expressao + Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/binaria/ExpSub.java" content='Expressao - Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/binaria/ExpAnd.java" content='Expressao and Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/binaria/ExpOr.java" content='Expressao or Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/binaria/ExpEquals.java" content='Expressao == Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/binaria/ExpConcat.java" content='Expressao ++ Expressao' %}

LeftExpression ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/leftExpression/Id.java" %} \| {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/leftExpression/AcessoAtributo.java" %}

AcessoAtributo ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/expressao/leftExpression/AcessoAtributoIdOO2.java" content='LeftExpression.Id' %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/expressao/leftExpression/AcessoAtributoThisOO2.java" content='this.Id' %} 

ListaDeclaracaoOO ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/declaracao/classe/DecClasseSimplesOO2.java" content='DecClasse' %} \
{% include code-indent.html %} \| {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/declaracao/classe/DecClasseSimplesOO2.java" content='DecClasse' %} "," {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/declaracao/ListaDeclaracaoOO.java" %}

DecClasse ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/declaracao/classe/DecClasseSimplesOO2.java" content='"classe" Id ["extends" Id] "{" DecVariavel ";" DecConstrutor "," DecProcedimento "}"' %}

DecConstrutor ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/declaracao/DecConstrutor.java" content='Id "(" ListaDeclaracaoParametro ")" "{" Comando "}"' %}

DecVariavel ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/declaracao/variavel/SimplesDecVariavel.java" content='Tipo Id "=" Expressao' %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/declaracao/variavel/CompostaDecVariavel.java" content='DecVariavel "," DecVariavel' %} \
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/declaracao/variavel/DecVariavelObjetoOO2.java" content='Tipo Id ":=" "new" Id' %}

DecProcedimento ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/declaracao/procedimento/DecProcedimento.java" content='"proc" Id "(" ListaDeclaracaoParametro ")" "{" Comando "}"' %}\
{% include code-indent.html %} | {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/declaracao/procedimento/DecProcedimentoComposta.java" content='DecProcedimento "," DecProcedimento' %}

ListaDeclaracaoParametro ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/declaracao/procedimento/DecParametro.java" content='Tipo Id ' %}\| {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/declaracao/procedimento/ListaDeclaracaoParametro.java" content='Tipo Id "," ListaDeclaracaoParametro' %}

Tipo ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/util/TipoClasse.java" %} \| {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/util/TipoPrimitivo.java" %}

TipoClasse ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/expressao/leftExpression/Id.java" %}

TipoPrimitivo ::= {% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos1/util/TipoPrimitivo.java" content='"string" \| "int" \| "boolean"' %}

## Parser

{% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/parser/OO2.jj" %}

## Classes Auxiliares

{% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/declaracao/ConstrutorNaoDeclaradoException.java" %} \
{% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/memoria/AmbienteCompilacaoOO2.java" %} \
{% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/memoria/AmbienteExecucaoOO2.java" %} \
{% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/memoria/ContextoCompilacaoOO2.java" %} \
{% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/memoria/ContextoExecucaoOO2.java" %} \
{% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/memoria/DefClasseOO2.java" %} \
{% include code-link.html path="Objetos2/src/loo2/plp/orientadaObjetos2/util/SuperClasseMap.java" %}