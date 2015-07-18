package ptyxiakh;

import java.awt.event.ActionEvent;
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
        RightSplitPane = new JSplitPane();
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
        BaseSplitPane.setLeftComponent(previewPanel);
        BaseSplitPane.setRightComponent(RightSplitPane);
        
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
        JMenuItem degreeMenuItem = new JMenuItem("Degree");
        JMenuItem closenessMenuItem = new JMenuItem("Closeness");
        JMenuItem betweenness = new JMenuItem("Betweenness");
        JMenuItem edgeBetweenness = new JMenuItem("Edge Betweenness");
        JMenuItem μpci = new JMenuItem("μ-Pci");
        JMenuItem kShell = new JMenuItem("k-Shell");
        JMenuItem pageRank = new JMenuItem("Page Rank");
        JMenuItem randomGraphMenuItem = new JMenuItem("Random graph");
            randomGraphMenuItem.setToolTipText("Creates a random graph using the Erdős–Rényi model");
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
                randomGraphMenuItem.addActionListener((ActionEvent e) -> {
                    NewRandomGraph();
                });
                generate.add(randomGraphMenuItem);
                
                generate.add(smallWorldGraph);
                generate.add(scaleFreeGraph);
            file.addSeparator();
            
            load.addActionListener((ActionEvent e) -> {
                discard = previewPanel.ClearGraphIfInUse();
                if(discard) return;
                
                final JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.setFileFilter(new FileNameExtensionFilter("MyProgram files", "cnt"));
                int returnVal = fc.showOpenDialog(MainFrame.this);
                
                
                if (returnVal == JFileChooser.APPROVE_OPTION){
                    lastLoadedFile = fc.getSelectedFile().toString();
                    previewPanel.LoadGraph(lastLoadedFile);
                }
            });
            file.add(load);
            file.addSeparator();
            
            save.addActionListener((ActionEvent e) -> {
                if(lastLoadedFile == null){
                    previewPanel.ShowSaveDialog();
                }
                else{
                    previewPanel.SaveGraph(lastLoadedFile);
                }
            });
            file.add(save);
            
            saveAs.addActionListener((ActionEvent e) -> {
                previewPanel.ShowSaveDialog();
            });
            file.add(saveAs);
            file.addSeparator();
            
                exit.addActionListener((ActionEvent e) -> {
                    System.exit(0);
                });
            file.add(exit);

        MainMenuBar.add(centralities);
            degreeMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    int result = JOptionPane.showOptionDialog(null, "Normalize Results?", "Degree Normalization",
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
                    
                    boolean currentGraphDirected = previewPanel.graphIsDirected();
                    boolean currentGraphWeighted = previewPanel.graphIsWeighted();
                    
                    DegreeCentrality degree = new DegreeCentrality(this, currentGraphDirected, currentGraphWeighted, normalized);
                    degree.CalculateDegree(previewPanel.nodes, previewPanel.edges);
                    resultsPaneUse = true;
                }
            });
            centralities.add(degreeMenuItem);
            
            closenessMenuItem.addActionListener((ActionEvent e) -> {
                if(previewPanel.graphInUse){
                    int result = JOptionPane.showOptionDialog(null, "Normalize Results?", "Closeness Normalization",
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
                    
                    boolean currentGraphDirected = previewPanel.graphIsDirected();
                    boolean currentGraphWeighted = previewPanel.graphIsWeighted();
                    
                    ClosenessCentrality closeness = new ClosenessCentrality(this, normalized);
                    closeness.CalculateCloseness(previewPanel.nodes);
                    resultsPaneUse = true;
                }
            });
            centralities.add(closenessMenuItem);
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
    
    public void UpdateInfoPanel(){
        
        String graphName;
        
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
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
							null, null, null);
        
        if(result != JOptionPane.OK_OPTION) {
            return;
        }
        
        int density = densitySlider.getValue();
        int numberOfNodes = (int)numOfNodesSpinner.getValue();
        
        previewPanel.RandomGraph(density, numberOfNodes);
    }
}
