package ptyxiakh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
//import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author kostas
 */
public class MainFrame extends JFrame{
    
    private boolean graphInUse = false;

    JSplitPane BaseSplitPane;
    JSplitPane LeftSplitPane;
    JSplitPane RightSplitPane;
    JPanel previewPanel;
    //JPanel resultsPanel;
    JPanel infoPanel;
    //JPanel historyPanel;
    JPanel toolsPanel;
    JPanel mapPanel;
    JScrollPane historyScrollPane;
    JScrollPane resultsScrollPane;
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
        //resultsPanel = new JPanel();
        infoPanel = new JPanel();
        //historyPanel = new JPanel();
        toolsPanel = new JPanel();
        mapPanel = new JPanel();
        historyScrollPane = new JScrollPane();
        resultsScrollPane = new JScrollPane();
        bottomRightTabbedPane = new JTabbedPane();
        toolsMapLayeredPane = new JLayeredPane();

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
        previewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.
                createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Preview"));
        
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
                .addComponent(mapPanel, 180, 200, 200)
                .addComponent(toolsPanel, 180, 200, 200)
        );
        toolsMapLayeredPane.setLayer(toolsPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        toolsMapLayeredPane.setLayer(mapPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        LeftSplitPane.setRightComponent(toolsMapLayeredPane);
        
        
        // Creates and sets the resultsScrollPane
        resultsScrollPane.setBorder(javax.swing.BorderFactory.
                createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Resutls")); 
        RightSplitPane.setTopComponent(resultsScrollPane);
        
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
        
        
        // Creates and sets the historyScrollPane
        historyScrollPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bottomRightTabbedPane.addTab("History", historyScrollPane);

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
        
        JMenuItem aboutItem = new JMenuItem("About...");
        JMenuItem load = new JMenuItem("Load Graph");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save As...");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem degree = new JMenuItem("Degree");
        JMenuItem closeness = new JMenuItem("Closeness");
        JMenuItem betweenness = new JMenuItem("Betweenness");
        JMenuItem edgeBetweenness = new JMenuItem("Edge Betweenness");
        JMenuItem μpci = new JMenuItem("μ-Pci");
        JMenuItem kShell = new JMenuItem("k-Shell");
        JMenuItem pageRank = new JMenuItem("Page Rank");
        JMenuItem randomGraph = new JMenuItem("Random graph");
            randomGraph.setToolTipText("Creates a random graph using the Erdős–Rényi model");
        JMenuItem smallWorldGraph = new JMenuItem("Small world graph");
            smallWorldGraph.setToolTipText("Six degrees of seperation");
        JMenuItem scaleFreeGraph = new JMenuItem("Scale free graph");
            scaleFreeGraph.setToolTipText("Creates a random graph using the Albert-Barabasi model");
        JMenuItem cpm = new JMenuItem("CPM");
            cpm.setToolTipText("Clique Percolation Method");
        JMenuItem ebc = new JMenuItem("EBC");
            ebc.setToolTipText("Newmann & Girvan using the Edge Betweenness Centrality");
        JMenuItem cibc = new JMenuItem("CiBC");
            cibc.setToolTipText("Communities identification with Betweenness Centrality");
        
        
        MainMenuBar.add(file);
            file.add(generate);
                randomGraph.addActionListener((ActionEvent e) -> {
                    NewRandomGraph();
                });
                generate.add(randomGraph);
                
                generate.add(smallWorldGraph);
                generate.add(scaleFreeGraph);
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
            centralities.add(degree);
            centralities.add(closeness);
            centralities.add(betweenness);
            centralities.add(edgeBetweenness);
            centralities.add(μpci);
            centralities.add(kShell);
            centralities.add(pageRank);
            
        MainMenuBar.add(communities);
            communities.add(cpm);
            communities.add(ebc);
            communities.add(cibc);
            
        MainMenuBar.add(epidemics);
          
        MainMenuBar.add(about);
            aboutItem.addActionListener((ActionEvent e) -> {
                JOptionPane.showMessageDialog(null, "----------------------------------------\n\n" + 
                        "Created by: Segditsas Konstantinos\n" + "ksegditsas@yahoo.gr\n\n" +
                        "Advisor Professor: Katsaros Dimitrios\n" + "dkatsar@inf.uth.gr\n\n" + "ver 1.0\n\n" + 
                        "----------------------------------------\n"
                        , "About MyProgram", JOptionPane.INFORMATION_MESSAGE);
            });
            about.add(aboutItem);
            
    
        return MainMenuBar;
    }
    
    private void NewRandomGraph(){

        Graph randomGraph;
        
        // If there is already a graph in use, asks the user to save his progress
        if(graphInUse){
            int graphInUseDialogResult = JOptionPane.showConfirmDialog(null, "Would you like to save the current Graph?", "MyProgram",
                                                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if(graphInUseDialogResult == JOptionPane.YES_OPTION) {
                SaveGraph();
                graphInUse = false;
            }
            else if(graphInUseDialogResult == JOptionPane.NO_OPTION){
                previewPanel.removeAll();
                previewPanel.updateUI();
                //randomGraph = null;
                graphInUse = false;
                
            }
            else {
                return;
            }
        }
        
        // Creates the Spinner for the number of Nodes with a downlimit of 2, and a spinner filter
        // for integers, to prevent wrong user input
        SpinnerNumberModel limits = new SpinnerNumberModel(20, 1, Short.MAX_VALUE, 1);
        JSpinner numOfNodesSpinner = new JSpinner(limits);
        
        JFormattedTextField spinnerFilter = ((JSpinner.NumberEditor) numOfNodesSpinner.getEditor()).getTextField();
        ((NumberFormatter) spinnerFilter.getFormatter()).setAllowsInvalid(false);
        
        int startValue = 50;
        final JLabel densitySliderLabel = new JLabel("Choose Graph density: " + startValue + "%");
        
        // Creates the Density Slider, right above is the laber for the Slider
        JSlider densitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, startValue);
        densitySlider.setMajorTickSpacing(20);
        densitySlider.setMinorTickSpacing(5);
        densitySlider.setPaintTicks(true);
        densitySlider.setPaintLabels(true);
        
        // Change listener for the Density Slider to catch the real time changes
        densitySlider.addChangeListener((ChangeEvent e) -> {
            densitySliderLabel.setText("Choose Graph density: " + densitySlider.getValue() + "%");
        });
        
        // An array of objects is created to carry all the information for the displayed window
        Object[] fullMessage = {"Enter number of nodes:", numOfNodesSpinner, "\n\n", densitySliderLabel, densitySlider};

        int result = JOptionPane.showOptionDialog(null, fullMessage, "Random Graph Properties",
							JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,
							null, null, null);
        
        if(result != JOptionPane.OK_OPTION) {
            return;
        }
        
        graphInUse = true;
        int density = densitySlider.getValue();
        int numberOfNodes = (int)numOfNodesSpinner.getValue();
        
        randomGraph = new Graph();
        randomGraph.createRandomGraph(density, numberOfNodes, previewPanel.getSize());
        
        for (int i = 0; i < numberOfNodes; i++) {
            previewPanel.add(randomGraph.nodes.get(i));
        }
        for(int i = 0; i < randomGraph.edges.size(); i++ ){
            Edge e = randomGraph.edges.get(i);
            previewPanel.add(e);
            e.repaint();
        }
        previewPanel.repaint();
    }
    
    private void SaveGraph(){}
}
