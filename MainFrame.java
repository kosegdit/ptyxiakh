package ptyxiakh;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author kostas
 */
public class MainFrame extends JFrame{

    JSplitPane BaseSplitPane;
    JSplitPane LeftSplitPane;
    JSplitPane RightSplitPane;
    JPanel previewPanel;
    JPanel resultsPanel;
    JPanel infoPanel;
    JPanel historyPanel;
    JPanel toolsPanel;
    JPanel mapPanel;
    JTabbedPane bottomRightTabbedPane;
    JLayeredPane toolsMapLayeredPane;
        
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

        BaseSplitPane = new JSplitPane();
        LeftSplitPane = new JSplitPane();
        RightSplitPane = new JSplitPane();
        previewPanel = new JPanel();
        resultsPanel = new JPanel();
        infoPanel = new JPanel();
        historyPanel = new JPanel();
        toolsPanel = new JPanel();
        mapPanel = new JPanel();
        bottomRightTabbedPane = new JTabbedPane();
        toolsMapLayeredPane = new JLayeredPane();

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setJMenuBar(createMainMenu());
        
        BaseSplitPane.setOrientation(javax.swing.JSplitPane.HORIZONTAL_SPLIT);
        BaseSplitPane.setDividerLocation(510);
        BaseSplitPane.setResizeWeight(0.5);
        BaseSplitPane.setLeftComponent(LeftSplitPane);
        BaseSplitPane.setRightComponent(RightSplitPane);
        
        LeftSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        LeftSplitPane.setResizeWeight(1);
        LeftSplitPane.setDividerLocation(Short.MAX_VALUE);
        LeftSplitPane.setDividerSize(0);
        
        RightSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        RightSplitPane.setResizeWeight(0.5);
        RightSplitPane.setDividerLocation(350);
        
        // Creates and sets the previewPanel
        previewPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        
        javax.swing.GroupLayout previewPanelLayout = new javax.swing.GroupLayout(previewPanel);
        previewPanel.setLayout(previewPanelLayout);
        previewPanelLayout.setHorizontalGroup(
            previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        previewPanelLayout.setVerticalGroup(
            previewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );

        LeftSplitPane.setTopComponent(previewPanel);
        
        // Creates and sets the toolsPanel
        toolsPanel.setPreferredSize(new Dimension(0, 200));
                
        toolsPanel.setBorder(javax.swing.BorderFactory.
                createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tools"));
        
        javax.swing.GroupLayout toolsPanelLayout = new javax.swing.GroupLayout(toolsPanel);
        toolsPanel.setLayout(toolsPanelLayout);
        toolsPanelLayout.setHorizontalGroup(
            toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        toolsPanelLayout.setVerticalGroup(
            toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        
        // Creates and sets the mapPanel
        mapPanel.setPreferredSize(new Dimension(260, 200));
        
        mapPanel.setBorder(javax.swing.BorderFactory.
                createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Map"));

        javax.swing.GroupLayout mapPanelLayout = new javax.swing.GroupLayout(mapPanel);
        mapPanel.setLayout(mapPanelLayout);
        mapPanelLayout.setHorizontalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        mapPanelLayout.setVerticalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        
        // Creates and sets the toolsMapLayeredPane
        javax.swing.GroupLayout toolsMapLayeredPaneLayout = new javax.swing.GroupLayout(toolsMapLayeredPane);
        toolsMapLayeredPane.setLayout(toolsMapLayeredPaneLayout);
        toolsMapLayeredPaneLayout.setHorizontalGroup(
            toolsMapLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolsMapLayeredPaneLayout.createSequentialGroup()
                .addComponent(toolsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addComponent(mapPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        toolsMapLayeredPaneLayout.setVerticalGroup(
            toolsMapLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mapPanel, 200, 200, 200)
                .addComponent(toolsPanel, 200, 200, 200)
        );
        toolsMapLayeredPane.setLayer(toolsPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        toolsMapLayeredPane.setLayer(mapPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        LeftSplitPane.setRightComponent(toolsMapLayeredPane);
        
        // Creates and sets the resultsPanel
        resultsPanel.setBorder(javax.swing.BorderFactory.
                createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Results"));
        
        javax.swing.GroupLayout resultsPanelLayout = new javax.swing.GroupLayout(resultsPanel);
        resultsPanel.setLayout(resultsPanelLayout);
        resultsPanelLayout.setHorizontalGroup(
            resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        resultsPanelLayout.setVerticalGroup(
            resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );

        RightSplitPane.setTopComponent(resultsPanel);
        
        // Creates and sets the infoPanel
        infoPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        
        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );

        bottomRightTabbedPane.addTab("Info", infoPanel);
        
        // Creates and sets the historyPanel
        historyPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout historyPanelLayout = new javax.swing.GroupLayout(historyPanel);
        historyPanel.setLayout(historyPanelLayout);
        historyPanelLayout.setHorizontalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );
        historyPanelLayout.setVerticalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        );

        bottomRightTabbedPane.addTab("History", historyPanel);

        RightSplitPane.setRightComponent(bottomRightTabbedPane);

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
