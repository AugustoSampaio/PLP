package plp.debug.objetos2;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import loo2.plp.orientadaObjetos2.parser.OO2Parser;
import loo2.plp.orientadaObjetos2.parser.Token;
import plp.debug.core.SourceToken;

/**
 * Relê o código-fonte usando o lexer real de Objetos2 (gerado pelo próprio
 * .jj daquela linguagem, que não é alterado) para obter as posições dos
 * tokens. Usado apenas para localizar faixas de escopo no debugger.
 */
public final class TokenReader {

	private TokenReader() {
	}

	public static List<SourceToken> read(String source) {
		List<SourceToken> out = new ArrayList<SourceToken>();
		if (source == null) {
			return out;
		}
		try {
			OO2Parser parser = new OO2Parser(new ByteArrayInputStream(source.getBytes()));
			Token token = parser.token_source.getNextToken();
			while (token != null && token.kind != 0) {
				out.add(new SourceToken(token.image, token.beginLine, token.beginColumn,
						token.endLine, token.endColumn));
				token = parser.token_source.getNextToken();
			}
		} catch (Throwable ignored) {
			// Falha ao relexar não deve impedir a execução: o debugger
			// simplesmente fica sem faixas de código-fonte.
		}
		return out;
	}
}
