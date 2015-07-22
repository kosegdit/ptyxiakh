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
public class ClosenessCentrality {
    
    MainFrame parent;
    JTable resultsTable = new JTable();
    boolean normalized;
    
    
    public ClosenessCentrality(MainFrame frame, boolean normalized){
        
        parent = frame;
        this.normalized = normalized;
    }
    
    
    public void CalculateCloseness(List<Node> nodes){
        
        int numOfNodes = nodes.size();
        double[][] floydArray = parent.previewPanel.adjacencyArray();
        double currentCloseness, temp;
        List<Double> closeness = new ArrayList<>();
        Object[][] results = new Object[numOfNodes][2];
        
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
            
            closeness.add(currentCloseness);
        }
        
        for(int i=0; i<numOfNodes; i++){
            results[i][0] = nodes.get(i).label;
            temp = 1/closeness.get(i);
            
            if(normalized){
                temp = temp/(numOfNodes-1);
                Object[] column = {"Node", "Normalized Closeness"};
                resultsTable = new JTable(results, column);
            }
            else{
                Object[] column = {"Node", "Closeness"};
                resultsTable = new JTable(results, column);
            }
            
            if(closeness.get(i)!=Double.POSITIVE_INFINITY){
                    results[i][1] = (double) Math.round(temp*10000)/10000;
            }
            else{
                results[i][1] = Double.NaN;
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
