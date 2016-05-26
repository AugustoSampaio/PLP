package loo1.plp.orientadaObjetos1.testes;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.Programa;
import loo1.plp.orientadaObjetos1.comando.Atribuicao;
import loo1.plp.orientadaObjetos1.comando.ChamadaMetodo;
import loo1.plp.orientadaObjetos1.comando.ComDeclaracao;
import loo1.plp.orientadaObjetos1.comando.Comando;
import loo1.plp.orientadaObjetos1.comando.Sequencial;
import loo1.plp.orientadaObjetos1.comando.Write;
import loo1.plp.orientadaObjetos1.declaracao.classe.DecClasse;
import loo1.plp.orientadaObjetos1.declaracao.classe.DecClasseSimples;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.DecProcedimento;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.DecProcedimentoComposta;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.DecProcedimentoSimples;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import loo1.plp.orientadaObjetos1.declaracao.variavel.CompostaDecVariavel;
import loo1.plp.orientadaObjetos1.declaracao.variavel.DecVariavel;
import loo1.plp.orientadaObjetos1.declaracao.variavel.DecVariavelObjeto;
import loo1.plp.orientadaObjetos1.declaracao.variavel.SimplesDecVariavel;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import loo1.plp.orientadaObjetos1.excecao.execucao.EntradaNaoFornecidaException;
import loo1.plp.orientadaObjetos1.expressao.Expressao;
import loo1.plp.orientadaObjetos1.expressao.ListaExpressao;
import loo1.plp.orientadaObjetos1.expressao.binaria.ExpSoma;
import loo1.plp.orientadaObjetos1.expressao.leftExpression.AcessoAtributoId;
import loo1.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo1.plp.orientadaObjetos1.expressao.valor.Valor;
import loo1.plp.orientadaObjetos1.expressao.valor.ValorInteiro;
import loo1.plp.orientadaObjetos1.memoria.ContextoCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.ContextoExecucaoOO1;
import loo1.plp.orientadaObjetos1.memoria.colecao.ListaValor;
import loo1.plp.orientadaObjetos1.util.Tipo;
import loo1.plp.orientadaObjetos1.util.TipoClasse;
import loo1.plp.orientadaObjetos1.util.TipoPrimitivo;

/*
   Programa teste na Linguagem Orientada a Objetos 1 (LOO1)

   {

      classe Contador {

          int valor = 1;

          proc incrementa() {
            this.valor := this.valor + 1
          },

          proc print() {
            write(this.valor)
          }

      }
     ;

     {
       Contador c := new Contador,
       Contador c2 := new Contador;
       c.incrementa();
       c2.incrementa();
       c2.incrementa();
       c.print();
       c2.print()
     }

   }

   O resultado e: 2 3
*/

public class Teste2LOO1 {

  public static void main(String [] args){
    Id valor = new Id("valor");
    Id idIncrementa = new Id("incrementa");
    Id idPrint = new Id("print");
    Id idThis = new Id("this");
    Id idValor = new Id("valor");
    Id idVarC = new Id("c");
    Id idVarC2 = new Id("c2");
    Id idContador = new Id("Contador");

    Valor int1 = new ValorInteiro(1);
    //Valor int2 = new ValorInteiro(2);

    ListaDeclaracaoParametro parVazios = new ListaDeclaracaoParametro();
    ListaExpressao parReaisVazios = new ListaExpressao();


    Tipo tipoInteiro = new TipoPrimitivo(TipoPrimitivo.INTEIRO);

    DecVariavel atributo = new SimplesDecVariavel(tipoInteiro, valor, int1);
    AcessoAtributoId thisValor = new AcessoAtributoId(idThis, idValor);
    Expressao soma = new ExpSoma(thisValor, int1);
    Atribuicao atribuicao = new Atribuicao(thisValor,soma);
    Comando write = new Write(thisValor);
    DecProcedimento incrementa = new DecProcedimentoSimples(idIncrementa, parVazios, atribuicao);
    DecProcedimento print = new DecProcedimentoSimples(idPrint, parVazios, write);

    DecProcedimento procedimentosContador = new DecProcedimentoComposta(incrementa,print);
    DecClasse classeContador = new DecClasseSimples(idContador, atributo, procedimentosContador);

    // comandos para a main
    Tipo tipoContador = new TipoClasse(idContador);
    DecVariavel declaracaoVarC = new DecVariavelObjeto( tipoContador, idVarC, idContador);
    DecVariavel declaracaoVarC2 = new DecVariavelObjeto( tipoContador, idVarC2, idContador);
    ChamadaMetodo chamadaIncrementaC = new ChamadaMetodo(idVarC, idIncrementa, parReaisVazios);
    ChamadaMetodo chamadaIncrementa1C2 = new ChamadaMetodo(idVarC2, idIncrementa, parReaisVazios);
    ChamadaMetodo chamadaIncrementa2C2 = new ChamadaMetodo(idVarC2, idIncrementa, parReaisVazios);
    ChamadaMetodo chamadaPrintC = new ChamadaMetodo(idVarC, idPrint, parReaisVazios);
    ChamadaMetodo chamadaPrintC2 = new ChamadaMetodo(idVarC2, idPrint, parReaisVazios);

    CompostaDecVariavel vars = new CompostaDecVariavel(declaracaoVarC, declaracaoVarC2);

    Comando comando4 = new Sequencial(chamadaPrintC, chamadaPrintC2);
    Comando comando3 = new Sequencial(chamadaIncrementa2C2, comando4);
    Comando comando2 = new Sequencial(chamadaIncrementa1C2, comando3);
    Comando comando1 = new Sequencial(chamadaIncrementaC, comando2);
    Comando comandosMain = comando1;

    Comando main = new ComDeclaracao(vars, comandosMain);

    Programa prg = new Programa(classeContador, main);

  try{
    if(prg.checaTipo(new ContextoCompilacaoOO1(new ListaValor()))) {
       System.out.println(prg.executar(new ContextoExecucaoOO1(new ListaValor())));
       System.out.println("TERMINOU");

     }
     else {
      System.out.println("Erro de tipo!");
     }
   }
  catch (VariavelNaoDeclaradaException e){
      System.out.println("Alguma variavel nao declarada foi referenciada!"+e.toString());
  }
  catch (EntradaNaoFornecidaException e){
      System.out.println("Entrada nao fornecida"+e.toString());
  }
  catch (VariavelJaDeclaradaException e){
      System.out.println("Valores de Entrada ja Declarados"+e.toString());
  }
  catch (ClasseNaoDeclaradaException e){
      System.out.println("Falta declarar a classe!"+e.toString());
  }
  catch (ClasseJaDeclaradaException e){
      System.out.println("Classe ja Declarada"+e.toString());
  }
  catch (ObjetoJaDeclaradoException e){
      System.out.println("Objeto de Entrada j� Declarados!"+e.toString());
  }
  catch (ObjetoNaoDeclaradoException e){
      System.out.println("Falta fornecer o Objeto de Entrada!"+e.toString());
  }
  catch (ProcedimentoJaDeclaradoException e){
      System.out.println("Metodos j� Declarados!"+e.toString());
  }
  catch (ProcedimentoNaoDeclaradoException e){
      System.out.println("Falta fornecer algum Metodo!"+e.toString());
  }
  catch (EntradaInvalidaException e){
      System.out.println(e.toString());
  }
 }


}