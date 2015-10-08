/*
 * /*
 * *
 * * This is a class file for the program AviNet
 * *
 * * Copyright (c) Segditsas Konstantinos, 2015
 * * Email: kosegdit@gmail.com
 * *
 * * All rights reserved
 * *
 */

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
        int dimension = matrix.length;

        for (int source=0; source<dimension; source++) {
            paths.add(dijkstra(source, matrix));
        }

        return paths;
    }
    
        
    public List<List<List<Integer>>> dijkstra(int source, double[][] matrix) {
        
        List<List<List<Integer>>> paths = new ArrayList<>();
        double[] d = new double[matrix.length]; // the distance matrix
        int[][] prev = new int[matrix.length][matrix.length]; // the distance matrix
        boolean[] visited = new boolean[matrix.length]; // the distance matrix
        int min;
        int dimension = matrix.length;

        for (int i=0; i<dimension; i++) {
            d[i] = Double.POSITIVE_INFINITY;
            prev[i][0] = 0;
            visited[i] = false;
        }

        d[source] = 0;

        for (int k=0; k<dimension; k++) {
            min = -1;

            for (int i=0; i<dimension; i++) {
                if (!visited[i] && ((min == -1) || (d[i] < d[min]))) {
                    min = i;
                }
            }

            visited[min] = true;

            for (int i=0; i<dimension; i++) {
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

        for (int i=0; i<dimension; i++) {
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