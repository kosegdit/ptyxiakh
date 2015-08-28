package ptyxiakh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author kostas
 */
public class MainFrame extends JFrame{
    
    
    JSplitPane BaseSplitPane;
    JSplitPane RightSplitPane;
    JPanel infoPanel;
    JScrollPane previewScroll;
    Graph previewPanel;
    boolean discard;
    boolean normalized;
    boolean resultsPaneUse;
    //JScrollPane historyScrollPane;
    JScrollPane resultsScrollPane;
    //JTabbedPane bottomRightTabbedPane;
    static String lastLoadedFile;
    JLabel graphTitle;
    JLabel currentAlgorithm;
    JLabel directedLabel;
    JLabel weightedLabel;
    JLabel nodesLabel;
    JLabel edgesLabel;
    JLabel nodesCounter;
    JLabel edgesCounter;
    JLabel directedCheckLabel;
    JLabel weightedCheckLabel;
    JMenuItem exportResultsMenuItem;
    JTable results = new JTable();
    int selectedNodes;
    
    public MainFrame() {
        initComponents();
        setVisible(true);
        setSize(1000, 700);
        setTitle("MyProgram");
        setLocation(300, 300);
        setResizable(true);
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            if(!previewPanel.ClearGraphIfInUse()){
                System.exit(0);
            }
        }

    });
    }
    
    
    private void initComponents() {

        BaseSplitPane = new JSplitPane();
        RightSplitPane = new JSplitPane();
        previewScroll = new JScrollPane();
        previewPanel = new Graph(this);
        infoPanel = new JPanel();
        //historyScrollPane = new JScrollPane();
        resultsScrollPane = new JScrollPane();
        //bottomRightTabbedPane = new JTabbedPane();
        graphTitle = new JLabel("Graph:");
        directedLabel = new JLabel("Directed:");
        directedCheckLabel = new JLabel("\u00D7");
        weightedLabel = new JLabel("Weighted:");
        weightedCheckLabel = new JLabel("\u00D7");
        currentAlgorithm = new JLabel();
        nodesLabel = new JLabel("Nodes:");
        edgesLabel = new JLabel("Edges:");
        nodesCounter = new JLabel("0");
        edgesCounter = new JLabel("0");
        
        
        setJMenuBar(createMainMenu());
        
        BaseSplitPane.setOrientation(javax.swing.JSplitPane.HORIZONTAL_SPLIT);
        BaseSplitPane.setDividerLocation(480);
        BaseSplitPane.setResizeWeight(0.5);
        BaseSplitPane.setLeftComponent(previewScroll);
        BaseSplitPane.setRightComponent(RightSplitPane);
        
        previewPanel.setPreferredSize(new Dimension(2000, 2000));
        
        previewScroll.setViewportView(previewPanel);
        previewScroll.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.
                createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Preview"));
        
        RightSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        RightSplitPane.setResizeWeight(0.5);
        RightSplitPane.setDividerLocation(350);
        
        
        
        
        // Creates and sets the resultsScrollPane
        resultsScrollPane.setBorder(javax.swing.BorderFactory.
                createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Results")); 
        RightSplitPane.setTopComponent(resultsScrollPane);
        
        // Creates and sets the infoPanel
        infoPanel.setBorder(javax.swing.BorderFactory.
                createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Informations"));
        
        
        
        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graphTitle)
                    .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, infoPanelLayout.createSequentialGroup()
                            .addComponent(edgesLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(edgesCounter))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, infoPanelLayout.createSequentialGroup()
                            .addComponent(nodesLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nodesCounter))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, infoPanelLayout.createSequentialGroup()
                            .addComponent(weightedLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(weightedCheckLabel))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, infoPanelLayout.createSequentialGroup()
                            .addComponent(directedLabel)
                            .addGap(18, 18, 18)
                            .addComponent(directedCheckLabel)))
                    .addComponent(currentAlgorithm))
                .addContainerGap(409, Short.MAX_VALUE))
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(graphTitle)
                .addGap(18, 18, 18)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(directedLabel)
                    .addComponent(directedCheckLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weightedLabel)
                    .addComponent(weightedCheckLabel))
                .addGap(18, 18, 18)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nodesLabel)
                    .addComponent(nodesCounter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edgesLabel)
                    .addComponent(edgesCounter))
                .addGap(22, 32, 32)
                .addComponent(currentAlgorithm)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        //bottomRightTabbedPane.addTab("Info", infoPanel);
        
        
        // Creates and sets the historyScrollPane
        //historyScrollPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        //bottomRightTabbedPane.addTab("History", historyScrollPane);

        RightSplitPane.setRightComponent(infoPanel);

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
        
        previewScroll.getVerticalScrollBar().setValue(700);
        previewScroll.getHorizontalScrollBar().setValue(700);
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
        exportResultsMenuItem = new JMenuItem("Export Results...");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem degreeMenuItem = new JMenuItem("Degree");
        JMenuItem closenessMenuItem = new JMenuItem("Closeness");
        JMenuItem betweennessMenuItem = new JMenuItem("Betweenness");
            betweennessMenuItem.setToolTipText("Shortest Path Betweenness Centrality");
        JMenuItem edgeBetweennessMenuItem = new JMenuItem("Edge Betweenness");
        JMenuItem μpciMenuItem = new JMenuItem("μ-Pci");
        JMenuItem kShellMenuItem = new JMenuItem("k-Shell / s-Core");
            kShellMenuItem.setToolTipText("k-Shell for Unweighted and s-Core for Weighted graphs");
        JMenuItem pageRankMenuItem = new JMenuItem("Page Rank");
        JMenuItem randomGraphMenuItem = new JMenuItem("Random graph");
            randomGraphMenuItem.setToolTipText("Creates a random graph using the Erdős–Rényi model");
        JMenuItem smallWorldGraphMenuItem = new JMenuItem("Small world graph");
            smallWorldGraphMenuItem.setToolTipText("Six degrees of seperation");
        JMenuItem scaleFreeGraph = new JMenuItem("Scale free graph");
            scaleFreeGraph.setToolTipText("Creates a random graph using the Albert-Barabasi model");
        JMenuItem cpm = new JMenuItem("CPM");
            cpm.setToolTipText("Clique Percolation Method");
        JMenuItem ebcMenuItem = new JMenuItem("EBC");
            ebcMenuItem.setToolTipText("Newmann & Girvan using the Edge Betweenness Centrality");
        JMenuItem cibcMenuItem = new JMenuItem("CiBC");
            cibcMenuItem.setToolTipText("Communities identification with Betweenness Centrality");
        JMenuItem linearThresholdMenuItem = new JMenuItem("Linear Threshold");
            linearThresholdMenuItem.setToolTipText("Susceptible Infected Susceptible");
        JMenuItem independentCascadeMenuItem = new JMenuItem("Independent Cascade");
            independentCascadeMenuItem.setToolTipText("Susceptible Infected Recovered");
        
        
        MainMenuBar.add(file);
            file.add(generate);
                randomGraphMenuItem.addActionListener((ActionEvent e) -> {
                    NewRandomGraph();
                });
                generate.add(randomGraphMenuItem);
                
                smallWorldGraphMenuItem.addActionListener((ActionEvent e) -> {
                    NewSmallWorldGraph();
                });
                generate.add(smallWorldGraphMenuItem);
                generate.add(scaleFreeGraph);
            file.addSeparator();
            
            load.addActionListener((ActionEvent e) -> {
                final JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.setFileFilter(new FileNameExtensionFilter("MyProgram files", "cnt"));
                int returnVal = fc.showOpenDialog(MainFrame.this);
                
                
                if (returnVal == JFileChooser.APPROVE_OPTION){
                    discard = previewPanel.ClearGraphIfInUse();
                    if(discard) return;
                    
                    lastLoadedFile = fc.getSelectedFile().toString();
                    previewPanel.LoadGraph(lastLoadedFile);
                }
            });
            file.add(load);
            file.addSeparator();
            
            save.addActionListener((ActionEvent e) -> {
                if(lastLoadedFile == null){
                    ShowSaveDialog();
                }
                else{
                    previewPanel.SaveGraph(lastLoadedFile);
                }
            });
            file.add(save);
            
            saveAs.addActionListener((ActionEvent e) -> {
                ShowSaveDialog();
            });
            file.add(saveAs);
            file.addSeparator();
            
            exportResultsMenuItem.addActionListener((ActionEvent e) -> {
                ExportResults exportTsv = new ExportResults(results);
                exportTsv.showDialog();
            });
            file.add(exportResultsMenuItem);
            exportResultsMenuItem.setEnabled(false);
            
            file.addSeparator();
            
            exit.addActionListener((ActionEvent e) -> {
                if(!previewPanel.ClearGraphIfInUse()){
                    System.exit(0);
                }
            });
            file.add(exit);

        MainMenuBar.add(centralities);
            degreeMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    int result = JOptionPane.showOptionDialog(this, "Normalize Results?", "Degree Normalization",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, null, null);
                    if(result == JOptionPane.YES_OPTION){
                        normalized = true;
                        
                    }
                    else if(result == JOptionPane.NO_OPTION){
                        normalized = false;
                    }
                    else{
                        return;
                    }
                    
                    if(resultsPaneUse) previewPanel.resetNodesColor();
                    
                    boolean currentGraphDirected = previewPanel.graphIsDirected();
                    boolean currentGraphWeighted = previewPanel.graphIsWeighted();
                    
                    DegreeCentrality degree = new DegreeCentrality(this, currentGraphDirected, currentGraphWeighted, normalized);
                    degree.CalculateDegree(previewPanel.nodes, previewPanel.edges);
                    degree.DisplayDegree();
                    resultsPaneUse = true;
                    exportResultsMenuItem.setEnabled(true);
                }
            });
            centralities.add(degreeMenuItem);
            
            closenessMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    int result = JOptionPane.showOptionDialog(this, "Normalize Results?", "Closeness Normalization",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, null, null);
                    if(result == JOptionPane.YES_OPTION){
                        normalized = true;
                        
                    }
                    else if(result == JOptionPane.NO_OPTION){
                        normalized = false;
                    }
                    else{
                        return;
                    }
                    
                    if(resultsPaneUse) previewPanel.resetNodesColor();
                    
                    ClosenessCentrality closeness = new ClosenessCentrality(this, normalized);
                    closeness.CalculateCloseness(previewPanel.nodes, previewPanel.edges);
                    closeness.DisplayCloseness(previewPanel.nodes);
                    resultsPaneUse = true;
                    exportResultsMenuItem.setEnabled(true);
                }
            });
            centralities.add(closenessMenuItem);
            
            betweennessMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    int result = JOptionPane.showOptionDialog(this, "Normalize Results?", "Betweenness Normalization",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, null, null);
                    if(result == JOptionPane.YES_OPTION){
                        normalized = true;
                        
                    }
                    else if(result == JOptionPane.NO_OPTION){
                        normalized = false;
                    }
                    else{
                        return;
                    }
                    
                    if(resultsPaneUse) previewPanel.resetNodesColor();
                    
                    BetweennessCentrality betweenness = new BetweennessCentrality(this, normalized);
                    betweenness.CalculateBetweenness(previewPanel.nodes, previewPanel.edges);
                    betweenness.DisplayBetweenness(previewPanel.nodes);
                    resultsPaneUse = true;
                    exportResultsMenuItem.setEnabled(true);
                }
            });
            centralities.add(betweennessMenuItem);
            
            edgeBetweennessMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    
                    if(resultsPaneUse) previewPanel.resetNodesColor();
                    
                    EdgeBetweennessCentrality edgeBetweenness = new EdgeBetweennessCentrality(this);
                    edgeBetweenness.CalculateEdgeBetweenness(previewPanel.nodes, previewPanel.edges);
                    edgeBetweenness.DisplayEdgeBetweenness(previewPanel.edges);
                    resultsPaneUse = true;
                    exportResultsMenuItem.setEnabled(true);
                }
            });
            centralities.add(edgeBetweennessMenuItem);
            
            μpciMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    if(!previewPanel.graphIsDirected() && !previewPanel.graphIsWeighted()){
                        if(resultsPaneUse) previewPanel.resetNodesColor();
                        
                        SpinnerNumberModel limits = new SpinnerNumberModel(1, 1, 3, 1);
                        JSpinner mFactor = new JSpinner(limits);

                        Object[] fullMessage = {"Choose the μ factor:", mFactor, "\n"};

                        int result = JOptionPane.showOptionDialog(this, fullMessage, "μ Value",
                                                                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                                        null, null, null);

                        if(result != JOptionPane.OK_OPTION) {
                            return;
                        }

                        int m = (int)mFactor.getValue();

                        DegreeCentrality degree = new DegreeCentrality(this, false, false, false);
                        List<Double> graphDegrees = degree.UndirectedUnweightedDegree(previewPanel.nodes, previewPanel.edges);
                        MpciCentrality mPci = new MpciCentrality(this, m);
                        mPci.CalculateMpci(graphDegrees, previewPanel.nodes, previewPanel.edges);
                        mPci.DisplayMpci(previewPanel.nodes);
                        resultsPaneUse = true;
                        exportResultsMenuItem.setEnabled(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "μ-Pci can only be applied to Undirected, Unweighted graphs");
                    }
                }
                
            });
            centralities.add(μpciMenuItem);
            
            kShellMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    if(!previewPanel.graphIsDirected()){
                        KshellScoreCentrality kshell = new KshellScoreCentrality(this);
                        
                        if(!previewPanel.graphIsWeighted()){
                            kshell.CalculateKshell(previewPanel.nodes, previewPanel.edges);
                            kshell.DisplayKshell();
                        }
                        else{
                            kshell.CalculateScore(previewPanel.nodes, previewPanel.edges);
                            kshell.DisplayKshell();
                        }
                        
                        resultsPaneUse = true;
                        exportResultsMenuItem.setEnabled(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "k-Shell or s-Core can only be applied to Undirected graphs");
                    }
                }
            });
            centralities.add(kShellMenuItem);
            
            pageRankMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    if(previewPanel.graphIsDirected() && !previewPanel.graphIsWeighted()){
                        PageRankCentrality pagerank = new PageRankCentrality(this);
                        pagerank.CalculatePageRank(previewPanel.nodes, previewPanel.edges);
                        pagerank.NodesRank(previewPanel.nodes);
                        pagerank.DisplayPageRank();

                        resultsPaneUse = true;
                        exportResultsMenuItem.setEnabled(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Page Rank can only be applied to Directed, Unweighted graphs");
                    }
                }
            });
            centralities.add(pageRankMenuItem);
            
        MainMenuBar.add(communities);
            communities.add(cpm);
            
            ebcMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    if(resultsPaneUse) previewPanel.resetNodesColor();
                    
                    EBCommunity ebc = new EBCommunity(this);
                    ebc.CalculateEBC(previewPanel.nodes, previewPanel.edges);
                    ebc.DisplayEBCommunity();
                    
                    resultsPaneUse = true;
                    exportResultsMenuItem.setEnabled(true);
                }
                
            });
            communities.add(ebcMenuItem);
            
            cibcMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    if(previewPanel.graphIsDirected() && previewPanel.graphIsWeighted()){
                        String message = "Edges directions and weights will only affect the \nBetweenness Centrality computation and not the Clique Merging!";
                        String title = "Current Graph is Directed and Weighted";
                        
                        int result = JOptionPane.showOptionDialog(this, message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                        
                        if(result != JOptionPane.OK_OPTION) {
                            return;
                        }
                    }
                    else if(previewPanel.graphIsDirected()){
                        String message = "Edges directions will only affect the Betweenness Centrality \ncomputation and not the Clique Merging!";
                        String title = "Current Graph is Directed";
                        
                        int result = JOptionPane.showOptionDialog(this, message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                        
                        if(result != JOptionPane.OK_OPTION) {
                            return;
                        }
                    }
                    else if(previewPanel.graphIsWeighted()){
                        String message = "Edges weights will only affect the Betweenness Centrality \ncomputation and not the Clique Merging!";
                        String title = "Current Graph is Weighted";
                        
                        int result = JOptionPane.showOptionDialog(this, message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                        
                        if(result != JOptionPane.OK_OPTION) {
                            return;
                        }
                    }
                    
                    SpinnerNumberModel sParameter = new SpinnerNumberModel(1.0,0.0 ,Double.POSITIVE_INFINITY,0.05);
                    JSpinner sSpinner = new JSpinner(sParameter);
                    
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor)sSpinner.getEditor();
                    editor.getFormat().setMinimumFractionDigits(2);
                    
                    ((NumberFormatter) editor.getTextField().getFormatter()).setAllowsInvalid(false);
                    
                    Object[] fullMessage = {"Enter desired S value:", sSpinner, "\n"};
                            
                    int result = JOptionPane.showOptionDialog(this, fullMessage, "Merging Parameter S",
                                                                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                                            null, null, null);
                    if(result != JOptionPane.OK_OPTION) {
                        return;
                    }

                    double s = (double)sSpinner.getValue();
                            
                    if(resultsPaneUse) previewPanel.resetNodesColor();
                    
                    CiBCommunity cibc = new CiBCommunity(this, s);
                    cibc.CalculateCiBC(previewPanel.nodes, previewPanel.edges);
                    cibc.DisplayCiBC();
                    
                    resultsPaneUse = true;
                    exportResultsMenuItem.setEnabled(true);
                }
                
            });
            communities.add(cibcMenuItem);
            
        MainMenuBar.add(epidemics);
            linearThresholdMenuItem.addActionListener((ActionEvent e) -> {
                selectedNodes = 0;
                Object[] choices = {"Load File..", "Step by Step..", "Cancel"};
                Object defaultChoice = choices[0];
                int result = JOptionPane.showOptionDialog(this, "Would you like to load the inputs by loading a file, or step by step?", "Linear Threshold Inputs", 
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, defaultChoice);
                
                if(result == JOptionPane.YES_OPTION) {
                    final JFileChooser fc = new JFileChooser();
                    fc.setAcceptAllFileFilterUsed(false);
                    fc.setFileFilter(new FileNameExtensionFilter("Epidemic Files", "ept"));
                    int returnVal = fc.showOpenDialog(MainFrame.this);


                    if (returnVal == JFileChooser.APPROVE_OPTION){
                        String epidemicFile = fc.getSelectedFile().toString();
                        LoadEpidemic(epidemicFile, "lt");
                        
                        resultsPaneUse = true;
                        exportResultsMenuItem.setEnabled(true);
                    }
                }
                else if(result == JOptionPane.NO_OPTION){
                    boolean nodesSelected = false;
                    List<Node> startingNodes = new ArrayList<>();
                    
                    do{
                        startingNodes = StartingNodesDialog();

                        if(startingNodes == null) return;

                        if(startingNodes.isEmpty()){
                            int result2 = JOptionPane.showOptionDialog(this, "You have to select at least 1 node to start the Algorithm", "No Nodes Selected", 
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);

                            if(result2 != JOptionPane.OK_OPTION) return;
                        }
                        else{
                            nodesSelected = true;
                        }
                    }while(!nodesSelected);
                    
                    List<Double> nodeThresholds = NodeThresholdsDialog(startingNodes);

                    if(nodeThresholds == null) return;

                    List<Double> edgeThresholds = EdgeThresholdsDialog();
                    
                    if(edgeThresholds == null) return;
                    
                    String epidemicSave = ShowEpidemicSaveDialog();
                    
                    if(!epidemicSave.equals("discard")){
                        SaveEpidemic(epidemicSave, "lt", nodeThresholds, edgeThresholds);
                    }
                    
                    if(resultsPaneUse) previewPanel.resetNodesColor();
                    
                    LinearThresholdEpidemic lte = new LinearThresholdEpidemic(this);
                    lte.CalculateLinearThreshold(nodeThresholds, edgeThresholds);
                    lte.DisplayLinearThreshold();
                    
                    resultsPaneUse = true;
                    exportResultsMenuItem.setEnabled(true);
                }
                else{
                    return;
                }
                
            });
            epidemics.add(linearThresholdMenuItem);
            
            independentCascadeMenuItem.addActionListener((ActionEvent e) -> {
                selectedNodes = 0;
                
                Object[] choices = {"Load File..", "Step by Step..", "Cancel"};
                Object defaultChoice = choices[0];
                int result = JOptionPane.showOptionDialog(this, "Would you like to load the inputs by loading a file, or step by step?", "Independent Cascade Inputs", 
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, defaultChoice);
                
                if(result == JOptionPane.YES_OPTION) {
                    final JFileChooser fc = new JFileChooser();
                    fc.setAcceptAllFileFilterUsed(false);
                    fc.setFileFilter(new FileNameExtensionFilter("Epidemic Files", "ept"));
                    int returnVal = fc.showOpenDialog(MainFrame.this);


                    if (returnVal == JFileChooser.APPROVE_OPTION){
                        String epidemicFile = fc.getSelectedFile().toString();
                        LoadEpidemic(epidemicFile, "ic");
                    }
                }
                else if(result == JOptionPane.NO_OPTION){
                    boolean nodesSelected = false;
                    List<Node> startingNodes = new ArrayList<>();
                    
                    do{
                        startingNodes = StartingNodesDialog();

                        if(startingNodes == null) return;

                        if(startingNodes.isEmpty()){
                            int result2 = JOptionPane.showOptionDialog(this, "You have to select at least 1 node to start the Algorithm", "No Nodes Selected", 
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);

                            if(result2 != JOptionPane.OK_OPTION) return;
                        }
                        else{
                            nodesSelected = true;
                        }
                    }while(!nodesSelected);
                    
                    List<Double> fullStartingNodes = new ArrayList<>();

                    List<Double> edgeThresholds = EdgeThresholdsDialog();
                    
                    for(int i=0; i<previewPanel.nodes.size(); i++) {
                        if(startingNodes.contains(previewPanel.nodes.get(i))) {
                            fullStartingNodes.add(-1.0);
                        }
                        else {
                            fullStartingNodes.add(0.0);
                        }
                    }
                    
                    String epidemicSave = ShowEpidemicSaveDialog();
                    
                    if(!epidemicSave.equals("discard")){
                        SaveEpidemic(epidemicSave, "ic", fullStartingNodes, edgeThresholds);
                    }
                }
                else{
                    return;
                }
            });
            epidemics.add(independentCascadeMenuItem);
          
        MainMenuBar.add(about);
            aboutItem.addActionListener((ActionEvent e) -> {
                JOptionPane.showMessageDialog(this, "----------------------------------------\n\n" + 
                        "Created by: Segditsas Konstantinos\n" + "ksegditsas@yahoo.gr\n\n" +
                        "Advisor Professor: Katsaros Dimitrios\n" + "dkatsar@inf.uth.gr\n\n" + "ver 1.0\n\n" + 
                        "----------------------------------------\n"
                        , "About MyProgram", JOptionPane.INFORMATION_MESSAGE);
            });
            about.add(aboutItem);
            
    
        return MainMenuBar;
    }
    
    
    private void LoadEpidemic(String file, String epidemic){
        
        Scanner inputFile;
        StringTokenizer current_line;
        
        int numOfNodes = previewPanel.nodes.size();
        int numOfEdges = previewPanel.edges.size();
        
        List<Double> nodeThreshold = new ArrayList<>();
        double[] nodeThresholdArray = new double[numOfNodes];
        List<Double> edgeThreshold = new ArrayList<>();
        double[] edgeThresholdArray = new double[numOfEdges];
        
        try {
            inputFile = new Scanner(new FileReader(file));
            
            while(inputFile.hasNextLine()){
                current_line = new StringTokenizer(inputFile.nextLine());

                if(current_line.hasMoreTokens()){
                    String s = current_line.nextToken();
                    String nextToken;

                    if(s.startsWith("#")) {
                        continue;
                    }
                    else{
                        int currentNodeLabel = Integer.valueOf(s);
                        int i;
                                
                        for(i=0; i<numOfNodes; i++){
                            if(previewPanel.nodes.get(i).label == currentNodeLabel){
                                nextToken = current_line.nextToken();
                                nodeThresholdArray[i] = Double.valueOf(nextToken);

                                
                                break;
                            }
                        }

                        while(current_line.hasMoreTokens()){
                            nextToken = current_line.nextToken();
                            String[] result = nextToken.split(",");
                            
                            int currentNeighborLabel = Integer.valueOf(result[0]);
                            
                            for(int j=0; j<numOfEdges; j++){
                                if(previewPanel.edges.get(j).node1.label == i && previewPanel.edges.get(j).node2.label == currentNeighborLabel){
                                    edgeThresholdArray[j] = Double.valueOf(result[1]);
                                }
                            }
                        }
                    }
                }
            }
            
            for(int i=0; i<numOfNodes; i++){
                nodeThreshold.add(nodeThresholdArray[i]);
            }
            
            for(int i=0; i<numOfEdges; i++){
                edgeThreshold.add(edgeThresholdArray[i]);
            }
            
            if(resultsPaneUse) previewPanel.resetNodesColor();
            
            if(epidemic.equals("lt")){
                LinearThresholdEpidemic lte = new LinearThresholdEpidemic(this);
                lte.CalculateLinearThreshold(nodeThreshold, edgeThreshold);
                lte.DisplayLinearThreshold();
            }
            
            inputFile.close();
        }
        catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog (this, "File Not Found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog (this, "Invalid Input File Format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    
    public List<Node> getStartingNodes(List<Double> list, String sort) {
        List<EpidemicNode> p = new ArrayList<>();
        
        for(int i=0; i<list.size(); i++) {
            p.add(new EpidemicNode(i, list.get(i)));
        }

        Collections.sort(p, new Comparator<EpidemicNode>() {
            @Override
            public int compare(EpidemicNode o1, EpidemicNode o2) {
                Double n1 = o1.value;
                Double n2 = o2.value;
                return n1.compareTo(n2);
            }
        });

        if(sort.equals("best")) {
            Collections.reverse(p);
        }
        
        List<Node> startingNodes = new ArrayList<>();

        for(int i=0; i<selectedNodes; i++) {
            startingNodes.add(previewPanel.nodes.get(p.get(i).index));
        }
        
        return startingNodes;
    }
    
    
    private String ShowEpidemicSaveDialog(){
        
        int result = JOptionPane.showOptionDialog(this, "Would you like to save the selected input values for future use?", "Save Input Values",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
							null, null, null);
        
        if(result != JOptionPane.OK_OPTION) {
            return "discard";
        }
        
        final JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileNameExtensionFilter("Epidemic Files", "ept"));
        int returnVal = fc.showSaveDialog(this);


        if (returnVal == JFileChooser.APPROVE_OPTION){
            String epidemicSave;
            return epidemicSave = fc.getSelectedFile().toString() + ".ept";
        }
        
        return "discard";
    }
    
    
    public boolean ShowSaveDialog(){
        
        final JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileNameExtensionFilter("MyProgram files", "cnt"));
        int returnVal = fc.showSaveDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION){
            MainFrame.lastLoadedFile = fc.getSelectedFile().toString() + ".cnt";
            previewPanel.SaveGraph(MainFrame.lastLoadedFile);
        }
        return returnVal == JFileChooser.APPROVE_OPTION;
    }
    
    
    private void SaveEpidemic(String file, String epidemic, List<Double> nodesThresholds, List<Double> edgesThresholds){
        
        int numOfNodes = nodesThresholds.size();
        int numOfEdges = edgesThresholds.size();
        
        PrintStream myOutput = null;
        try {
            myOutput = new PrintStream(new FileOutputStream(file));
        }
        catch (Exception ex) {
                System.out.println("No output file written");
                return;
        }
        
        if(epidemic.equals("lt")){
            myOutput.print("#Linear Threshold input\n\n");
            myOutput.print("#List of Nodes, with their Thresholds and Edges Thresholds\n\n");
        }
        else{
            myOutput.print("#Independent Cascade input\n\n");
            myOutput.print("#List of Nodes and Edges Thresholds\n\n");
        }
        
        for(int i=0; i<numOfNodes; i++){
            myOutput.print(previewPanel.nodes.get(i).label + "\t");
            myOutput.print(nodesThresholds.get(i) + "\t");
            
            for(int j=0; j<numOfEdges; j++){
                if(previewPanel.edges.get(j).node1.label == previewPanel.nodes.get(i).label){
                    myOutput.print(previewPanel.edges.get(j).node2.label);
                    myOutput.print("," + edgesThresholds.get(j));
                    myOutput.print("\t");
                }
            }
            myOutput.print("\n");
        }
        myOutput.close();
    }
    
    
    public void UpdateInfoPanel(String name){
        
        String graphName = name;
        
        if(graphName.isEmpty()){
            if(previewPanel.nodes.isEmpty()){
                graphName = "";
            }
            else{
                if(lastLoadedFile == null){
                graphName = "User Graph";
            }
                else{
                    graphName = lastLoadedFile;
                }
            }
        }

        if(previewPanel.directedGraphMenuItem.getState()){
            directedCheckLabel.setText("\u2713");
        }
        else{
            directedCheckLabel.setText("\u00D7");
        }

        if(previewPanel.weightedGraphMenuItem.getState()){
            weightedCheckLabel.setText("\u2713");
        }
        else{
            weightedCheckLabel.setText("\u00D7");
        }

        graphTitle.setText("Graph:   " + graphName);
        nodesCounter.setText("" + previewPanel.nodes.size());
        edgesCounter.setText("" + previewPanel.edges.size());
    }
    
    
    public void UpdateAlgorithmName(String name){
        
        String algorithmName = name;
        
        currentAlgorithm.setText(algorithmName);
    }
    
    
    private void NewRandomGraph(){

        // Creates the Spinner for the number of Nodes with a downlimit of 1, and a spinner filter
        // for integers, to prevent wrong user input
        SpinnerNumberModel limits = new SpinnerNumberModel(20, 1, Short.MAX_VALUE, 1);
        JSpinner numOfNodesSpinner = new JSpinner(limits);
        
        JFormattedTextField spinnerFilter = ((JSpinner.NumberEditor) numOfNodesSpinner.getEditor()).getTextField();
        ((NumberFormatter) spinnerFilter.getFormatter()).setAllowsInvalid(false);
        
        int startValue = 50;
        final JLabel densitySliderLabel = new JLabel("Choose Graph density: " + startValue + "%");
        
        // Creates the Density Slider, right above is the label for the Slider
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

        int result = JOptionPane.showOptionDialog(this, fullMessage, "Random Graph Properties",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
							null, null, null);
        
        if(result != JOptionPane.OK_OPTION) {
            return;
        }
        
        // If there is already a graph in use, asks the user to save his progress
        discard = previewPanel.ClearGraphIfInUse();
        if(discard) return;
        
        int density = densitySlider.getValue();
        int numberOfNodes = (int)numOfNodesSpinner.getValue();
        
        previewPanel.RandomGraph(density, numberOfNodes);
    }
    
    
    private void NewSmallWorldGraph(){
        
        // Creates the Spinner for the number of Nodes with a downlimit of 1, and a spinner filter
        // for integers, to prevent wrong user input
        SpinnerNumberModel nodeLimits = new SpinnerNumberModel(20, 1, Short.MAX_VALUE, 1);
        JSpinner numOfNodesSpinner = new JSpinner(nodeLimits);
        
        JFormattedTextField spinnerFilter = ((JSpinner.NumberEditor) numOfNodesSpinner.getEditor()).getTextField();
        ((NumberFormatter) spinnerFilter.getFormatter()).setAllowsInvalid(false);
        
        int startValue = 50;
        final JLabel rewireSliderLabel = new JLabel("Possibility to maintain starting edges: " + startValue + "%");
        
        // Creates the Density Slider, right above is the label for the Slider
        JSlider rewireSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, startValue);
        rewireSlider.setMajorTickSpacing(20);
        rewireSlider.setMinorTickSpacing(5);
        rewireSlider.setPaintTicks(true);
        rewireSlider.setPaintLabels(true);
        
        // Change listener for the Density Slider to catch the real time changes
        rewireSlider.addChangeListener((ChangeEvent e) -> {
            rewireSliderLabel.setText("Possibility to maintain starting edges: " + rewireSlider.getValue() + "%");
        });
        
        Object[] getNodes = {"Enter number of nodes:", numOfNodesSpinner, "\n\n", rewireSliderLabel, rewireSlider};
        
        int result = JOptionPane.showOptionDialog(this, getNodes, "Small World Graph Properties 1/2",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
							null, null, null);
        
        if(result != JOptionPane.OK_OPTION) {
            return;
        }
        
        // If there is already a graph in use, asks the user to save his progress
        discard = previewPanel.ClearGraphIfInUse();
        if(discard) return;
        
        int numberOfNodes = (int)numOfNodesSpinner.getValue();
        int p = rewireSlider.getValue();
        
        int zLimit = (int)Math.floor(0.15*numberOfNodes);
        
        if ( (zLimit % 2) != 0 ) zLimit--;
        
        zLimit /= 2;
        
        Object[] zValues = new Object[zLimit + 1];
        
        for(int i=0; i<=zLimit; i++){
            zValues[i] = i*2;
        }
        
        Object s = JOptionPane.showInputDialog(this, "Starting amount of neighbors for each Node:\n", "Small World Graph Properties 2/2", 
                                                    JOptionPane.PLAIN_MESSAGE, null, zValues, 0);
        
        if(s == null) return;
        
        int Z = (int) s;
        
        previewPanel.SmallWorldGraph(numberOfNodes, p, Z);
    }
    
    
    public List<Node> StartingNodesDialog() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        Border b = javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED);

        JRadioButton firstRB = new JRadioButton("Random");
        firstRB.setActionCommand("first");
        JRadioButton secondRB = new JRadioButton("User Select");
        secondRB.setActionCommand("second");
        JRadioButton thirdRB = new JRadioButton("Centrality Based", true);
        thirdRB.setActionCommand("third");

        ButtonGroup group = new ButtonGroup();
        group.add(firstRB);
        group.add(secondRB);
        group.add(thirdRB);

        // random
        SpinnerNumberModel limits = new SpinnerNumberModel(1, 1, previewPanel.nodes.size(), 1);
        JSpinner firstInfectedNodes = new JSpinner(limits);

        JFormattedTextField spinnerFilter = ((JSpinner.NumberEditor) firstInfectedNodes.getEditor()).getTextField();
        ((NumberFormatter) spinnerFilter.getFormatter()).setAllowsInvalid(false);

        JPanel randomPanel = new JPanel(new BorderLayout());
        randomPanel.add(firstInfectedNodes);
        randomPanel.setVisible(false);

        // centralities
        
        JRadioButton kshellButton;
        if(!previewPanel.graphIsWeighted()){
            kshellButton = new JRadioButton("k-Shell");
        }
        else{
            kshellButton = new JRadioButton("s-Core");
        }
        kshellButton.setActionCommand("kshell");
        kshellButton.setEnabled(!previewPanel.graphIsDirected());
        
        JRadioButton pciButton = new JRadioButton("μ-PCI");
        pciButton.setActionCommand("pci");
        pciButton.setEnabled(!previewPanel.graphIsDirected() && !previewPanel.graphIsWeighted());
        
        JRadioButton betweennessButton = new JRadioButton("Betweenness");
        betweennessButton.setActionCommand("betweenness");
        
        JRadioButton degreeButton;
        if(!previewPanel.graphIsDirected()){
            degreeButton = new JRadioButton("Degree", true);
        }
        else{
            degreeButton = new JRadioButton("Out Degree", true);
        }
        degreeButton.setActionCommand("degree");
        
        JRadioButton pageRankButton = new JRadioButton("Page Rank");
        pageRankButton.setActionCommand("pagerank");
        pageRankButton.setEnabled(previewPanel.graphIsDirected() && !previewPanel.graphIsWeighted());

        ButtonGroup centralitiesGroup = new ButtonGroup();
        centralitiesGroup.add(degreeButton);
        centralitiesGroup.add(betweennessButton);
        centralitiesGroup.add(kshellButton);
        centralitiesGroup.add(pciButton);
        centralitiesGroup.add(pageRankButton);


        JRadioButton bestButton = new JRadioButton("Best");
        bestButton.setActionCommand("best");
        bestButton.setSelected(true);
        JRadioButton worstButton = new JRadioButton("Worst");
        worstButton.setActionCommand("worst");

        ButtonGroup sortingGroup = new ButtonGroup();
        sortingGroup.add(bestButton);
        sortingGroup.add(worstButton);

        JPanel leftPanel = new JPanel(new GridLayout(0, 1));
        leftPanel.add(degreeButton);
        leftPanel.add(betweennessButton);
        leftPanel.add(kshellButton);
        leftPanel.add(pciButton);
        leftPanel.add(pageRankButton);

        JPanel rightPanel = new JPanel(new GridLayout(0, 1));
        rightPanel.add(bestButton);
        rightPanel.add(worstButton);

        JSpinner firstInfectedNodes2 = new JSpinner(limits);
        JFormattedTextField spinnerFilter2 = ((JSpinner.NumberEditor) firstInfectedNodes2.getEditor()).getTextField();
        ((NumberFormatter) spinnerFilter2.getFormatter()).setAllowsInvalid(false);

        JPanel centralitiesPanel = new JPanel(new BorderLayout(0, 10));
        centralitiesPanel.add(firstInfectedNodes2, BorderLayout.PAGE_START);
        centralitiesPanel.add(leftPanel, BorderLayout.LINE_START);
        centralitiesPanel.add(rightPanel, BorderLayout.LINE_END);


        // user select
        JPanel selectPanel = new JPanel(new GridLayout(0, 1));

        List<JCheckBox> cbs = new ArrayList<>();

        for(int i=0; i<previewPanel.nodes.size(); i++) {
            JCheckBox cb = new JCheckBox(previewPanel.nodes.get(i).label + "");
            cb.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {

                    if(cb.isSelected()) {
                        selectedNodes++;
                    }
                    else {
                        selectedNodes--;
                    }

                    mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Nodes Selected: " + selectedNodes));
                }
            });
            cbs.add(cb);
            selectPanel.add(cb);
        }

        JScrollPane selectScrollPane = new JScrollPane(selectPanel);
        selectScrollPane.setVisible(false);
        selectScrollPane.setBorder(null);


        mainPanel.setPreferredSize(new Dimension(93, 167));
        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Centrality and Order"));
        mainPanel.add(randomPanel, BorderLayout.PAGE_START);
        mainPanel.add(selectScrollPane, BorderLayout.CENTER);
        mainPanel.add(centralitiesPanel, BorderLayout.PAGE_END);

        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.add(firstRB, BorderLayout.LINE_START);
        panel.add(secondRB, BorderLayout.CENTER);
        panel.add(thirdRB, BorderLayout.LINE_END);
        panel.add(mainPanel, BorderLayout.PAGE_END);

        firstRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Number of Nodes"));
                randomPanel.setVisible(true);
                centralitiesPanel.setVisible(false);
                selectScrollPane.setVisible(false);
            }
        });

        thirdRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Centrality and Order"));
                randomPanel.setVisible(false);
                centralitiesPanel.setVisible(true);
                selectScrollPane.setVisible(false);
            }
        });

        secondRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Nodes Selected: " + selectedNodes));
                randomPanel.setVisible(false);
                centralitiesPanel.setVisible(false);
                selectScrollPane.setVisible(true);
            }
        });

        Object[] firstMessage = {"How would you like to select the first Infected Nodes?\n\n", panel};

        int result = JOptionPane.showOptionDialog(this, firstMessage, "Starting Nodes", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if(result != JOptionPane.OK_OPTION) return null;

        List<Node> startingNodes = new ArrayList<>();

        String selection = group.getSelection().getActionCommand();
        if(selection.equals("first")) {
            selectedNodes = (int)firstInfectedNodes.getValue();

            List<Integer> tempArray = new ArrayList<>();

            for(int i=0; i<previewPanel.nodes.size(); i++) {
                tempArray.add(i);
            }

            Collections.shuffle(tempArray);

            for(int i=0; i<selectedNodes; i++) {
                startingNodes.add(previewPanel.nodes.get(tempArray.get(i)));
            }
        }
        else if(selection.equals("second")) {
            for(int i=0; i<cbs.size(); i++) {
                if(cbs.get(i).isSelected()) {
                    startingNodes.add(previewPanel.nodes.get(i));
                }
            }
        }
        else if(selection.equals("third")) {
            selectedNodes = (int)firstInfectedNodes2.getValue();

            String centralitySelection = centralitiesGroup.getSelection().getActionCommand();
            String sortingSelection = sortingGroup.getSelection().getActionCommand();

            if(centralitySelection.equals("kshell")) {
                KshellScoreCentrality kshell = new KshellScoreCentrality(this);
                List<List<Node>> list = kshell.CalculateKshell(previewPanel.nodes, previewPanel.edges);

                if(sortingSelection.equals("best")) {
                    Collections.reverse(list);
                }

                for(int i=0; i<list.size(); i++) {
                    for(int j=0; j<list.get(i).size(); j++) {
                        startingNodes.add(list.get(i).get(j));

                        if(startingNodes.size() == selectedNodes) {
                            break;
                        }
                    }

                    if(startingNodes.size() == selectedNodes) {
                        break;
                    }
                }
            }
            else if(centralitySelection.equals("pci")) {
                DegreeCentrality degree = new DegreeCentrality(this, false, false, false);
                List<Double> graphDegrees = degree.UndirectedUnweightedDegree(previewPanel.nodes, previewPanel.edges);
                MpciCentrality mPci = new MpciCentrality(this, 1);
                List<Double> list = mPci.CalculateMpci(graphDegrees, previewPanel.nodes, previewPanel.edges);

                startingNodes = getStartingNodes(list, sortingSelection);
            }
            else if(centralitySelection.equals("pagerank")) {
                PageRankCentrality pagerank = new PageRankCentrality(this);
                pagerank.CalculatePageRank(previewPanel.nodes, previewPanel.edges);
                List<Node> list = pagerank.NodesRank(previewPanel.nodes);

                if(sortingSelection.equals("worst")) {
                    Collections.reverse(list);
                }

                for(int i=0; i<selectedNodes; i++) {
                    startingNodes.add(list.get(i));
                }
            }
            else if(centralitySelection.equals("betweenness")) {
                BetweennessCentrality bc = new BetweennessCentrality(this, false);
                List<Double> list = bc.CalculateBetweenness(previewPanel.nodes, previewPanel.edges);

                startingNodes = getStartingNodes(list, sortingSelection);
            }
            else if(centralitySelection.equals("degree")) {
                DegreeCentrality degree = new DegreeCentrality(this, previewPanel.graphIsDirected(), previewPanel.graphIsWeighted(), normalized);
                List<Double> list;

                if(previewPanel.graphIsDirected()){
                    if(previewPanel.graphIsWeighted()){
                        list = degree.DirectedWeightedDegree(previewPanel.nodes, previewPanel.edges).get(1);
                    }
                    else{
                        list = degree.DirectedUnweightedDegree(previewPanel.nodes, previewPanel.edges).get(1);
                    }
                }
                else{
                    if(previewPanel.graphIsWeighted()){
                        list = degree.UndirectedWeightedDegree(previewPanel.nodes, previewPanel.edges);
                    }
                    else{
                        list = degree.UndirectedUnweightedDegree(previewPanel.nodes, previewPanel.edges);
                    }
                }

                startingNodes = getStartingNodes(list, sortingSelection);
            }
        }
        
        return startingNodes;
    }
    
    
    public List<Double> NodeThresholdsDialog(List<Node> starting) {
        int remain = previewPanel.nodes.size() - starting.size();

        JPanel mainPanel = new JPanel(new BorderLayout());
        Border b = javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED);

        JRadioButton firstRB = new JRadioButton("Global", true);
        firstRB.setActionCommand("first");
        JRadioButton secondRB = new JRadioButton("User Input");
        secondRB.setActionCommand("second");
        JRadioButton thirdRB = new JRadioButton("Random");
        thirdRB.setActionCommand("third");

        ButtonGroup group = new ButtonGroup();
        group.add(firstRB);
        group.add(secondRB);
        group.add(thirdRB);

        // global
        SpinnerNumberModel limits = new SpinnerNumberModel(0.5, 0.0, 1.0, 0.05);
        JSpinner globalSpinner = new JSpinner(limits);

        JSpinner.NumberEditor editor = (JSpinner.NumberEditor)globalSpinner.getEditor();
        editor.getFormat().setMinimumFractionDigits(2);

        ((NumberFormatter) editor.getTextField().getFormatter()).setAllowsInvalid(false);

        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.add(globalSpinner);

        // random
        JPanel randomPanel = new JPanel(new BorderLayout());
        randomPanel.add(new JLabel("Random Threshold for each single Node"));
        randomPanel.setVisible(false);

        // user input
        JPanel userInputPanel = new JPanel(new GridLayout(0, 2));

        List<JSpinner> spinners = new ArrayList<>();

        for(int i=0; i<previewPanel.nodes.size(); i++) {
            if(!starting.contains(previewPanel.nodes.get(i))) {
                JSpinner spinner = new JSpinner(new SpinnerNumberModel(0.5, 0.0, 1.0, 0.05));
                JSpinner.NumberEditor e = (JSpinner.NumberEditor)spinner.getEditor();
                e.getFormat().setMinimumFractionDigits(2);
                ((NumberFormatter) e.getTextField().getFormatter()).setAllowsInvalid(false);

                spinners.add(spinner);
                
                userInputPanel.add(new JLabel(previewPanel.nodes.get(i).label + ""));
                userInputPanel.add(spinner);
            }
        }

        JScrollPane selectScrollPane = new JScrollPane(userInputPanel);
        selectScrollPane.setVisible(false);
        selectScrollPane.setBorder(null);

        mainPanel.setPreferredSize(new Dimension(93, 22 + (remain > 6 ? 120 : remain*20)));
        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Same Threshold for all Nodes"));
        mainPanel.add(globalPanel, BorderLayout.PAGE_START);
        mainPanel.add(selectScrollPane, BorderLayout.CENTER);
        mainPanel.add(randomPanel, BorderLayout.PAGE_END);

        JPanel panel = new JPanel(new BorderLayout(46, 20));
        panel.add(firstRB, BorderLayout.LINE_START);
        panel.add(secondRB, BorderLayout.CENTER);
        panel.add(thirdRB, BorderLayout.LINE_END);
        panel.add(mainPanel, BorderLayout.PAGE_END);

        firstRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Same Threshold for all Nodes"));
                globalPanel.setVisible(true);
                randomPanel.setVisible(false);
                selectScrollPane.setVisible(false);
            }
        });

        secondRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Specific Threshold for each Node"));
                globalPanel.setVisible(false);
                randomPanel.setVisible(false);
                selectScrollPane.setVisible(true);
            }
        });

        thirdRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Global Random Threshold"));
                globalPanel.setVisible(false);
                randomPanel.setVisible(true);
                selectScrollPane.setVisible(false);
            }
        });

        Object[] firstMessage = {"Which way would you like to insert the Nodes Thresholds?\n\n", panel};

        int result = JOptionPane.showOptionDialog(this, firstMessage, "Nodes Thresholds", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if(result != JOptionPane.OK_OPTION) return null;

        List<Double> thresholds = new ArrayList<>();

        String selection = group.getSelection().getActionCommand();
        if(selection.equals("first")) {
            double value = (double)globalSpinner.getValue();
            for(int i=0; i<previewPanel.nodes.size(); i++) {
                if(starting.contains(previewPanel.nodes.get(i))) {
                    thresholds.add(-1.0);
                }
                else {
                    thresholds.add(value);
                }
            }
        }
        else if(selection.equals("second")) {
            for(int i=0; i<previewPanel.nodes.size(); i++) {
                if(starting.contains(previewPanel.nodes.get(i))) {
                    thresholds.add(-1.0);
                }
                else {
                    thresholds.add((double)Math.round((double)spinners.get(0).getValue()*100)/100);
                    spinners.remove(0);
                }
            }
        }
        else if(selection.equals("third")) {
            Random rand = new Random();

            for(int i=0; i<previewPanel.nodes.size(); i++) {
                if(starting.contains(previewPanel.nodes.get(i))) {
                    thresholds.add(-1.0);
                }
                else {
                    thresholds.add((double)Math.round(rand.nextDouble()*100)/100);
                }
            }
        }
        
        return thresholds;
    }
    
    
    public List<Double> EdgeThresholdsDialog() {
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        Border b = javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED);

        JRadioButton firstRB = new JRadioButton("Global", true);
        firstRB.setActionCommand("first");
        JRadioButton secondRB = new JRadioButton("User Input");
        secondRB.setActionCommand("second");
        JRadioButton thirdRB = new JRadioButton("Random");
        thirdRB.setActionCommand("third");

        ButtonGroup group = new ButtonGroup();
        group.add(firstRB);
        group.add(secondRB);
        group.add(thirdRB);

        // global
        SpinnerNumberModel limits = new SpinnerNumberModel(0.5, 0.0, 1.0, 0.05);
        JSpinner globalSpinner = new JSpinner(limits);

        JSpinner.NumberEditor editor = (JSpinner.NumberEditor)globalSpinner.getEditor();
        editor.getFormat().setMinimumFractionDigits(2);

        ((NumberFormatter) editor.getTextField().getFormatter()).setAllowsInvalid(false);

        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.add(globalSpinner);

        // random
        JPanel randomPanel = new JPanel(new BorderLayout());
        randomPanel.add(new JLabel("Random Possibility for each single Edge"));
        randomPanel.setVisible(false);

        // user input
        JPanel userInputPanel = new JPanel(new GridLayout(0, 2));

        List<JSpinner> spinners = new ArrayList<>();

        for(int i=0; i<previewPanel.edges.size(); i++) {
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0.5, 0.0, 1.0, 0.05));
            JSpinner.NumberEditor e = (JSpinner.NumberEditor)spinner.getEditor();
            e.getFormat().setMinimumFractionDigits(2);
            ((NumberFormatter) e.getTextField().getFormatter()).setAllowsInvalid(false);

            spinners.add(spinner);

            userInputPanel.add(new JLabel(previewPanel.edges.get(i).label + ""));
            userInputPanel.add(spinner);
        }

        JScrollPane selectScrollPane = new JScrollPane(userInputPanel);
        selectScrollPane.setVisible(false);
        selectScrollPane.setBorder(null);

        mainPanel.setPreferredSize(new Dimension(93, 22 + (previewPanel.edges.size() > 6 ? 120 : previewPanel.edges.size()*20)));
        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Global"));
        mainPanel.add(globalPanel, BorderLayout.PAGE_START);
        mainPanel.add(selectScrollPane, BorderLayout.CENTER);
        mainPanel.add(randomPanel, BorderLayout.PAGE_END);

        JPanel panel = new JPanel(new BorderLayout(46, 20));
        panel.add(firstRB, BorderLayout.LINE_START);
        panel.add(secondRB, BorderLayout.CENTER);
        panel.add(thirdRB, BorderLayout.LINE_END);
        panel.add(mainPanel, BorderLayout.PAGE_END);

        firstRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Same Possibility for all Edges"));
                globalPanel.setVisible(true);
                randomPanel.setVisible(false);
                selectScrollPane.setVisible(false);
            }
        });

        secondRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Specific Possibility for each Edge"));
                globalPanel.setVisible(false);
                randomPanel.setVisible(false);
                selectScrollPane.setVisible(true);
            }
        });

        thirdRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(b, "Global Random polution Possibility"));
                globalPanel.setVisible(false);
                randomPanel.setVisible(true);
                selectScrollPane.setVisible(false);
            }
        });

        Object[] firstMessage = {"How would you like to insert the polution Possibility of the Edges?\n\n", panel};

        int result = JOptionPane.showOptionDialog(this, firstMessage, "Edges polution Possibility", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if(result != JOptionPane.OK_OPTION) return null;

        List<Double> thresholds = new ArrayList<>();

        String selection = group.getSelection().getActionCommand();
        if(selection.equals("first")) {
            double value = (double)globalSpinner.getValue();
            for(int i=0; i<previewPanel.edges.size(); i++) {
                thresholds.add(value);
            }
        }
        else if(selection.equals("second")) {
            for(int i=0; i<previewPanel.edges.size(); i++) {
                thresholds.add((double)Math.round((double)spinners.get(i).getValue()*100)/100);
            }
        }
        else if(selection.equals("third")) {
            Random rand = new Random();

            for(int i=0; i<previewPanel.edges.size(); i++) {
                thresholds.add((double)Math.round(rand.nextDouble()*100)/100);
            }
        }
        
        return thresholds;
    }
}
