/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mebigfatguy.clytemnestra.controllers;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.actions.OpenKeySpaceAction;
import com.mebigfatguy.clytemnestra.model.KeySpacesTableModel;


public class KeySpacesController implements Controller<KsDef>, ListSelectionListener {

    private final Context context;
    private final JTable table;
    private final KeySpacesTableModel model;

    public KeySpacesController(Context ctxt, JTable ksTable, KeySpacesTableModel ksModel) {
        context = ctxt;
        table = ksTable;
        model = ksModel;

        table.getSelectionModel().addListSelectionListener(this);
        table.addMouseListener(new OpenKeySpaceMouseListener());
    }

    @Override
    public void refresh(Cassandra.Client client) {
        try {
            final List<KsDef> keySpaces = (client == null) ? new ArrayList<KsDef>() : client.describe_keyspaces();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    model.replaceContents(keySpaces);
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(table, e.getMessage());
        }
    }
    
    @Override
    public List<KsDef> getSelectedItems() {
		int[] selRows = table.getSelectedRows();
		List<KsDef> selectedKeySpaces = new ArrayList<KsDef>();
		for (int i = 0; i < selRows.length; i++) {
			selectedKeySpaces.add(model.getKeySpaceAt(selRows[i]));
		}
		
		return selectedKeySpaces;
    }

    @Override
    public void clear() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                model.replaceContents(new ArrayList<KsDef>());
            }
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {
            int[] rowIds = table.getSelectedRows();
            List<KsDef> selectedKeySpaces = new ArrayList<KsDef>();
            for (int i = 0; i < rowIds.length; i++) {
                selectedKeySpaces.add(model.getKeySpaceAt(rowIds[i]));
            }
            context.setSelectedKeySpaces(selectedKeySpaces);
        }
    }
    
    public class OpenKeySpaceMouseListener extends MouseAdapter {
    	
    	@Override
		public void mouseClicked(MouseEvent me) {
            if (me.getComponent().isEnabled() && me.getButton() == MouseEvent.BUTTON1 && me.getClickCount() == 2)
            {
                Point p = me.getPoint();
                int row = table.rowAtPoint(p); 
                
                KsDef keySpace = model.getKeySpaceAt(row);
                if (keySpace != null) {
                	OpenKeySpaceAction action = new OpenKeySpaceAction(context);
                	ActionEvent ae = new ActionEvent(table, 0, "");
                	action.actionPerformed(ae);
                }
            }
    	}
    }
}

