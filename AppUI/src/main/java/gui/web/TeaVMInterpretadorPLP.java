package gui.web;

import org.teavm.jso.dom.html.*;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.browser.Window;
import org.teavm.jso.JSBody;

import gui.MultiInterpretador;

public class TeaVMInterpretadorPLP {

    public static void main(String[] args) {
        new TeaVMInterpretadorPLP().init();
    }
    public void init() {
        HTMLTextAreaElement codigo = (HTMLTextAreaElement) Window.current().getDocument().getElementById("codigo");
        HTMLTextAreaElement mensagens = (HTMLTextAreaElement) Window.current().getDocument().getElementById("mensagens");
        HTMLInputElement entrada = (HTMLInputElement) Window.current().getDocument().getElementById("entrada");
        HTMLSelectElement linguagens = (HTMLSelectElement) Window.current().getDocument().getElementById("linguagens");
        HTMLButtonElement btn = (HTMLButtonElement) Window.current().getDocument().getElementById("executar");

        MultiInterpretador interpreter = new MultiInterpretador(new MultiInterpretador.MessageBoard() {
            public void setText(String text) { mensagens.setValue(text); }
            public void append(String text) { mensagens.setValue(mensagens.getValue() + text); }
        });

        linguagens.addEventListener("change", evt -> {
            int index = linguagens.getSelectedIndex();            
            entrada.setDisabled(index <= 3);
        });

        btn.addEventListener("click", evt -> {
            String src = codigo.getValue();
            String input = entrada.getValue();
            int lang = linguagens.getSelectedIndex();
            interpreter.interpretarCodigo(src, input, lang);
        });
    }
}
