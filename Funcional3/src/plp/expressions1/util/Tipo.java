package plp.expressions1.util;

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
	 * Dois tipos são iguais se eles têm o mesmo nome.
	 * 
	 * @return <code>true</code> se forem o mesmo tipo;
	 *         <code>false</code> caso contrario.
	 */
	public abstract boolean eIgual(Tipo tipo);

	/**
	 * Este método indica se este tipo é válido.
	 * Os tipos primitivos sempre são válidos.
	 * Outros tipos devem sobreescrever este método para implementar
	 * mais restrições sobre validade, mas o nome deverá ser ainda
	 * assim primordial para a definição de validade.
	 * 
	 * @return <code>true</code> se este tipo é válido;
	 *         <code>false</code> caso contrario.
	 */
	public abstract boolean eValido();

	/**
	 * Este método retorna o tipo mais abrangente que engloba este tipo e o tipo
	 * dado. Por exemplo, se este tipo pode ser String ou inteiro e o tipo dado
	 * pode ser inteiro ou booleano, entao este metodo retorna um tipo que so
	 * pode ser inteiro.
	 * 
	 * É usado para obter o tipo de expressões como IfThenElse.
	 * 
	 * @param outroTipo
	 * @return O tipo interseção; <code>null</code> caso não haja interseção.
	 */
	public abstract Tipo intersecao(Tipo outroTipo);

}
