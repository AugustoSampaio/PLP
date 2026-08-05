package li2.plp.expressions2.memory;

public class SourceRange {
	private final int startLine;
	private final int startColumn;
	private final int endLine;
	private final int endColumn;

	public SourceRange(int startLine, int startColumn, int endLine, int endColumn) {
		this.startLine = startLine;
		this.startColumn = startColumn;
		this.endLine = endLine;
		this.endColumn = endColumn;
	}

	public int getStartLine() {
		return startLine;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public int getEndLine() {
		return endLine;
	}

	public int getEndColumn() {
		return endColumn;
	}
}