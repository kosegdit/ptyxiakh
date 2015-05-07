package ptyxiakh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;

/**
 *
 * @author kostas
 */

public class Node extends JComponent {
    
    private Point mousePt;
    public String label;
    public int radius;
    //public Color color;
    public boolean selected;
    public Rectangle b = new Rectangle();
    
    // Default Constructor
    public Node(){
    }

    public Node(String label, Point p) {
    
        this.label = label;
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Node node = (Node) e.getSource();
                node.setLocation(node.getX() + e.getX() - mousePt.x, node.getY() + e.getY() - mousePt.y);
            }
        });
        
        setLocation(p);
        setSize(new Dimension(10, 10));
        
        setVisible(true);
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);
        g2.fillOval(0, 0, getWidth(), getHeight());
    }
}
