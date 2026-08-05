package plp.web;

import org.teavm.jso.JSExport;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import le1.plp.expressions1.parser.Exp1Parser;
import le1.plp.expressions1.parser.ParseException;
import le2.plp.expressions2.parser.Exp2Parser;
import lf1.plp.functional1.parser.Func1Parser;
import lf2.plp.functional2.parser.Func2Parser;
import lf3.plp.functional3.parser.Func3Parser;
import li1.plp.imperative1.memory.ContextoCompilacaoImperativa;
import li1.plp.imperative1.memory.ContextoExecucaoImperativa;
import li1.plp.imperative1.parser.Imp1Parser;
import li2.plp.imperative2.memory.ContextoExecucaoImperativa2;
import li2.plp.imperative2.parser.Imp2Parser;
import loo1.plp.orientadaObjetos1.parser.OO1Parser;
import loo2.plp.orientadaObjetos2.parser.OO2Parser;

public final class PlpWebApi {

  public static final int EXP1  = 0;
  public static final int EXP2  = 1;
  public static final int FUNC1 = 2;
  public static final int FUNC2 = 3;
  public static final int FUNC3 = 4;
  public static final int IMP1  = 5;
  public static final int IMP2  = 6;
  public static final int OO1   = 7;
  public static final int OO2   = 8;

  private String output;
  private String message;
  private String compilationEnv;

  private static Exp1Parser  exp1Parser;
  private static Exp2Parser  exp2Parser;
  private static Func1Parser func1Parser;
  private static Func2Parser func2Parser;
  private static Func3Parser func3Parser;
  private static Imp1Parser  imp1Parser;
  private static Imp2Parser  imp2Parser;
  private static OO1Parser   oo1Parser;
  private static OO2Parser   oo2Parser;

  @JSExport
  public static PlpResult __runCode(String language, String sourceCode, String input) {
    return new PlpWebApi().interpretarCodigo(sourceCode, input, parseLanguage(language));
  }

  private PlpResult interpretarCodigo(String sourceCode, String listaEntrada, int selectedIndex) {
    output = null;
    message = null;
    compilationEnv = null;

    try {
      ByteArrayInputStream fis = new ByteArrayInputStream(
          (sourceCode == null ? "" : sourceCode).getBytes()
      );
      switch (selectedIndex) {
        case EXP1:  interpretarExp1(fis);               break;
        case EXP2:  interpretarExp2(fis);               break;
        case FUNC1: interpretarFunc1(fis);              break;
        case FUNC2: interpretarFunc2(fis);              break;
        case FUNC3: interpretarFunc3(fis);              break;
        case IMP1:  interpretarImp1(fis, listaEntrada); break;
        case IMP2:  interpretarImp2(fis, listaEntrada); break;
        case OO1:   interpretarOO1(fis, listaEntrada);  break;
        case OO2:   interpretarOO2(fis, listaEntrada);  break;
        default:    return PlpResultImpl.create(false, null, "linguagem inválida", compilationEnv);
      }
      return PlpResultImpl.create(true, output, message, compilationEnv);
    } catch (Exception e) {
      return PlpResultImpl.create(false, null, e.getMessage(), compilationEnv);
    } catch (Throwable t) {
      return PlpResultImpl.create(false, null, t.getMessage(), compilationEnv);
    }
  }

  private static int parseLanguage(String language) {
    if (language == null) return -1;
    switch (language.trim().toLowerCase()) {
      case "exp1":  return EXP1;
      case "exp2":  return EXP2;
      case "func1": return FUNC1;
      case "func2": return FUNC2;
      case "func3": return FUNC3;
      case "imp1":  return IMP1;
      case "imp2":  return IMP2;
      case "oo1":   return OO1;
      case "oo2":   return OO2;
      default:      return -1;
    }
  }

  private String compilationEnvJson(String languageId, java.util.List<? extends java.util.Map<String, ?>> frames) {
    return toJsonString(CompilationSnapshot.fromGenericSnapshot(languageId, frames));
  }

  private void interpretarExp1(InputStream fis) throws ParseException {
    if (exp1Parser == null) exp1Parser = new Exp1Parser(fis);
    else Exp1Parser.ReInit(fis);
    le1.plp.expressions1.Programa prog = Exp1Parser.Input();
    message = "sintaxe verificada com sucesso!";
    if (prog.checaTipo()) {
      compilationEnv = compilationEnvJson("exp1", prog.getAmbCompSnapshot());
      output = prog.executar().toString();
    }
    else throw new RuntimeException("erro de tipos!");
  }

  private void interpretarExp2(InputStream fis) throws Exception {
    if (exp2Parser == null) exp2Parser = new Exp2Parser(fis);
    else Exp2Parser.ReInit(fis);
    le2.plp.expressions2.Programa prog = Exp2Parser.Input();
    message = "sintaxe verificada com sucesso!";
    if (prog.checaTipo()) {
      compilationEnv = compilationEnvJson("exp2", prog.getAmbCompSnapshot());
      output = prog.executar().toString();
    }
    else throw new RuntimeException("erro de tipos!");
  }

  private void interpretarFunc1(InputStream fis) throws Exception {
    if (func1Parser == null) func1Parser = new Func1Parser(fis);
    else Func1Parser.ReInit(fis);
    lf1.plp.functional1.Programa prog = Func1Parser.Input();
    message = "sintaxe verificada com sucesso!";
    if (prog.checaTipo()) {
      compilationEnv = compilationEnvJson("func1", prog.getAmbCompSnapshot());
      output = prog.executar().toString();
    }
    else throw new RuntimeException("erro de tipos!");
  }

  private void interpretarFunc2(InputStream fis) throws Exception {
    if (func2Parser == null) func2Parser = new Func2Parser(fis);
    else Func2Parser.ReInit(fis);
    lf2.plp.functional2.Programa prog = Func2Parser.Input();
    message = "sintaxe verificada com sucesso!";
    if (prog.checaTipo()) {
      compilationEnv = compilationEnvJson("func2", prog.getAmbCompSnapshot());
      output = prog.executar().toString();
    }
    else throw new RuntimeException("erro de tipos!");
  }

  private void interpretarFunc3(InputStream fis) throws Exception {
    if (func3Parser == null) func3Parser = new Func3Parser(fis);
    else Func3Parser.ReInit(fis);
    lf3.plp.functional3.Programa prog = Func3Parser.Input();
    message = "sintaxe verificada com sucesso!";
    if (prog.checaTipo()) {
      // convert Func3's generic snapshot to a standardized CompilationSnapshot
      java.util.List<java.util.Map<String,Object>> generic = prog.getAmbCompSnapshot();
      CompilationSnapshot cs = CompilationSnapshot.fromGenericSnapshot("func3", generic);
      compilationEnv = toJsonString(cs);
      output = prog.executar().toString();
    } else {
      throw new RuntimeException("erro de tipos!");
    }
  }

  private void interpretarImp1(InputStream fis, String entradaStr) throws Exception {
    if (imp1Parser == null) imp1Parser = new Imp1Parser(fis);
    else Imp1Parser.ReInit(fis);
    li1.plp.imperative1.Programa prog = Imp1Parser.Input();
    message = "sintaxe verificada com sucesso!";
    li1.plp.imperative1.memory.ListaValor entrada = obterListaEntradaImp1(entradaStr);
    if (prog.checaTipo(new ContextoCompilacaoImperativa(entrada))) {
      compilationEnv = compilationEnvJson("imp1", prog.getAmbCompSnapshot());
      output = prog.executar(new ContextoExecucaoImperativa(entrada)).toString();
    }
    else throw new RuntimeException("erro de tipos!");
  }

  private void interpretarImp2(InputStream fis, String entradaStr) throws Exception {
    if (imp2Parser == null) imp2Parser = new Imp2Parser(fis);
    else Imp2Parser.ReInit(fis);
    li2.plp.imperative2.Programa prog = Imp2Parser.Input();
    message = "sintaxe verificada com sucesso!";
    li2.plp.imperative1.memory.ListaValor entrada = obterListaEntradaImp2(entradaStr);
    if (prog.checaTipo(new li2.plp.imperative1.memory.ContextoCompilacaoImperativa(entrada))) {
      compilationEnv = compilationEnvJson("imp2", prog.getAmbCompSnapshot());
      output = prog.executar(new ContextoExecucaoImperativa2(entrada)).toString();
    }
    else throw new RuntimeException("erro de tipos!");
  }

  private void interpretarOO1(InputStream fis, String entradaStr) throws Exception {
    if (oo1Parser == null) oo1Parser = new OO1Parser(fis);
    else oo1Parser.ReInit(fis);
    loo1.plp.orientadaObjetos1.Programa prog = oo1Parser.processaEntrada();
    message = "sintaxe verificada com sucesso!";
    loo1.plp.orientadaObjetos1.memoria.colecao.ListaValor entrada = obterListaEntradaOO1(entradaStr);
    if (prog.checaTipo(new loo1.plp.orientadaObjetos1.memoria.ContextoCompilacaoOO1(entrada))) {
      compilationEnv = compilationEnvJson("oo1", prog.getAmbCompSnapshot());
      output = prog.executar(new loo1.plp.orientadaObjetos1.memoria.ContextoExecucaoOO1(entrada)).toString();
    }
    else throw new RuntimeException("erro de tipos!");
  }

  private void interpretarOO2(InputStream fis, String entradaStr) throws Exception {
    if (oo2Parser == null) oo2Parser = new OO2Parser(fis);
    else oo2Parser.ReInit(fis);
    loo2.plp.orientadaObjetos2.Programa prog = oo2Parser.processaEntrada();
    message = "sintaxe verificada com sucesso!";
    loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor entrada = obterListaEntradaOO2(entradaStr);
    if (prog.checaTipo(new loo2.plp.orientadaObjetos2.memoria.ContextoCompilacaoOO2(entrada))) {
      compilationEnv = compilationEnvJson("oo2", prog.getAmbCompSnapshot());
      output = prog.executar(new loo2.plp.orientadaObjetos2.memoria.ContextoExecucaoOO2(entrada)).toString();
    }
    else throw new RuntimeException("erro de tipos!");
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private li1.plp.imperative1.memory.ListaValor obterListaEntradaImp1(String texto) {
    List valores = new LinkedList<li1.plp.expressions2.expression.ValorConcreto>();
    StringTokenizer parser = new StringTokenizer(texto == null ? "" : texto);
    while (parser.hasMoreTokens()) {
      String p = parser.nextToken();
      try { valores.add(new li1.plp.expressions2.expression.ValorInteiro(Integer.parseInt(p))); continue; }
      catch (NumberFormatException ignored) {}
      if (p.equalsIgnoreCase("true") || p.equalsIgnoreCase("false"))
        valores.add(new li1.plp.expressions2.expression.ValorBooleano(Boolean.parseBoolean(p)));
      else
        valores.add(new li1.plp.expressions2.expression.ValorString(p));
    }
    return Imp1Parser.criaListaValor(valores);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private li2.plp.imperative1.memory.ListaValor obterListaEntradaImp2(String texto) {
    List valores = new LinkedList<li2.plp.expressions2.expression.ValorConcreto>();
    StringTokenizer parser = new StringTokenizer(texto == null ? "" : texto);
    while (parser.hasMoreTokens()) {
      String p = parser.nextToken();
      try { valores.add(new li2.plp.expressions2.expression.ValorInteiro(Integer.parseInt(p))); continue; }
      catch (NumberFormatException ignored) {}
      if (p.equalsIgnoreCase("true") || p.equalsIgnoreCase("false"))
        valores.add(new li2.plp.expressions2.expression.ValorBooleano(Boolean.parseBoolean(p)));
      else
        valores.add(new li2.plp.expressions2.expression.ValorString(p));
    }
    return Imp2Parser.criaListaValor(valores);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private loo1.plp.orientadaObjetos1.memoria.colecao.ListaValor obterListaEntradaOO1(String texto) {
    List valores = new LinkedList<loo1.plp.expressions2.expression.ValorConcreto>();
    StringTokenizer parser = new StringTokenizer(texto == null ? "" : texto);
    while (parser.hasMoreTokens()) {
      String p = parser.nextToken();
      try { valores.add(new loo1.plp.orientadaObjetos1.expressao.valor.ValorInteiro(Integer.parseInt(p))); continue; }
      catch (NumberFormatException ignored) {}
      if (p.equalsIgnoreCase("true") || p.equalsIgnoreCase("false"))
        valores.add(new loo1.plp.orientadaObjetos1.expressao.valor.ValorBooleano(Boolean.parseBoolean(p)));
      else
        valores.add(new loo1.plp.orientadaObjetos1.expressao.valor.ValorString(p));
    }
    return OO1Parser.criaListaValor(valores);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private loo2.plp.orientadaObjetos1.memoria.colecao.ListaValor obterListaEntradaOO2(String texto) {
    List valores = new LinkedList<loo2.plp.expressions2.expression.ValorConcreto>();
    StringTokenizer parser = new StringTokenizer(texto == null ? "" : texto);
    while (parser.hasMoreTokens()) {
      String p = parser.nextToken();
      try { valores.add(new loo2.plp.orientadaObjetos1.expressao.valor.ValorInteiro(Integer.parseInt(p))); continue; }
      catch (NumberFormatException ignored) {}
      if (p.equalsIgnoreCase("true") || p.equalsIgnoreCase("false"))
        valores.add(new loo2.plp.orientadaObjetos1.expressao.valor.ValorBooleano(Boolean.parseBoolean(p)));
      else
        valores.add(new loo2.plp.orientadaObjetos1.expressao.valor.ValorString(p));
    }
    return OO2Parser.criaListaValor(valores);
  }

  private String debugString(Object compilationContext) {
    try {
      Class<?> current = compilationContext.getClass();
      while (current != null) {
        try {
          Field field = current.getDeclaredField("pilha");
          field.setAccessible(true);
          Object value = field.get(compilationContext);
          return value == null ? null : value.toString();
        } catch (NoSuchFieldException ignored) {
          current = current.getSuperclass();
        }
      }
      return null;
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private String toJsonString(Object obj) {
    if (obj == null) return "null";
    if (obj instanceof String) return '"' + escapeJson((String) obj) + '"';
    if (obj instanceof Number || obj instanceof Boolean || obj instanceof Character) {
      if (obj instanceof Character) {
        return '"' + escapeJson(obj.toString()) + '"';
      }
      return obj.toString();
    }
    if (obj instanceof CompilationSnapshot) {
      CompilationSnapshot snapshot = (CompilationSnapshot) obj;
      StringBuilder sb = new StringBuilder();
      sb.append("{");
      boolean first = true;
      if (snapshot.getLanguageId() != null) {
        sb.append("\"languageId\":").append(toJsonString(snapshot.getLanguageId()));
        first = false;
      }
      if (!first) sb.append(',');
      sb.append("\"frames\":").append(toJsonString(snapshot.getFrames()));
      sb.append("}");
      return sb.toString();
    }
    if (obj instanceof Frame) {
      Frame frame = (Frame) obj;
      StringBuilder sb = new StringBuilder();
      sb.append("{");
      boolean first = true;
      if (frame.getName() != null) {
        sb.append("\"name\":").append(toJsonString(frame.getName()));
        first = false;
      }
      if (frame.getScope() != null) {
        if (!first) sb.append(',');
        sb.append("\"scope\":").append(toJsonString(frame.getScope()));
        first = false;
      }
      if (!first) sb.append(',');
      sb.append("\"bindings\":").append(toJsonString(frame.getBindings()));
      if (frame.getSourceRange() != null) {
        sb.append(',');
        sb.append("\"sourceRange\":").append(toJsonString(frame.getSourceRange()));
      }
      sb.append("}");
      return sb.toString();
    }
    if (obj instanceof SourceRange) {
      SourceRange range = (SourceRange) obj;
      StringBuilder sb = new StringBuilder();
      sb.append("{");
      sb.append("\"startLine\":").append(range.getStartLine());
      sb.append(',');
      sb.append("\"startColumn\":").append(range.getStartColumn());
      sb.append(',');
      sb.append("\"endLine\":").append(range.getEndLine());
      sb.append(',');
      sb.append("\"endColumn\":").append(range.getEndColumn());
      sb.append("}");
      return sb.toString();
    }
    if (obj instanceof Enum<?>) {
      return '"' + escapeJson(((Enum<?>) obj).name()) + '"';
    }
    if (obj instanceof java.util.Map) {
      StringBuilder sb = new StringBuilder();
      sb.append("{");
      boolean first = true;
      java.util.Map<?,?> map = (java.util.Map<?,?>) obj;
      for (java.util.Map.Entry<?,?> entry : map.entrySet()) {
        if (!first) sb.append(',');
        first = false;
        String key = entry.getKey() == null ? "null" : entry.getKey().toString();
        sb.append('"').append(escapeJson(key)).append('"').append(":");
        sb.append(toJsonString(entry.getValue()));
      }
      sb.append("}");
      return sb.toString();
    }
    if (obj instanceof java.util.Collection) {
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      boolean first = true;
      for (Object item : (java.util.Collection<?>) obj) {
        if (!first) sb.append(',');
        first = false;
        sb.append(toJsonString(item));
      }
      sb.append("]");
      return sb.toString();
    }
    if (obj.getClass().isArray()) {
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      int length = java.lang.reflect.Array.getLength(obj);
      for (int i = 0; i < length; i++) {
        if (i > 0) sb.append(',');
        sb.append(toJsonString(java.lang.reflect.Array.get(obj, i)));
      }
      sb.append("]");
      return sb.toString();
    }

    String jsonObject = toJsonObjectString(obj);
    if (jsonObject != null) return jsonObject;

    // fallback to debugString representation
    String s = debugString(obj);
    if (s == null) return "null";
    return '"' + escapeJson(s) + '"';
  }

  private String toJsonObjectString(Object obj) {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    boolean first = true;
    boolean hasField = false;

    Class<?> current = obj.getClass();
    while (current != null && current != Object.class) {
      Field[] fields = current.getDeclaredFields();
      for (Field field : fields) {
        int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;
        hasField = true;
        if (!first) sb.append(',');
        first = false;
        try {
          field.setAccessible(true);
          Object value = field.get(obj);
          sb.append('"').append(escapeJson(field.getName())).append('"').append(':');
          sb.append(toJsonString(value));
        } catch (IllegalAccessException e) {
          return null;
        }
      }
      current = current.getSuperclass();
    }

    if (!hasField) return null;
    sb.append("}");
    return sb.toString();
  }

  private String escapeJson(String s) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      switch (c) {
        case '\\': sb.append("\\\\"); break;
        case '"': sb.append("\\\""); break;
        case '\n': sb.append("\\n"); break;
        case '\r': sb.append("\\r"); break;
        case '\t': sb.append("\\t"); break;
        default:
          if (c < 0x20) {
            sb.append(String.format("\\u%04x", (int) c));
          } else sb.append(c);
      }
    }
    return sb.toString();
  }
}
