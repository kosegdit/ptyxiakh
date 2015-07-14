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
    
    public List<Integer> generalDegree;
    public List<Integer> inDegree;
    public List<Integer> outDegree;
    
    MainFrame parent;
    boolean directed;
    
    
    public DegreeCentrality(MainFrame frame, boolean graphDirected){
        
        parent = frame;
        directed = graphDirected;
    }
    
    
    public void CalculateDegree(List<Node> nodes, List<Edge> edges){
        
        if(directed){
            InOutDegree(nodes, edges);
        }
        else{
            GeneralDegree(nodes, edges);
        }
    }
    
    
    public void GeneralDegree(List<Node> nodes, List<Edge> edges){
        
        generalDegree = new ArrayList<>();
        int currentDegree;
        
        for(int i=0; i<nodes.size(); i++){
            currentDegree = 0;
            
            for(int j=0; j<edges.size(); j++){
                if(nodes.get(i).equals(edges.get(j).node1) || nodes.get(i).equals(edges.get(j).node2)){
                    currentDegree++;
                }
            }
            
            generalDegree.add(currentDegree); 
        }
        
        DisplayResults(nodes, generalDegree);
    }
    
    
    public void InOutDegree(List<Node> nodes, List<Edge> edges){
        
        inDegree = new ArrayList<>();
        outDegree = new ArrayList<>();
        int currentInDegree, currentOutDegree;
        
        for(int i=0; i<nodes.size(); i++){
            currentInDegree = 0;
            currentOutDegree = 0;
            
            for(int j=0; j<edges.size(); j++){
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
        
        DisplayResults(nodes, inDegree, outDegree);
    }
    
    
    public void DisplayResults(List<Node> nodes, List<Integer> generalDegree){
        
        int range = nodes.size();
        Object[] column = {"Node", "Degree"};
        Object[][] results = new Object[range][2];
        
        for(int i=0; i<range; i++){
            results[i][0] = nodes.get(i).label;
            results[i][1] = generalDegree.get(i);
        }

        JTable resultsTable = new JTable(results, column);
        resultsTable.setEnabled(false);
        resultsTable.setCellSelectionEnabled(false);
        resultsTable.setBackground(new Color(240,240,240));
        
        JTableHeader header = resultsTable.getTableHeader();
        header.setDefaultRenderer(new LostHeaderRenderer());
    
        parent.resultsScrollPane.getViewport().add(resultsTable);
    }
    
    public void DisplayResults(List<Node> nodes, List<Integer> inDegree, List<Integer> outDegree){
        
        int range = nodes.size();
        Object[] column = {"Node", "In Degree", "Out Degree"};
        Object[][] results = new Object[range][3];
        
        for(int i=0; i<range; i++){
            results[i][0] = nodes.get(i).label;
            results[i][1] = inDegree.get(i);
            results[i][2] = outDegree.get(i);
        }
        
        JTable resultsTable = new JTable(results, column);
        resultsTable.setEnabled(false);
        resultsTable.setCellSelectionEnabled(false);
        resultsTable.setBackground(new Color(240,240,240));
        
        JTableHeader header = resultsTable.getTableHeader();
        header.setDefaultRenderer(new LostHeaderRenderer());
    
        parent.resultsScrollPane.getViewport().add(resultsTable);
    } 
}

