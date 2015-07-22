package ptyxiakh;

import java.util.*;

/**
 *
 * @author kostas
 */
public class DijkstraAllPaths {
    
    
    public DijkstraAllPaths(){
        
    }
    
    
    public List<List<List<List<Integer>>>> DijkstraPaths(double[][] matrix){
        
        List<List<List<List<Integer>>>> paths = new ArrayList<>();

        for (int source=0; source<5; source++) {
            paths.add(dijkstra(source, matrix));
            System.out.println(paths.get(source));
        }

        return paths;
    }
    
        
    public List<List<List<Integer>>> dijkstra(int source, double[][] matrix) {
        
        List<List<List<Integer>>> paths = new ArrayList<>();
        double[] d = new double[5]; // the distance matrix
        int[][] prev = new int[5][5]; // the distance matrix
        boolean[] visited = new boolean[5]; // the distance matrix
        int min;

        for (int i=0; i<5; i++) {
            d[i] = Double.POSITIVE_INFINITY;
            prev[i][0] = 0;
            visited[i] = false;
        }

        d[source] = 0;

        for (int k=0; k<5; k++) {
            min = -1;

            for (int i=0; i<5; i++) {
                if (!visited[i] && ((min == -1) || (d[i] < d[min]))) {
                    min = i;
                }
            }

            visited[min] = true;

            for (int i=0; i<5; i++) {
                if (min!=i && matrix[min][i] != Double.POSITIVE_INFINITY) {
                    if (d[min] + matrix[min][i] < d[i]) {
                        d[i] = d[min] + matrix[min][i];
                        prev[i][0] = 1;
                        prev[i][1] = min;
                    }
                    else if (d[min] + matrix[min][i] == d[i]) {
                        prev[i][0]++;
                        prev[i][prev[i][0]] = min;
                    }
                }
            }
        }

        for (int i=0; i<5; i++) {
            if (i == source || d[i] == Double.POSITIVE_INFINITY) continue;

            paths.add(buildPaths(i, 0, prev));
        }

        return paths;
    }
        
    public List<List<Integer>> buildPaths(int dest, int depth, int[][] prev) {
        List<List<Integer>> list = new ArrayList<>();

        for (int i = 0; i < prev[dest][0]; ++i) {
            list.addAll(buildPaths(prev[dest][i+1], depth + 1, prev));
        }

        if (list.isEmpty()) {
            List<Integer> l = new ArrayList<>();
            l.add(dest);

            list.add(l);
        }
        else {
            for (int i=0; i<list.size(); i++) {
                list.get(i).add(dest);
            }
        }

        return list;
    }
}