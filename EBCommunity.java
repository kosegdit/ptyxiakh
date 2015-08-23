package ptyxiakh;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author kostas
 */
public class EBCommunity {
    
    MainFrame parent;
    
    List<Node> copyGraphNodes = new ArrayList<>();
    List<Edge> copyGraphEdges = new ArrayList<>();
    List<Edge> removedEdges = new ArrayList<>();
    
    List<Double> graphEdgeCentral;
    
    List<List<List<Integer>>> stepComponents = new ArrayList<>();
    
    int[] visited;
    int[] cc;
    
    double[][] connectivityMatrix;

    
    public EBCommunity(MainFrame frame){
        
        parent = frame;
    }
    
    
    public void CalculateEBC(List<Node> nodesList, List<Edge> edgesList){
        
        // Get a copy of the original Graph
        GetGraphCopy(nodesList, edgesList);
        
        EdgeBetweennessCentrality edgeCentral = new EdgeBetweennessCentrality(parent);
        
        boolean done = false;
        
        do{
            graphEdgeCentral = edgeCentral.CalculateEdgeBetweenness(copyGraphNodes, copyGraphEdges);

            // Get the adjacency array
            connectivityMatrix = parent.previewPanel.adjacencyArray(copyGraphNodes, copyGraphEdges);

            // Adjust the adjacency array if the Graph is Directed or Weighted
            if(parent.previewPanel.graphIsDirected() || parent.previewPanel.graphIsWeighted()){
                for(int i=0; i<connectivityMatrix.length; i++){
                    for(int j=i+1; j<connectivityMatrix.length; j++){
                        if(connectivityMatrix[i][j] != Double.POSITIVE_INFINITY || connectivityMatrix[j][i] != Double.POSITIVE_INFINITY){
                                connectivityMatrix[i][j] = 1.0;
                                connectivityMatrix[j][i] = 1.0;
                        }
                    }
                }
            }

            GraphSearch();

            List<List<Integer>> nodeComponents = new ArrayList<>();

            int current = 1;
            int seen = 0;

            do{
                List<Integer> currentComponent = new ArrayList<>();

                for(int i=0; i<cc.length; i++){
                    if(cc[i] == current){
                        currentComponent.add(copyGraphNodes.get(i).label);
                        seen++;
                    }
                }
                nodeComponents.add(currentComponent);
                current++;

            }while(seen<cc.length);
            
            stepComponents.add(nodeComponents);

            int maxEdge = 0;
            
            for(int i=1; i<graphEdgeCentral.size(); i++){
                if(graphEdgeCentral.get(i) > graphEdgeCentral.get(maxEdge)){
                    maxEdge = i;
                }
            }
            
            if(copyGraphEdges.size()>0){
                removedEdges.add(copyGraphEdges.get(maxEdge));
                copyGraphEdges.remove(maxEdge);
            }
            else{
                done = true;
            }

            graphEdgeCentral.clear();
            
        }while(copyGraphEdges.size()>=0 && !done);
        
    }
    
    
    public void DisplayEBCommunity(){
        
        int totalRows = 0;
        int currentRow = 0;
        String result;
        
        for(int i=0; i<stepComponents.size(); i++){
            totalRows += stepComponents.get(i).size();
        }
        
        JTable resultsTable;
        
        Object[][] results = new Object[totalRows][4];
        Object[] column = {"Step", "Components", "Nodes", "Edge"};
        
        for(int i=0; i<stepComponents.size(); i++){
            results[currentRow][0] = i+1;
            
            if(i<=removedEdges.size()-1){
                results[currentRow][3] = removedEdges.get(i).label;
            }
            else{
                results[currentRow][3] = "No Edges";
            }
            
            results[currentRow][1] = stepComponents.get(i).size();
            
            for(int j=0; j<stepComponents.get(i).size(); j++){
                result = "";
                
                for(int k=0; k<stepComponents.get(i).get(j).size(); k++){
                    result += ", " + stepComponents.get(i).get(j).get(k);
                }
                
                results[currentRow+j][2] = result.substring(2);
            }
            
            currentRow = currentRow + stepComponents.get(i).size();
        }
        
        results[0][0] = "1: Init";
        
        resultsTable = new JTable(results, column);
        DisplayCentralities.DisplayResults(resultsTable, parent);
    }
    
    
    private void GraphSearch(){
        
        int numOfNodes = copyGraphNodes.size();
        int ccNumber = 1;
        
        visited = new int[numOfNodes];
        cc = new int[numOfNodes];
        
        for(int i=0; i<numOfNodes; i++){
            if(visited[i] == 0){
                Dfs(i, ccNumber);
                ccNumber++;
            }
        }
    }
    
    
    private void Dfs(int nodePosition, int ccNumber){
        
        int numOfNodes = copyGraphNodes.size();
        
        cc[nodePosition] = ccNumber;
        visited[nodePosition] = 1;
        
        for(int i=0; i<numOfNodes; i++){
            if(connectivityMatrix[nodePosition][i] == 1.0){
                if(visited[i] == 0){
                    Dfs(i, ccNumber);
                }
            }
        }
    }
    
    
    private void GetGraphCopy(List<Node> nodesList, List<Edge> edgesList){
        
        int numOfNodes = nodesList.size();
        int numOfEdges = edgesList.size();
        
        // Initializing the Graph Copy
        for(int i=0; i<numOfNodes; i++){
            copyGraphNodes.add(nodesList.get(i));
        }
        for(int i=0; i<numOfEdges; i++){
            copyGraphEdges.add(edgesList.get(i));
        }
    }
}
