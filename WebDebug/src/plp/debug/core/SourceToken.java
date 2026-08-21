package plp.debug.core;

/**
 * Um token do código-fonte com sua posição, independente de linguagem.
 * Produzido pelos TokenReader de cada linguagem a partir do lexer real
 * (gerado pelo próprio .jj daquela linguagem, sem qualquer alteração nele).
 */
public final class SourceToken {

	private final String image;
	private final int beginLine;
	private final int beginColumn;
	private final int endLine;
	private final int endColumn;

	public SourceToken(String image, int beginLine, int beginColumn, int endLine, int endColumn) {
		this.image = image;
		this.beginLine = beginLine;
		this.beginColumn = beginColumn;
		this.endLine = endLine;
		this.endColumn = endColumn;
	}

	public String getImage() {
		return image;
	}

	public int getBeginLine() {
		return beginLine;
	}

	public int getBeginColumn() {
		return beginColumn;
	}

	public int getEndLine() {
		return endLine;
	}

	public int getEndColumn() {
		return endColumn;
	}
}
