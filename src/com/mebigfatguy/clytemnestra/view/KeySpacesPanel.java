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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.controllers.Controller;
import com.mebigfatguy.clytemnestra.controllers.KeySpacesController;
import com.mebigfatguy.clytemnestra.model.KeySpacesTableModel;

public class KeySpacesPanel extends JPanel {

    private static final long serialVersionUID = 291151369935073424L;

    private final Context context;
    private JTable keySpacesTable;
    private KeySpacesController controller;

    public KeySpacesPanel(Context ctxt) {
        context = ctxt;
        initComponents();
    }

    public Controller getController() {
        return controller;
    }
    
    public void refresh() {
    	controller.refresh(context.getClient());
    }
    
    public List<KsDef> getSelectedKeySpaces() {
    	int[] selRows = keySpacesTable.getSelectedRows();
    	KeySpacesTableModel model = (KeySpacesTableModel) keySpacesTable.getModel();
    	List<KsDef> selectedKeySpaces = new ArrayList<KsDef>();
    	for (int i = 0; i < selRows.length; i++) {
    		selectedKeySpaces.add(model.getKeySpaceAt(selRows[i]));
    	}
    	
    	return selectedKeySpaces;
    }

    private void initComponents() {
        setLayout(new BorderLayout(4, 4));

        KeySpacesTableModel model = new KeySpacesTableModel();
        keySpacesTable = new JTable(model);
        add(new JScrollPane(keySpacesTable), BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        controller = new KeySpacesController(context, keySpacesTable, model);
    }
}
