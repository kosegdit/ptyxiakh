package ptyxiakh;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author kostas
 */
public class KshellScoreCentrality {
    
    MainFrame parent;
    List<List<Node>> kShells = new ArrayList<>();
    
    
    public KshellScoreCentrality(MainFrame frame){
        
        parent = frame;
    }
    
    
    public List<List<Node>> CalculateKshell(){
        
        List<Node> copyGraphNodes = new ArrayList<>();
        List<Edge> copyGraphEdges = new ArrayList<>();
        
        List<Node> tempNodes;
        boolean done;
        
        DegreeCentrality degree = new DegreeCentrality(parent, false, false, false);
        
        // Initializing the Graph Copy
        for(int i=0; i<parent.previewPanel.nodes.size(); i++){
            copyGraphNodes.add(parent.previewPanel.nodes.get(i));
        }
        for(int i=0; i<parent.previewPanel.edges.size(); i++){
            copyGraphEdges.add(parent.previewPanel.edges.get(i));
        }
        
        for(int k=1; copyGraphNodes.size()>0; k++){
            tempNodes = new ArrayList<>();
            
            do{
                done = true;

                List<Double> graphDegrees = degree.UndirectedUnweightedDegree(copyGraphNodes, copyGraphEdges);
                List<Edge> tempEdges = new ArrayList<>();

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
            
            kShells.add(tempNodes);
        }
        
        return kShells;
    }
    
    
    public void DisplayKshell(){
        
        int numOfShells = kShells.size();
        
        List<Color> colors = ColorBands.getColorBands(Color.BLUE, numOfShells);
        
        for(int i=0; i<numOfShells; i++){
            for(int j=0; j<kShells.get(i).size(); j++){
                kShells.get(i).get(j).setNodeColor(colors.get(i));
            }
        }
        parent.previewPanel.repaint();
        
        JTable resultsTable;
        
        Object[][] results = new Object[numOfShells][2];
        String result;
        for(int i=0; i<numOfShells; i++){
            results[i][0] = i+1;
            result = "";
            
            for(int j=0; j<kShells.get(i).size(); j++){
                result += ", " + kShells.get(i).get(j).label;
            }
            
            if(result.isEmpty()){
                results[i][1] = "empty";
            }
            else{
                results[i][1] = result.substring(2);
            }
        }
        
        Object[] column = {"k", "Shell"};
        resultsTable = new JTable(results, column);
        
        DisplayCentralities.DisplayResults(resultsTable, parent);
    }
}
