package plp.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Representa uma foto serializável do estado da compilação para uma linguagem
 * específica. Um snapshot reúne todos os frames capturados naquele momento,
 * permitindo que a interface mostre os escopos ativos e seus bindings.
 */
public class CompilationSnapshot {
  /**
   * Identificador da linguagem associada ao snapshot.
   */
  private String languageId;

  /**
   * Lista de frames capturados durante a compilação.
   */
  private List<Frame> frames = new ArrayList<Frame>();

  /**
   * Cria um snapshot de compilação vazio.
   */
  public CompilationSnapshot() {}

  /**
   * Cria um snapshot de compilação para a linguagem informada.
   *
   * @param languageId identificador da linguagem.
   */
  public CompilationSnapshot(String languageId) {
    this.languageId = languageId;
  }

  /**
   * Retorna o identificador da linguagem.
   *
   * @return identificador da linguagem.
   */
  public String getLanguageId() { return languageId; }

  /**
   * Define o identificador da linguagem.
   *
   * @param languageId identificador da linguagem.
   */
  public void setLanguageId(String languageId) { this.languageId = languageId; }

  /**
   * Retorna os frames do snapshot.
   *
   * @return lista de frames.
   */
  public List<Frame> getFrames() { return frames; }

  /**
   * Define a lista de frames do snapshot.
   *
   * @param frames lista de frames.
   */
  public void setFrames(List<Frame> frames) { this.frames = frames; }

  /**
   * Converte uma estrutura genérica de snapshot em um snapshot tipado.
   *
   * @param languageId identificador da linguagem.
   * @param generic lista genérica de frames.
   * @return snapshot de compilação convertido.
   */
  @SuppressWarnings({"unchecked","rawtypes"})
  public static CompilationSnapshot fromGenericSnapshot(String languageId, List<? extends Map<String, ?>> generic) {
    CompilationSnapshot cs = new CompilationSnapshot(languageId);
    if (generic == null) return cs;
    for (Map<String, ?> frame : generic) {
      Frame f = new Frame();
      Object b = frame.get("bindings");
      if (b instanceof Map) {
        f.setBindings((Map) b);
      }
      Object scope = frame.get("scope");
      if (scope instanceof String) {
        f.setScope((String) scope);
      }
      // O campo name é opcional e só aparece quando a linguagem o publica.
      Object name = frame.get("name");
      if (name instanceof String) {
        f.setName((String) name);
      }
      Object range = frame.get("sourceRange");
      if (range instanceof Map) {
        Map<String,Number> r = (Map<String,Number>) range;
        int sl = r.getOrDefault("startLine", 0).intValue();
        int sc = r.getOrDefault("startColumn", 0).intValue();
        int el = r.getOrDefault("endLine", 0).intValue();
        int ec = r.getOrDefault("endColumn", 0).intValue();
        f.setSourceRange(new SourceRange(sl, sc, el, ec));
      }
      cs.getFrames().add(f);
    }
    return cs;
  }
}
