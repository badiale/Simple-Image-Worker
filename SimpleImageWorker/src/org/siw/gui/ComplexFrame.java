package org.siw.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import org.siw.image.transformations.ConvolutionFilter;
import org.siw.image.transformations.ConvolutionGaussian;
import org.siw.image.transformations.FastFourierTransform;
import org.siw.image.transformations.FourierTransform;
import org.siw.util.ComplexNumber;

public class ComplexFrame extends JFrame {
	private static final long serialVersionUID = -6548421362146772446L;

	private JMenuBar menubar;
	
	private JMenu mnTransformacoes;
	private JMenuItem mntmDesfazer;
	
	private JMenu mnFiltros;
	private JMenuItem mntmGaussiano;
	
	private ButtonGroup btngrpViews;
	private JMenu mnViews;
	private JRadioButtonMenuItem mntmViewAbs;
	private JRadioButtonMenuItem mntmViewAbsLog;
	private JRadioButtonMenuItem mntmViewReal;
	private JRadioButtonMenuItem mntmViewRealLog;
	private JRadioButtonMenuItem mntmViewImg;
	private JRadioButtonMenuItem mntmViewImgLog;
	
	private ComplexPanel cplxPanel;
	
	public ComplexFrame(String titulo, ComplexNumber[][][] matrix, int imgType) {
		super(titulo);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// menu
		menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		// operacoes
		mnTransformacoes = new JMenu("Transformações");
		mntmDesfazer = new JMenuItem("Desfazer...");
		
		menubar.add(mnTransformacoes);
		mnTransformacoes.add(mntmDesfazer);
		
		mntmDesfazer.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmDesfazerAction(arg0); }});
		
		// filtros
		mnFiltros = new JMenu("Filtros");
		mntmGaussiano = new JMenuItem("Filtro gaussiano...");
		
		menubar.add(mnFiltros);
		mnFiltros.add(mntmGaussiano);
		
		mntmGaussiano.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmGaussianoAction(arg0); }});
		
		// visualizacoes
		mnViews = new JMenu("Visualizações");
		mntmViewAbs = new JRadioButtonMenuItem("Valor absoluto");
		mntmViewAbsLog = new JRadioButtonMenuItem("Valor absoluto + log");
		mntmViewReal = new JRadioButtonMenuItem("Valor real");
		mntmViewRealLog = new JRadioButtonMenuItem("Valor real + log");
		mntmViewImg = new JRadioButtonMenuItem("Valor imaginário");
		mntmViewImgLog = new JRadioButtonMenuItem("Valor imaginário + log");
		
		btngrpViews = new ButtonGroup();
		btngrpViews.add(mntmViewAbs);
		mntmViewAbs.setSelected(true);
		btngrpViews.add(mntmViewAbsLog);
		btngrpViews.add(mntmViewReal);
		btngrpViews.add(mntmViewRealLog);
		btngrpViews.add(mntmViewImg);
		btngrpViews.add(mntmViewImgLog);
		menubar.add(mnViews);
		mnViews.add(mntmViewAbs);
		mnViews.add(mntmViewAbsLog);
		mnViews.add(mntmViewReal);
		mnViews.add(mntmViewRealLog);
		mnViews.add(mntmViewImg);
		mnViews.add(mntmViewImgLog);
		
		mntmViewAbs.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmViewAbsAction(arg0); }});
		mntmViewAbsLog.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmViewAbsLogAction(arg0); }});
		mntmViewReal.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmViewRealAction(arg0); }});
		mntmViewRealLog.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmViewRealLogAction(arg0); }});
		mntmViewImg.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmViewImgAction(arg0); }});
		mntmViewImgLog.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmViewImgLogAction(arg0); }});
		
		
		// numeros complexos
		cplxPanel = new ComplexPanel(matrix, imgType);
		add(cplxPanel);
		
		pack();
		setLocationRelativeTo(null);
	}

	protected void mntmDesfazerAction(ActionEvent arg0) {
		BufferedImage img = new BufferedImage(cplxPanel.getMatrixWidth(), cplxPanel.getMatrixHeight(), cplxPanel.getImageType() );
		
		FourierTransform ft = new FastFourierTransform();
		ft.revert(img, cplxPanel.getMatrix());
		
		try {
			new ImageFrame(img).setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Não foi possível desfazer a transformada.", "Erro.", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void mntmGaussianoAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("sigma", 30.0);
		
		ImageOptions opt = new ImageOptions(this, "Convolução com gaussiano", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			ConvolutionFilter cf = new ConvolutionGaussian(data.get("sigma"), cplxPanel.getMatrixWidth(), cplxPanel.getMatrixHeight());
			cf.execute(cplxPanel.getMatrix());
			
			cplxPanel.repaint();
		}
	}

	protected void mntmViewAbsAction(ActionEvent arg0) {
		cplxPanel.setDrawType(ComplexPanel.DRAW_ABS);
	}

	protected void mntmViewAbsLogAction(ActionEvent arg0) {
		cplxPanel.setDrawType(ComplexPanel.DRAW_ABS_LOG);
	}

	protected void mntmViewRealAction(ActionEvent arg0) {
		cplxPanel.setDrawType(ComplexPanel.DRAW_REAL);
	}

	protected void mntmViewRealLogAction(ActionEvent arg0) {
		cplxPanel.setDrawType(ComplexPanel.DRAW_REAL_LOG);
	}

	protected void mntmViewImgAction(ActionEvent arg0) {
		cplxPanel.setDrawType(ComplexPanel.DRAW_IMG);
	}

	protected void mntmViewImgLogAction(ActionEvent arg0) {
		cplxPanel.setDrawType(ComplexPanel.DRAW_IMG_LOG);
	}
}
