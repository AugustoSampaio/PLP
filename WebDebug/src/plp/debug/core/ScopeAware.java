package plp.debug.core;

/**
 * Implementado pelos ambientes de compilação do WebDebug. Permite que um nó
 * da AST (subclasse Debug construída pelo parser do WebDebug) publique o
 * escopo que acabou de abrir, com sua faixa exata de código-fonte.
 *
 * As classes das linguagens não conhecem esta interface: os nós que a usam
 * vivem inteiramente no WebDebug.
 */
public interface ScopeAware {

	/** Registra o escopo aberto pelo último incrementa(). */
	void registraEscopo(InfoEscopo info);

	/**
	 * Reregistra um binding já existente no escopo corrente, atualizando seu
	 * texto de exibição.
	 *
	 * Necessário nas linguagens funcionais: um parâmetro formal é vinculado
	 * como {@code TipoPolimorfico} ANTES de o corpo da função ser verificado,
	 * quando seu nome ainda é "?". Depois da unificação o mesmo objeto passa a
	 * conhecer o tipo inferido, então o nó da AST reregistra o binding para
	 * que o debugger mostre o tipo concreto em vez de "?".
	 *
	 * Só altera o snapshot do debugger — o ambiente real não é modificado.
	 */
	default void registraBinding(Object idArg, Object valorArg, String display) {
	}
}
