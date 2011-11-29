package org.siw.image.geometric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Crop implements GeometricOperation {

    private int x;
    private int y;
    private int width;
    private int height;

    public Crop(int x, int y, int width, int height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public BufferedImage execute(BufferedImage img) {
        BufferedImage crop = new BufferedImage(width, height, img.getType());
        Graphics2D g = crop.createGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.drawImage(img, -x, -y, null);

        return crop;
    }

    public static void main(String[] args) throws Exception {
        BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));

        GeometricOperation op = new Crop(230, 250, 130, 40);
        lena = op.execute(lena);

        ImageIO.write(lena, "png", new File("testes_out/teste.png"));
    }
}
