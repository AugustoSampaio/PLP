package le1.plp.expressions1.util;

/**
 * Classe que representa os possiveis tipos de uma expressao.
 * 
 * @author Allan Araujo
 * @author Joabe Jesus
 * @author Marcus Machado
 * @author Rafael Oliveira
 */
public interface Tipo {

	public abstract String getNome();

	/**
	 * Indica se esta expressao &eacute; inteira.
	 * 
	 * @return <code>true</code> se esta expressao for inteira;
	 *         <code>false</code> caso contrario.
	 */
	public abstract boolean eInteiro();

	/**
	 * Indica se esta expressao &eacute; booleana.
	 * 
	 * @return <code>true</code> se esta expressao for booleana;
	 *         <code>false</code> caso contrario.
	 */
	public abstract boolean eBooleano();

	/**
	 * Indica se esta expressao &eacute; string.
	 * 
	 * @return <code>true</code> se esta expressao for string;
	 *         <code>false</code> caso contrario.
	 */
	public abstract boolean eString();

	/**
	 * Compara este tipo com o tipo dado.
	 * Dois tipos s�o iguais se eles t�m o mesmo nome.
	 * 
	 * @return <code>true</code> se forem o mesmo tipo;
	 *         <code>false</code> caso contrario.
	 */
	public abstract boolean eIgual(Tipo tipo);

	/**
	 * Este m�todo indica se este tipo � v�lido.
	 * Os tipos primitivos sempre s�o v�lidos.
	 * Outros tipos devem sobreescrever este m�todo para implementar
	 * mais restri��es sobre validade, mas o nome dever� ser ainda
	 * assim primordial para a defini��o de validade.
	 * 
	 * @return <code>true</code> se este tipo � v�lido;
	 *         <code>false</code> caso contrario.
	 */
	public abstract boolean eValido();

	/**
	 * Este m�todo retorna o tipo mais abrangente que engloba este tipo e o tipo
	 * dado. Por exemplo, se este tipo pode ser String ou inteiro e o tipo dado
	 * pode ser inteiro ou booleano, entao este metodo retorna um tipo que so
	 * pode ser inteiro.
	 * 
	 * � usado para obter o tipo de express�es como IfThenElse.
	 * 
	 * @param outroTipo
	 * @return O tipo interse��o; <code>null</code> caso n�o haja interse��o.
	 */
	public abstract Tipo intersecao(Tipo outroTipo);

}
