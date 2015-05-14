package ptyxiakh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
//import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 *
 * @author kostas
 */

public class Node extends JComponent  {
    
    private Point mousePt;
    public int label;
    public int radius;
    public Color color;
    public boolean selected;
    public Rectangle b = new Rectangle();
    
    JPopupMenu NodePopup = new JPopupMenu();
    JMenuItem deleteNode = new JMenuItem("Delete");
    
    // Default Constructor
    public Node(){
    }

    
    public Node(int label, Point p, Graph currentGraph) {
    
        this.label = label;
        
        deleteNode.addActionListener((ActionEvent e) -> {
                    System.out.println("first: " + currentGraph.nodes.size());
                    currentGraph.userDeleteNode(this);
                });
        NodePopup.add(deleteNode);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
                if (SwingUtilities.isRightMouseButton(e)){
                    NodePopup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    if(mousePt.x > 10 && mousePt.x < getWidth()-10 && mousePt.y > 10 && mousePt.y < getHeight()-10) {
                        Node node = (Node) e.getSource();
                        node.setLocation(node.getX() + e.getX() - mousePt.x, node.getY() + e.getY() - mousePt.y);
                    }
                }
            }
        });
        
        setNodeColor(Color.BLUE);
        
        setLocation(p);
        setSize(new Dimension(30, 30));
        
        setVisible(true);
        repaint();
    }
    
    
    public Node otherNeighbor(Edge edge){
        
        if(edge.node1.equals(this)){
            return edge.node2;
        }
        else{
            return edge.node1;
        }
    }
    
    
    public void resize(int action){
        
        this.setSize(this.getWidth() + action, this.getHeight() + action);
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
        g2.drawString(this.label + "", 9, 9); 
    }
}
