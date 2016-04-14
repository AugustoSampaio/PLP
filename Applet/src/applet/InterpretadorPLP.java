package applet;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InterpretadorPLP extends JFrame {

	private static final long serialVersionUID = -7647474898074642286L;
	private static final Font COURIER_NEW = new Font("Courier New", Font.PLAIN,
			12);

	private JPanel jContentPane = null;
	JTextArea jTextAreaCodigo = null;
	private JScrollPane jScrollPaneMensagens = null;
	JTextArea jTextAreaMensagens = null;
	private JLabel jLabelCodigo = null;
	private JLabel jLabelMasg = null;
	private JScrollPane jScrollPaneCodigo = null;
	JComboBox jComboBoxLinguagens = null;
	private JLabel jLabelExecutar = null;

	MultiInterpretador interpreter;

	private JButton jButtonExecutar = null;
	JTextField jTextFieldListaEntrada = null;
	private JLabel jLabelListaEntrada = null;
	private InterpreterKeyListener listener;

	/**
	 * This is the default constructor
	 */
	public InterpretadorPLP() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("Interpretador PLP V 0.3");
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.listener = new InterpreterKeyListener(this);
		this.addKeyListener(this.listener);
		this.jTextAreaCodigo.addKeyListener(this.listener);
		this.jTextAreaMensagens.addKeyListener(this.listener);
		this.jTextFieldListaEntrada.addKeyListener(this.listener);
		this.jTextFieldListaEntrada.setEnabled(false);

		interpreter = new MultiInterpretador(this.jTextAreaMensagens);
		Dimension d;
		int w, h;

		w = 390;
		h = 480;

		d = Toolkit.getDefaultToolkit().getScreenSize();
		d.height /= 2;
		d.width /= 2;
		d.height -= h / 2 + 15;
		d.width -= w / 2;

		this.setBounds(d.width, d.height, w, h);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelListaEntrada = new JLabel();
			jLabelListaEntrada.setBounds(new java.awt.Rectangle(20, 194, 127,
					20));
			jLabelListaEntrada
					.setToolTipText("informe os valores da lista de entrada separados por espaços");
			jLabelListaEntrada.setText("Lista de Entrada");
			jLabelExecutar = new JLabel();
			jLabelExecutar.setBounds(new java.awt.Rectangle(19, 434, 157, 17));
			jLabelExecutar.setText("Pressione F1 para executar");
			jLabelMasg = new JLabel();
			jLabelMasg.setBounds(new java.awt.Rectangle(20, 245, 80, 16));
			jLabelMasg.setText("Mensagens");
			jLabelCodigo = new JLabel();
			jLabelCodigo.setBounds(new java.awt.Rectangle(20, 33, 70, 16));
			jLabelCodigo.setText("Código");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPaneMensagens(), null);
			jContentPane.add(jLabelCodigo, null);
			jContentPane.add(jLabelMasg, null);
			jContentPane.add(getJScrollPaneCodigo(), null);
			jContentPane.add(getJTextFieldListaEntrada(), null);
			jContentPane.add(getJComboBoxLinguagens(), null);
			jContentPane.add(jLabelExecutar, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(jLabelListaEntrada, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextAreaCodigo
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextAreaCodigo() {
		if (jTextAreaCodigo == null) {
			jTextAreaCodigo = new JTextArea();
			jTextAreaCodigo.setFont(COURIER_NEW);
			jTextAreaCodigo.setTabSize(2);
			UndoUtil.registerUndoManager(jTextAreaCodigo);
		}
		return jTextAreaCodigo;
	}

	/**
	 * This method initializes jScrollPaneMensagens
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneMensagens() {
		if (jScrollPaneMensagens == null) {
			jScrollPaneMensagens = new JScrollPane();
			jScrollPaneMensagens.setBounds(new java.awt.Rectangle(20, 267, 350,
					160));
			jScrollPaneMensagens.setViewportView(getJTextAreaMensagens());
		}
		return jScrollPaneMensagens;
	}

	/**
	 * This method initializes jTextAreaMensagens
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextAreaMensagens() {
		if (jTextAreaMensagens == null) {
			jTextAreaMensagens = new JTextArea();
		}
		return jTextAreaMensagens;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneCodigo() {
		if (jScrollPaneCodigo == null) {
			jScrollPaneCodigo = new JScrollPane();
			jScrollPaneCodigo
					.setBounds(new java.awt.Rectangle(20, 52, 350, 134));
			jScrollPaneCodigo.setViewportView(getJTextAreaCodigo());
		}
		return jScrollPaneCodigo;
	}

	/**
	 * This method initializes jComboBoxLingaugens
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxLinguagens() {
		if (jComboBoxLinguagens == null) {
			jComboBoxLinguagens = new JComboBox();
			jComboBoxLinguagens
					.setBounds(new java.awt.Rectangle(19, 7, 250, 20));
			jComboBoxLinguagens
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							switch (jComboBoxLinguagens.getSelectedIndex()) {
							case 0:
							case 1:
							case 2:
							case 3:
								jTextFieldListaEntrada.setEnabled(false);
								break;
							default:
								jTextFieldListaEntrada.setEnabled(true);
								break;
							}
						}
					});
			jComboBoxLinguagens.addItem("Expressoes 1");
			jComboBoxLinguagens.addItem("Expressoes 2");
			jComboBoxLinguagens.addItem("Funcional 1");
			jComboBoxLinguagens.addItem("Funcional 2");
			jComboBoxLinguagens.addItem("Funcional 3");
			jComboBoxLinguagens.addItem("Imperativa 1");
			jComboBoxLinguagens.addItem("Imperativa 2");
			jComboBoxLinguagens.addItem("Orientada a Objetos 1");
			jComboBoxLinguagens.addItem("Orientada a Objetos 2");			

		}
		return jComboBoxLinguagens;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButtonExecutar == null) {
			jButtonExecutar = new JButton();
			jButtonExecutar.setBounds(new java.awt.Rectangle(283, 8, 86, 19));
			jButtonExecutar.setText("executar");
			jButtonExecutar
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String sourceCode = jTextAreaCodigo.getText();
							String listaEntrada = jTextFieldListaEntrada
									.getText();
							interpreter.interpretarCodigo(sourceCode,
									listaEntrada, jComboBoxLinguagens
											.getSelectedIndex());
						}
					});
		}
		return jButtonExecutar;
	}

	/**
	 * This method initializes jTextFieldListaEntrada
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldListaEntrada() {
		if (jTextFieldListaEntrada == null) {
			jTextFieldListaEntrada = new JTextField();
			jTextFieldListaEntrada.setBounds(new java.awt.Rectangle(20, 218,
					350, 20));
			jTextFieldListaEntrada
					.setToolTipText("informe os valores da lista de entrada separados por espaços");
		}
		return jTextFieldListaEntrada;
	}

	public static void main(String[] args) {
		(new InterpretadorPLP()).setVisible(true);
	}

} // @jve:decl-index=0:visual-constraint="10,10"

class InterpreterKeyListener implements KeyListener {

	InterpretadorPLP frame;

	public InterpreterKeyListener(InterpretadorPLP frm) {
		super();
		this.frame = frm;
	}

	public void keyPressed(java.awt.event.KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F1) {
			String sourceCode = this.frame.jTextAreaCodigo.getText();
			String listaEntrada = this.frame.jTextFieldListaEntrada.getText();
			this.frame.interpreter.interpretarCodigo(sourceCode, listaEntrada,
					this.frame.jComboBoxLinguagens.getSelectedIndex());
		}
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}
}
