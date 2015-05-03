package ptyxiakh;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author kostas
 */

public class Graph {
    
    public static List<Node> nodes = new ArrayList<>();
    public List<Node> selectedNodes = new ArrayList<>();
    public List<Edge> edges = new ArrayList<>();
    
    public static void createRandomGraph(double density, int numOfNodes, Dimension size){
        // Creates a random nuber [0, 100] to become the connection possibility of two nodes
        int connectivity = (int)(Math.random() * (101));
        
        Point[] coords = getCircleCoords(numOfNodes, size);
        
        for(int i=0; i<numOfNodes; i++){
            
            Node iNode = new Node(""+i, coords[i]);
            nodes.add(iNode);
        }
    }
    
    public static Node getNode(int i) {
        return nodes.get(i);
    }
    
    public static void printListNodes(){
    
        //for(int i=0; i<nodes.size(); i++){
            System.out.println("Graph with " + nodes.size() + " nodes");
        //}
    }
    
    public static void deleteNode(int index){
    
        nodes.remove(index);
    }
    
    private static Point[] getCircleCoords(int n, Dimension size) {
        Point[] p = new Point[n];
        int a = n*5;

        int r = a/2;
        int xx = (size.width - a)/2;
        int yy = (size.height - a)/2;

        if(xx < 0) xx = 0;
        if(yy < 0) yy = 0;

        
        for (int i = 0; i < n; i++) {
            double t = 2 * Math.PI * i / n;
            int x = (int) Math.round(r * (1 + Math.cos(t)));
            int y = (int) Math.round(r * (1 + Math.sin(t)));
            p[i] = new Point(x + xx, y + yy);
        }
        
        return p;
    }
}
