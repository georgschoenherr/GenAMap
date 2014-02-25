/*
 * AssociationGUI.java
 *
 * Created on Aug 5, 2009, 10:29:34 AM
 */
package associationmapper;

import datamodel.Model;
import realdata.Data1;
import javax.swing.ImageIcon;
import control.algodialog.AssociationAlgorithmDialog;
import control.itempanel.ThreadingItemFrame;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

/**
 * This is the main GUI interface. Its job is to initialize all the components in
 * the right order and then hold them in the display. 
 * @author ross
 */
public class AssociationGUI extends javax.swing.JFrame
{

    /**
     * The main GUI initializes by first initializes the model instance.
     * This checks up on the SQL settings, getting the user to create an
     * account if they do not already have one. Once they have passed
     * the authorization steps, then the Model method automatically fills in
     * the trees for the AssociationObjectTabs to query. Once everything
     * is set up, the method waits just a second longer for the Flash screen
     * to not disappear too quickly, and then the user is in business. 
     */
    public AssociationGUI()
    {
        super();

        if (Model.getInstance() == null)
        {
            System.exit(0);
        }

        initComponents();

        associationView1.init(this);
        associationObjectTabs1.setup();

        ImageIcon i = new ImageIcon(getClass().getResource("/associationmapper/GAMicon.png"));
        this.setIconImage(i.getImage());
        this.setTitle("GenAMap");

        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
            System.out.println(ex.getMessage());
        }
        ThreadingItemFrame.getInstance().startJobs();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        algorithmView1 = new algorithm.AlgorithmView();
        associationObjectTabs1 = new views.AssociationObjectTabs();
        associationView1 = new views.AssociationView();
        jMenuBar1 = new javax.swing.JMenuBar();
        filemenuitem = new javax.swing.JMenu();
        exitmenuitem = new javax.swing.JMenuItem();
        assocmenuitem = new javax.swing.JMenu();
        createassocmenuitem = new javax.swing.JMenuItem();
        parallelmenuitem = new javax.swing.JMenu();
        setupmenuitem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 3, 13));
        jLabel1.setText("Algorithm Control Center");

        algorithmView1.setMaximumSize(new java.awt.Dimension(375, 263));
        algorithmView1.setMinimumSize(new java.awt.Dimension(375, 263));
        algorithmView1.setPreferredSize(new java.awt.Dimension(375, 263));

        associationObjectTabs1.setMaximumSize(new java.awt.Dimension(32767, 798));
        associationObjectTabs1.setMinimumSize(new java.awt.Dimension(75, 75));
        associationObjectTabs1.setPreferredSize(new java.awt.Dimension(258, 798));

        filemenuitem.setMnemonic('F');
        filemenuitem.setText("File");

        exitmenuitem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, java.awt.event.InputEvent.CTRL_MASK));
        exitmenuitem.setMnemonic('x');
        exitmenuitem.setText("Exit");
        exitmenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitmenuitemActionPerformed(evt);
            }
        });
        filemenuitem.add(exitmenuitem);

        jMenuBar1.add(filemenuitem);

        assocmenuitem.setMnemonic('A');
        assocmenuitem.setText("Association");

        createassocmenuitem.setText("Add Association");
        createassocmenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createassocmenuitemActionPerformed(evt);
            }
        });
        assocmenuitem.add(createassocmenuitem);

        jMenuBar1.add(assocmenuitem);

        parallelmenuitem.setMnemonic('P');
        parallelmenuitem.setText("User");

        setupmenuitem.setMnemonic('S');
        setupmenuitem.setText("Logout");
        setupmenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setupmenuitemActionPerformed(evt);
            }
        });
        parallelmenuitem.add(setupmenuitem);

        jMenuBar1.add(parallelmenuitem);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(associationObjectTabs1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(algorithmView1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(associationView1, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(associationObjectTabs1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(algorithmView1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(associationView1, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * The AssociationView is where all visualization of the data takes place. 
     * @return the associationView1
     */
    public views.AssociationView getAssociationView1()
    {
        return associationView1;
    }

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    /**
     * When the GenAMap shuts down, it serializes the data used by the Model
     * and the AlgorithmControl. The AlgorithmControl makes sure to deserialize
     * the SQL settings. This ensures that the GUI can pick up just about where
     * it left off before it was closed down. 
     * @param evt
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
            Model.serialize();
            this.algorithmView1.acceptClosingMessage();
    }//GEN-LAST:event_formWindowClosing

    @Override
    protected void processWindowEvent(WindowEvent e)
    {
        if(e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            if(ThreadingItemFrame.getInstance().canClose())
            {
                super.processWindowEvent(e);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "You cannot quit until all data operations finish.");
            }
        }
        else
        {
            super.processWindowEvent(e);
        }

    }

    /**
     * When the user logs out, we just mess up the serialization settings so
     * they will have to re-login on their next attempt. Then, we shut down
     * the application. 
     * @param evt
     */
    private void setupmenuitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setupmenuitemActionPerformed
        Data1.getInstance().mysqlusername = "bogus";
        exitmenuitemActionPerformed(evt);
    }//GEN-LAST:event_setupmenuitemActionPerformed

    /**
     * All associations are run from the same option box. Because this
     * is common across all of them ... and it is a bit of the focus of the
     * application, we have an item in the main GUI menu. 
     * @param evt
     */
    private void createassocmenuitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createassocmenuitemActionPerformed
        AssociationAlgorithmDialog form = new AssociationAlgorithmDialog(this, true, this.algorithmView1, true, null, null, null);
        form.show();
    }//GEN-LAST:event_createassocmenuitemActionPerformed

    /**
     * The user can also exit the application from the menu, instead of clicking
     * on the bright red x usually used to exit the application. The same serialization
     * process takes place. 
     * @param evt
     */
    private void exitmenuitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitmenuitemActionPerformed
        if (ThreadingItemFrame.getInstance().canClose())
        {
            Model.serialize();
            this.algorithmView1.acceptClosingMessage();
            System.exit(0);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "You cannot quit until all data operations finish.");
        }
}//GEN-LAST:event_exitmenuitemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {

            public void run()
            {
                new AssociationGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private algorithm.AlgorithmView algorithmView1;
    private views.AssociationObjectTabs associationObjectTabs1;
    private views.AssociationView associationView1;
    private javax.swing.JMenu assocmenuitem;
    private javax.swing.JMenuItem createassocmenuitem;
    private javax.swing.JMenuItem exitmenuitem;
    private javax.swing.JMenu filemenuitem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu parallelmenuitem;
    private javax.swing.JMenuItem setupmenuitem;
    // End of variables declaration//GEN-END:variables
}
