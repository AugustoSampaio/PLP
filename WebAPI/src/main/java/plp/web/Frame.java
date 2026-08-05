package plp.web;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Representa um frame individual dentro de um snapshot. Cada frame corresponde
 * a um nível de escopo e concentra os bindings declarados naquele contexto,
 * além de metadados opcionais como a faixa de origem do trecho analisado.
 */
public class Frame {

  /**
   * Nome do frame.
   */
  private String name;

  /**
   * Tipo de escopo representado por este frame (e.g. "let-in", "block",
   * "procedure", "class", "constructor").
   */
  private String scope;

  /**
   * Associações de bindings presentes neste frame.
   */
  private Map<String, Object> bindings = new LinkedHashMap<String,Object>();

  /**
   * Faixa de origem associada ao frame.
   */
  private SourceRange sourceRange;

  /**
   * Cria um frame vazio.
   */
  public Frame() {}

  /**
   * Retorna o nome do frame.
   *
   * @return nome do frame.
   */
  public String getName() { return name; }

  /**
   * Define o nome do frame.
   *
   * @param name nome do frame.
   */
  public void setName(String name) { this.name = name; }

  /**
   * Retorna o tipo de escopo deste frame.
   *
   * @return tipo de escopo.
   */
  public String getScope() { return scope; }

  /**
   * Define o tipo de escopo deste frame.
   *
   * @param scope tipo de escopo (e.g. "let-in", "block", "procedure", "class").
   */
  public void setScope(String scope) { this.scope = scope; }

  /**
   * Retorna os bindings do frame.
   *
   * @return mapa de bindings.
   */
  public Map<String,Object> getBindings() { return bindings; }

  /**
   * Define os bindings do frame.
   *
   * @param bindings mapa de bindings.
   */
  public void setBindings(Map<String,Object> bindings) { this.bindings = bindings; }

  /**
   * Retorna a faixa de origem associada ao frame.
   *
   * @return faixa de origem.
   */
  public SourceRange getSourceRange() { return sourceRange; }

  /**
   * Define a faixa de origem associada ao frame.
   *
   * @param sourceRange faixa de origem.
   */
  public void setSourceRange(SourceRange sourceRange) { this.sourceRange = sourceRange; }
}
