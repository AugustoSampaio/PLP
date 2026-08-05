package plp.web;

import org.teavm.jso.JSBody;

public abstract class PlpResultImpl implements PlpResult {

  @JSBody(params = {"success", "output", "message", "compilationEnv"}, script =
      "return { success: success, output: output, message: message, compilationEnv: compilationEnv };")
  public static native PlpResult create(boolean success, String output, String message, String compilationEnv);
}
