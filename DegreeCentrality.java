package ptyxiakh;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author kostas
 */
public class DegreeCentrality {
    
    MainFrame parent;
    boolean directed, weighted, normalized;
    
    List<Double> undirectedUnweightedDegree = new ArrayList<>();
    
    List<Double> undirectedWeightedDegree = new ArrayList<>();
    List<Double> edgeNormalizedDegree = new ArrayList<>();
    
    List<Double> inDegree = new ArrayList<>();
    List<Double> outDegree = new ArrayList<>();

    List<Double> edgeNormalizedInDegree = new ArrayList<>();
    List<Double> edgeNormalizedOutDegree = new ArrayList<>();
    
    
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
    
    
    public List<Double> UndirectedUnweightedDegree(List<Node> nodes, List<Edge> edges){
        
        int numOfNodes = nodes.size();
        int numOfEdges = edges.size();
        double currentDegree;
        
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
        }
        
        return undirectedUnweightedDegree;
    }
    
    
    public List<Double> UndirectedWeightedDegree(List<Node> nodes, List<Edge> edges){
        
        
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
            for(int i=0; i< undirectedWeightedDegree.size(); i++){
                undirectedWeightedDegree.set(i, (edgeNormalizedDegree.get(i)/(numOfNodes-1)));
            }
        }
        
        return undirectedWeightedDegree;
    }
    
    
    public List<List<Double>> DirectedUnweightedDegree(List<Node> nodes, List<Edge> edges){
        
        
        int numOfNodes = nodes.size();
        int numOfEdges = edges.size();
        List<List<Double>> directedUnweightedDegree = new ArrayList<>();
        double currentInDegree, currentOutDegree;

        
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
            for(int i=0; i< inDegree.size(); i++){
                inDegree.set(i, (inDegree.get(i)/(numOfNodes-1)));
                outDegree.set(i, (outDegree.get(i)/(numOfNodes-1)));
            }
        }
        
        directedUnweightedDegree.add(inDegree);
        directedUnweightedDegree.add(outDegree);
        
        return directedUnweightedDegree;
    }
    
    
    public List<List<Double>> DirectedWeightedDegree(List<Node> nodes, List<Edge> edges){
        
        
        List<Integer> neighborInEdges = new ArrayList<>();
        List<Integer> neighborOutEdges = new ArrayList<>();
        List<List<Double>> directedWeightedDegree = new ArrayList<>();
        
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
            for(int i=0; i< inDegree.size(); i++){
                inDegree.set(i, (edgeNormalizedInDegree.get(i)/(numOfNodes-1)));
                outDegree.set(i, (edgeNormalizedOutDegree.get(i)/(numOfNodes-1)));
            }
        }
        
        directedWeightedDegree.add(inDegree);
        directedWeightedDegree.add(outDegree);
        
        return directedWeightedDegree;
    }
    
    
    public void DisplayDegree(){
        
        int numOfNodes = parent.previewPanel.nodes.size();
        JTable resultsTable;
        
        if(directed){
            if(weighted){
                if(normalized){
                    Object[] column = {"Node", "Normalized In Degree", "Normalized Out Degree"};
                    Object[][] results = new Object[numOfNodes][3];
                    resultsTable = new JTable(results, column);

                    for(int i=0; i<numOfNodes; i++){
                        results[i][0] = parent.previewPanel.nodes.get(i).label;
                        results[i][1] = (double) Math.round(inDegree.get(i)*1000)/1000;
                        results[i][2] = (double) Math.round(outDegree.get(i)*1000)/1000;
                    }
                }
                else{
                    Object[] column = {"Node", "In Degree", "Edge Normalized In Degree", "Out Degree", "Edge Normalized Out Degree"};
                    Object[][] results = new Object[numOfNodes][5];
                    resultsTable = new JTable(results, column);

                    for(int i=0; i<numOfNodes; i++){
                        results[i][0] = parent.previewPanel.nodes.get(i).label;
                        results[i][1] = (double) Math.round(inDegree.get(i)*1000)/1000;
                        results[i][2] = (double) Math.round(edgeNormalizedInDegree.get(i)*1000)/1000;
                        results[i][3] = (double) Math.round(outDegree.get(i)*1000)/1000;
                        results[i][4] = (double) Math.round(edgeNormalizedOutDegree.get(i)*1000)/1000;
                    }
                }
            }
            else{
                Object[][] results = new Object[numOfNodes][3];
                
                for(int i=0; i<numOfNodes; i++){
                    results[i][0] = parent.previewPanel.nodes.get(i).label;
                    
                    if(normalized){
                        results[i][1] = (double) Math.round(inDegree.get(i)*1000)/1000;
                        results[i][2] = (double) Math.round(outDegree.get(i)*1000)/1000;
                    }
                    else{
                        results[i][1] = (int)((double)inDegree.get(i));
                        results[i][2] = (int)((double)outDegree.get(i));
                    }
                }
                
                if(normalized){
                    Object[] normColumn = {"Node", "Normalized In Degree", "Normalized Out Degree"};
                    resultsTable = new JTable(results, normColumn);
                }
                else{
                    Object[] column = {"Node", "In Degree", "Out Degree"};
                    resultsTable = new JTable(results, column);
                }
            }
        }
        else{
            if(weighted){
                if(normalized){
                    Object[] column = {"Node", "Normalized Degree"};
                    Object[][] results = new Object[numOfNodes][2];
                    resultsTable = new JTable(results, column);

                    for(int i=0; i<numOfNodes; i++){
                        results[i][0] = parent.previewPanel.nodes.get(i).label;
                        results[i][1] = (double) Math.round(undirectedWeightedDegree.get(i)*1000)/1000;
                    }
                }
                else{
                    Object[] column = {"Node", "Degree", "Edge Normalized Degree"};
                    Object[][] results = new Object[numOfNodes][3];
                    resultsTable = new JTable(results, column);

                    for(int i=0; i<numOfNodes; i++){
                        results[i][0] = parent.previewPanel.nodes.get(i).label;
                        results[i][1] = (double) Math.round(undirectedWeightedDegree.get(i)*1000)/1000;
                        results[i][2] = (double) Math.round(edgeNormalizedDegree.get(i)*1000)/1000;
                    }
                }
            }
            else{
                Object[][] results = new Object[numOfNodes][2];
                
                for(int i=0; i<numOfNodes; i++){
                    results[i][0] = parent.previewPanel.nodes.get(i).label;
                    
                    if(normalized){
                        results[i][1] = (double) Math.round(undirectedUnweightedDegree.get(i)*1000)/1000;
                    }
                    else{
                        results[i][1] = (int)((double)undirectedUnweightedDegree.get(i));
                    }
                }
                
                if(normalized){
                    Object[] normColumn = {"Node", "Normalized Degree"};
                    resultsTable = new JTable(results, normColumn);
                }
                else{
                    Object[] column = {"Node", "Degree"};
                    resultsTable = new JTable(results, column);
                }
            }
        }
        
        parent.UpdateAlgorithmName("Results: Centralities -> Degree");
        DisplayCentralities.DisplayResults(resultsTable, parent);
    }
}