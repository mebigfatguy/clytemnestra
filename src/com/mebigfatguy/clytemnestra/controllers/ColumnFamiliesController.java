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

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.model.ColumnFamiliesTableModel;

public class ColumnFamiliesController implements Controller<CfDef>, ListSelectionListener {

	private KsDef keySpace;
    private final Context context;
    private final JTable table;
    private final ColumnFamiliesTableModel model;

    public ColumnFamiliesController(KsDef ks, Context ctxt, JTable cfTable, ColumnFamiliesTableModel cfModel) {
        keySpace = ks;
    	context = ctxt;
        table = cfTable;
        model = cfModel;

        table.getSelectionModel().addListSelectionListener(this);
    }
    
	@Override
	public void refresh(Cassandra.Client client) {
        try {
        	keySpace = (client == null) ? null : client.describe_keyspace(keySpace.getName());
            final List<CfDef> columnFamilies = (client == null) ? new ArrayList<CfDef>() : keySpace.getCf_defs();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    model.replaceContents(columnFamilies);
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(table, e.getMessage());
        }
	}
	
    @Override
	public List<CfDef> getSelectedItems() {
		int[] selRows = table.getSelectedRows();
		List<CfDef> selectedColumnFamilies = new ArrayList<CfDef>();
		for (int i = 0; i < selRows.length; i++) {
			selectedColumnFamilies.add(model.getColumnFamilyAt(selRows[i]));
		}
		
		return selectedColumnFamilies;
	}

	@Override
	public void clear() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                model.replaceContents(new ArrayList<CfDef>());
            }
        });
	}
	
    @Override
    public void valueChanged(ListSelectionEvent lse) {
    }

}
