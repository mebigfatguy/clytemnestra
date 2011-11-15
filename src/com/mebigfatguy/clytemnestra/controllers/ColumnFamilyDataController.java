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

import java.nio.ByteBuffer;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;

import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.model.ColumnFamilyDataTableModel;

public class ColumnFamilyDataController implements Controller<CfDef>, ListSelectionListener {

	private final CfDef columnFamily;
    private final Context context;
    private final JTable table;
    private final ColumnFamilyDataTableModel model;
    
    public ColumnFamilyDataController(CfDef cf, Context ctxt, JTable cfTable, ColumnFamilyDataTableModel cfModel) {
    	columnFamily = cf;
    	context = ctxt;
        table = cfTable;
        model = cfModel;

        table.getSelectionModel().addListSelectionListener(this);
    }
    
	@Override
	public void refresh(Client client) {
        try {
        	client.set_keyspace(columnFamily.getKeyspace());
        	ByteBuffer key = ByteBuffer.wrap(columnFamily.getKeyspace().getBytes("UTF-8"));
        	ColumnParent parent = new ColumnParent(columnFamily.getName());
        	SlicePredicate predicate = new SlicePredicate();
        	SliceRange sliceRange = new SliceRange(ByteBuffer.wrap(new byte[0]), ByteBuffer.wrap(new byte[0]), false, 10);
        	predicate.setSlice_range(sliceRange);
        	
        	List<ColumnOrSuperColumn> data = client.get_slice(key, parent, predicate, ConsistencyLevel.ONE);
        	
        	for (ColumnOrSuperColumn d : data) {
        		System.out.println(d);
        	}
        } catch (InvalidRequestException ire) {
        	JOptionPane.showMessageDialog(table, ire.getWhy());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(table, e.getMessage());
        }
	}

	@Override
	public List<CfDef> getSelectedItems() {
		return null;
	}

	@Override
	public void clear() {

	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {

	}
}
