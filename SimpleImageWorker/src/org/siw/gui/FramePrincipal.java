package org.siw.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

public class FramePrincipal extends JFrame {
	public FramePrincipal() throws Exception {
		setTitle("Simple Image Worker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenuItem mntmCarregar = new JMenuItem("Carregar");
		mnArquivo.add(mntmCarregar);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		ImagePanel image = new ImagePanel("testes/lena.big.png");
		scrollPane.setViewportView(image);
	}
	
	public static void main (String[] args) throws Exception {
		JFrame principal = new FramePrincipal();
		principal.pack();
		principal.setVisible(true);
	}
}