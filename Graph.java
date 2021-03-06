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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
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
    private Point mousePt;
    public boolean graphInUse;
    public boolean draft;
    private int nodesCounter; 
    private int lastLabel = 0;
    public boolean noAction;
    public Node readyToConnect;
    public Node readyToDisconnect;
    
    MainFrame parent;
    
    JPopupMenu previewPanelPopup;
    JMenuItem clearMenuItem;
    JMenuItem newNodeMenuItem;
    JMenu propertiesMenu;
    JCheckBoxMenuItem directedGraphMenuItem;
    JCheckBoxMenuItem weightedGraphMenuItem;
    

    
    public Graph(MainFrame frame){
        
        init();
        parent = frame;
        
        previewPanelPopup = new JPopupMenu();
        
        propertiesMenu = new JMenu("Graph Properties");
        directedGraphMenuItem = new JCheckBoxMenuItem("Directed");
        weightedGraphMenuItem = new JCheckBoxMenuItem("Weighted");
        
        clearMenuItem = new JMenuItem("Clear Graph");
        newNodeMenuItem = new JMenuItem("New node");
        
        clearMenuItem.setEnabled(false);
        
        // Creates and sets the previewPanel and the previewPanel Pop up Menu
        javax.swing.GroupLayout previewPanelLayout = new javax.swing.GroupLayout(this);
        setLayout(previewPanelLayout);
        previewPanelLayout.setHorizontalGroup(
            previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        previewPanelLayout.setVerticalGroup(
            previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        
        // previewPanel Pop up Menu to decide Graph properties and to create a new node and clear the current Graph
        directedGraphMenuItem.addActionListener((ActionEvent e) -> {
                    parent.UpdateInfoPanel("");
                });
        propertiesMenu.add(directedGraphMenuItem);
        
        weightedGraphMenuItem.addActionListener((ActionEvent e) -> {
                    parent.UpdateInfoPanel("");
                });
        propertiesMenu.add(weightedGraphMenuItem);
        previewPanelPopup.add(propertiesMenu);
        
        newNodeMenuItem.addActionListener((ActionEvent e) -> {
                    this.userNewNode(mousePt);
                    add(this.nodes.get(nodes.size()-1));
                    repaint();
                    graphInUse = true;
                    clearMenuItem.setEnabled(true);
                });
        previewPanelPopup.add(newNodeMenuItem);
        clearMenuItem.addActionListener((ActionEvent e) -> {
                    ClearGraphIfInUse();
                    repaint();
                });
        previewPanelPopup.add(clearMenuItem);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)){
                    mousePt = e.getPoint();
                    previewPanelPopup.show(e.getComponent(), e.getX(), e.getY());
                }     
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                readyToConnect = null;
                readyToDisconnect = null;
            }
        });
    }
    
    
    private void init() {
        nodes = new ArrayList<>();
        selectedNodes = new ArrayList<>();
        edges = new ArrayList<>();
        graphInUse = false;
        draft = false;
        readyToConnect = null;
        readyToDisconnect = null;
        lastLabel = 0;
        nodesCounter = 0;
        boolean noAction = false;
    }
    
    private void reset() {
        init();
        clearMenuItem.setEnabled(false);
        propertiesMenu.setEnabled(true);
        directedGraphMenuItem.setState(false);
        weightedGraphMenuItem.setState(false);
        MainFrame.lastLoadedFile = null;
        this.removeAll();
        
        if(parent.resultsPaneUse) {
            parent.resultsScrollPane.getViewport().remove(0);
            parent.resultsScrollPane.repaint();
            parent.resultsPaneUse = false;
            parent.exportResultsMenuItem.setEnabled(false);
        }
        
        parent.UpdateInfoPanel("");
        parent.UpdateAlgorithmName("");
    }
    
    public void resetNodesColor(){
        
        for(int i=0; i<nodes.size(); i++){
                nodes.get(i).setNodeColor(Color.BLUE);
        }
    }
    
    

    public boolean ClearGraph() {
        
        int clearDialogResult;
                
        if(draft){
            clearDialogResult = JOptionPane.showConfirmDialog(parent, "Would you like to save the current Graph?", "AviNet",
                                                                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        else{
            clearDialogResult = JOptionPane.NO_OPTION;
        }
        switch(clearDialogResult) {
            case JOptionPane.YES_OPTION:
                if(MainFrame.lastLoadedFile == null){
                    if(parent.ShowSaveDialog()){
                        reset();
                        return false;
                    }
                    return true;
                }
                else{
                    SaveGraph(MainFrame.lastLoadedFile);
                    reset();
                    return false;
                }
                
            case JOptionPane.NO_OPTION:
                reset();
                return false;
            default:
                return true;
        }
    }
    
    public boolean ClearGraphIfInUse() {
        
        if(graphInUse){
            return(noAction = ClearGraph());
        }
        return noAction;
    }
    
    public boolean graphIsDirected(){
        
        return(directedGraphMenuItem.getState());
    }
    
    public boolean graphIsWeighted(){
        
        return(weightedGraphMenuItem.getState());
    }
    
    public void LoadGraph(String file){
        
        Scanner inputFile = null;
        StringTokenizer current_line;
        List<String> labels = new ArrayList<>();
        List<String> locations = new ArrayList<>();
        List<List<String>> neighbors = new ArrayList<>();
        List<List<String>> weights = new ArrayList<>();
        
        try {
            inputFile = new Scanner(new FileReader(file));
            graphInUse = true;
            clearMenuItem.setEnabled(graphInUse);

            while(inputFile.hasNextLine()){
                current_line = new StringTokenizer(inputFile.nextLine());

                if(current_line.hasMoreTokens()){
                    String s = current_line.nextToken();
                    String nextToken;

                    if(s.startsWith("#")) {
                        continue;
                    }
                    else if(s.equals("directed")){
                        directedGraphMenuItem.setState(Boolean.parseBoolean(current_line.nextToken()));
                    }
                    else if(s.equals("weighted")){
                        weightedGraphMenuItem.setState(Boolean.parseBoolean(current_line.nextToken()));
                    }
                    else{
                        labels.add(s);
                        List<String> nodeNeighbors = new ArrayList<>();
                        List<String> edgesWeights = new ArrayList<>();
                        nodesCounter++;
                        lastLabel = Integer.valueOf(s);
                        locations.add(current_line.nextToken());

                        while(current_line.hasMoreTokens()){
                            nextToken = current_line.nextToken();

                            if(graphIsWeighted()){
                                String[] result = nextToken.split(",");
                                nodeNeighbors.add(result[0]);
                                edgesWeights.add(result[1]);
                            }
                            else{
                                nodeNeighbors.add(nextToken);
                                edgesWeights.add("1.0");
                            }
                        }

                        neighbors.add(nodeNeighbors);
                        weights.add(edgesWeights);
                    }
                }
            }
        
            if(nodesCounter>0){
                LoadGraphNodes(nodesCounter, labels, locations);
                for(int i=0; i<labels.size(); i++){
                    for(int j=0; j<neighbors.get(i).size(); j++){
                        LoadgraphEdges(labels.get(i), neighbors.get(i).get(j), graphIsDirected(), graphIsWeighted(), Double.valueOf(weights.get(i).get(j)));
                    }
                }

                propertiesMenu.setEnabled(false);
                parent.UpdateInfoPanel("");
                this.repaint();
            }
        } 
        catch (FileNotFoundException ex) {
            graphInUse = false;
            clearMenuItem.setEnabled(graphInUse);
            nodesCounter = 0;
            directedGraphMenuItem.setState(false);
            weightedGraphMenuItem.setState(false);
            lastLabel = 0;
            System.err.println(ex);
            JOptionPane.showMessageDialog (parent, "File does not exist", "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        catch (Exception ex) {
            graphInUse = false;
            clearMenuItem.setEnabled(graphInUse);
            nodesCounter = 0;
            directedGraphMenuItem.setState(false);
            weightedGraphMenuItem.setState(false);
            lastLabel = 0;
            System.err.println(ex);
            JOptionPane.showMessageDialog (parent, "Invalid Input File Format", "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        finally{
            if(inputFile == null) return;
            inputFile.close();
        }
        
    }
    
    public void SaveGraph(String file){
        
        PrintStream myOutput = null;
        try {
            myOutput = new PrintStream(new FileOutputStream(file));
        }
        catch (Exception ex) {
                System.out.println("No output file written");
                return;
        }
        
        myOutput.print("#Graph Properties\n\n");
        
        myOutput.print("directed\t");
        myOutput.print(directedGraphMenuItem.getState() + "\n");
        
        myOutput.print("weighted\t");
        myOutput.print(weightedGraphMenuItem.getState() + "\n\n\n");
        
        myOutput.print("#List of Nodes, with their Locations and their Edges\n\n");
        
        for(int i=0; i<nodes.size(); i++){
            myOutput.print(nodes.get(i).label + "\t");
            myOutput.print("@" + nodes.get(i).getX() + "," + nodes.get(i).getY() + "\t");
            
            for(int j=0; j<edges.size(); j++){
                if(edges.get(j).node1.label == nodes.get(i).label){
                    myOutput.print(edges.get(j).node2.label);
                    if(graphIsWeighted()){
                        myOutput.print("," + edges.get(j).weight);
                    }
                    myOutput.print("\t");
                }
            }
            myOutput.print("\n");
        }
        draft = false;
        myOutput.close();
        
        parent.UpdateInfoPanel("");
    }
    
    private void LoadGraphNodes(int numOfNodes, List<String> labels, List<String> locations){
        
        Point[] coords = new Point[numOfNodes];
                
        if(locations.get(0).endsWith("@")){
            coords = getCircleCoords(numOfNodes, this.getSize());
        }
        else{
            for(int i=0; i<locations.size(); i++){
                
                String[] result = locations.get(i).substring(1).split(",");
                coords[i] = new Point(Integer.valueOf(result[0]), Integer.valueOf(result[1]));
            }
        }
        
        for(int i=0; i<numOfNodes; i++){
            nodes.add(new Node(Integer.valueOf(labels.get(i)), coords[i], this));
            this.add(nodes.get(i));
        }
    }
    
    private void LoadgraphEdges(String node1, String node2, boolean directed, boolean weighted, double weight){
        
        Node n1 = null, n2 = null;
        
        for(int i=0; i<nodes.size(); i++){
            if(nodes.get(i).label == Integer.valueOf(node1)){
                n1=nodes.get(i);
            }
            if(nodes.get(i).label == Integer.valueOf(node2)){
                n2=nodes.get(i);
            }
        }
        
        Edge e = new Edge(n1, n2, directed, weighted, weight, this.getSize());
        edges.add(e);
        this.add(e);
        
        n1.resize(1);
        n2.resize(1);
    }
    
    
    //Creates the Adjacency Array for the current Graph
    public double[][] adjacencyArray(List<Node> nodesList, List<Edge> edgesList){
        
        int numOfNodes = nodesList.size();
        int numOfEdges = edgesList.size();
        
        double[][] adjMatrix = new double[numOfNodes][numOfNodes];
        
        for(int i=0; i<numOfNodes; i++){
            for(int j=0; j<numOfNodes; j++){
                if(i!=j){
                    adjMatrix[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
        
        for(int i=0; i<numOfEdges; i++){
            adjMatrix[edgesList.get(i).node1.myListPosition()][edgesList.get(i).node2.myListPosition()] = edgesList.get(i).weight;
            
            if(!graphIsDirected()){
                adjMatrix[edgesList.get(i).node2.myListPosition()][edgesList.get(i).node1.myListPosition()] = edgesList.get(i).weight;
            }

        }
        
        return adjMatrix;
    }

    
    // Adds a new node after user request
    public void userNewNode(Point location){
        
        Point nodeLocation  = new Point();
        nodeLocation.x = location.x -10;
        nodeLocation.y = location.y -10;
        
        if(lastLabel>0){
            nodes.add(new Node(++lastLabel, nodeLocation, this));
        }
        else{
            nodes.add(new Node(nodesCounter++, nodeLocation, this));
        }
        if(parent.resultsPaneUse){
            resetNodesColor();
        }
        
        
        draft = true;
        
        parent.UpdateInfoPanel("");
    }
    
    
    // Adds a new edge after user request
    public void userConnectNodes(Node n2, boolean directed, boolean weighted, double weight){
        
        if(readyToConnect.equals(n2)){
            readyToConnect = null;
            return;
        }
        
        Edge e = new Edge(readyToConnect, n2, directed, weighted, weight, this.getSize());
        
        if(edgeExists(e)){
            readyToConnect = null;
            return;
        }
        
        if(!graphIsDirected()){
            e = new Edge(n2, readyToConnect, directed, weighted, weight, this.getSize());
            if(edgeExists(e)){
                readyToConnect = null;
                return;
            }
        }
        
        edges.add(e);
        this.add(e);
        
        readyToConnect.resize(1);
        n2.resize(1);

        this.repaint();
        
        readyToConnect = null;
        
        propertiesMenu.setEnabled(false);
        draft = true;
        
        if(parent.resultsPaneUse){
            resetNodesColor();
        }
        
        parent.UpdateInfoPanel("");
    }
    
    // Disconnects 2 nodes, deleting the edge that is between them
    public void userDisconnectNodes(Node n2){
        
        for(int i=0; i<edges.size(); i++){
            if(readyToDisconnect.equals(edges.get(i).node1) && n2.equals(edges.get(i).node2)){
                this.remove(edges.get(i));
                edges.remove(edges.get(i));
                draft = true;
                readyToDisconnect.resize(-1);
                n2.resize(-1);
                this.repaint();
            }
        }
        
        if(!graphIsDirected()){
            for(int i=0; i<edges.size(); i++){
                if(readyToDisconnect.equals(edges.get(i).node2) && n2.equals(edges.get(i).node1)){
                    this.remove(edges.get(i));
                    edges.remove(edges.get(i));
                    draft = true;
                    readyToDisconnect.resize(-1);
                    n2.resize(-1);
                    this.repaint();
                }
            }
        }
        
        readyToDisconnect = null;
        
        if(edges.size()==0) propertiesMenu.setEnabled(true);
        
        if(parent.resultsPaneUse){
            if(nodes.size()>0){
                resetNodesColor();
            }
        }
        
        parent.UpdateInfoPanel("");
    }
    
    // Checks if the desired edge exists already in the Graph
    public boolean edgeExists(Edge e){
        for(int i=0; i<edges.size(); i++){
            if(e.node1.equals(edges.get(i).node1) && e.node2.equals(edges.get(i).node2)){
                System.out.println("edge already exists");
                return true;
            }
        }
        return false;
    }
    
    
    // Deletes a node and all his edges after user request
    public void userDeleteNode(Node node){
        
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
        
        draft = nodes.size()>0;
        
        if(nodes.size() == 0) {
            clearMenuItem.setEnabled(false);
        }
        else{
            if(parent.resultsPaneUse){
                resetNodesColor();
            }
        }
        
        if(edges.size()>0) {
            propertiesMenu.setEnabled(false);
        }
        else{
            propertiesMenu.setEnabled(true);
        }
        
        parent.UpdateInfoPanel("");
    }
    
    
    // Creates the random Graph
    public void RandomGraph(double density, int numOfNodes){
        
        graphInUse = true;
        draft = true;
        clearMenuItem.setEnabled(true);
        
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
                    edges.add(new Edge(nodes.get(i), nodes.get(j), false, false, 1.0, this.getSize()));
                    this.add(edges.get(edges.size()-1));
                    
                    // Resize nodes i, j for every new (i,j) edge
                    nodes.get(i).resize(1);
                    nodes.get(j).resize(1);
                    
                }
            }
        }
        
        directedGraphMenuItem.setState(false);
        weightedGraphMenuItem.setState(false);
        
        if(edges.size()>0){
            propertiesMenu.setEnabled(false);
        }

        this.repaint();
        parent.UpdateInfoPanel("Erdős–Rényi Random Graph");
        parent.UpdateAlgorithmName("");
    }
    
    
    public void SmallWorldGraph(int numOfNodes, int p, int Z){
        
        graphInUse = true;
        draft = true;
        clearMenuItem.setEnabled(true);
        
        Point[] coords = getCircleCoords(numOfNodes, this.getSize());
        
        // Initializing Nodes
        for(int i=0; i<numOfNodes; i++){
            nodes.add(new Node(nodesCounter++, coords[i], this));
            this.add(nodes.get(i));
        }
        
        // Initializing Edges
        for(int i=0; i<numOfNodes; i++){
            
            for(int j=0; j<=(Z/2); j++){
                int position = i+j;
                
                if(position!=i){
                    if(position >= nodes.size()) position -= nodes.size();
                    
                    edges.add(new Edge(nodes.get(i), nodes.get(position), false, false, 1.0, this.getSize()));
                    this.add(edges.get(edges.size()-1));
                    
                    nodes.get(i).resize(1);
                    nodes.get(position).resize(1);
                }
            }
        }
        
        boolean correctEdge;
        
        // Rewiring Edges
        for(int i=0; i<numOfNodes; i++){
            for(int j=0; j<edges.size(); j++){
                if(nodes.get(i).equals(edges.get(j).node1) || nodes.get(i).equals(edges.get(j).node2)){
                    int rewire = (int)(Math.random() * (101));
                    
                    if(rewire > p){
                        this.remove(edges.get(j));
                        edges.get(j).node1.resize(-1);
                        edges.get(j).node2.resize(-1);
                        edges.remove(j);
                        
                        correctEdge = false;
                        
                        do{
                            int newNode = (int)(Math.random() * (numOfNodes));
                            
                            if(newNode != i){
                                Edge e = new Edge(nodes.get(i), nodes.get(newNode), false, false, 1.0, this.getSize());
                                
                                if(!edgeExists(e)){
                                    edges.add(e);
                                    this.add(e);
                                    
                                    nodes.get(i).resize(1);
                                    nodes.get(newNode).resize(1);
                                    
                                    correctEdge = true;
                                }
                            }
                            
                        }while(!correctEdge);
                    }
                }
            }
        }
        
        directedGraphMenuItem.setState(false);
        weightedGraphMenuItem.setState(false);
        
        propertiesMenu.setEnabled(false);
        
        this.repaint();
        parent.UpdateInfoPanel("Small World Graph");
        parent.UpdateAlgorithmName("");
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
