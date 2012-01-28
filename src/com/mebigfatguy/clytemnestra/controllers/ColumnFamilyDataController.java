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
package com.mebigfatguy.clytemnestra.controllers;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.cassandra.thrift.Cassandra;
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
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;

import com.mebigfatguy.clytemnestra.ByteBufferUtils;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.Pair;
import com.mebigfatguy.clytemnestra.model.ColumnFamilyDataTableModel;

public class ColumnFamilyDataController implements Controller<CfDef>, ListSelectionListener, ChangeListener {

	private static final int FETCH_SIZE = 100;
	
	private final CfDef columnFamily;
    private final Context context;
    private final JTable table;
    private final JScrollPane scrollPane;
    private final ColumnFamilyDataTableModel model;
    
    public ColumnFamilyDataController(CfDef cf, Context ctxt, JTable cfTable, JScrollPane scroller, ColumnFamilyDataTableModel cfModel) {
    	columnFamily = cf;
    	context = ctxt;
        table = cfTable;
        scrollPane = scroller;
        model = cfModel;

        table.getSelectionModel().addListSelectionListener(this);
        
        scroller.getViewport().addChangeListener(this);
    }
    
	@Override
	public void refresh(Client client) {
        try {
        	List<Pair<String, List<ColumnOrSuperColumn>>> data = loadRows(client, null);
        	
        	model.clear();
        	model.append(data);
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

	@Override
	public void stateChanged(ChangeEvent e) {
		Rectangle viewRect = scrollPane.getViewport().getViewRect();
	    int first = table.rowAtPoint(new Point(0, viewRect.y));
	    if (first == -1) {
	        return;
	    }
	    
	    int last = table.rowAtPoint(new Point(0, viewRect.y + viewRect.height - 1));
	    if (last == 0) {
	    	return;
	    }

	    if (last == model.getRowCount() - 1) {
	    	page();
	    }
	}
	
	private void page() {	
		try {
			String key = (String) model.getValueAt(model.getRowCount() - 1, 0);
			List<Pair<String, List<ColumnOrSuperColumn>>> data = loadRows(context.getClient(), key);
	    	
	    	model.append(data);
		} catch (InvalidRequestException ire) {
        	JOptionPane.showMessageDialog(table, ire.getWhy());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(table, e.getMessage());
        }
	}
	
	private List<Pair<String, List<ColumnOrSuperColumn>>> loadRows(Cassandra.Client client, String afterKey) throws TException, TimedOutException, UnavailableException, InvalidRequestException, UnsupportedEncodingException {
		
		client.set_keyspace(columnFamily.getKeyspace());
    	KeyRange keyRange = new KeyRange(FETCH_SIZE);
    	keyRange.setStart_key((afterKey == null) ? ByteBufferUtils.ZERO_BYTES : afterKey.getBytes());
    	keyRange.setEnd_key(ByteBufferUtils.ZERO_BYTES);
    	ColumnParent parent = new ColumnParent(columnFamily.getName());
    	
        SlicePredicate predicate = new SlicePredicate();
        predicate.setSlice_range(new SliceRange(ByteBufferUtils.ZERO_BYTE_BUFFER, ByteBufferUtils.ZERO_BYTE_BUFFER, false, Integer.MAX_VALUE));

    	List<KeySlice> keySlices = client.get_range_slices(parent, predicate, keyRange, ConsistencyLevel.ONE);
    	
    	List<Pair<String, List<ColumnOrSuperColumn>>> data = new ArrayList<Pair<String, List<ColumnOrSuperColumn>>>();
    	
    	List<ByteBuffer> keys = new ArrayList<ByteBuffer>();
    	for (KeySlice slice : keySlices) {
    		keys.add(slice.bufferForKey());
    	}
    	Map<ByteBuffer, List<ColumnOrSuperColumn>> slices = client.multiget_slice(keys, parent, predicate, ConsistencyLevel.ONE);

    	for (Map.Entry<ByteBuffer, List<ColumnOrSuperColumn>> entry : slices.entrySet()) {
    		String key = ByteBufferUtils.toString(entry.getKey());
    		if (!key.equals(afterKey)) {
    			data.add(new Pair<String, List<ColumnOrSuperColumn>>(key, entry.getValue()));
    		}
    	}
    	
    	return data;
	}
}
