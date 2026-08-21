package plp.debug.imperativa1;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import li1.plp.imperative1.parser.Imp1Parser;
import li1.plp.imperative1.parser.Token;
import plp.debug.core.SourceToken;

/**
 * Relê o código-fonte usando o lexer real de Imperativa1 (gerado pelo próprio
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
			Imp1Parser.ReInit(new ByteArrayInputStream(source.getBytes()));
			Token token = Imp1Parser.token_source.getNextToken();
			while (token != null && token.kind != 0) {
				out.add(new SourceToken(token.image, token.beginLine, token.beginColumn,
						token.endLine, token.endColumn));
				token = Imp1Parser.token_source.getNextToken();
			}
		} catch (Throwable ignored) {
			// Falha ao relexar não deve impedir a execução: o debugger
			// simplesmente fica sem faixas de código-fonte.
		}
		return out;
	}
}
