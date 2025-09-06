package gui.applet;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class UndoUtil {

	public static void registerUndoManager(JTextArea textArea) {
		final UndoManager undo = new UndoManager();
		Document doc = textArea.getDocument();

		// Listen for undo and redo events
		doc.addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent evt) {
				undo.addEdit(evt.getEdit());
			}
		});

		// Create an undo action and add it to the text component
		textArea.getActionMap().put("Undo", new AbstractAction("Undo") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent evt) {
				try {
					if (undo.canUndo()) {
						undo.undo();
					}
				} catch (CannotUndoException e) {
				}
			}
		});

		// Bind the undo action to ctl-Z
		textArea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");

		// Create a redo action and add it to the text component
		textArea.getActionMap().put("Redo", new AbstractAction("Redo") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent evt) {
				try {
					if (undo.canRedo()) {
						undo.redo();
					}
				} catch (CannotRedoException e) {
				}
			}
		});

		// Bind the redo action to ctl-Y
		textArea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
	}

}
