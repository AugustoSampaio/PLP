package plp.web;

public class SourceRange {
  private int startLine;
  private int startColumn;
  private int endLine;
  private int endColumn;

  public SourceRange() {}

  public SourceRange(int startLine, int startColumn, int endLine, int endColumn) {
    this.startLine = startLine;
    this.startColumn = startColumn;
    this.endLine = endLine;
    this.endColumn = endColumn;
  }

  public int getStartLine() { return startLine; }
  public int getStartColumn() { return startColumn; }
  public int getEndLine() { return endLine; }
  public int getEndColumn() { return endColumn; }

  public void setStartLine(int startLine) { this.startLine = startLine; }
  public void setStartColumn(int startColumn) { this.startColumn = startColumn; }
  public void setEndLine(int endLine) { this.endLine = endLine; }
  public void setEndColumn(int endColumn) { this.endColumn = endColumn; }
}
