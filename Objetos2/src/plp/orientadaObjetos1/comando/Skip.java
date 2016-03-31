package plp.orientadaObjetos1.comando;

import plp.orientadaObjetos1.memoria.AmbienteExecucaoOO1;
import plp.orientadaObjetos1.memoria.AmbienteCompilacaoOO1;

public class Skip implements Comando{

    /**
     * Não realiza nenhuma alteração no ambiente.
     * @param ambiente o ambiente de execução.
     * @return o ambiente inalterado.
     */
    public AmbienteExecucaoOO1 executar(AmbienteExecucaoOO1 ambiente) {
        return ambiente;
    }

    /**
     * Realiza a verificacao de tipos do comando
     * @param ambiente o ambiente de compilação.
     * @return <code>true</code> se o comando é bem tipado;
     *          <code>false</code> caso contrario. 
     */
    public boolean checaTipo(AmbienteCompilacaoOO1 ambiente) {
        return true;
    }
}
