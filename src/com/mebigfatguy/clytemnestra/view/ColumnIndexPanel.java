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

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.ColumnDef;

import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.controllers.ColumnIndexController;
import com.mebigfatguy.clytemnestra.controllers.Controller;
import com.mebigfatguy.clytemnestra.model.ColumnIndexTableModel;

public class ColumnIndexPanel extends JPanel {

	private static final long serialVersionUID = -1746511016214826850L;
	
	private final CfDef cfDef;
	private final Context context;
	private JTable columnIndexTable;
    private ColumnIndexController controller;
	
	public ColumnIndexPanel(CfDef cf, Context ctxt) {
		cfDef = cf;
		context = ctxt;
        initComponents();
	}
		
    public Controller<ColumnDef> getController() {
        return controller;
    }
	
	private void initComponents() {
        setLayout(new BorderLayout(4, 4));

        ColumnIndexTableModel model = new ColumnIndexTableModel();
        columnIndexTable = new JTable(model);
        add(new JScrollPane(columnIndexTable), BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        controller = new ColumnIndexController(cfDef, context, columnIndexTable, model);
	}
}
