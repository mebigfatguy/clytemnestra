/*
 * clytemnastra - a simple gui explorer and stresstool for cassandra
 * Copyright 2011 MeBigFatGuy.com
 * Copyright 2011 Dave Brosius
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
import java.text.MessageFormat;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.cassandra.thrift.CfDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.actions.CreateColumnDefinitionAction;
import com.mebigfatguy.clytemnestra.actions.DeleteColumnDefinitionAction;
import com.mebigfatguy.clytemnestra.actions.ViewDataAction;

public class ColumnFamilyFrame extends JFrame {
	
	private static final long serialVersionUID = 323420044098761465L;
	
	private final Context context;
	private final CfDef columnFamily;
	private ColumnDefinitionsPanel columnDefinitionsPanel;
	private JMenu columnDefinitionsMenu;
    private JMenuItem createColumnDefinitionItem;
    private JMenuItem deleteColumnDefinitionItem;
    private JMenu dataMenu;
    private JMenuItem viewDataItem;
	
	public ColumnFamilyFrame(Context ctxt, CfDef cf) {
		context = ctxt;
		columnFamily = cf;
		String title = Bundle.getString(Bundle.Key.ColumnFamilyTitle);
		setTitle(MessageFormat.format(title,  columnFamily.getName()));
		
        initComponents();
        initMenus();
        initListeners();
        
        columnDefinitionsPanel.getController().refresh(ctxt.getClient());
        
		pack();
	}

	private void initComponents() {
		Container cp = getContentPane();
        cp.setLayout(new BorderLayout(4, 4));

        columnDefinitionsPanel = new ColumnDefinitionsPanel(columnFamily, context);
        cp.add(columnDefinitionsPanel, BorderLayout.CENTER);
	}

	private void initMenus() {
        JMenuBar mb = new JMenuBar();

        columnDefinitionsMenu = new JMenu(Bundle.getString(Bundle.Key.ColumnDefinition));
        createColumnDefinitionItem = new JMenuItem(new CreateColumnDefinitionAction(context, columnFamily));
        columnDefinitionsMenu.add(createColumnDefinitionItem);
        columnDefinitionsMenu.addSeparator();
        deleteColumnDefinitionItem = new JMenuItem(new DeleteColumnDefinitionAction(context, columnDefinitionsPanel.getController()));
        columnDefinitionsMenu.add(deleteColumnDefinitionItem);
        mb.add(columnDefinitionsMenu);
        
        dataMenu = new JMenu(Bundle.getString(Bundle.Key.Data));
        viewDataItem = new JMenuItem(new ViewDataAction(context, columnFamily));
        dataMenu.add(viewDataItem);
        mb.add(dataMenu);
        
        setJMenuBar(mb);
	}

	private void initListeners() {
	}

}
