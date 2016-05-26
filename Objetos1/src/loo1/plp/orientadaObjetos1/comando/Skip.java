package loo1.plp.orientadaObjetos1.comando;

import loo1.plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import loo1.plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;

public class Skip implements Comando{

    /**
     * N�o realiza nenhuma altera��o no ambiente.
     * @param ambiente o ambiente de execu��o.
     * @return o ambiente inalterado.
     */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente) {
        return ambiente;
    }

    /**
     * Realiza a verificacao de tipos do comando
     * @param ambiente o ambiente de compila��o.
     * @return <code>true</code> se o comando � bem tipado;
     *          <code>false</code> caso contrario. 
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente) {
        return true;
    }
}
