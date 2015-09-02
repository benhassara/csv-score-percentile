/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvperc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JFileChooser;

/**
 *
 * @author bhassara
 */
public class CSVPercentileGUI extends javax.swing.JFrame {

    private File saveTo;
    private File toOpen;
    /**
     * Creates new form CSVPercentileGUI
     */
    public CSVPercentileGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fc = new javax.swing.JFileChooser();
        btnOpen = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();
        lblOpen = new javax.swing.JLabel();
        lblSave = new javax.swing.JLabel();
        btnConvert = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CSV Percentile");

        btnOpen.setText("Open");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        btnSave.setText("Save to...");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        log.setEditable(false);
        log.setColumns(20);
        log.setRows(5);
        jScrollPane1.setViewportView(log);

        lblOpen.setText("Choose a file.");

        lblSave.setText("Choose a save location.");

        btnConvert.setText("Convert");
        btnConvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvertActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnOpen)
                                .addGap(18, 18, 18)
                                .addComponent(lblOpen))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSave)
                                .addGap(18, 18, 18)
                                .addComponent(lblSave)))
                        .addGap(0, 120, Short.MAX_VALUE))
                    .addComponent(btnConvert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnOpen, btnSave});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOpen)
                    .addComponent(lblOpen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(lblSave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConvert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /*
     * Open btn handler.
     * opens a FileChooser to pick a .csv for processing
    */
    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        
        int userChoice = fc.showOpenDialog(this);
        
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            toOpen = fc.getSelectedFile();
            lblOpen.setText(toOpen.getPath());
            log.append("Opened file: " + toOpen.getAbsolutePath() + '\n');
        }
        else {
            log.append("No file selected." + '\n');
        }
    }//GEN-LAST:event_btnOpenActionPerformed
    
    /*
     * Save btn handler.
     * opens a FileChooser to determine save location for the .csv
     */
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        int userChoice = fc.showSaveDialog(this);
        
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            saveTo = fc.getSelectedFile();
            lblSave.setText(saveTo.getPath());
            log.append("Save output to: " + saveTo.getAbsolutePath() + '\n');
        }
        else {
            log.append("No save location selected." + '\n');
        }
    }//GEN-LAST:event_btnSaveActionPerformed
    
    /*
     * Convert button handler.
     * Reads data into a PercData object, and writes to a .csv
     */
    private void btnConvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvertActionPerformed

        PercData data = new PercData();
        BufferedReader reader;
        
        // read in the list of scores and add them to PercData obj
        try {
            String line;
            reader = new BufferedReader(new FileReader(toOpen));
            while ((line = reader.readLine()) != null) {
                data.addScore(line);
            }
        }
        catch (IOException err) {
            log.append("Error opening file: " + toOpen.getPath() + '\n');
        }
        
        Map<String, Integer> percs = data.getPercentiles();
        ArrayList<Integer> sortedScores = data.sort();
        
        // write to a .csv
        try (Writer writer = new BufferedWriter(
                             new OutputStreamWriter(
                             new FileOutputStream(saveTo.getAbsolutePath()), "utf-8"))) {
            // write column headers first
            writer.write("Score,Percentile Rank" + '\n');
            
            // iterate over percentile data, and write to file
            for (Integer score: sortedScores) {
                String line = score.toString() + ',' + percs.get(score.toString()) + '\n';
                writer.write(line);
            }
            writer.close();
            log.append("Success!" + '\n');
        }
        catch (IOException err) {
            log.append("Error writing to file: " + saveTo.getPath() + '\n');
        }
        
    }//GEN-LAST:event_btnConvertActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CSVPercentileGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CSVPercentileGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CSVPercentileGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CSVPercentileGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CSVPercentileGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConvert;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSave;
    private javax.swing.JFileChooser fc;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblOpen;
    private javax.swing.JLabel lblSave;
    private javax.swing.JTextArea log;
    // End of variables declaration//GEN-END:variables
}
