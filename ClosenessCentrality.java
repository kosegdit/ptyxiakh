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
    boolean normalized;
    
    
    public ClosenessCentrality(MainFrame frame, boolean normalized){
        
        parent = frame;
        this.normalized = normalized;
    }
    
    
    public List<Double> CalculateCloseness(){
        
        int numOfNodes = parent.previewPanel.nodes.size();
        double[][] floydArray = parent.previewPanel.adjacencyArray();
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
    
    
    public void DisplayCloseness(){
        
        int numOfNodes = parent.previewPanel.nodes.size();
        JTable resultsTable;
        
        Object[][] results = new Object[numOfNodes][2];
        
        for(int i=0; i<numOfNodes; i++){
            results[i][0] = parent.previewPanel.nodes.get(i).label;
            
            if(closeness.get(i)!=Double.POSITIVE_INFINITY){
                results[i][1] = (double) Math.round(closeness.get(i)*10000)/10000;
            }
            else{
                results[i][1] = Double.NaN;
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
