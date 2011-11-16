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
import java.util.ArrayList;
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
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.KeySlice;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;

import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.Pair;
import com.mebigfatguy.clytemnestra.model.ColumnFamilyDataTableModel;

public class ColumnFamilyDataController implements Controller<CfDef>, ListSelectionListener {

	private static final int FETCH_SIZE = 100;
	
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
        	KeyRange keyRange = new KeyRange(FETCH_SIZE);
        	keyRange.setStart_key(new byte[0]);
        	keyRange.setEnd_key(new byte[0]);
        	ColumnParent parent = new ColumnParent(columnFamily.getName());
        	
            SlicePredicate predicate = new SlicePredicate();
            predicate.setSlice_range(new SliceRange(ByteBuffer.wrap(new byte[0]), ByteBuffer.wrap(new byte[0]), false, 100));

        	List<KeySlice> keySlices = client.get_range_slices(parent, predicate, keyRange, ConsistencyLevel.ONE);
        	
        	List<Pair<String, List<ColumnOrSuperColumn>>> data = new ArrayList<Pair<String, List<ColumnOrSuperColumn>>>();
        	
        	for (KeySlice slice : keySlices) {
        		List<ColumnOrSuperColumn> columns = client.get_slice(ByteBuffer.wrap(slice.getKey()), parent, predicate, ConsistencyLevel.ONE);
        		String key = new String(slice.getKey(), "UTF-8");
        		data.add(new Pair<String, List<ColumnOrSuperColumn>>(key, columns));
        	}
        	
        	model.replaceContents(data);
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
