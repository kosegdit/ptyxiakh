package ptyxiakh;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

/**
 *
 * @author kostas
 */
public class MainFrame extends JFrame{
    
    
    public MainFrame() {
        initComponents();
        setVisible(true);
        setSize(1000, 700);
        setTitle("MyProgram");
        setLocation(300, 300);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    private void initComponents() {

        JSplitPane BaseSplitPane = new JSplitPane();
        JSplitPane LeftSplitPane = new JSplitPane();
        JSplitPane RightSplitPane = new JSplitPane();

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setJMenuBar(createMainMenu());
        
        BaseSplitPane.setOrientation(javax.swing.JSplitPane.HORIZONTAL_SPLIT);
        BaseSplitPane.setDividerLocation(510);
        BaseSplitPane.setResizeWeight(0.5);
        BaseSplitPane.setLeftComponent(LeftSplitPane);
        BaseSplitPane.setRightComponent(RightSplitPane);
        
        LeftSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        LeftSplitPane.setResizeWeight(0.5);
        LeftSplitPane.setDividerLocation(450);
        
        RightSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        RightSplitPane.setResizeWeight(0.5);
        RightSplitPane.setDividerLocation(350);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(BaseSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(BaseSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
        );

        pack();
    }
    
    private JMenuBar createMainMenu(){
        
        JMenuBar MainMenuBar = new JMenuBar();
        
        JMenu file = new JMenu("File");
        JMenu centralities = new JMenu("Centralities");
        JMenu communities = new JMenu("Communities");
        JMenu epidemics = new JMenu("Epidemics");
        JMenu about = new JMenu("About");
        JMenu generate = new JMenu("Generate Graph");
        
        JMenuItem load = new JMenuItem("Load Graph");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save As...");
        JMenuItem exit = new JMenuItem("Exit");
        
        
        MainMenuBar.add(file);
        
            file.add(generate);
            file.addSeparator();
            file.add(load);
            file.addSeparator();
            file.add(save);
            file.add(saveAs);
            file.addSeparator();
                exit.addActionListener((ActionEvent e) -> {
                    System.exit(0);
                });
            file.add(exit);

        MainMenuBar.add(centralities);
        MainMenuBar.add(communities);
        MainMenuBar.add(epidemics);
        MainMenuBar.add(about);
    
        return MainMenuBar;
    }
}
