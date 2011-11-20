package org.siw.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

public class ImageOptions extends JDialog {
	private static final long serialVersionUID = -2592993946849824208L;
	
	private JPanel panItems;
	private JButton btnOk;
	private JButton btnCancelar;
	private JPanel panButtons;
	
	private HashMap<String, Double> data;
	private HashMap<String, JFormattedTextField> map;

	public ImageOptions(JFrame pai, String title, HashMap<String, Double> data) {
		super(pai, title, true);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		panItems = new JPanel();
		panItems.setLayout(new GridLayout(1,2, 10, 10));
		add(panItems, BorderLayout.CENTER);
		
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
		
		this.data = data;
		this.map = new HashMap<String, JFormattedTextField>();
		
		Set<String> keys = data.keySet();
		Iterator<String> it = keys.iterator();
		
		while (it.hasNext()) {
			add(it.next());
		}
		
		setLocationRelativeTo(pai);
	}
	
	public void add(String key) {
		//data.put(key, new Double(0));
		
		JLabel label = new JLabel(key);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		JFormattedTextField text = new JFormattedTextField(new NumberFormatter(NumberFormat.getNumberInstance()));
		text.setValue(data.get(key));
		
		panItems.add(label);
		panItems.add(text);
		map.put(key, text);
		
		GridLayout layout = (GridLayout) panItems.getLayout();
		layout.setRows(data.size());
		
		pack();
	}
	
	protected void btnCancelarAction(ActionEvent arg0) {
		this.data.clear();
		this.dispose();
	}

	protected void btnOkAction(ActionEvent arg0) {
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		
		while (it.hasNext()) {
			String key = it.next();
			data.put(key, ((Number) map.get(key).getValue()).doubleValue());
		}
		
		this.dispose();
	}
}
