package org.siw.gui;

import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import org.siw.image.filter.Filter;
import org.siw.image.filter.Gaussian;
import org.siw.image.filter.LaplaceOfGaussian;
import org.siw.image.filter.LaplaceOfGaussianZeroCross;
import org.siw.image.filter.Mean;
import org.siw.image.filter.Median;
import org.siw.image.geometric.Crop;
import org.siw.image.geometric.Expand;
import org.siw.image.geometric.GeometricOperation;
import org.siw.image.geometric.Padd;
import org.siw.image.pointoperations.Brightness;
import org.siw.image.pointoperations.Contrast;
import org.siw.image.pointoperations.Exponential;
import org.siw.image.pointoperations.Logarithm;
import org.siw.image.pointoperations.PointOperation;
import org.siw.image.pointoperations.Threshold;
import org.siw.image.transformations.FastFourierTransform;
import org.siw.image.transformations.FourierTransform;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

public class ImageFrame extends JFrame {
	private static final long serialVersionUID = -3249878958195244978L;
	
	private JMenuBar menuBar;
	
	private JMenu mnArquivo;
	private JMenuItem mntmCarregar;
	private JMenuItem mntmRecarregar;
	private JMenuItem mntmSalvar;
	private JMenuItem mntmFechar;
	
	private JMenu mnGeometric;
	private JMenuItem mntmPadd;
	private JMenuItem mntmExpand;
	private JMenuItem mntmCrop;
	
	private JMenu mnPontos;
	private JMenuItem mntmThreshold;
	private JMenuItem mntmBrilho;
	private JMenuItem mntmContraste;
	private JMenuItem mntmLogaritmo;
	private JMenuItem mntmExponencial;
	
	private JMenu mnFiltros;
	private JMenuItem mntmFiltroCustom;
	private JMenuItem mntmGaussian;
	private JMenuItem mntmLaplace;
	private JMenuItem mntmLaplaceZero;
	private JMenuItem mntmMean;
	private JMenuItem mntmMedian;
	
	private JMenu mnTransform;
	private JMenuItem mntmFastFourier;
	
	private JScrollPane scrollPane;
	private ImagePanel image;
	
	public ImageFrame(String filename) throws Exception{
		this(new File(filename));
	}
	
	public ImageFrame(File filename) throws Exception {
		initComponents(filename);
		
		setTitle("Simple Image Worker - " + filename);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
	}
	
	public ImageFrame(BufferedImage img) throws Exception {
		initComponents(null);
		
		mntmRecarregar.setEnabled(false);
		image = new ImagePanel(img);
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(image);
		
		setTitle("Simple Image Worker");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
	}

	private void initComponents(File filename) throws Exception {
		// menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// operacoes de arquivo
		mnArquivo = new JMenu("Arquivo");
		mntmCarregar = new JMenuItem("Carregar...");
		mntmRecarregar = new JMenuItem("Recarregar");
		mntmSalvar = new JMenuItem("Salvar...");
		mntmFechar = new JMenuItem("Fechar");
		
		menuBar.add(mnArquivo);
		mnArquivo.add(mntmCarregar);
		mnArquivo.add(mntmRecarregar);
		mnArquivo.add(new JSeparator());
		mnArquivo.add(mntmSalvar);
		mnArquivo.add(new JSeparator());
		mnArquivo.add(mntmFechar);
		
		mntmCarregar.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmCarregarAction(arg0); }});
		mntmRecarregar.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmRecarregarAction(arg0); }});
		mntmSalvar.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmSalvarAction(arg0); }});
		mntmFechar.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmFecharAction(arg0); }});

		// operacoes geometricas
		mnGeometric = new JMenu("Geometria");
		mntmPadd = new JMenuItem("Padd...");
		mntmExpand = new JMenuItem("Expandir...");
		mntmCrop = new JMenuItem("Cortar...");
		
		menuBar.add(mnGeometric);
		mnGeometric.add(mntmPadd);
		mnGeometric.add(mntmExpand);
		mnGeometric.add(mntmCrop);
		
		mntmPadd.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmPaddAction(arg0); }});
		mntmExpand.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmExpandAction(arg0); }});
		mntmCrop.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmCropAction(arg0); }});
		
		// operacoes pontuais
		mnPontos = new JMenu("Operações pontuais");
		mntmThreshold = new JMenuItem("Threshold...");
		mntmBrilho = new JMenuItem("Brilho...");
		mntmContraste = new JMenuItem("Contraste...");
		mntmLogaritmo = new JMenuItem("Logarítmo...");
		mntmExponencial = new JMenuItem("Exponencial...");
		
		menuBar.add(mnPontos);
		mnPontos.add(mntmThreshold);
		mnPontos.add(mntmBrilho);
		mnPontos.add(mntmContraste);
		mnPontos.add(mntmLogaritmo);
		mnPontos.add(mntmExponencial);
		
		mntmThreshold.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmThresholdAction(arg0); }});
		mntmBrilho.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmBrilhoAction(arg0); }});
		mntmContraste.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmContrasteAction(arg0); }});
		mntmLogaritmo.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmLogaritmoAction(arg0); }});
		mntmExponencial.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmExponencialAction(arg0); }});
		
		// filtros
		mnFiltros = new JMenu("Filtros");
		mntmFiltroCustom = new JMenuItem("Definir matrix...");
		mntmMean = new JMenuItem("Média...");
		mntmMedian = new JMenuItem("Mediana...");
		mntmGaussian = new JMenuItem("Gaussiano...");
		mntmLaplace = new JMenuItem("Laplace do gaussiano...");
		mntmLaplaceZero = new JMenuItem("Laplace do gaussiano, com zero crossing...");
		
		menuBar.add(mnFiltros);
		mnFiltros.add(mntmFiltroCustom);
		mnFiltros.add(new JSeparator());
		mnFiltros.add(mntmMean);
		mnFiltros.add(mntmMedian);
		mnFiltros.add(mntmGaussian);
		mnFiltros.add(new JSeparator());
		mnFiltros.add(mntmLaplace);
		mnFiltros.add(mntmLaplaceZero);
		
		mntmFiltroCustom.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmFiltroCustomAction(arg0); }});
		mntmMean.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmMeanAction(arg0); }});
		mntmMedian.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmMedianAction(arg0); }});
		mntmGaussian.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmGaussianAction(arg0); }});
		mntmLaplace.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmLaplaceAction(arg0); }});
		mntmLaplaceZero.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmLaplaceZeroAction(arg0); }});
		
		// transformacoes
		mnTransform = new JMenu("Transformações");
		mntmFastFourier = new JMenuItem("Fast Fourier...");
		
		menuBar.add(mnTransform);
		mnTransform.add(mntmFastFourier);
		
		mntmFastFourier.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { mntmFastFourierAction(arg0); }});
		
		// imagem
		image = new ImagePanel(filename);
		
		// scroll
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(image);
	}
	
	private void mntmCarregarAction(ActionEvent ev) {
		JFileChooser filechooser = new JFileChooser();
		filechooser.addChoosableFileFilter(new ImageFilter());
		
		if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				ImageFrame frame = new ImageFrame(filechooser.getSelectedFile());
				frame.setVisible(true);
			} catch (Exception e) {
				System.err.println("ImageFrame = " + this.getTitle());
				System.err.println("mntmCarregarAction = " + filechooser.getSelectedFile());
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(this, "O arquivo \"" + filechooser.getSelectedFile() + "\" não pode ser lido.", "Erro de leitura.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void mntmSalvarAction(ActionEvent ev) {
		JFileChooser filechooser = new JFileChooser();
		filechooser.addChoosableFileFilter(new ImageFilter());
		
		if (filechooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				File file = filechooser.getSelectedFile();
				String filename = file.getName();
				
				int dot = filename.lastIndexOf(".");
				String suffix = "png";
				if (dot > 0) {
					suffix = filename.substring(dot + 1);
				} else {
					file = new File(file.getAbsolutePath() + "." + suffix);
				}
				
				ImageIO.write(image.getImage(), suffix, file);
				
			} catch (Exception e) {
				System.err.println("ImageFrame = " + this.getTitle());
				System.err.println("mntmSalvarAction = " + filechooser.getSelectedFile());
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(this, "O arquivo \"" + filechooser.getSelectedFile() + "\" não pode ser salvo.", "Erro ao salvar.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void mntmRecarregarAction(ActionEvent arg0) {
		try {
			image.reload();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Não foi possível recarregar a imagem.", "Erro ao recarregar.", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void mntmFecharAction(ActionEvent ev) {
		this.dispose();
	}
	
	protected void mntmPaddAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("tamanho", 10.0);
		
		ImageOptions opt = new ImageOptions(this, "Padding", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			GeometricOperation op = new Padd(data.get("tamanho").intValue());
			try {
				new ImageFrame(op.execute(img)).setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Não foi possível fazer o padding na imagem.", "Erro.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	protected void mntmExpandAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("width", 800.0);
		data.put("height", 640.0);
		
		ImageOptions opt = new ImageOptions(this, "Expand", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			GeometricOperation op = new Expand(data.get("width").intValue(), data.get("height").intValue());
			try {
				new ImageFrame(op.execute(img)).setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Não foi possível expandir a imagem.", "Erro.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	protected void mntmCropAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("x", 200.0);
		data.put("y", 200.0);
		data.put("width", 200.0);
		data.put("height", 200.0);
		
		ImageOptions opt = new ImageOptions(this, "Cortar", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			GeometricOperation op = new Crop(data.get("x").intValue(), data.get("y").intValue(), data.get("width").intValue(), data.get("height").intValue());
			try {
				new ImageFrame(op.execute(img)).setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Não foi possível cortar a imagem.", "Erro.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	protected void mntmExponencialAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("gamma", 1.0);
		
		ImageOptions opt = new ImageOptions(this, "Exponencial", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			PointOperation op = new Exponential(data.get("gamma"));
			op.execute(img);
			
			image.repaint();
		}
	}

	protected void mntmLogaritmoAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("c", 255 / (Math.log(255)));
		
		ImageOptions opt = new ImageOptions(this, "Logarítmo", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			PointOperation op = new Logarithm(data.get("c"));
			op.execute(img);
			
			image.repaint();
		}
	}

	protected void mntmContrasteAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("min", 0.0);
		data.put("max", 255.0);
		
		ImageOptions opt = new ImageOptions(this, "Contraste", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			PointOperation op = new Contrast(data.get("min").intValue(), data.get("max").intValue());
			op.execute(img);
			
			image.repaint();
		}
	}

	protected void mntmBrilhoAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("offset", 20.0);
		
		ImageOptions opt = new ImageOptions(this, "Contraste", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			PointOperation op = new Brightness(data.get("offset").intValue());
			op.execute(img);
			
			image.repaint();
		}
	}

	protected void mntmThresholdAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("threshold", 127.0);
		
		ImageOptions opt = new ImageOptions(this, "Threshold", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			PointOperation op = new Threshold(data.get("threshold").intValue());
			op.execute(img);
			
			image.repaint();
		}
	}
	
	protected void mntmFiltroCustomAction(ActionEvent arg0) {
		double[][] matrix = {
				{0, 0, 0, 0, 0},
				{0,-1,-1,-1, 0},
				{0,-1, 8,-1, 0},
				{0,-1,-1,-1, 0},
				{0, 0, 0, 0, 0}
		};
		
		FilterDialog fd = new FilterDialog(this, "Definir matrix", matrix);
		fd.setVisible(true);
		
		if (!fd.wasCanceled()) {
			BufferedImage img = image.getImage();
			
			Filter f = new Filter(matrix);
			f.execute(img);
			
			image.repaint();
		}
	}
	
	protected void mntmMeanAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("tamanho", 9.0);
		
		ImageOptions opt = new ImageOptions(this, "Média", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			Filter f = new Mean(data.get("tamanho").intValue());
			f.execute(img);
			
			image.repaint();
		}
	}

	protected void mntmMedianAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("tamanho", 9.0);
		
		ImageOptions opt = new ImageOptions(this, "Mediana", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			Filter f = new Median(data.get("tamanho").intValue());
			f.execute(img);
			
			image.repaint();
		}
	}

	protected void mntmGaussianAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("sigma", 5.0);
		
		ImageOptions opt = new ImageOptions(this, "Gaussiano", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			Filter f = new Gaussian(data.get("sigma"));
			f.execute(img);
			
			image.repaint();
		}
	}

	protected void mntmLaplaceAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("sigma", 1.4);
		
		ImageOptions opt = new ImageOptions(this, "Laplaciano do Gaussiano", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			Filter f = new LaplaceOfGaussian(data.get("sigma"));
			f.execute(img);
			
			image.repaint();
		}
	}

	protected void mntmLaplaceZeroAction(ActionEvent arg0) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		data.put("sigma", 2.0);
		
		ImageOptions opt = new ImageOptions(this, "Laplaciano do Gaussiano, com Zero Crossing", data);
		opt.setVisible(true);
		
		if (data.size() > 0) {
			BufferedImage img = image.getImage();
			Filter f = new LaplaceOfGaussianZeroCross(data.get("sigma"));
			f.execute(img);
			
			image.repaint();
		}
	}
	
	protected void mntmFastFourierAction(ActionEvent arg0) {
		BufferedImage img = image.getImage();
		
		double logWidth = Math.log(img.getWidth()) / Math.log(2);
		double logHeight = Math.log(img.getHeight()) / Math.log(2);
		
		if (logWidth != Math.floor(logWidth) || logHeight != Math.floor(logHeight)) {
			JOptionPane.showMessageDialog(this, "As dimensões desta imagem não são multiplas de 2.\nNão é possível aplicar o FFT.", "Erro.", JOptionPane.ERROR_MESSAGE);
		} else {
			FourierTransform ft = new FastFourierTransform();
			ComplexFrame cf = new ComplexFrame("Transformada Rápida de Fourier", ft.apply(img), img.getType());
			cf.setVisible(true);
		}
	}
	
	public static void main (String[] args) throws Exception {
		JFrame principal = new ImageFrame("testes/lena.big.png");
		principal.setVisible(true);
	}
}