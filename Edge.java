package ptyxiakh;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author kostas
 */
public class Edge {
    
    public Node node1;
    public Node node2;
    String label;
    public boolean directed;
    public boolean weighted;
    public int weight;
    
    public Edge(Node node1, Node node2, boolean directed, boolean weighted, int weight) {
        
        this.node1 = node1;
        this.node2 = node2;
        this.label = "" + node1.label + ", " + node2.label;
        
        this.directed = directed;
        
        if(weighted){
            this.weighted = true;
            this.weight = weight;
        }
    }
    
    public void draw(Graphics g) {
        
            Point point1 = node1.getLocation();
            Point point2 = node2.getLocation();
            g.setColor(Color.darkGray);
            g.drawLine(point1.x, point1.y, point2.x, point2.y);
        }
}
