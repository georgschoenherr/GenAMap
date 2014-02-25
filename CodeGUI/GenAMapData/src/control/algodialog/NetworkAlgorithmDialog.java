/*
 * NetworkAlgorithmDialog.java
 *
 * Created on Sep 3, 2009, 4:12:57 PM
 */
package control.algodialog;

import algorithm.AlgorithmView;
import algorithm.Algorithms;
import control.itempanel.CytoNet;
import control.DataAddRemoveHandler;
import datamodel.TraitSet;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import java.util.ArrayList;
import datamodel.Project;
import datamodel.Model;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import realdata.DataManager;

/**
 * Allows a user to choose and algorithm that will build a network.  
 * @author ross
 */
public class NetworkAlgorithmDialog extends java.awt.Dialog
{
    AlgorithmView view;
    boolean canSelect = false;
    int traitSetId;
    JFrame parent;

    /** Creates new form NetworkAlgorithmDialog */
    public NetworkAlgorithmDialog(java.awt.Frame parent, boolean modal, AlgorithmView view, boolean createSelected,
            String traits, String project)
    {
        super(parent, modal);
        this.view = view;
        initComponents();
        this.parent = (JFrame) parent;
        this.setLocation(parent.getLocation());

        this.traitComboBox.setEnabled(false);

        for (int i = 0; i < Algorithms.NetworkAlgorithms.getalgorithms().size(); i++)
        {
            this.algorithmComboBox.addItem(Algorithms.NetworkAlgorithms.getalgorithms().get(i));
        }

        ArrayList<Project> temp = Model.getInstance().getProjects();
        for (int i = 0; i < temp.size(); i++)
        {
            this.projectComboBox.addItem(temp.get(i).getName());
        }

        if (createSelected)
        {
            this.createRadBtn.setSelected(true);
            this.fileButton.setEnabled(false);
            this.networkFileBox.setEnabled(false);
        }
        else
        {
            this.loadRadBtn.setSelected(true);
            this.fileButton.setEnabled(true);
            this.networkFileBox.setEnabled(true);
            this.algorithmComboBox.setEnabled(false);
        }
        if (project != null)
        {
            for (int i = 0; i < this.projectComboBox.getItemCount(); i++)
            {
                String s = projectComboBox.getItemAt(i).toString();
                if (s.equals(project))
                {
                    this.projectComboBox.setSelectedIndex(i);
                    i = this.projectComboBox.getItemCount();
                }
            }

            if (traits != null)
            {
                for (int i = 0; i < this.traitComboBox.getItemCount(); i++)
                {
                    if (this.traitComboBox.getItemAt(i).toString().equals(traits))
                    {
                        this.traitComboBox.setSelectedIndex(i);
                        i = this.traitComboBox.getItemCount();
                    }
                }
            }
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        projectComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        traitComboBox = new javax.swing.JComboBox();
        networkFileBox = new javax.swing.JTextField();
        fileButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        algorithmComboBox = new javax.swing.JComboBox();
        createRadBtn = new javax.swing.JRadioButton();
        loadRadBtn = new javax.swing.JRadioButton();
        importButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        importButton1 = new javax.swing.JButton();
        tabDelButton = new javax.swing.JRadioButton();
        EdgeButton = new javax.swing.JRadioButton();
        errorLabel = new javax.swing.JLabel();
        netNameText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.LineBorder(java.awt.Color.blue, 2, true));

        projectComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Select Project>" }));
        projectComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectComboBoxActionPerformed(evt);
            }
        });

        jLabel4.setText("Project");

        jLabel5.setText("Trait Set");

        traitComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Select Trait>" }));
        traitComboBox.setEnabled(false);
        traitComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                traitComboBoxActionPerformed(evt);
            }
        });

        networkFileBox.setEditable(false);
        networkFileBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                networkFileBoxMouseClicked(evt);
            }
        });

        fileButton.setText("...");
        fileButton.setEnabled(false);
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Algorithm");

        algorithmComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Select Algorithm>" }));

        buttonGroup1.add(createRadBtn);
        createRadBtn.setFont(new java.awt.Font("DejaVu Sans", 1, 18));
        createRadBtn.setText("Create a new Network");
        createRadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createRadBtnActionPerformed(evt);
            }
        });

        buttonGroup1.add(loadRadBtn);
        loadRadBtn.setFont(new java.awt.Font("DejaVu Sans", 1, 18));
        loadRadBtn.setText("Load from File");
        loadRadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadRadBtnActionPerformed(evt);
            }
        });

        importButton.setText("Run");
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        importButton1.setText("Set Parameters");
        importButton1.setEnabled(false);
        importButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButton1ActionPerformed(evt);
            }
        });

        buttonGroup2.add(tabDelButton);
        tabDelButton.setText("Tab delimited Format");
        tabDelButton.setEnabled(false);

        buttonGroup2.add(EdgeButton);
        EdgeButton.setText("Edge by edge format");
        EdgeButton.setEnabled(false);

        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        errorLabel.setText("                             ");

        jLabel1.setText("Name:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addComponent(projectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addComponent(traitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(createRadBtn))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel6)
                .addGap(15, 15, 15)
                .addComponent(algorithmComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(loadRadBtn)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(importButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addGap(9, 9, 9)
                        .addComponent(netNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(tabDelButton)
                .addGap(5, 5, 5)
                .addComponent(EdgeButton))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(networkFileBox, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(fileButton))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(importButton)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(cancelButton))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(projectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5))
                    .addComponent(traitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110)
                .addComponent(createRadBtn)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(algorithmComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(loadRadBtn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(importButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(netNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabDelButton)
                    .addComponent(EdgeButton))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(networkFileBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileButton))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(errorLabel)
                    .addComponent(importButton)
                    .addComponent(cancelButton)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void networkFileBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_networkFileBoxMouseClicked
        fileButtonActionPerformed(null);
}//GEN-LAST:event_networkFileBoxMouseClicked

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        if (!canSelect)
        {
            return;
        }

        JFileChooser c = new JFileChooser(Model.getInstance().GetLastFilePath());
        // Demonstrate "Open" dialog:
        int rVal = c.showOpenDialog(this);
        if (rVal == JFileChooser.APPROVE_OPTION)
        {
            this.networkFileBox.setText(c.getSelectedFile().getAbsolutePath());
            Model.getInstance().AccountForLastFilePath(c.getSelectedFile().getAbsolutePath());
        }
}//GEN-LAST:event_fileButtonActionPerformed

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed

        String projectName;
        String traitName;
        if (this.projectComboBox.getSelectedItem().equals("<Select Project>"))
        {
            String s = "You must select a valid project option.";
            this.errorLabel.setText(s);
            return;
        }
        projectName = (String) this.projectComboBox.getSelectedItem();
        if (this.traitComboBox.getSelectedItem().equals("<Select Traits>"))
        {
            String s = "You must select a valid traits option.";
            this.errorLabel.setText(s);
            return;
        }
        traitName = (String) this.traitComboBox.getSelectedItem();

        if (this.algorithmComboBox.getSelectedItem().equals("<Select Algorithm>") && !this.loadRadBtn.isSelected())
        {
            String s = "You must select a valid algorithms option.";
            this.errorLabel.setText(s);
            return;
        }

        DataAddRemoveHandler.getInstance().refreshDisplay();
        int projID = Model.getInstance().getProject(projectName).getId();
        int traitID = Model.getInstance().getProject(projectName).getTrait(traitName).getId();

        if (this.createRadBtn.isSelected())
        {
            String s = Algorithms.NetworkAlgorithms.algonames().get(algorithmComboBox.getSelectedIndex() - 1);
            ArrayList<String> inUse = Model.getInstance().getProject(this.projectComboBox.getSelectedItem().toString()).getTrait(this.traitComboBox.getSelectedItem().toString()).getNetworkTypes();

            for (int i = 0; i < inUse.size(); i++)
            {
                if (s.equals(inUse.get(i)))
                {
                    String s1 = "Network already exists for these traits";
                    this.errorLabel.setText(s1);
                    return;
                }
            }

            view.addAlgorithm(Algorithms.NetworkAlgorithms.algonames().get(this.algorithmComboBox.getSelectedIndex() - 1),
                    Algorithms.NetworkAlgorithms.jobTypeID().get(this.algorithmComboBox.getSelectedIndex() - 1), projID, traitID, 0);
        }
        else if (this.loadRadBtn.isSelected())
        {
            //start from here
            FileInputStream fstream = null;

            TraitSet t = Model.getInstance().getProject(projectName).getTrait(traitName);

            ArrayList<String> whereArgs = new ArrayList<String>();
            whereArgs.add("traitsetid=" + traitID);
            int traitCount = Integer.parseInt((String) DataManager.runSelectQuery("count(*)", "trait", true, whereArgs, null).get(0));

            if (this.netNameText.getText().equals(""))
            {
                errorLabel.setText("You must choose a name");
                return;
            }

            if (this.tabDelButton.isSelected())
            {
                try
                {
                    String networkFile = this.networkFileBox.getText();
                    double[][] network = this.loadNetwork(networkFile, traitCount);

                    DataAddRemoveHandler.getInstance().addNetwork(projID, traitID, this.netNameText.getText(), network);
                }
                catch (Exception e)
                {
                    errorLabel.setText("There was an error reading this file.");
                    return;
                }
            }
            else if (this.EdgeButton.isSelected())
            {
                String networkFile = this.networkFileBox.getText();
                try
                {
                    ArrayList<CytoNet> net = loadCytoFile(networkFile);

                    DataAddRemoveHandler.getInstance().addNetwork(projID, traitID, this.netNameText.getText(), net);
                }
                catch (Exception ex)
                {
                    this.errorLabel.setText("Error reading in this file");
                    return;
                }


            }
            else
            {
                this.errorLabel.setText("You must select a valid file format.");
                return;
            }

        }
        this.closeDialog(null);
}//GEN-LAST:event_importButtonActionPerformed

    private ArrayList<CytoNet> loadCytoFile(String fi) throws FileNotFoundException, IOException
    {
        ArrayList<CytoNet> edges = new ArrayList<CytoNet>();

        FileInputStream fstream = new FileInputStream(fi);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;

        while ((strLine = br.readLine()) != null)
        {
            strLine = strLine.trim();
            String[] ln = strLine.split("\t");

            if (strLine.length() > 0)
            {
                edges.add(new CytoNet(ln[0], ln[1], Double.parseDouble(ln[2])));
            }
        }
        in.close();
        in = null;
        return edges;
    }

    private double[][] loadNetwork(String fi, int k) throws FileNotFoundException, IOException
    {
        double[][] vals = new double[k][k];
        String networkFile = this.networkFileBox.getText();
        FileInputStream fstream = new FileInputStream(networkFile);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;

        int idx1 = 0;
        while ((strLine = br.readLine()) != null)
        {
            strLine = strLine.trim();
            String[] ln = strLine.split("\t");

            if (strLine.length() > 0)
            {
                for (int i = 0; i < k; i++)
                {
                    double temp = Double.parseDouble(ln[i]);
                    vals[idx1][i] = temp;
                }
            }
            idx1++;
        }
        in.close();
        in = null;
        return vals;
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.closeDialog(null);
}//GEN-LAST:event_cancelButtonActionPerformed

    private void projectComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectComboBoxActionPerformed
        if (this.projectComboBox.getSelectedItem().equals("<Select Project>"))
        {
            this.traitComboBox.setEnabled(false);
            return;
        }
        else
        {
            this.traitComboBox.setEnabled(true);
        }

        Project project = Model.getInstance().getProject(this.projectComboBox.getSelectedItem().toString());

        this.traitComboBox.removeAllItems();
        this.traitComboBox.addItem("<Select Traits>");
        for (int i = 0; i < project.getTraits().size(); i++)
        {
            this.traitComboBox.addItem(project.getTraits().get(i).getName());
        }
    }//GEN-LAST:event_projectComboBoxActionPerformed

    private void createRadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createRadBtnActionPerformed
        this.fileButton.setEnabled(false);
        this.networkFileBox.setEnabled(false);
        this.EdgeButton.setEnabled(false);
        this.tabDelButton.setEnabled(false);
        //this.projectComboBox.setEnabled(true);
        //this.traitComboBox.setEnabled(true);
        this.algorithmComboBox.setEnabled(true);
        this.jLabel1.setEnabled(false);
        this.netNameText.setEnabled(false);
        canSelect = false;
    }//GEN-LAST:event_createRadBtnActionPerformed

    private void loadRadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadRadBtnActionPerformed
        this.fileButton.setEnabled(true);
        this.networkFileBox.setEnabled(true);
        //this.projectComboBox.setEnabled(false);
        //this.traitComboBox.setEnabled(false);
        this.algorithmComboBox.setEnabled(false);
        this.EdgeButton.setEnabled(true);
        this.tabDelButton.setEnabled(true);
        this.jLabel1.setEnabled(true);
        this.netNameText.setEnabled(true);
        canSelect = true;
    }//GEN-LAST:event_loadRadBtnActionPerformed

    private void importButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_importButton1ActionPerformed
    {//GEN-HEADEREND:event_importButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_importButton1ActionPerformed

    private void traitComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_traitComboBoxActionPerformed
    {//GEN-HEADEREND:event_traitComboBoxActionPerformed
        if (this.projectComboBox.getSelectedItem().equals("<Select Project>"))
        {
            this.traitComboBox.setEnabled(false);
            return;
        }
        else
        {
            this.traitComboBox.setEnabled(true);
        }

        Project project = Model.getInstance().getProject(this.projectComboBox.getSelectedItem().toString());

        if(this.traitComboBox.getSelectedItem() == null || this.traitComboBox.getSelectedItem().equals("<Select Traits>"))
        {
            return;
        }

        String s = ((String) traitComboBox.getSelectedItem());
        TraitSet ts = project.getTrait((String)traitComboBox.getSelectedItem());

        if(ts.getHasData())
        {
            this.jLabel6.setEnabled(true);
            this.createRadBtn.setEnabled(true);
            this.createRadBtn.setSelected(true);
            this.createRadBtnActionPerformed(null);
        }
        else
        {
            this.jLabel6.setEnabled(false);
            this.createRadBtn.setEnabled(false);
            this.loadRadBtn.setSelected(true);
            this.loadRadBtnActionPerformed(null);
        }
    }//GEN-LAST:event_traitComboBoxActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton EdgeButton;
    private javax.swing.JComboBox algorithmComboBox;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton cancelButton;
    private javax.swing.JRadioButton createRadBtn;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JButton fileButton;
    private javax.swing.JButton importButton;
    private javax.swing.JButton importButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton loadRadBtn;
    private javax.swing.JTextField netNameText;
    private javax.swing.JTextField networkFileBox;
    private javax.swing.JComboBox projectComboBox;
    private javax.swing.JRadioButton tabDelButton;
    private javax.swing.JComboBox traitComboBox;
    // End of variables declaration//GEN-END:variables
}
