package ptyxiakh;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author kostas
 */
public class KshellScoreCentrality {
    
    MainFrame parent;
    List<List<Node>> kShellScore = new ArrayList<>();
    
    List<Node> copyGraphNodes = new ArrayList<>();
    List<Edge> copyGraphEdges = new ArrayList<>();

    List<Node> tempNodes;
    List<Edge> tempEdges;
    
    List<Double> graphDegrees;
            
    boolean done;
    
    
    public KshellScoreCentrality(MainFrame frame){
        
        parent = frame;
    }
    
    
    public List<List<Node>> CalculateKshell(){
        
        DegreeCentrality degree = new DegreeCentrality(parent, false, false, false);
        
        GetGraphCopy();
        
        for(int k=1; copyGraphNodes.size()>0; k++){
            tempNodes = new ArrayList<>();
            
            do{
                done = true;

                graphDegrees = degree.UndirectedUnweightedDegree(copyGraphNodes, copyGraphEdges);
                tempEdges = new ArrayList<>();

                for(int i=0; i<copyGraphNodes.size(); i++){
                    if(graphDegrees.get(i) <= k){
                        tempNodes.add(copyGraphNodes.get(i));
                        done = false;
                    }
                }
                copyGraphNodes.removeAll(tempNodes);

                for (Edge edge : copyGraphEdges) {
                    for (Node node : tempNodes) {
                        if (edge.node1.equals(node) || edge.node2.equals(node)) {
                            tempEdges.add(edge);
                        }
                    }
                }
                copyGraphEdges.removeAll(tempEdges);
                graphDegrees.clear();

            }while(!done);
            
            kShellScore.add(tempNodes);
        }
        
        return kShellScore;
    }
    
    
    public List<List<Node>> CalculateScore(){
        
        DegreeCentrality degree = new DegreeCentrality(parent, false, true, false);
        
        GetGraphCopy();
        
        do{
            List<Double> graphInitDegrees = degree.UndirectedWeightedDegree(copyGraphNodes, copyGraphEdges);
            double currentMindegree = Collections.min(graphInitDegrees);
            
            tempNodes = new ArrayList<>();
            
            do{
                done = true;

                graphDegrees = degree.UndirectedWeightedDegree(copyGraphNodes, copyGraphEdges);
                tempEdges = new ArrayList<>();

                for(int i=0; i<copyGraphNodes.size(); i++){
                    if(graphDegrees.get(i) <= currentMindegree){
                        tempNodes.add(copyGraphNodes.get(i));
                        done = false;
                    }
                }
                copyGraphNodes.removeAll(tempNodes);

                for (Edge edge : copyGraphEdges) {
                    for (Node node : tempNodes) {
                        if (edge.node1.equals(node) || edge.node2.equals(node)) {
                            tempEdges.add(edge);
                        }
                    }
                }
                copyGraphEdges.removeAll(tempEdges);
                graphDegrees.clear();

            }while(!done);
            
            kShellScore.add(tempNodes);
            graphInitDegrees.clear();
            
        }while(copyGraphNodes.size()>0);
        
        return kShellScore;
    }
    
    
    private void GetGraphCopy(){
        
        // Initializing the Graph Copy
        for(int i=0; i<parent.previewPanel.nodes.size(); i++){
            copyGraphNodes.add(parent.previewPanel.nodes.get(i));
        }
        for(int i=0; i<parent.previewPanel.edges.size(); i++){
            copyGraphEdges.add(parent.previewPanel.edges.get(i));
        }
    }
    
    
    public void DisplayKshell(){
        
        int numOfShells = kShellScore.size();
        
        List<Color> colors = ColorBands.getColorBands(Color.BLUE, numOfShells);
        
        for(int i=0; i<numOfShells; i++){
            for(int j=0; j<kShellScore.get(i).size(); j++){
                kShellScore.get(i).get(j).setNodeColor(colors.get(i));
            }
        }
        parent.previewPanel.repaint();
        
        JTable resultsTable;
        
        Object[][] results = new Object[numOfShells][2];
        String result;
        for(int i=0; i<numOfShells; i++){
            results[i][0] = i+1;
            result = "";
            
            for(int j=0; j<kShellScore.get(i).size(); j++){
                result += ", " + kShellScore.get(i).get(j).label;
            }
            
            if(result.isEmpty()){
                results[i][1] = "empty";
            }
            else{
                results[i][1] = result.substring(2);
            }
        }
        
        if(parent.previewPanel.graphIsWeighted()){
            Object[] column = {"s", "Core"};
            resultsTable = new JTable(results, column);
        }
        else{
            Object[] column = {"k", "Shell"};
            resultsTable = new JTable(results, column);
        }
        
        DisplayCentralities.DisplayResults(resultsTable, parent);
    }
}
