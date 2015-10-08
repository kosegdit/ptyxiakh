/*
 * /*
 * *
 * * This is a class file for the program AviNet
 * *
 * * Copyright (c) Segditsas Konstantinos, 2015
 * * Email: kosegdit@gmail.com
 * *
 * * All rights reserved
 * *
 */

package ptyxiakh;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author kostas
 */
public class LinearThresholdEpidemic {
    
    MainFrame parent;
    
    List<Node> initNodes = new ArrayList<>();
    List<Node> infectedNodes = new ArrayList<>();
    
    int iterations = 0;
    
    
    public LinearThresholdEpidemic(MainFrame frame){
        
        parent = frame;
    }
    
    
    public void CalculateLinearThreshold(List<Double> nodesThresholds, List<Double> edgesThresholds){
        
        int numOfNodes = parent.previewPanel.nodes.size();
        int numOfEdges = parent.previewPanel.edges.size();
        
        // Initializing Lists
        for(int i=0; i<numOfNodes; i++){
            if(nodesThresholds.get(i) == -1.0){
                initNodes.add(parent.previewPanel.nodes.get(i));
            }
        }
        
        // Initializing the Epidemice Matrix
        double[][] epidemicMatrix = new double[numOfNodes][numOfNodes];
        
        for(int i=0; i<numOfNodes; i++){
            for(int j=0; j<numOfNodes; j++){
                if(i==j){
                    epidemicMatrix[i][j] = nodesThresholds.get(i);
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
            List<Integer> currentInfected = new ArrayList<>();
            infectionTerminated = true;
            
            for(int column=0; column<numOfNodes; column++){
                double threat = 0.0;

                if(epidemicMatrix[column][column] != -1.0){
                    for(int row=0; row<numOfNodes; row++){
                        if(row!=column){
                            if(epidemicMatrix[row][column] != 0.0 && epidemicMatrix[row][row] == -1.0){
                                threat += epidemicMatrix[row][column];
                            }
                        }
                    }

                    if(threat > epidemicMatrix[column][column]){
                        currentInfected.add(column);
                        infectionTerminated = false;
                    }
                }
            }
            
            if(!infectionTerminated){
                iterations++;
                
                for(int i=0; i<currentInfected.size(); i++){
                    int nodePosition = currentInfected.get(i);
                    epidemicMatrix[nodePosition][nodePosition] = -1.0;
                }
            }
            
        }while(!infectionTerminated);
        
        for(int i=0; i<epidemicMatrix.length; i++){
            if(epidemicMatrix[i][i] == -1.0 && !initNodes.contains(parent.previewPanel.nodes.get(i))){
                infectedNodes.add(parent.previewPanel.nodes.get(i));
            }
        }
    }
    
    
    public void DisplayLinearThreshold(){
        
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
        
        parent.UpdateAlgorithmName("Results: Epidemics -> Linear Threshold");
        DisplayCentralities.DisplayResults(resultsTable, parent);
    }
}
