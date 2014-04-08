package fractals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author mhelenurm
 */
public class FractalImageRenderer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            final int pow2 = 14;
            int width = (int)Math.pow(2, pow2);
            int height = (int)Math.pow(2, pow2);
            //width = 2560;
            //height = 1600;
            BufferedImage b = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = b.createGraphics();
            
            int loopcount = 0;
            int percent5 = (int)((double)width*(double)height*5.0/100.0);
            for(int i = 0; i < width; i++) {
                for(int j = 0; j < height; j++) {
                    double whr = (double)width/(double)height;
                    double scalex = ((double)(i-width/2))/((double)width/2)*2.0; //
                    double scaley = ((double)(j-height/2))/((double)height/2)/whr*2.0;
                    scalex -= 0.3;
                    
                    double real = scalex;
                    double imag = scaley;
                    Imaginary start = new Imaginary(imag, real);//new Imaginary(-0.8, 0.156);
                    Imaginary cur = new Imaginary(real, imag);
                    int iterations = 0;
                    int maxiterations = 100;
                    while(cur.absoluteValue() < 2.0) {
                        double ea = Math.pow(Math.E, cur.real);
                        double bb = cur.imaginary;
                        cur.real = ea * Math.cos(bb);
                        cur.imaginary = ea * Math.sin(bb);
                        
                        cur.add(start);
                        iterations++;
                        if(iterations >= maxiterations) {
                            break;
                        }
                    }
                    double percent = (double)iterations/(double)maxiterations;
                    Color c = gradient(percent, new Color(48, 48, 48), new Color(122, 205, 166));
                    g.setColor(c);
                    g.fillRect(i, j, 1, 1);
                    loopcount++;
                    if(loopcount % percent5 == 0)
                    {
                        int pdone = (int)(5 * (loopcount/percent5));
                        System.out.println(pdone + "%");
                    }
                }
            }
            ImageIO.write(b, "png", new File("what.png"));
        } catch (IOException ex) {
            Logger.getLogger(FractalImageRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static class Imaginary {
        double real, imaginary;
        
        public Imaginary(double r, double i) {
            this.real = r;
            this.imaginary = i;
        }
        
        public double absoluteValue() {
            return Math.sqrt(real*real+imaginary*imaginary);
        }
        
        public void multiply(Imaginary m) {
            double newreal = real*m.real - imaginary*m.imaginary;
            double newimag = real*m.imaginary + imaginary*m.real;
            real = newreal;
            imaginary = newimag;
        }
        
        public void add(Imaginary m) {
            real += m.real;
            imaginary += m.imaginary;
        }
    }
    
    public static Color grayScale(double percent) {
        int rgb = 255 - (int)(255.0*percent);
        return new Color(rgb, rgb, rgb);
    }
    
    public static Color gradient(double percent, Color c1, Color c2) {
        int r = c1.getRed() + (int)(percent*((double)c2.getRed()-(double)c1.getRed()));
        int g = c1.getBlue() + (int)(percent*((double)c2.getBlue()-(double)c1.getBlue()));
        int b = c1.getGreen() + (int)(percent*((double)c2.getGreen()-(double)c1.getGreen()));
        return new Color(r, g, b);
    }
}
