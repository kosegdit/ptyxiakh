package ptyxiakh;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author kostas
 */
public class PageRankCentrality {
    
    MainFrame parent;
    double alpha = 0.85;
    double epsilon = 0.00000001;
    int[] danglingNodes, e;
    double[] pageRank, pageRankCopy;
    List<Double> graphOutDegrees;
    List<Node> nodesRank;
    double[][] H, G;
    boolean converged = false;
    
    
    public PageRankCentrality(MainFrame frame){
        
        parent = frame;
    }
    
    
    public void CalculatePageRank(List<Node> nodesList, List<Edge> edgesList){
        
        int n = nodesList.size();
        danglingNodes = new int[n];
        e = new int[n];
        pageRank = new double[n];
        double[] lastPageRank = new double[n];
        double[] error = new double[n];
        double totalError;
        
        DegreeCentrality degree = new DegreeCentrality(parent, true, false, false);
        List<List<Double>> graphDegrees = degree.DirectedUnweightedDegree(parent.previewPanel.nodes, parent.previewPanel.edges);
        graphOutDegrees = graphDegrees.get(1);
        
        double[][] adjMatrix = parent.previewPanel.adjacencyArray(nodesList, edgesList);
        H = new double[n][n];
        G = new double[n][n];
        
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(adjMatrix[i][j] == 1){
                    H[i][j] = 1.0/graphOutDegrees.get(i);
                }
                else{
                    H[i][j] = 0.0;
                }
            }
        }
        
        for(int i=0; i<n; i++){
            e[i] = 1;
            pageRank[i] = 1.0/n;
            if(graphOutDegrees.get(i) == 0){
                danglingNodes[i] = 1;
            }
            else{
                danglingNodes[i] = 0;
            }
        }
        
        // Initializing the G matrix
        // G = alpha*H + (alpha*danglingNodes + (1-alpha)*e_reverse)*(1/n)*e
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                G[i][j] = alpha*H[i][j] + ((alpha*danglingNodes[i]) + ((1.0-alpha)*e[i])) * ((1.0/n) * e[j]);
            }
        }
        
        // Computing Page Rank
        do{
            totalError = 0.0;
            
            for(int i=0; i<n; i++){
                lastPageRank[i] = pageRank[i];
                pageRank[i] = 0.0;
            }

            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    pageRank[i] = pageRank[i] + lastPageRank[j]*G[j][i];
                }
            }

            for(int i=0; i<n; i++){
                error[i] = Math.abs(pageRank[i]-lastPageRank[i]);
                totalError = totalError + error[i];
            }
            
            if(totalError <= epsilon){
                converged = true;
            }
            
        }while(!converged);
        
    }
    
    
    public List<Node> NodesRank(List<Node> nodesList){
        
        pageRankCopy = new double[pageRank.length];
        nodesRank = new ArrayList<>();
        
        Node tempNode;
        int max;
        double tempPagerank;
        
        for(int i=0; i<pageRank.length; i++){
            pageRankCopy[i] = pageRank[i];
            nodesRank.add(nodesList.get(i));
        }

        for(int i=0; i<pageRankCopy.length; i++){
            max = i;
            
            for(int j=i+1; j<pageRankCopy.length; j++){
                if(pageRankCopy[j] > pageRankCopy[max]){
                    max = j;
                }
            }
            
            tempPagerank = pageRankCopy[i];
            pageRankCopy[i] = pageRankCopy[max];
            pageRankCopy[max] = tempPagerank;
            
            tempNode = nodesRank.get(i);
            nodesRank.set(i, nodesRank.get(max));
            nodesRank.set(max, tempNode);
        }
        
        return nodesRank;
    }
    
    
    public void DisplayPageRank(){
        
        JTable resultsTable;
        
        Object[][] results = new Object[pageRank.length][3];
        Object[] column = {"Rank", "Node", "Page Rank"};
        
        
        for(int i=0; i<pageRank.length; i++){
            results[i][0] = i+1;
            results[i][1] = nodesRank.get(i).label;
            results[i][2] = (double) Math.round(pageRankCopy[i]*100000)/100000;
        }
        
        resultsTable = new JTable(results, column);
        
        parent.UpdateAlgorithmName("Results: Centralities -> Page Rank");
        DisplayCentralities.DisplayResults(resultsTable, parent);
    }
}
