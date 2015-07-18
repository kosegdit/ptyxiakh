package ptyxiakh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author kostas
 */

public class Node extends JComponent  {
    
    private Point mousePt;
    public int label;
    public int radius;
    public Color color;
    public boolean selected;
    
    public Rectangle b = new Rectangle();
    
    Node firstNode;
    Node secondNode;
    
    JPopupMenu NodePopup = new JPopupMenu();
    JMenuItem newEdgeMenuItem = new JMenuItem("Connect with..");
    JMenuItem deleteEdgeMenuItem = new JMenuItem("Disconnect from..");
    JMenuItem deleteNode = new JMenuItem("Delete");


    // Default Constructor
    public Node(){
    }


    public Node(int label, Point p, Graph currentGraph) {
    
        this.label = label;
        
        newEdgeMenuItem.setToolTipText("Choose the node you want to connect");
        newEdgeMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((Graph) Node.this.getParent()).readyToConnect = Node.this;
            }
        });
        NodePopup.add(newEdgeMenuItem);
        
        deleteEdgeMenuItem.setToolTipText("Choose the node you want to connect");
        deleteEdgeMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((Graph) Node.this.getParent()).readyToDisconnect = Node.this;
            }
        });
        NodePopup.add(deleteEdgeMenuItem);
        
        deleteNode.addActionListener((ActionEvent e) -> {
                    currentGraph.userDeleteNode(this);
                });
        NodePopup.add(deleteNode);
        
        
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)){
                    Graph g = (Graph) Node.this.getParent();
                    if (g.readyToConnect != null) {
                        boolean directed = ((Graph) Node.this.getParent()).graphIsDirected();
                        boolean weighted = ((Graph) Node.this.getParent()).graphIsWeighted();
                        int weight = 1;
                        
                        if(weighted){
                            SpinnerNumberModel edgeWeight = new SpinnerNumberModel();
                            JSpinner edgeWeightSpinner = new JSpinner(edgeWeight);
                            
                            JFormattedTextField spinnerFilter = ((JSpinner.NumberEditor) edgeWeightSpinner.getEditor()).getTextField();
                            ((NumberFormatter) spinnerFilter.getFormatter()).setAllowsInvalid(false);
                            
                            // An array of objects is created to carry all the information for the displayed window
                            Object[] fullMessage = {"Enter desired weight:", edgeWeightSpinner, "\n\n"};

                            int result = JOptionPane.showOptionDialog(null, fullMessage, "Current Edge Weight",
                                                                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                                            null, null, null);

                            if(result != JOptionPane.OK_OPTION) {
                                return;
                            }

                            weight = (int)edgeWeightSpinner.getValue();
                            System.out.println(weight);
                        }
                        
                        g.userConnectNodes(Node.this, directed, weighted, weight);
                    }
                    if(g.readyToDisconnect != null){
                        g.userDisconnectNodes(Node.this);
                    }
                }
                
                mousePt = e.getPoint();
                
                if (SwingUtilities.isRightMouseButton(e)){
                    NodePopup.show(e.getComponent(), e.getX(), e.getY());
                    newEdgeMenuItem.setEnabled(currentGraph.nodes.size() > 1);
                    deleteEdgeMenuItem.setEnabled(hasNeighbor());
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    if(mousePt.x > 10 && mousePt.x < getWidth()-10 && mousePt.y > 10 && mousePt.y < getHeight()-10) {
                        Node node = (Node) e.getSource();
                        node.setLocation(node.getX() + e.getX() - mousePt.x, node.getY() + e.getY() - mousePt.y);
                    }
                }
            }
        });
        
        setNodeColor(Color.BLUE);
        
        setLocation(p);
        setSize(new Dimension(30, 30));
        
        setVisible(true);
        repaint();
    }
    
    public int myListPosition(){
        
        Graph g = (Graph) Node.this.getParent();
        
        for(int i=0; i<g.nodes.size(); i++){
            if(this.equals(g.nodes.get(i))){
                return i;
            }
        }
        
        return -1;
    }
    
    public boolean hasNeighbor(){
    
        Graph g = (Graph) Node.this.getParent();
        
        for(int i=0; i<g.edges.size(); i++){
            if(this.equals(g.edges.get(i).node1)){
                return true;
            }
        }
        
        if(!g.graphIsDirected()){
            for(int i=0; i<g.edges.size(); i++){
                if(this.equals(g.edges.get(i).node2)){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public Node otherNeighbor(Edge edge){
        
        if(edge.node1.equals(this)){
            return edge.node2;
        }
        else{
            return edge.node1;
        }
    }
    
    
    public void resize(int action){
        
        this.setSize(this.getWidth() + action, this.getHeight() + action);
    }
    
    
    public Color setNodeColor(Color desiredColor){
        return(this.color = desiredColor);
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(this.color);
        g2.fillOval(10, 10, getWidth()-20, getHeight()-20);
        g2.setColor(Color.BLACK);
        g2.drawString(this.label + "", 9, 9); 
    }
}
