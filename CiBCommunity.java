package ptyxiakh;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author kostas
 */
public class CiBCommunity {
    
    MainFrame parent;
    double s;
    double[][] adjacencyMatrix;
    
    List<List<Node>> cliques;
    
    
    public CiBCommunity(MainFrame frame, double s){
        parent = frame;
        this.s = s;
    }
    
    
    public void CalculateCiBC(List<Node> nodesList, List<Edge> edgesList){
        
        BetweennessCentrality betw = new BetweennessCentrality(parent, false);
        List<Double> graphBetweenness = betw.CalculateBetweenness(nodesList, edgesList);
        
        // Get the adjacency array
        adjacencyMatrix = parent.previewPanel.adjacencyArray(nodesList, edgesList);

        // Adjust the adjacency array if the Graph is Directed or Weighted
        if(parent.previewPanel.graphIsDirected() || parent.previewPanel.graphIsWeighted()){
            for(int i=0; i<adjacencyMatrix.length; i++){
                for(int j=i+1; j<adjacencyMatrix.length; j++){
                    if(adjacencyMatrix[i][j] != Double.POSITIVE_INFINITY || adjacencyMatrix[j][i] != Double.POSITIVE_INFINITY){
                            adjacencyMatrix[i][j] = 1.0;
                            adjacencyMatrix[j][i] = 1.0;
                    }
                }
            }
        }
        
        cliques = InitializationOfCliques(nodesList, graphBetweenness);
        
        CliqueMerging(edgesList);
        
        
    }
    
    
    private List<List<Node>> InitializationOfCliques(List<Node> nodesList, List<Double> graphBetweenness){
        
        int numOfNodes = nodesList.size();
        int[] nodeUsed = new int[numOfNodes];
        boolean firstMinFound;
        int minPosition;
        List<List<Node>> initCliques = new ArrayList<>();
        int finishedNodes = 0;
        
        do{
            firstMinFound = false;
            minPosition = -1;
            
            for(int i=0; i<graphBetweenness.size(); i++){
                if(!firstMinFound && nodeUsed[i] == 0){
                    firstMinFound = true;
                    minPosition = i;
                }

                if(firstMinFound && nodeUsed[i] == 0 && graphBetweenness.get(i) < graphBetweenness.get(minPosition)){
                    minPosition = i;
                }
            }

            nodeUsed[minPosition] = 1;
            finishedNodes++;

            List<Node> currentClique = new ArrayList<>();

            currentClique.add(nodesList.get(minPosition));

            for(int i=0; i<adjacencyMatrix.length; i++){
                if(i != minPosition){
                    if(adjacencyMatrix[minPosition][i] == 1.0 && nodeUsed[i] == 0){
                        currentClique.add(nodesList.get(i));
                        nodeUsed[i] = 1;
                        finishedNodes++;
                    }
                }
            }

            initCliques.add(currentClique);
            
        }while(finishedNodes < numOfNodes);
        
        return initCliques;
    }
    
    
    private List<List<Node>> CliqueMerging(List<Edge> edgesList){
        
        int cliqueAmount = cliques.size();
        int[][] cliqueMatrix = new int[cliqueAmount][cliqueAmount];
        
        
        for(int i=0; i<cliqueAmount; i++){
            for(int j=0; j<cliqueAmount; j++){
                for(int k=0; k<edgesList.size(); k++){
                    if(Contains(cliques.get(i), edgesList.get(k).node1) && Contains(cliques.get(j), edgesList.get(k).node2)
                            || Contains(cliques.get(i), edgesList.get(k).node2) && Contains(cliques.get(j), edgesList.get(k).node1))
                        cliqueMatrix[i][j]++;
                }
            }
        }
        
        Point merge = MaxFractal(cliqueAmount, cliqueMatrix);
        
        if(merge==null){
            return cliques;
        }
        
        cliques.get(merge.x).addAll(cliques.get(merge.y));
        cliques.remove(merge.y);
        
        if(cliques.size()>1){
            return CliqueMerging(edgesList);
        }
        
        return cliques;
    }
    
    private boolean Contains(List<Node> clique, Node node){
        
        for(Node n : clique){
            if(node.equals(n)){
                return true;
            }
        }
        
        return false;
    }
    
    
    private Point MaxFractal(int cliqueAmount, int[][] cliqueMatrix){
        
        double maxFractal = -1;
        double maxTemp;
        Point position = new Point();
        
        for(int i=0; i<cliqueAmount; i++){
            for(int j=0; j<cliqueAmount; j++){
                if(i==j) {
                    continue;
                }
                
                maxTemp = (double)cliqueMatrix[j][i]/(double)cliqueMatrix[i][i];
                
                if(maxTemp > maxFractal){
                    maxFractal = maxTemp;
                    position.x = i;
                    position.y = j;
                }
            }
        }
        
        if(maxFractal>=s){
            return position;
        }
        
        return null;
    }
    
    
    public void DisplayCiBC(){
        
        JTable resultsTable;
        
        int totalRows = cliques.size();
        String result;
        
        Object[][] results = new Object[totalRows][3];
        Object[] column = {"S", "Community", "Nodes"};
        
        results[0][0] = (double) Math.round(s*10000)/10000;
        
        for(int i=0; i<totalRows; i++){
            results[i][1] = i+1;
            result = "";
            
            for(int j=0; j<cliques.get(i).size(); j++){
                result += ", " + cliques.get(i).get(j).label;
            }
                
            results[i][2] = result.substring(2);
        }
        
        for(int i=0; i<cliques.size(); i++){
            Color color = ColorBands.getRandomColor();
            
            for(int j=0; j<cliques.get(i).size(); j++){
                cliques.get(i).get(j).setNodeColor(color);
            }
        }
        
        resultsTable = new JTable(results, column);
        DisplayCentralities.DisplayResults(resultsTable, parent);
    }
}
