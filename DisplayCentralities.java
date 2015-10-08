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

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 *
 * @author kostas
 */
public class DisplayCentralities {
    
    
    public static void DisplayResults(JTable resultsTable, MainFrame frame){
        
        MainFrame parent = frame;
        
        resultsTable.setEnabled(false);
        resultsTable.setCellSelectionEnabled(false);
        resultsTable.setBackground(new Color(240,240,240));

        JTableHeader header = resultsTable.getTableHeader();
        header.setDefaultRenderer(new LostHeaderRenderer());
        
        parent.results = resultsTable;
        parent.resultsScrollPane.getViewport().add(resultsTable);
    }
}
