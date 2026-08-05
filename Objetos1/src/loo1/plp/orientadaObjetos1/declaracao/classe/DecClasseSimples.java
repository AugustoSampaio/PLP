package loo1.plp.orientadaObjetos1.declaracao.classe;

import loo1.plp.expressions2.memory.VariavelJaDeclaradaException;
import loo1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.declaracao.procedimento.DecProcedimento;
import loo1.plp.orientadaObjetos1.declaracao.variavel.DecVariavel;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseJaDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ClasseNaoDeclaradaException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoJaDeclaradoException;
import loo1.plp.orientadaObjetos1.excecao.declaracao.ProcedimentoNaoDeclaradoException;
import loo1.plp.orientadaObjetos1.expressao.leftExpression.Id;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo1.plp.orientadaObjetos1.memoria.DefClasse;
import loo1.plp.orientadaObjetos1.memoria.InfoEscopo;
import loo1.plp.orientadaObjetos1.util.TipoClasse;

/**
 * Classe que representa a declaracao de uma unica classe.
 */
public class DecClasseSimples implements DecClasse {

    protected Id nomeClasse;
    protected DecVariavel atributos;
    protected DecProcedimento metodos;
	protected InfoEscopo infoEscopo;

    public DecClasseSimples(Id nomeClasse, DecVariavel atributos, DecProcedimento metodos){
        this.nomeClasse = nomeClasse;
        this.atributos = atributos;
        this.metodos = metodos;
    }

	public DecClasseSimples(Id nomeClasse, DecVariavel atributos, DecProcedimento metodos, InfoEscopo infoEscopo){
		this(nomeClasse, atributos, metodos);
		this.infoEscopo = infoEscopo;
	}

    /**
     * Verifica se a declaracao esta bem tipada.
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente)
        throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
               ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
               ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException {

        ambiente.mapDefClasse(nomeClasse, new DefClasse(nomeClasse, atributos, metodos));
        boolean resposta = false;
        ambiente.incrementa();
		// O frame de debugger precisa carregar o nome da classe além do rótulo do escopo.
		((loo1.plp.orientadaObjetos1.memoria.ContextoCompilacaoOO1) ambiente).registraEscopo(infoEscopo, nomeClasse.toString());
        if (atributos.checaTipo(ambiente)){
            ambiente.map(new Id("this"), new TipoClasse(nomeClasse));
            resposta = metodos.checaTipo(ambiente);
        }
        ambiente.restaura();
        return resposta;
    }

    /**
     * Cria um mapeamento do identificador para a declaração desta classe.
     */
	public AmbienteExecucaoOO1 elabora(AmbienteExecucaoOO1 ambiente)
			throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException,
			ClasseJaDeclaradaException, ClasseNaoDeclaradaException,
			ProcedimentoNaoDeclaradoException, ProcedimentoJaDeclaradoException {
		ambiente.mapDefClasse(nomeClasse, new DefClasse(nomeClasse, atributos, metodos));
		return ambiente;
	}
}
