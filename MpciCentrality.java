package ptyxiakh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author kostas
 */
public class MpciCentrality {
    
    MainFrame parent;
    int m;
    List<Integer> mPci = new ArrayList<>();
    
    
    public MpciCentrality(MainFrame frame, int m){
        
        parent = frame;
        this.m = m;
    }
    
    
    public List<Integer> CalculateMpci(List<Double> degree){
        
        int numOfNodes = parent.previewPanel.nodes.size();
        double[][] adjMatrix = parent.previewPanel.adjacencyArray();
        List<Integer> currentNodeNeighbors;
        List<List<Integer>> firstNeighbors = new ArrayList<>();
        List<List<Integer>> secondNeighbors = new ArrayList<>();
        List<List<Integer>> thirdNeighbors = new ArrayList<>();
        List<List<Integer>> finalNeighbors;
        List<Double> currentNodeNeighborsDegrees;
        List<List<Double>> neighborDegrees = new ArrayList<>();
        boolean nodeExists;
        
        for(int i=0; i<numOfNodes; i++){
            currentNodeNeighbors = new ArrayList<>();
            
            for(int j=0; j<numOfNodes; j++){
                if(adjMatrix[i][j] == 1.0){
                    currentNodeNeighbors.add(j);
                }
            }
            firstNeighbors.add(currentNodeNeighbors);
        }
        finalNeighbors = firstNeighbors;
        
        System.out.println(finalNeighbors);
        System.out.println();
        
        if(m>1){
            List<Integer> currentNodeSecondNeighbors;
            
            for(int i=0; i<firstNeighbors.size(); i++){
                currentNodeSecondNeighbors = new ArrayList<>();
                currentNodeSecondNeighbors.addAll(firstNeighbors.get(i));
                
                for(int j=0; j<firstNeighbors.get(i).size(); j++){
                    int list = firstNeighbors.get(i).get(j);
                    
                    for(int k=0; k<firstNeighbors.get(list).size(); k++){
                        if(firstNeighbors.get(list).get(k)!=i){
                            nodeExists = false;
                            for(int l=0; l<currentNodeSecondNeighbors.size(); l++){
                                if(currentNodeSecondNeighbors.get(l)==firstNeighbors.get(list).get(k)){
                                    nodeExists = true;
                                }
                            }
                            if(!nodeExists){
                                currentNodeSecondNeighbors.add(firstNeighbors.get(list).get(k));
                            }
                        }
                    }
                }
                secondNeighbors.add(currentNodeSecondNeighbors);
            }
            finalNeighbors = secondNeighbors;
            
            System.out.println(finalNeighbors);
            System.out.println();
        }
        
        if(m>2){
            List<Integer> currentNodeThirdNeighbors;
            
            for(int i=0; i<secondNeighbors.size(); i++){
                currentNodeThirdNeighbors = new ArrayList<>();
                currentNodeThirdNeighbors.addAll(secondNeighbors.get(i));
                
                for(int j=0; j<secondNeighbors.get(i).size(); j++){
                    int list = secondNeighbors.get(i).get(j);
                    
                    for(int k=0; k<firstNeighbors.get(list).size(); k++){
                        if(firstNeighbors.get(list).get(k)!=i){
                            nodeExists = false;
                            for(int l=0; l<currentNodeThirdNeighbors.size(); l++){
                                if(currentNodeThirdNeighbors.get(l)==firstNeighbors.get(list).get(k)){
                                    nodeExists = true;
                                }
                            }
                            if(!nodeExists){
                                currentNodeThirdNeighbors.add(firstNeighbors.get(list).get(k));
                            }
                        }
                    }
                }
                thirdNeighbors.add(currentNodeThirdNeighbors);
            }
            finalNeighbors = thirdNeighbors;
            
            System.out.println(finalNeighbors);
            System.out.println();
        }
        
        for(int i=0; i<finalNeighbors.size(); i++){
            currentNodeNeighborsDegrees = new ArrayList<>();
            for(int j=0; j<finalNeighbors.get(i).size(); j++){
                int currentNode = finalNeighbors.get(i).get(j);
                currentNodeNeighborsDegrees.add(degree.get(currentNode));
            }
            neighborDegrees.add(currentNodeNeighborsDegrees);
        }
        System.out.println(neighborDegrees);
        
        for(int i=0; i<neighborDegrees.size(); i++){
            double k=0;
            boolean found = false;
            
            do{
                int counter=0;
                
                for(int j=0; j<neighborDegrees.get(i).size(); j++){
                    if(neighborDegrees.get(i).get(j) >= (k)){
                        counter++;
                    }
                }
                
                if(counter>=(m*k)){
                    k++;
                }
                else{
                    found = true;
                }
                
            }while(!found);
            System.out.println(k);
            mPci.add((int)((double)--k));
        }
        
        return mPci;
    }
    
    
    public void DisplayMpci(){
        
        int numOfNodes = parent.previewPanel.nodes.size();
        JTable resultsTable;
        
        Object[][] results = new Object[numOfNodes][2];
        
        for(int i=0; i<numOfNodes; i++){
            results[i][0] = parent.previewPanel.nodes.get(i).label;
            results[i][1] = mPci.get(i);
        }
        
        Object[] column = {"Node", m + "-Pci"};
        resultsTable = new JTable(results, column);
        
        DisplayCentralities.DisplayResults(resultsTable, parent);
    }
}
