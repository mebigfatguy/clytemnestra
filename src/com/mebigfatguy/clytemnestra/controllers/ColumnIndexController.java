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
package com.mebigfatguy.clytemnestra.controllers;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.ColumnDef;

import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.model.ColumnIndexTableModel;

public class ColumnIndexController implements Controller<ColumnDef>, ListSelectionListener {

	private final CfDef columnFamily;
	private final JTable table;
	private final ColumnIndexTableModel model;
	
	public ColumnIndexController(CfDef cf, Context ctxt, JTable cdTable, ColumnIndexTableModel cdModel) {
		columnFamily = cf;
        table = cdTable;
        model = cdModel;

        table.getSelectionModel().addListSelectionListener(this);
	}
	
	@Override
	public void refresh(Cassandra.Client client) {
        try {
            final List<ColumnDef> columnDefinitions = columnFamily.getColumn_metadata();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    model.replaceContents(columnDefinitions);
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(table, e.getMessage());
        }
	}
	
	@Override
	public List<ColumnDef> getSelectedItems() {
		return null;
	}
	
	@Override
	public void clear() {
		
	}

    @Override
    public void valueChanged(ListSelectionEvent lse) {
    }
}
