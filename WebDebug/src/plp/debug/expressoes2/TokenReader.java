package plp.debug.expressoes2;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import le2.plp.expressions2.parser.Exp2Parser;
import le2.plp.expressions2.parser.Token;
import plp.debug.core.SourceToken;

/**
 * Relê o código-fonte usando o lexer real de Expressoes2 (gerado pelo próprio
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
			// Parser estático: um segundo construtor é rejeitado pelo JavaCC,
			// então reinicializamos o lexer já existente.
			Exp2Parser.ReInit(new ByteArrayInputStream(source.getBytes()));
			Token token = Exp2Parser.token_source.getNextToken();
			while (token != null && token.kind != 0) {
				out.add(new SourceToken(token.image, token.beginLine, token.beginColumn,
						token.endLine, token.endColumn));
				token = Exp2Parser.token_source.getNextToken();
			}
		} catch (Throwable ignored) {
			// Falha ao relexar não deve impedir a execução: o debugger
			// simplesmente fica sem faixas de código-fonte.
		}
		return out;
	}
}
