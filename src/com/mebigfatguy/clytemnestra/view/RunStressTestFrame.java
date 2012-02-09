/*
 * clytemnastra - a simple gui explorer and stresstool for cassandra
 * Copyright 2011-2012 MeBigFatGuy.com
 * Copyright 2011-2012 Dave Brosius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.clytemnestra.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.jgoodies.forms.layout.FormLayout;
import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.FormHelper;
import com.mebigfatguy.clytemnestra.StressTestData.ColumnFamilyData;
import com.mebigfatguy.clytemnestra.StressTestData.ColumnInfo;
import com.mebigfatguy.clytemnestra.StressTestData.KeySpaceData;
import com.mebigfatguy.clytemnestra.actions.CloseStressTestAction;
import com.mebigfatguy.clytemnestra.actions.NewStressTestAction;
import com.mebigfatguy.clytemnestra.actions.OpenStressTestAction;
import com.mebigfatguy.clytemnestra.actions.RunStressTestAction;
import com.mebigfatguy.clytemnestra.actions.SaveAsStressTestAction;
import com.mebigfatguy.clytemnestra.actions.SaveStressTestAction;
import com.mebigfatguy.clytemnestra.model.StressTestTreeModel;

public class RunStressTestFrame extends JFrame {

	private static final long serialVersionUID = -5267023846973078444L;
	private Context context;
    private JMenu stressMenu;
    private JMenuItem newStressTestItem;
    private JMenuItem openStressTestItem;
    private JMenuItem closeStressTestItem;
    private JMenuItem saveStressTestItem;
    private JMenuItem saveAsStressTestItem;
    private JMenuItem runStressTestItem;
    private JTree testConfiguration;
    private JProgressBar insertBar;
    private JProgressBar updateBar;
    private JProgressBar readBar;
    private StressTestTreeModel testModel;
    private JButton runButton;
    private JButton stopButton;
    
	
	public RunStressTestFrame(Context ctxt) {
		setTitle(Bundle.getString(Bundle.Key.RunStressTest));
		context = ctxt;
        initComponents();
        initMenus();
        initListeners();
        pack();
	}

	private void initComponents() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout(4, 4));
		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		cp.add(sp, BorderLayout.CENTER);	
		sp.add(createConfigurationPanel());
		sp.add(createRunPanel());
	}
	
	private JPanel createConfigurationPanel() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(4, 4));
		
		testModel = new StressTestTreeModel(context.getStressTestData());
		testConfiguration = new JTree(testModel);
		testConfiguration.setRootVisible(false);
		testConfiguration.setShowsRootHandles(true);
		testConfiguration.setEditable(true);
		JScrollPane sp = new JScrollPane(testConfiguration);
		sp.setPreferredSize(new Dimension(500, 200));
		p.add(sp, BorderLayout.CENTER);
		
		p.setBorder(BorderFactory.createTitledBorder(Bundle.getString(Bundle.Key.TestConfiguration)));
		return p;
	}
	
	private JPanel createRunPanel() {
	    JPanel p = new JPanel();
	    p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
	    
		JPanel progressPanel = new JPanel();
		
		progressPanel.setLayout(new FormLayout("6dlu, pref, 5dlu, pref:grow, 6dlu", "6dlu, pref, 4dlu, pref, 4dlu, pref, 6dlu"));

        insertBar = new JProgressBar(0, 100);
        FormHelper.addFormRow(progressPanel, Bundle.Key.Inserts, insertBar, 2);
        
        updateBar = new JProgressBar(0, 100);
        FormHelper.addFormRow(progressPanel, Bundle.Key.Updates, updateBar, 4);
        
        readBar = new JProgressBar(0, 100);
        FormHelper.addFormRow(progressPanel, Bundle.Key.Reads, readBar, 6);
        
        progressPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createTitledBorder(Bundle.getString(Bundle.Key.Status))));
        
        p.add(progressPanel);
        
        JPanel ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new BoxLayout(ctrlPanel, BoxLayout.X_AXIS));
        ctrlPanel.add(Box.createHorizontalGlue());
        
        runButton = new JButton(Bundle.getString(Bundle.Key.Run));
        ctrlPanel.add(runButton);
       
        ctrlPanel.add(Box.createHorizontalStrut(10));
        
        stopButton = new JButton(Bundle.getString(Bundle.Key.Stop));
        stopButton.setEnabled(false);
        ctrlPanel.add(stopButton);
        
        ctrlPanel.add(Box.createHorizontalStrut(10));
        
        ctrlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        p.add(ctrlPanel);
        
        
		return p;
	}
	
	private void initMenus() {
		JMenuBar mb = new JMenuBar();
        stressMenu = new JMenu(Bundle.getString(Bundle.Key.StressTest));
        newStressTestItem = new JMenuItem(new NewStressTestAction(context));
        stressMenu.add(newStressTestItem);
        openStressTestItem = new JMenuItem(new OpenStressTestAction(context));
        stressMenu.add(openStressTestItem);
        closeStressTestItem = new JMenuItem(new CloseStressTestAction(context));
        stressMenu.add(closeStressTestItem);
        saveStressTestItem = new JMenuItem(new SaveStressTestAction(context));
        stressMenu.addSeparator();
        stressMenu.add(saveStressTestItem);
        saveAsStressTestItem = new JMenuItem(new SaveAsStressTestAction(context));
        stressMenu.add(saveAsStressTestItem);
        runStressTestItem = new JMenuItem(new RunStressTestAction(context));
        stressMenu.addSeparator();
        stressMenu.add(runStressTestItem);
        mb.add(stressMenu);
        
        setJMenuBar(mb);
	}
	
	private void initListeners() {
	    testConfiguration.setCellEditor(new StressCellEditor());
	}
	
	class StressCellEditor extends DefaultTreeCellEditor {
	    
	    public StressCellEditor() {
	        super(testConfiguration, (DefaultTreeCellRenderer) testConfiguration.getCellRenderer());
	    }

        @Override
        public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {

            if (value instanceof KeySpaceData) {
                value = ((KeySpaceData) value).getName();
            } else if (value instanceof ColumnFamilyData) {
                value = ((ColumnFamilyData) value).getName();
            } else if (value instanceof ColumnInfo) {
                value = ((ColumnInfo) value).getName();
            }
            
            return super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
        }
	}
}
