package plp.web;

import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;

public interface PlpResult extends JSObject {
  @JSProperty
  boolean isSuccess();

  @JSProperty
  String getOutput();

  @JSProperty
  String getMessage();

  @JSProperty
  String getCompilationEnv();
}
