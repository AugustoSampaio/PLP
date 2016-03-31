package plp.orientadaObjetos1.testes;

import plp.expressions2.memory.VariavelJaDeclaradaException;
import plp.expressions2.memory.VariavelNaoDeclaradaException;
import plp.orientadaObjetos1.Programa;
import plp.orientadaObjetos1.comando.Atribuicao;
import plp.orientadaObjetos1.comando.ChamadaMetodo;
import plp.orientadaObjetos1.comando.ComDeclaracao;
import plp.orientadaObjetos1.comando.IfThenElse;
import plp.orientadaObjetos1.comando.New;
import plp.orientadaObjetos1.comando.Sequencial;
import plp.orientadaObjetos1.comando.Skip;
import plp.orientadaObjetos1.comando.While;
import plp.orientadaObjetos1.comando.Write;
import plp.orientadaObjetos1.declaracao.classe.DecClasseSimples;
import plp.orientadaObjetos1.declaracao.procedimento.DecParametro;
import plp.orientadaObjetos1.declaracao.procedimento.DecProcedimentoComposta;
import plp.orientadaObjetos1.declaracao.procedimento.DecProcedimentoSimples;
import plp.orientadaObjetos1.declaracao.procedimento.ListaDeclaracaoParametro;
import plp.orientadaObjetos1.declaracao.variavel.CompostaDecVariavel;
import plp.orientadaObjetos1.declaracao.variavel.DecVariavelObjeto;
import plp.orientadaObjetos1.declaracao.variavel.SimplesDecVariavel;
import plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ObjetoNaoDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import plp.orientadaObjetos1.excecao.execucao.EntradaInvalidaException;
import plp.orientadaObjetos1.excecao.execucao.EntradaNaoFornecidaException;
import plp.orientadaObjetos1.expressao.ListaExpressao;
import plp.orientadaObjetos1.expressao.This;
import plp.orientadaObjetos1.expressao.binaria.ExpAnd;
import plp.orientadaObjetos1.expressao.binaria.ExpEquals;
import plp.orientadaObjetos1.expressao.leftExpression.AcessoAtributoId;
import plp.orientadaObjetos1.expressao.leftExpression.AcessoAtributoThis;
import plp.orientadaObjetos1.expressao.leftExpression.Id;
import plp.orientadaObjetos1.expressao.unaria.ExpNot;
import plp.orientadaObjetos1.expressao.valor.ValorInteiro;
import plp.orientadaObjetos1.expressao.valor.ValorNull;
import plp.orientadaObjetos1.memoria.ContextoCompilacaoOO1;
import plp.orientadaObjetos1.memoria.ContextoExecucaoOO1;
import plp.orientadaObjetos1.memoria.colecao.ListaValor;
import plp.orientadaObjetos1.util.TipoClasse;
import plp.orientadaObjetos1.util.TipoPrimitivo;

/*
   Programa teste na Linguagem Orientada a Objetos 1 (LOO1)

{
      classe LValor {

          int valor = 1,
          LValor prox = null;

          proc insere(int v) {
            {
                LValor aux = this;
                while ( not(aux.prox == null)) do {
                    aux := aux.prox
		};
                aux.prox := new LValor;
                ((aux).prox).valor := v
            }
          },

          proc remove(int v) {
            {
                LValor aux = this;
                while(not( (aux.prox == null)  or (((aux).prox).valor == v))) do {
                  aux := aux.prox
		};
                if ( not( aux.prox == null) ) then {
                   aux.prox := ((aux).prox).prox
		}
                else { skip}
            }

          },

          proc print() {
           write(this.valor);
           if ( not( this.prox == null) ) then {
             (this).prox.print()
	    }
           else {skip}
          }

     }
     ;

     {
       LValor lv := new LValor;
       lv.insere(2);
       lv.insere(3);
       lv.insere(4);
       lv.print();
       lv.remove(3);
       lv.print()
     }

  }

     O resultado é: 1 2 3 4 1 2 4
*/

public class TLOO1{

  public static void main(String [] args){

   Programa prg = new Programa

    (

      new DecClasseSimples
      (
       new Id("LValor")
       ,
       new CompostaDecVariavel
          (
           new SimplesDecVariavel(new TipoPrimitivo(1), new Id("valor"), new ValorInteiro(1))
           ,
           new SimplesDecVariavel(new TipoClasse(new Id("LValor")),new Id("prox"), new ValorNull())
          )
       ,
       new DecProcedimentoComposta
        (
         new DecProcedimentoSimples
          (
           new Id("insere"),
           new ListaDeclaracaoParametro(new DecParametro(new Id("v"), new TipoPrimitivo(1))),
           new ComDeclaracao
            (
             new SimplesDecVariavel( new TipoClasse(new Id("LValor")),new Id("aux") , new This()),
             new Sequencial
              (
               new Sequencial
               (
                new While
                  (
                    new ExpNot(new ExpEquals(new AcessoAtributoId(new Id("aux"), new Id("prox")) ,new ValorNull()))
                   ,
                    new Atribuicao( new Id("aux") , new AcessoAtributoId(new Id("aux"), new Id("prox")) )
                  )
               ,
                new New ( new AcessoAtributoId(new Id("aux"), new Id("prox")) , new Id("LValor"))
               )
              ,
               new Atribuicao
                 (
                  new AcessoAtributoId(new AcessoAtributoId(new Id("aux"), new Id("prox")), new Id("valor")),
                  new Id("v")
                 )
              )
            )
          )
         ,
         new DecProcedimentoComposta
          (
           new DecProcedimentoSimples
             (
              new Id("print"),
              new ListaDeclaracaoParametro(),
              new Sequencial
               (
                new Write(new AcessoAtributoThis(new This(), new Id("valor")))
                ,
                new IfThenElse
                 (
                  new ExpNot(new ExpEquals(new AcessoAtributoThis(new This(), new Id("prox")) ,new ValorNull()))
                  ,
                  new ChamadaMetodo( new AcessoAtributoThis(new This(), new Id("prox")) , new Id("print"), new ListaExpressao())
                  ,
                  new Skip()
                 )
              )
            )
          ,
          new DecProcedimentoSimples
           (
             new Id("remove")
            ,
             new ListaDeclaracaoParametro(new DecParametro( new Id("v"), new TipoPrimitivo(1)))
            ,
             new ComDeclaracao
              (
               new SimplesDecVariavel(new TipoClasse(new Id("LValor")), new Id("aux") , new This() )
               ,
                new Sequencial
                 (
                  new While
                  (
                    new ExpAnd
                      (
                       new ExpNot(new ExpEquals(new AcessoAtributoId(new Id("aux"), new Id("prox")) ,new ValorNull()))
                       ,
                       new ExpNot(new ExpEquals
                                   (
                                    new AcessoAtributoId(new AcessoAtributoId(new Id("aux"), new Id("prox")), new Id("valor"))
                                   ,
                                    new Id("v")
                                   )
                                 )
                     )
                   ,
                    new Atribuicao( new Id("aux") , new AcessoAtributoId(new Id("aux"), new Id("prox")) )
                  )
                  ,
                 new IfThenElse
                 (
                  new ExpNot(new ExpEquals(new AcessoAtributoId(new Id("aux"), new Id("prox")) , new ValorNull()))
                  ,
                  new Atribuicao
                   (
                    new AcessoAtributoId(new Id("aux"), new Id("prox"))
                     ,
                    new AcessoAtributoId(new AcessoAtributoId(new Id("aux"), new Id("prox")), new Id("prox"))
                   )
                  ,
                  new Skip()
                 )

                 )
             )
           )
         )
       )
     )

    ,

      new ComDeclaracao
       (
         new DecVariavelObjeto( new TipoClasse(new Id ("LValor")), new Id("lv"), new Id("LValor"))
        ,
         new Sequencial
          (
            new ChamadaMetodo( new Id("lv") , new Id("insere"), new ListaExpressao(new ValorInteiro(2)))
           ,
           new Sequencial
            (
             new Sequencial
              (
               new ChamadaMetodo( new Id("lv") , new Id("insere"), new ListaExpressao(new ValorInteiro(3)))
               ,
               new Sequencial
                (
                 new ChamadaMetodo( new Id("lv") , new Id("insere"), new ListaExpressao(new ValorInteiro(4)))
                 ,
                 new ChamadaMetodo( new Id("lv") , new Id("print"), new ListaExpressao())
                )
              )
             ,
             new Sequencial
              (
               new ChamadaMetodo( new Id("lv") , new Id("remove"), new ListaExpressao(new ValorInteiro(3)))
               ,
               new ChamadaMetodo( new Id("lv") , new Id("print"), new ListaExpressao())
              )
            )
         )


     )
    );

  try{
    if(prg.checaTipo(new ContextoCompilacaoOO1(new ListaValor()))) {
       System.out.println(prg.executar(new ContextoExecucaoOO1(new ListaValor())));
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
      System.out.println("Objeto de Entrada já Declarados!"+e.toString());
  }
  catch (ObjetoNaoDeclaradoException e){
      System.out.println("Falta fornecer o Objeto de Entrada!"+e.toString());
      e.printStackTrace();
  }
  catch (ProcedimentoJaDeclaradoException e){
      System.out.println("Metodos já Declarados!"+e.toString());
  }
  catch (ProcedimentoNaoDeclaradoException e){
      System.out.println("Falta fornecer algum Metodo!"+e.toString());
  }
  catch (EntradaInvalidaException e){
      System.out.println(e.toString());
  }
 }


}
