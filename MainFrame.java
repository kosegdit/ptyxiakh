package ptyxiakh;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
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
    JScrollPane historyScrollPane;
    JScrollPane resultsScrollPane;
    JTabbedPane bottomRightTabbedPane;
    static String lastLoadedFile;
    JLabel graphTitle;
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
        historyScrollPane = new JScrollPane();
        resultsScrollPane = new JScrollPane();
        bottomRightTabbedPane = new JTabbedPane();
        graphTitle = new JLabel("Graph:");
        directedLabel = new JLabel("Directed:");
        directedCheckLabel = new JLabel("\u00D7");
        weightedLabel = new JLabel("Weighted:");
        weightedCheckLabel = new JLabel("\u00D7");
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
        infoPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        
        
        
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
                            .addComponent(directedCheckLabel))))
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
                .addContainerGap(32, Short.MAX_VALUE))
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
        JMenuItem ebc = new JMenuItem("EBC");
            ebc.setToolTipText("Newmann & Girvan using the Edge Betweenness Centrality");
        JMenuItem cibc = new JMenuItem("CiBC");
            cibc.setToolTipText("Communities identification with Betweenness Centrality");
        
        
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
                    closeness.CalculateCloseness();
                    closeness.DisplayCloseness();
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
                    betweenness.CalculateBetweenness();
                    betweenness.DisplayBetweenness();
                    resultsPaneUse = true;
                    exportResultsMenuItem.setEnabled(true);
                }
            });
            centralities.add(betweennessMenuItem);
            
            edgeBetweennessMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    
                    if(resultsPaneUse) previewPanel.resetNodesColor();
                    
                    EdgeBetweennessCentrality edgeBetweenness = new EdgeBetweennessCentrality(this);
                    edgeBetweenness.CalculateEdgeBetweenness();
                    edgeBetweenness.DisplayEdgeBetweenness();
                    resultsPaneUse = true;
                    exportResultsMenuItem.setEnabled(true);
                }
            });
            centralities.add(edgeBetweennessMenuItem);
            
            μpciMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    if(resultsPaneUse) previewPanel.resetNodesColor();
                    
                    if(!previewPanel.graphIsDirected() && !previewPanel.graphIsWeighted()){
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
                        mPci.CalculateMpci(graphDegrees);
                        mPci.DisplayMpci();
                        resultsPaneUse = true;
                        exportResultsMenuItem.setEnabled(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "μ-Pci can only be applied to Undirected, Unweighted graphs");
                    }
                }
                
            });
            centralities.add(μpciMenuItem);
            
            kShellMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    if(!previewPanel.graphIsDirected()){
                        KshellScoreCentrality kshell = new KshellScoreCentrality(this);
                        
                        if(!previewPanel.graphIsWeighted()){
                            kshell.CalculateKshell();
                            kshell.DisplayKshell();
                        }
                        else{
                            kshell.CalculateScore();
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
                        pagerank.CalculatePageRank();
                        pagerank.NodesRank();
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
            communities.add(ebc);
            communities.add(cibc);
            
        MainMenuBar.add(epidemics);
          
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
    
    
    private void NewRandomGraph(){

        // If there is already a graph in use, asks the user to save his progress
        discard = previewPanel.ClearGraphIfInUse();
        if(discard) return;
        
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
        
        int density = densitySlider.getValue();
        int numberOfNodes = (int)numOfNodesSpinner.getValue();
        
        previewPanel.RandomGraph(density, numberOfNodes);
    }
    
    
    private void NewSmallWorldGraph(){
        
        // If there is already a graph in use, asks the user to save his progress
        discard = previewPanel.ClearGraphIfInUse();
        if(discard) return;
        
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
}
