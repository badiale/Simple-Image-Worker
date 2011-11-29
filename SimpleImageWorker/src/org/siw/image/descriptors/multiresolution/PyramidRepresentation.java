/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siw.image.descriptors.multiresolution;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.siw.gui.ImagePanel;
import org.siw.image.filter.Gaussian;

/**
 *
 * @author Lucas
 */
public class PyramidRepresentation {
    private int J;
    private int k;
    
    public PyramidRepresentation (int J, int k) {
        this.J = J;
        this.k = k;
    }
    
    public List<BufferedImage> execute (BufferedImage img) {
        // Converting RGB image to Grayscale (8bit depth, 1 color channel)
	//IplImage *gray = cvCreateImage(cvSize(I->width,I->height),IPL_DEPTH_8U,1);
	//cvCvtColor(I,gray,CV_RGB2GRAY);
        BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
        ((Graphics2D) gray.getGraphics()).drawImage(img, null, 0, 0);
        
        // Placing the base of the pyramid
        //pyramid[J-1] = gray;
	LinkedList<BufferedImage> pyramid = new LinkedList<BufferedImage>();
        pyramid.addLast(gray);
	
	for (int i = J-1; i > 0; i--) {
		// Allocate new image for the filtered layer
		int width  = pyramid.getLast().getWidth();
		int height = pyramid.getLast().getHeight();
                //IplImage *smooth = cvCreateImage(cvSize(width,height),IPL_DEPTH_8U,1);
		BufferedImage smooth = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);
                ((Graphics2D) smooth.getGraphics()).drawImage(pyramid.getLast(), null, 0, 0);
		
		// Applying gaussian smooth filter
		//cvSmooth(pyramid[i],smooth,CV_GAUSSIAN,k,k,1,1);
                new Gaussian(k, k, 1).execute(smooth);
		
		// Resize image to create next layer of the pyramid
		//IplImage *nextLayer = cvCreateImage(cvSize((int)width/2,(int)height/2),IPL_DEPTH_8U,1);
		//cvResize(smooth,nextLayer,CV_INTER_LINEAR);
                BufferedImage nextLayer = new BufferedImage(width / 2, height / 2, BufferedImage.TYPE_USHORT_GRAY);
                ((Graphics2D) nextLayer.getGraphics()).drawImage(smooth, 0, 0, width /2, height/2, null);
		
		// Place next layer at the pyramid
		//pyramid[i-1] = nextLayer;
                pyramid.addLast(nextLayer);
	}
        
        return pyramid;
    }
    
    public static void main (String[] args) throws Exception {
        BufferedImage img = ImageIO.read(new File("testes/link.png"));
        
        List<BufferedImage> list = new PyramidRepresentation(5, 5).execute(img);
        
        for (BufferedImage layer : list) {
            JFrame frame = new JFrame("");
            frame.add(new ImagePanel(layer));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
    }
}
