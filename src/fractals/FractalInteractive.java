package fractals;

import static fractals.FractalImageRenderer.gradient;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FractalInteractive extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    Color[] colors = new Color[]{Color.WHITE, Color.RED, Color.PINK, Color.BLUE, Color.GREEN, Color.BLACK};
    
    int width, height;
    int mx, my;
    boolean mousein = false;
    final int MAX_ITERATIONS = 255;
    double cx, cy, w, h;
    Timer t;

    public FractalInteractive(int width, int height) {
        this.width = width;
        this.height = height;

        w = 4.0;
        h = 4.0;
        cx = 0.0;
        cy = 0.0;

        this.addMouseWheelListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public static void main(String[] args) {
        int width = 200;
        int height = 200;
        JFrame f = new JFrame();
        f.setSize(new Dimension(width, height + 21));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        FractalInteractive c = new FractalInteractive(width, height);
        c.setSize(new Dimension(width, height));
        f.getContentPane().add(c);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double inita = w * ((double) i / (double) width - .5) + cx;
                double initb = h * ((double) j / (double) height - .5) + cy;

                double a = inita;
                double b = initb;
                int iterations = 0;
                while (a * a + b * b < 4.0) {
                    double newa = a*a-b*b  +inita;//+inita;
                    double newb = 2*a*b + initb;//+initb;
                    //double newa = a * nnewa - nnewb*b + inita;
                    //double newb = nnewb*a + b*nnewa + initb;
                    
                    //double ea = Math.pow(Math.E, a);
                    //double newa = ea * Math.cos(b);
                    //double newb = ea * Math.sin(b);
                    a = newa;
                    b = newb;
                    iterations++;
                    if (iterations == MAX_ITERATIONS) {
                        break;
                    }
                }
    
                double percent = (double) iterations / (double) MAX_ITERATIONS;
                //Color c = gradient(percent, new Color(48, 48, 48), new Color(122, 205, 166));
                Color c = gradient(percent, new Color(255, 255, 255), new Color(0, 0, 0));
                g.setColor(c);
                g.fillRect(i, j, 1, 1);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mousein = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mousein = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double oxx = w*((double)mx/(double)width-0.5)+cx;
        double oyy = h*((double)my/(double)height-0.5)+cy;
        
        double nxx = w*((double)e.getX()/(double)width-0.5)+cx;
        double nyy = h*((double)e.getY()/(double)height-0.5)+cy;
        cx -= nxx-oxx;
        cy -= nyy-oyy;
        repaint();
        mx = e.getX();
        my = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rot = e.getWheelRotation();
        

        if(rot > 0) {
            w = w*.9;
            h = h*.9;
        } else {
            w = w/.9;
            h = h/.9;
        }
        
        repaint();
    }

    public Color gradient(double percent, Color c1, Color c2) {
        int r = c1.getRed() + (int) (percent * ((double) c2.getRed() - (double) c1.getRed()));
        int g = c1.getBlue() + (int) (percent * ((double) c2.getBlue() - (double) c1.getBlue()));
        int b = c1.getGreen() + (int) (percent * ((double) c2.getGreen() - (double) c1.getGreen()));
        return new Color(r, g, b);
    }
}
