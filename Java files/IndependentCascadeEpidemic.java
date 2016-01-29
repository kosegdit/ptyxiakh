/*
 * This is a class file for the program AviNet
 *
 * Copyright (c) Segditsas Konstantinos, 2015
 * Email: kosegdit@gmail.com
 *
 */
package ptyxiakh;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JTable;

/**
 *
 * @author kostas
 */
public class IndependentCascadeEpidemic {
    
    MainFrame parent;
    
    List<Node> initNodes = new ArrayList<>();
    List<Node> infectedNodes = new ArrayList<>();
    List<Node> tempInfectedNodes = new ArrayList<>();
    
    
    int iterations = 0;
    
    
    public IndependentCascadeEpidemic(MainFrame frame){
        
        parent = frame;
    }
    
    
    public void CalculateIndependentCascde(List<Double> startingNodes, List<Double> edgesThresholds){
        
        int numOfNodes = parent.previewPanel.nodes.size();
        int numOfEdges = parent.previewPanel.edges.size();
                 
        Random rand = new Random();
        
        for(int i=0; i<numOfNodes; i++){
            if(startingNodes.get(i) == -1.0){
                initNodes.add(parent.previewPanel.nodes.get(i));
                tempInfectedNodes.add(parent.previewPanel.nodes.get(i));
            }
        }
        
        // Initializing the Epidemice Matrix
        double[][] epidemicMatrix = new double[numOfNodes][numOfNodes];
        
        for(int i=0; i<numOfNodes; i++){
            for(int j=0; j<numOfNodes; j++){
                if(i==j){
                    epidemicMatrix[i][j] = startingNodes.get(i);
                }
            }
        }
        
        for(int i=0; i<numOfEdges; i++){
            epidemicMatrix[parent.previewPanel.edges.get(i).node1.myListPosition()][parent.previewPanel.edges.get(i).node2.myListPosition()] = edgesThresholds.get(i);
            
            if(!parent.previewPanel.graphIsDirected()){
                epidemicMatrix[parent.previewPanel.edges.get(i).node2.myListPosition()][parent.previewPanel.edges.get(i).node1.myListPosition()] = edgesThresholds.get(i);
            }

        }
        
        boolean infectionTerminated;
        
        do{
            List<Node> currentInfected = new ArrayList<>();
            infectionTerminated = true;
                    
            for(int i=0; i<tempInfectedNodes.size(); i++){
                int currentNode = tempInfectedNodes.get(i).myListPosition();

                for(int j=0; j<numOfNodes; j++){
                    if(j != currentNode){
                        if(epidemicMatrix[currentNode][j] != 0.0 && epidemicMatrix[j][j] != -1.0){
                            double possibility = (double)Math.round(rand.nextDouble()*1000)/1000;

                            if(possibility < epidemicMatrix[currentNode][j]){
                                currentInfected.add(parent.previewPanel.nodes.get(j));
                                epidemicMatrix[j][j] = -1.0;
                                infectionTerminated = false;
                            }
                        }
                    }
                }
            }

            if(!infectionTerminated){
                iterations++;
                
                tempInfectedNodes.clear();
                tempInfectedNodes.addAll(currentInfected);
                
                infectedNodes.addAll(currentInfected);
            }
            
        }while(!infectionTerminated);
    }
    
    
    public void DisplayIndependentCascade(){
        
        JTable resultsTable;
        String result = "";
        
        List<Node> totalInfectedNodes = new ArrayList<>();
        totalInfectedNodes.addAll(initNodes);
        totalInfectedNodes.addAll(infectedNodes);
        double coverage = totalInfectedNodes.size();
        
        Object[][] results = new Object[1][3];
        Object[] column = {"Iterations", "Infected Nodes", "Coverage"};
        
        results[0][0] = iterations;
        
        for(int i=0; i<initNodes.size(); i++){
            result += ", " + initNodes.get(i).label;
        }
        for(int i=0; i<infectedNodes.size(); i++){
            result += ", " + infectedNodes.get(i).label;
        }
        
        if(result.isEmpty()){
            results[0][1] = "empty";
        }
        else{
            results[0][1] = result.substring(2);
        }
        
        results[0][2] = (int)(100*(coverage/parent.previewPanel.nodes.size())) + "%";
        
        resultsTable = new JTable(results, column);
        
        for(int i=0; i<infectedNodes.size(); i++){
            infectedNodes.get(i).setNodeColor(Color.MAGENTA);
        }
        
        for(int i=0; i<initNodes.size(); i++){
            initNodes.get(i).setNodeColor(Color.RED);
        }
        
        parent.UpdateAlgorithmName("Results: Epidemics -> Independent Cascade");
        DisplayCentralities.DisplayResults(resultsTable, parent);
    }
}
