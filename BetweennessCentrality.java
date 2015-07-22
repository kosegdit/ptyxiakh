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
public class BetweennessCentrality {
    
    MainFrame parent;
    JTable resultsTable = new JTable();
    boolean normalized;
    
    
    public BetweennessCentrality(MainFrame frame, boolean normalized){
        
        parent = frame;
        this.normalized = normalized;
    }
    
    
    public void CalculateBetweenness(){
        
        int numOfNodes = parent.previewPanel.nodes.size();
        double[][] adjMatrix = parent.previewPanel.adjacencyArray();
        double currentBetweenness;
        Object[][] results = new Object[numOfNodes][2];
        
        List<Double> betweenness = new ArrayList<>();
        
        DijkstraAllPaths dijkstra = new DijkstraAllPaths();
        List<List<List<List<Integer>>>> graphPaths = dijkstra.DijkstraPaths(adjMatrix);
        
        for(int i=0; i<numOfNodes; i++){
            currentBetweenness = 0;
            for(int j=0; j<graphPaths.size(); j++){
                if(i!=j){
                    for(int k=0; k<graphPaths.get(j).size(); k++){
                        for(int l=0; l<graphPaths.get(j).get(k).size(); l++){
                            if(graphPaths.get(j).get(k).get(l).size()>2){
                                for(int t=1; t<(graphPaths.get(j).get(k).get(l).size()-1); t++){
                                    if(i==graphPaths.get(j).get(k).get(l).get(t)){
                                        currentBetweenness += 1.0/graphPaths.get(j).get(k).size();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            betweenness.add(currentBetweenness);
        }
        
        for(int i=0; i<numOfNodes; i++){
            results[i][0] = parent.previewPanel.nodes.get(i).label;
            
            if(normalized){
                betweenness.set(i, betweenness.get(i)/((numOfNodes-1)*(numOfNodes-2)));
                Object[] column = {"Node", "Normalized SPBC"};
                resultsTable = new JTable(results, column);
            }
            else{
                Object[] column = {"Node", "SPBC"};
                resultsTable = new JTable(results, column);
            }
            
            results[i][1] = (double) Math.round(betweenness.get(i)*10000)/10000;
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
