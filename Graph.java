package ptyxiakh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 *
 * @author kostas
 */

public class Graph extends JPanel {
    
    public List<Node> nodes;
    public List<Node> selectedNodes;
    public List<Edge> edges;
    JPopupMenu previewPanelPopup;
    JMenuItem clear;
    private Point mousePt;
    private boolean graphInUse;
    private int nodesCounter;
    public boolean noAction;

    
    public Graph(){
        
        init();
        
        previewPanelPopup = new JPopupMenu();
        clear = new JMenuItem("Clear Graph");
        clear.setEnabled(false);
        
        // Creates and sets the previewPanel and the previewPanel Pop up Menu
        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.
                createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Preview"));
        
        javax.swing.GroupLayout previewPanelLayout = new javax.swing.GroupLayout(this);
        setLayout(previewPanelLayout);
        previewPanelLayout.setHorizontalGroup(
            previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        previewPanelLayout.setVerticalGroup(
            previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        
        // previewPanel Pop up Menu to create a new node and clear the current Graph

        JMenuItem newNode = new JMenuItem("New node");
        newNode.addActionListener((ActionEvent e) -> {
                    this.userNewNode(mousePt);
                    add(this.nodes.get(nodes.size()-1));
                    repaint();
                    graphInUse = true;
                    clear.setEnabled(true);
                });
        previewPanelPopup.add(newNode);
        clear.addActionListener((ActionEvent e) -> {
                    ClearGraphIfInUse();
                    this.repaint();
                });
        previewPanelPopup.add(clear);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)){
                    mousePt = e.getPoint();
                    previewPanelPopup.show(e.getComponent(), e.getX(), e.getY());
                }     
            }
        });
    }
    
    private void init() {
        nodes = new ArrayList<>();;
        selectedNodes = new ArrayList<>();
        edges = new ArrayList<>();
        graphInUse = false;
        nodesCounter = 0;
        boolean noAction = false;
    }
    
    private void reset() {
        init();
        clear.setEnabled(false);
        this.removeAll();
    }

    public boolean ClearGraphIfInUse() {
        
        if(graphInUse){
            return(noAction = ClearGraph());
        }
        return noAction;
    }
    
    public boolean ClearGraph() {
        
        int clearDialogResult = JOptionPane.showConfirmDialog(null, "Would you like to save the current Graph?", "MyProgram",
                                                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        switch(clearDialogResult) {
            case JOptionPane.YES_OPTION:
                SaveGraph();
                reset();
                return false;
            case JOptionPane.NO_OPTION:
                reset();
                return false;
            default:
                return true;
        }
    }
    
    private void SaveGraph(){}

    // Adds a new node after user request
    public void userNewNode(Point location){
        
        Point nodeLocation  = new Point();
        nodeLocation.x = location.x -10;
        nodeLocation.y = location.y -10;
        
        nodes.add(new Node(nodesCounter++, nodeLocation, this));
    }
    
    // Adds a new edge after user request
    public void userNewEdge(){
        
        
    }
    
    // Deletes a node and all his edges after user request
    public void userDeleteNode(Node node){
        
        System.out.println("middle:" + nodes.size());
        
        int i = 0;
        Edge e;
        while (i < edges.size()) {
            e = edges.get(i);
            
            if(e.node1.equals(node)){
                e.node1.otherNeighbor(e).resize(-1);
                this.remove(e);
                edges.remove(e);
                
                continue;
            }
            else if(e.node2.equals(node)){
                e.node2.otherNeighbor(e).resize(-1);
                this.remove(e);
                edges.remove(e);
                
                continue;
            }
            
            i++;
        }
        
        this.remove(node);
        this.repaint();
        nodes.remove(node);
        if(nodes.size() == 0) clear.setEnabled(false);
        
        System.out.println("last:" + nodes.size());
    }
    
    // Creates the random Graph
    public void RandomGraph(double density, int numOfNodes){
        
        graphInUse = true;
        clear.setEnabled(true);
        
        Point[] coords = getCircleCoords(numOfNodes, this.getSize());
        
        for(int i=0; i<numOfNodes; i++){
            nodes.add(new Node(nodesCounter++, coords[i], this));
            this.add(nodes.get(i));
        }
        
        // Run through every possible pair of nodes in the Graph
        for(int i=0; i<nodes.size(); i++){
            for(int j=i+1; j<nodes.size(); j++){
                // Creates a random nuber [0, 100] to become the connection possibility of two nodes
                int connectivity = (int)(Math.random() * (101)) + 1;
                
                if(connectivity <= density){
                    edges.add(new Edge(nodes.get(i), nodes.get(j), false, false, 0, this.getSize()));
                    this.add(edges.get(edges.size()-1));
                    
                    // Resize nodes i, j for every new (i,j) edge
                    nodes.get(i).resize(1);
                    nodes.get(j).resize(1);
                    
                }
            }
        }

        this.repaint();
    }
    
    public void printListNodes(){
        
        for(int i=0; i<nodes.size(); i++){
            System.out.println("" + nodes.size() + " nodes");
            //System.out.println("Node: " + nodes.get(i).label + " width: " + nodes.get(i).getWidth() + " heigth: " + nodes.get(i).getHeight());
        }
    }
    
    // Returns the Circle coordinates for the nodes
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
