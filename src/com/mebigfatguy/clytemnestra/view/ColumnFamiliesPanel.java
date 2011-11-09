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

import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.controllers.ColumnFamiliesController;
import com.mebigfatguy.clytemnestra.controllers.Controller;
import com.mebigfatguy.clytemnestra.model.ColumnFamiliesTableModel;

public class ColumnFamiliesPanel extends JPanel {

	private static final long serialVersionUID = 8755403933881853128L;
	
	private KsDef ksDef;
	private Context context;
	private JTable columnFamiliesTable;
    private ColumnFamiliesController controller;
	
	public ColumnFamiliesPanel(KsDef ks, Context ctxt) {
		ksDef = ks;
		context = ctxt;
        initComponents();
	}
	
    public Controller getController() {
        return controller;
    }
    
	private void initComponents() {
        setLayout(new BorderLayout(4, 4));

        ColumnFamiliesTableModel model = new ColumnFamiliesTableModel();
        columnFamiliesTable = new JTable(model);
        add(new JScrollPane(columnFamiliesTable), BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        
        controller = new ColumnFamiliesController(ksDef, context, columnFamiliesTable, model);
	}
}
