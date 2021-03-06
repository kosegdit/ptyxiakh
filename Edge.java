/*
 * This is a class file for the program AviNet
 *
 * Copyright (c) Segditsas Konstantinos, 2015
 * Email: kosegdit@gmail.com
 *
 */
package ptyxiakh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;

/**
 *
 * @author kostas
 */
public class Edge extends JComponent{
    
    
    public Node node1;
    public Node node2;
    String label;
    public boolean directed;
    public boolean weighted;
    public double weight;
    
    public Edge(Node node1, Node node2, boolean directed, boolean weighted, double weight, Dimension size) {
        
        this.node1 = node1;
        this.node2 = node2;
        this.label ="(" + node1.label + "," + node2.label + ")";
        
        this.directed = directed;
        
        if(weighted){
            this.weighted = true;
        }
        this.weight = weight;
        
        setSize(2000,2000);

        setVisible(true);
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics g2 = (Graphics) g;

        g2.setColor(Color.BLACK);
        
        Point point1 = node1.getLocation();
        Point point2 = node2.getLocation();

        int w = Math.abs(point1.x - point2.x);
        int h = Math.abs(point1.y - point2.y);
        
        int x, y, x1, x2, y1, y2;
        if(point1.x < point2.x) {
            x = point1.x + node1.getWidth()/2;
            x1 = 0;
            x2 = w + node2.getWidth()/2 - node1.getWidth()/2;
        }
        else {
            x = point2.x + node2.getWidth()/2;
            x2 = 0;
            x1 = w - node2.getWidth()/2 + node1.getWidth()/2;
        }
        
        if(point1.y < point2.y) {
            y = point1.y + node1.getHeight()/2;
            y1 = 0;
            y2 = h + node2.getHeight()/2 - node1.getHeight()/2;
        }
        else {
            y = point2.y + node2.getHeight()/2;
            y2 = 0;
            y1 = h + node1.getHeight()/2 - node2.getHeight()/2;
        }
        
        g2.drawLine(x + x1, y + y1, x + x2, y + y2);
        
        if(weighted){
            if(directed){
                if(node1.label < node2.label){
                    g2.setColor(Color.decode("0x439351"));
                    
                    if(weight%1==0){
                        g2.drawString(String.valueOf((int) weight), (2*x+x1+x2)/2, ((2*y+y1+y2)/2)-5);
                    }
                    else{
                        g2.drawString(String.valueOf(weight), (2*x+x1+x2)/2, ((2*y+y1+y2)/2)-5);
                    }
                }
                else{
                    g2.setColor(Color.RED);
                    
                    if(weight%1==0){
                        g2.drawString(String.valueOf((int) weight), (2*x+x1+x2)/2, ((2*y+y1+y2)/2)+15);
                    }
                    else{
                        g2.drawString(String.valueOf(weight), (2*x+x1+x2)/2, ((2*y+y1+y2)/2)+15);
                    }
                }
                
                
            }
            else{
                g2.setColor(Color.BLACK);
                if(weight%1==0){
                    g2.drawString(String.valueOf((int) weight), (2*x+x1+x2)/2, ((2*y+y1+y2)/2)-5);
                }
                else{
                    g2.drawString(String.valueOf(weight), (2*x+x1+x2)/2, ((2*y+y1+y2)/2)-5);
                }
            }
        }
        
        if(directed) {
            drawArrow(g, x + x1, y + y1, x + x2, y + y2);
        }

        setVisible(false);
        setVisible(true);
    }
    
    // Draws the arrow for the directed edges
    private void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();

        final int ARR_SIZE = 10;

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy) - node2.getWidth()/2 + 11;
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                      new int[] {0, -ARR_SIZE/2, ARR_SIZE/2, 0}, 4);
    }
}
