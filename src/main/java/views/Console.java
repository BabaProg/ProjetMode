package views;

import javafx.scene.control.TextArea;

public class Console {
    protected TextArea textArea;

    public Console(TextArea textArea) {
        this.textArea = textArea;
    }

    public void println(String msg) {
        textArea.appendText(msg + "\n");
        textArea.setScrollTop(Double.MAX_VALUE);
    }

    public void clear() {
        textArea.clear();
    }
}
