package ptyxiakh;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author kostas
 */
public class ClosenessCentrality {
    
    MainFrame parent;
    List<Double> closeness = new ArrayList<>();
    List<Integer> reaches = new ArrayList<>();
    boolean normalized;
    
    
    public ClosenessCentrality(MainFrame frame, boolean normalized){
        
        parent = frame;
        this.normalized = normalized;
    }
    
    
    public List<Double> CalculateCloseness(List<Node> nodesList, List<Edge> edgesList){
        
        int numOfNodes = nodesList.size();
        double[][] floydArray = parent.previewPanel.adjacencyArray(nodesList, edgesList);
        double currentCloseness, temp;
        
        for(int k=0; k<numOfNodes; k++){
            for(int i=0; i<numOfNodes; i++){
                if(floydArray[i][k]<Double.POSITIVE_INFINITY) {
                    for(int j=0;j<numOfNodes;j++){
                      floydArray[i][j]=Math.min(floydArray[i][j],floydArray[i][k]+floydArray[k][j]);
                    }
                }
            }
        }
        
        for(int i=0; i<numOfNodes; i++){
            currentCloseness = 0;
            
            for(int j=0; j<numOfNodes; j++){
                currentCloseness = currentCloseness + floydArray[i][j];
            }
            
            if(currentCloseness == Double.POSITIVE_INFINITY){
                reaches.add(0);
            }
            else{
                reaches.add(1);
            }
            
            temp = 1/currentCloseness;
            if(normalized){
                closeness.add(temp/(numOfNodes-1));
            }
            else{
                closeness.add(temp);
            }
        }
        
        return closeness;
    }
    
    
    public void DisplayCloseness(List<Node> nodesList){
        
        int numOfNodes = nodesList.size();
        JTable resultsTable;
        
        Object[][] results = new Object[numOfNodes][2];
        
        for(int i=0; i<numOfNodes; i++){
            results[i][0] = nodesList.get(i).label;
            
            if(reaches.get(i)==0){
                results[i][1] = Double.NaN;
            }
            else{
                results[i][1] = (double) Math.round(closeness.get(i)*10000)/10000;
            }
        }
        
        if(normalized){
            Object[] column = {"Node", "Normalized Closeness"};
            resultsTable = new JTable(results, column);
        }
        else{
            Object[] column = {"Node", "Closeness"};
            resultsTable = new JTable(results, column);
        }
        
        DisplayCentralities.DisplayResults(resultsTable, parent);
    }
}
