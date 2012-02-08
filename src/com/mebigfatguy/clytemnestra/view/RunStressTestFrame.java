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
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
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
    private StressTestTreeModel testModel;
	
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
		p.add(testConfiguration, BorderLayout.CENTER);
		
		p.setBorder(BorderFactory.createTitledBorder(Bundle.getString(Bundle.Key.TestConfiguration)));
		return p;
	}
	
	private JPanel createRunPanel() {
		JPanel p = new JPanel();
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
	}
}
