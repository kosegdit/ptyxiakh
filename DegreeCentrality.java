package ptyxiakh;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 *
 * @author kostas
 */
public class DegreeCentrality {
    
    MainFrame parent;
    JTable resultsTable = new JTable();
    boolean directed, weighted, normalized;
    
    
    public DegreeCentrality(MainFrame frame, boolean graphDirected, boolean graphWeighted, boolean normalized){
        
        parent = frame;
        directed = graphDirected;
        weighted = graphWeighted;
        this.normalized = normalized;
    }
    
    
    public void CalculateDegree(List<Node> nodes, List<Edge> edges){
        
        if(directed){
            if(weighted){
                DirectedWeightedDegree(nodes, edges);
            }
            else{
                DirectedUnweightedDegree(nodes, edges);
            }
        }
        else{
            if(weighted){
                UndirectedWeightedDegree(nodes, edges);
            }
            else{
                UndirectedUnweightedDegree(nodes, edges);
            }
        }
    }
    
    
    public void UndirectedUnweightedDegree(List<Node> nodes, List<Edge> edges){
        
        List<Double> undirectedUnweightedDegree = new ArrayList<>();
        int numOfNodes = nodes.size();
        int numOfEdges = edges.size();
        double currentDegree;
        Object[][] results = new Object[numOfNodes][2];
        
        for(int i=0; i<numOfNodes; i++){
            currentDegree = 0;
            
            for(int j=0; j<numOfEdges; j++){
                if(nodes.get(i).equals(edges.get(j).node1) || nodes.get(i).equals(edges.get(j).node2)){
                    currentDegree++;
                }
            }
            
            undirectedUnweightedDegree.add(currentDegree); 
        }
        
        if(normalized){
            for(int i=0; i< undirectedUnweightedDegree.size(); i++){
                undirectedUnweightedDegree.set(i, (undirectedUnweightedDegree.get(i)/(numOfNodes-1)));
            }
            
            Object[] normColumn = {"Node", "Normalized Degree"};
            resultsTable = new JTable(results, normColumn);
        }
        else{
            Object[] column = {"Node", "Degree"};
            resultsTable = new JTable(results, column);
        }
        
        for(int i=0; i<numOfNodes; i++){
                results[i][0] = nodes.get(i).label;
                if(normalized){
                    results[i][1] = (double) Math.round(undirectedUnweightedDegree.get(i)*1000)/1000;
                }
                else{
                    results[i][1] = (int)((double)undirectedUnweightedDegree.get(i));
                }
            }
        
        DisplayResults(resultsTable);
    }
    
    
    public void UndirectedWeightedDegree(List<Node> nodes, List<Edge> edges){
        
        List<Double> undirectedWeightedDegree = new ArrayList<>();
        List<Double> edgeNormalizedDegree = new ArrayList<>();
        List<Integer> neighborEdges = new ArrayList<>();
        int numOfNodes = nodes.size();
        int numOfEdges = edges.size();
        double currentDegree;
        double edgesCounter;
        
        for(int i=0; i<numOfNodes; i++){
            currentDegree = 0;
            neighborEdges.add(i, 0);
            
            for(int j=0; j<numOfEdges; j++){
                if(nodes.get(i).equals(edges.get(j).node1) || nodes.get(i).equals(edges.get(j).node2)){
                    currentDegree = currentDegree + edges.get(j).weight;
                    neighborEdges.set(i, (neighborEdges.get(i)+1));
                }
            }
            
            edgesCounter = neighborEdges.get(i);
            undirectedWeightedDegree.add(currentDegree);
            edgeNormalizedDegree.add(currentDegree/edgesCounter);
        }
        
        if(normalized){
            Object[] normColumn = {"Node", "Normalized Degree"};
            Object[][] results = new Object[numOfNodes][2];
            resultsTable = new JTable(results, normColumn);

            for(int j=0; j<numOfNodes; j++){
                results[j][0] = nodes.get(j).label;
                results[j][1] = (double) Math.round(edgeNormalizedDegree.get(j)/(numOfNodes-1)*1000)/1000;
            }
        }
        else{
            Object[] column = {"Node", "Degree", "Edge Normalized Degree"};
            Object[][] results = new Object[numOfNodes][3];
            resultsTable = new JTable(results, column);

            for(int j=0; j<numOfNodes; j++){
                results[j][0] = nodes.get(j).label;
                results[j][1] = (double) Math.round(undirectedWeightedDegree.get(j)*1000)/1000;
                results[j][2] = (double) Math.round(edgeNormalizedDegree.get(j)*1000)/1000;
            }
        }
        
        DisplayResults(resultsTable);
    }
    
    
    public void DirectedUnweightedDegree(List<Node> nodes, List<Edge> edges){
        
        List<Double> inDegree = new ArrayList<>();
        List<Double> outDegree = new ArrayList<>();
        int numOfNodes = nodes.size();
        int numOfEdges = edges.size();
        double currentInDegree, currentOutDegree;
        Object[][] results = new Object[numOfNodes][3];
        
        for(int i=0; i<numOfNodes; i++){
            currentInDegree = 0;
            currentOutDegree = 0;
            
            for(int j=0; j<numOfEdges; j++){
                if(nodes.get(i).label == edges.get(j).node1.label){
                    currentOutDegree++;
                }
                if(nodes.get(i).label == edges.get(j).node2.label){
                    currentInDegree++;
                }
            }
            
            inDegree.add(currentInDegree);
            outDegree.add(currentOutDegree);
        }

        if(normalized){
            for(int i=0; i< numOfNodes; i++){
                inDegree.set(i, (inDegree.get(i)/(numOfNodes-1)));
                outDegree.set(i, (outDegree.get(i)/(numOfNodes-1)));
            }
            
            Object[] normColumn = {"Node", "Normalized In Degree", "Normalized Out Degree"};
            resultsTable = new JTable(results, normColumn);
        }
        else{
            Object[] column = {"Node", "In Degree", "Out Degree"};
            resultsTable = new JTable(results, column);
        }
        
        for(int i=0; i<numOfNodes; i++){
                results[i][0] = nodes.get(i).label;
                if(normalized){
                    results[i][1] = (double) Math.round(inDegree.get(i)*1000)/1000;
                    results[i][2] = (double) Math.round(outDegree.get(i)*1000)/1000;
                }
                else{
                    results[i][1] = (int)((double)inDegree.get(i));
                    results[i][2] = (int)((double)outDegree.get(i));
                }
            }
        
        DisplayResults(resultsTable);
    }
    
    
    public void DirectedWeightedDegree(List<Node> nodes, List<Edge> edges){
        
        List<Double> inDegree = new ArrayList<>();
        List<Double> outDegree = new ArrayList<>();
        List<Double> edgeNormalizedInDegree = new ArrayList<>();
        List<Double> edgeNormalizedOutDegree = new ArrayList<>();
        List<Integer> neighborInEdges = new ArrayList<>();
        List<Integer> neighborOutEdges = new ArrayList<>();
        
        int numOfNodes = nodes.size();
        int numOfEdges = edges.size();
        double currentInDegree, currentOutDegree;
        double inEdgesCounter, outEdgesCounter;
        
        for(int i=0; i<numOfNodes; i++){
            currentInDegree = 0;
            currentOutDegree = 0;
            neighborInEdges.add(i, 0);
            neighborOutEdges.add(i, 0);
            
            for(int j=0; j<numOfEdges; j++){
                if(nodes.get(i).label == edges.get(j).node1.label){
                    currentOutDegree = currentOutDegree + edges.get(j).weight;
                    neighborOutEdges.set(i, (neighborOutEdges.get(i)+1));
                }
                else if(nodes.get(i).label == edges.get(j).node2.label){
                    currentInDegree = currentInDegree + edges.get(j).weight;
                    neighborInEdges.set(i, (neighborInEdges.get(i)+1));
                }
            }
            
            inEdgesCounter = neighborInEdges.get(i);
            outEdgesCounter = neighborOutEdges.get(i);
            
            inDegree.add(currentInDegree);
            outDegree.add(currentOutDegree);
            
            edgeNormalizedInDegree.add(currentInDegree/inEdgesCounter);
            edgeNormalizedOutDegree.add(currentOutDegree/outEdgesCounter);
        }
        
        if(normalized){
            Object[] normColumn = {"Node", "Normalized In Degree", "Normalized Out Degree"};
            Object[][] results = new Object[numOfNodes][3];
            resultsTable = new JTable(results, normColumn);
            
            for(int j=0; j<numOfNodes; j++){
                results[j][0] = nodes.get(j).label;
                results[j][1] = (double) Math.round(edgeNormalizedInDegree.get(j)/(numOfNodes-1)*1000)/1000;
                results[j][2] = (double) Math.round(edgeNormalizedOutDegree.get(j)/(numOfNodes-1)*1000)/1000;
            }
        }
        else{
            Object[] column = {"Node", "In Degree", "Edge Normalized In Degree", "Out Degree", "Edge Normalized Out Degree"};
            Object[][] results = new Object[numOfNodes][5];
            resultsTable = new JTable(results, column);

            for(int j=0; j<numOfNodes; j++){
                results[j][0] = nodes.get(j).label;
                results[j][1] = (double) Math.round(inDegree.get(j)*1000)/1000;
                results[j][2] = (double) Math.round(edgeNormalizedInDegree.get(j)*1000)/1000;
                results[j][3] = (double) Math.round(outDegree.get(j)*1000)/1000;
                results[j][4] = (double) Math.round(edgeNormalizedOutDegree.get(j)*1000)/1000;
            }
        }
        
        DisplayResults(resultsTable);
    }
    
    
    public void DisplayResults(JTable resultsTable){
        
        resultsTable.setEnabled(false);
        resultsTable.setCellSelectionEnabled(false);
        resultsTable.setBackground(new Color(240,240,240));

        JTableHeader header = resultsTable.getTableHeader();
        header.setDefaultRenderer(new LostHeaderRenderer());

        parent.resultsScrollPane.getViewport().add(resultsTable);
    }
}