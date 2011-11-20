package org.siw.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

public class FilterDialog extends JDialog {
	private static final long serialVersionUID = -1533010430347484624L;

	private double[][] matrix;
	
	private JPanel panMatrix;
	private JFormattedTextField[][] text;
	private JButton btnOk;
	private JButton btnCancelar;
	private JPanel panButtons;

	private boolean cancel;
	
	public FilterDialog (JFrame pai, String titulo, double[][] matrix) {
		super(pai, titulo, true);
		
		int height = matrix.length;
		int width = matrix[0].length; 
		this.matrix = matrix;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		panMatrix = new JPanel();
		panMatrix.setLayout(new GridLayout(height, width, 10, 10));
		add(panMatrix, BorderLayout.CENTER);
		text = new JFormattedTextField[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				text[y][x] = new JFormattedTextField(new NumberFormatter(NumberFormat.getNumberInstance()));
				text[y][x].setValue(matrix[y][x]);
				panMatrix.add(text[y][x]);
			}
		}
		
		panButtons = new JPanel();
		btnOk = new JButton("OK");
		btnCancelar = new JButton("Cancelar");
		
		btnOk.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { btnOkAction(arg0); }});
		btnCancelar.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { btnCancelarAction(arg0); }});
		
		add(panButtons, BorderLayout.SOUTH);
		panButtons.add(btnOk);
		panButtons.add(btnCancelar);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				btnCancelarAction(null);
			}
		});
		pack();
		setLocationRelativeTo(pai);
	}
	

	protected void btnCancelarAction(ActionEvent arg0) {
		this.cancel = true;
		this.dispose();
	}

	protected void btnOkAction(ActionEvent arg0) {
		this.cancel = false;

		int height = matrix.length;
		int width = matrix[0].length;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				matrix[y][x] = ((Number) text[y][x].getValue()).doubleValue();
			}
		}
		
		this.dispose();
	}
	
	public boolean wasCanceled() { return this.cancel; }
}
