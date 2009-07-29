/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ArcPropertiesPanel.java
 *
 * Created on Jun 30, 2009, 8:32:10 PM
 */

package com.googlecode.sarasvati.editor.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.googlecode.sarasvati.editor.command.CommandStack;
import com.googlecode.sarasvati.editor.model.ArcState;
import com.googlecode.sarasvati.editor.model.EditorArc;
import com.googlecode.sarasvati.editor.model.EditorExternal;
import com.googlecode.sarasvati.editor.model.Library;
import com.googlecode.sarasvati.load.definition.NodeDefinition;
import com.googlecode.sarasvati.load.definition.ProcessDefinition;
import com.googlecode.sarasvati.util.SvUtil;

/**
 *
 * @author paul
 */
public class ArcPropertiesPanel extends javax.swing.JPanel {

  private static final long serialVersionUID = 1L;

    /** Creates new form ArcPropertiesPanel */
    public ArcPropertiesPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        arcNameLabel = new javax.swing.JLabel();
        arcNameInput = new javax.swing.JTextField();
        fromLabel = new javax.swing.JLabel();
        fromInput = new javax.swing.JComboBox();
        toLabel = new javax.swing.JLabel();
        toInput = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();
        fromWarning = new javax.swing.JLabel();
        toWarning = new javax.swing.JLabel();

        arcNameLabel.setText(org.openide.util.NbBundle.getMessage(ArcPropertiesPanel.class, "ArcPropertiesPanel.arcNameLabel.text")); // NOI18N

        arcNameInput.setText(org.openide.util.NbBundle.getMessage(ArcPropertiesPanel.class, "ArcPropertiesPanel.arcNameInput.text")); // NOI18N

        fromLabel.setText(org.openide.util.NbBundle.getMessage(ArcPropertiesPanel.class, "ArcPropertiesPanel.fromLabel.text")); // NOI18N

        fromInput.setEditable(true);
        fromInput.setModel(getFromComboBoxModel());

        toLabel.setText(org.openide.util.NbBundle.getMessage(ArcPropertiesPanel.class, "ArcPropertiesPanel.toLabel.text")); // NOI18N

        toInput.setEditable(true);
        toInput.setModel(getToComboBoxModel());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        cancelButton.setText(org.openide.util.NbBundle.getMessage(ArcPropertiesPanel.class, "ArcPropertiesPanel.cancelButton.text")); // NOI18N
        cancelButton.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel1.add(cancelButton);

        applyButton.setText(org.openide.util.NbBundle.getMessage(ArcPropertiesPanel.class, "ArcPropertiesPanel.applyButton.text")); // NOI18N
        applyButton.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel1.add(applyButton);

        fromWarning.setForeground(new java.awt.Color(153, 0, 51));
        fromWarning.setText(org.openide.util.NbBundle.getMessage(ArcPropertiesPanel.class, "ArcPropertiesPanel.fromWarning.text")); // NOI18N

        toWarning.setForeground(new java.awt.Color(153, 0, 51));
        toWarning.setText(org.openide.util.NbBundle.getMessage(ArcPropertiesPanel.class, "ArcPropertiesPanel.toWarning.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fromLabel)
                    .addComponent(arcNameLabel)
                    .addComponent(toLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fromWarning, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(toInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(arcNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fromInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(toWarning, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(arcNameLabel)
                    .addComponent(arcNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fromLabel)
                    .addComponent(fromInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fromWarning)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toWarning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyButton;
    private javax.swing.JTextField arcNameInput;
    private javax.swing.JLabel arcNameLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox fromInput;
    private javax.swing.JLabel fromLabel;
    private javax.swing.JLabel fromWarning;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox toInput;
    private javax.swing.JLabel toLabel;
    private javax.swing.JLabel toWarning;
    // End of variables declaration//GEN-END:variables

    private final ComboBoxModel toComboBoxModel = new DefaultComboBoxModel();
    private final ComboBoxModel fromComboBoxModel = new DefaultComboBoxModel();

    private ComboBoxModel getToComboBoxModel ()
    {
      return toComboBoxModel;
    }

    private ComboBoxModel getFromComboBoxModel ()
    {
      return fromComboBoxModel;
    }

    public void setup (final JDialog dialog,
                       final EditorArc arc)
    {
      cancelButton.addActionListener( new ActionListener()
      {
        @Override
        public void actionPerformed (ActionEvent e)
        {
          dialog.setVisible( false );
        }
      });

      applyButton.addActionListener( new ActionListener()
      {
        @Override
        public void actionPerformed (final ActionEvent e)
        {
          String fromNode = null;
          String toNode = null;

          if ( arc.isExternalInArc() )
          {
            fromNode = SvUtil.nullIfBlank( (String)fromInput.getSelectedItem());
            if ( arc.isExternalOutArc() )
            {
              toNode = SvUtil.nullIfBlank( (String)toInput.getSelectedItem());
            }
          }
          else if ( arc.isExternalOutArc() )
          {
            toNode = SvUtil.nullIfBlank( (String)fromInput.getSelectedItem());
          }

          ArcState newState = new ArcState( SvUtil.nullIfBlank( arcNameInput.getText() ), fromNode, toNode );

          if ( !newState.equals( arc.getState() ) )
          {
            CommandStack.editArc( arc, newState );
          }

          dialog.setVisible( false );
        }
      });

      ArcState state = arc.getState();
      arcNameInput.setText( state.getLabel() );

      if ( arc.isExternalInArc() )
      {
        setupComboBox( fromInput, arc.getStart().asExternal(), state.getExternalStart(), fromWarning );
        if ( arc.isExternalOutArc() )
        {
          setupComboBox( toInput, arc.getEnd().asExternal(), state.getExternalEnd(), toWarning );
        }
      }
      else if ( arc.isExternalOutArc() )
      {
        fromLabel.setText( "To" );
        setupComboBox( fromInput, arc.getEnd().asExternal(), state.getExternalEnd(), fromWarning );
      }
      else
      {
        fromLabel.setVisible( false );
        fromInput.setVisible( false );
        fromWarning.setVisible( false );
      }

      if ( !arc.isExternalOutArc() || !arc.isExternalInArc() )
      {
        toLabel.setVisible( false );
        toInput.setVisible( false );
        toWarning.setVisible( false );
      }
    }

    private void setupComboBox (final JComboBox input,
                                final EditorExternal external,
                                final String currentValue,
                                final JLabel warningLabel)
    {
      final String processDefinitionName = external.getState().getGraphName();
      ProcessDefinition pDef = Library.getInstance().getProcessDefinition( processDefinitionName );

      if ( pDef != null )
      {
        DefaultComboBoxModel model = (DefaultComboBoxModel) input.getModel();
        for ( NodeDefinition nodeDef : pDef.getNodes() )
        {
          model.addElement( nodeDef.getName() );
        }
        warningLabel.setVisible( false );
      }
      else
      {
        warningLabel.setText( "'" + processDefinitionName + "' is not in the process definition library" );
      }

      input.setSelectedItem( currentValue );
    }
}