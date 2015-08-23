package ptyxiakh;

import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

/**
 *
 * @author kostas
 */
public class ExportResults {
    
    JTable resultsTable;
    MainFrame parent;
    String file;
    
    
    public ExportResults(JTable results){
        this.resultsTable = results;
    }
    
    
    public void showDialog(){
        final JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileNameExtensionFilter("TSV files", "tsv"));
        int returnVal;
        returnVal = fc.showSaveDialog(parent);
        
        if (returnVal == JFileChooser.APPROVE_OPTION){
            file = fc.getSelectedFile().toString() + ".tsv";
            toExcel();
        }
    }
    
    
    public void toExcel(){
        
        try{
            TableModel model = resultsTable.getModel();
            FileWriter excel = new FileWriter(file);

            for(int i = 0; i < model.getColumnCount(); i++){
                excel.write(model.getColumnName(i) + "\t");
            }

            excel.write("\n");

            for(int i=0; i< model.getRowCount(); i++) {
                for(int j=0; j < model.getColumnCount(); j++) {
                    if(model.getValueAt(i,j) == null){
                        excel.write("\t");
                    }
                    else{
                        excel.write(model.getValueAt(i,j).toString()+"\t");
                    }
                }
                excel.write("\n");
            }

            excel.close();

        }catch(IOException e){ System.out.println(e); }
    }
}
