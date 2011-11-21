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

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.cassandra.thrift.CfDef;

import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.controllers.ColumnFamilyDataController;
import com.mebigfatguy.clytemnestra.controllers.Controller;
import com.mebigfatguy.clytemnestra.model.ColumnFamilyDataTableModel;

public class ColumnFamilyDataPanel extends JPanel {

	private static final long serialVersionUID = -5103655242558804181L;
	
	private final CfDef columnFamily;
	private final Context context;
	private JTable columnFamilyDataTable;
    private ColumnFamilyDataController controller;
	
	public ColumnFamilyDataPanel(CfDef cf, Context ctxt) {
		columnFamily = cf;
		context = ctxt;
		initComponents();
	}
	
    public Controller<CfDef> getController() {
        return controller;
    }
    
	private void initComponents() {
        setLayout(new BorderLayout(4, 4));

        ColumnFamilyDataTableModel model = new ColumnFamilyDataTableModel();
        columnFamilyDataTable = new JTable(model);
        JScrollPane pane = new JScrollPane(columnFamilyDataTable);
        add(pane, BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        controller = new ColumnFamilyDataController(columnFamily, context, columnFamilyDataTable, pane, model);
	}
}
