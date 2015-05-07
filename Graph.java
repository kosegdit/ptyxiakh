package ptyxiakh;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kostas
 */

public class Graph {
    
    public List<Node> nodes = new ArrayList<>();;
    public List<Node> selectedNodes = new ArrayList<>();
    public List<Edge> edges = new ArrayList<>();
    
    public Graph(){
    }
    
    public void createRandomGraph(double density, int numOfNodes, Dimension size){
        
        Point[] coords = getCircleCoords(numOfNodes, size);
        
        for(int i=0; i<numOfNodes; i++){
            nodes.add(new Node(""+i, coords[i]));
        }
        
        // Run through every possible pair of nodes in the Graph
        for(int i=0; i<nodes.size(); i++){
            for(int j=i+1; j<nodes.size(); j++){
                // Creates a random nuber [0, 100] to become the connection possibility of two nodes
                int connectivity = (int)(Math.random() * (101));
                
                if(connectivity <= density){
                    edges.add(new Edge(nodes.get(i), nodes.get(j), false, false, 0, size));
                    
                    // Resize nodes i, j for every new (i,j) edge
                    nodes.get(i).setSize(nodes.get(i).getWidth() + 1, nodes.get(i).getHeight()+ 1);
                    nodes.get(j).setSize(nodes.get(j).getWidth() + 1, nodes.get(j).getHeight()+ 1);
                    
                }
            }
        }
    }
    
//    public Node getNode(int i) {
//        
//        return nodes.get(i);
//    }
    
    public void printListNodes(){
        
        for(int i=0; i<nodes.size(); i++){
            System.out.println("" + nodes.size() + " nodes");
            //System.out.println("Node: " + nodes.get(i).label + " width: " + nodes.get(i).getWidth() + " heigth: " + nodes.get(i).getHeight());
        }
    }
    
    public void deleteNode(int index){
        
        nodes.remove(index);
    }
    
//    public void deleteGraph(){
//        
//        nodes.clear();
//        nodes = null;
//        //System.gc();
//    }
    
    private static Point[] getCircleCoords(int n, Dimension size) {
        
        Point[] p = new Point[n];
        int a = n*10;

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
