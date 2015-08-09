package ptyxiakh;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author kostas
 */
public class EdgeBetweennessCentrality {
    
    MainFrame parent;
    List<Double> edgeBetweenness = new ArrayList<>();
    
    
    public EdgeBetweennessCentrality(MainFrame frame){
        
        parent = frame;
    }
    
    
    
    public List<Double> CalculateEdgeBetweenness(List<Node> nodesList, List<Edge> edgesList){
        
        int numOfEdges = edgesList.size();
        double[][] adjMatrix = parent.previewPanel.adjacencyArray(nodesList, edgesList);
        double currentEdgeBetweenness;

        DijkstraAllPaths dijkstra = new DijkstraAllPaths();
        List<List<List<List<Integer>>>> graphPaths = dijkstra.DijkstraPaths(adjMatrix);
        
        for(int i=0; i<numOfEdges; i++){
            int n1 = edgesList.get(i).node1.myListPosition();
            int n2 = edgesList.get(i).node2.myListPosition();
            currentEdgeBetweenness = 0;
            
            for(int j=0; j<graphPaths.size(); j++){
                for(int k=0; k<graphPaths.get(j).size(); k++){
                    for(int l=0; l<graphPaths.get(j).get(k).size(); l++){
                        if(graphPaths.get(j).get(k).get(l).size()>2){
                            for(int t=0; t<(graphPaths.get(j).get(k).get(l).size()-1); t++){
                                int w = t+1;
                                
                                if((n1 == graphPaths.get(j).get(k).get(l).get(t) || n1 == graphPaths.get(j).get(k).get(l).get(w)) 
                                        && (n2 == graphPaths.get(j).get(k).get(l).get(t) || n2 == graphPaths.get(j).get(k).get(l).get(w))){
                                    
                                    currentEdgeBetweenness += 1.0/graphPaths.get(j).get(k).size();
                                }
                            }
                        }
                    }
                }
            }
            
            edgeBetweenness.add(currentEdgeBetweenness);
        }
        
        return edgeBetweenness;
    }
    
    
    public void DisplayEdgeBetweenness(List<Edge> edgesList){
        
        int numOfEdges = edgesList.size();
        JTable resultsTable;
        
        Object[][] results = new Object[numOfEdges][2];
        Object[] column = {"Edge", "Betweenness Centrality"};
        
        for(int i=0; i<numOfEdges; i++){
            results[i][0] = edgesList.get(i).label;
            results[i][1] = (double) Math.round(edgeBetweenness.get(i)*10000)/10000;
        }
        
        resultsTable = new JTable(results, column);
        
        DisplayCentralities.DisplayResults(resultsTable, parent);
    }
}
