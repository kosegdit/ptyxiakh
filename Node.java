package ptyxiakh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
//import java.awt.RenderingHints;
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
    public Color color;
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
                if(mousePt.x > 10 && mousePt.x < getWidth()-10 && mousePt.y > 10 && mousePt.y < getHeight()-10) {
                    Node node = (Node) e.getSource();
                    node.setLocation(node.getX() + e.getX() - mousePt.x, node.getY() + e.getY() - mousePt.y);
                }
                
            }
        });
        
        setLocation(p);
        setSize(new Dimension(30, 30));
        
        setVisible(true);
        repaint();
    }
    
    public Color setNodeColor(Color desiredColor){
        return(this.color = desiredColor);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(this.color);
        g2.fillOval(10, 10, getWidth()-20, getHeight()-20);
        g2.setColor(Color.BLACK);
        g2.drawString(this.label, 9, 9); 
    }
}
