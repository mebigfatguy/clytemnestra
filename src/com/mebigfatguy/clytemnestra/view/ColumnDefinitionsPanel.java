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
import com.mebigfatguy.clytemnestra.controllers.ColumnDefinitionsController;
import com.mebigfatguy.clytemnestra.controllers.ColumnFamiliesController;
import com.mebigfatguy.clytemnestra.controllers.Controller;
import com.mebigfatguy.clytemnestra.model.ColumnDefinitionsTableModel;

public class ColumnDefinitionsPanel extends JPanel {

	private CfDef cfDef;
	private Context context;
	private JTable columnDefinitionsTable;
    private ColumnDefinitionsController controller;
	
	public ColumnDefinitionsPanel(CfDef cf, Context ctxt) {
		cfDef = cf;
		context = ctxt;
        initComponents();
	}
		
    public Controller getController() {
        return controller;
    }
	
	private void initComponents() {
        setLayout(new BorderLayout(4, 4));

        ColumnDefinitionsTableModel model = new ColumnDefinitionsTableModel();
        columnDefinitionsTable = new JTable(model);
        add(new JScrollPane(columnDefinitionsTable), BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        controller = new ColumnDefinitionsController(cfDef, context, columnDefinitionsTable, model);
	}
}
