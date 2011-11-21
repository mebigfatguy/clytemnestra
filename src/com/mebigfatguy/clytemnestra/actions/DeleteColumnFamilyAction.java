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
package com.mebigfatguy.clytemnestra.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.InvalidRequestException;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.FrameManager;
import com.mebigfatguy.clytemnestra.controllers.Controller;

public class DeleteColumnFamilyAction extends AbstractAction {

    private static final long serialVersionUID = -7330872430191016007L;
	private final Context context;
	private final Controller<CfDef> controller;

    public DeleteColumnFamilyAction(Context ctxt, Controller<CfDef> ctrlr) {
        super(Bundle.getString(Bundle.Key.DeleteColumnFamily));
        context = ctxt;
        controller = ctrlr;
    }
    
	@Override
	public void actionPerformed(ActionEvent ae) {
    	Cassandra.Client client = context.getClient();
        List<CfDef> columnFamilies = controller.getSelectedItems();
        for (CfDef columnFamily : columnFamilies) {
        	try {
        		client.set_keyspace(columnFamily.getKeyspace());
        		client.system_drop_column_family(columnFamily.getName());
        		List<JFrame> frames = FrameManager.findColumnFamilyDependentFrames(columnFamily);
        		for (JFrame f : frames) {
        			f.dispose();
        		}
        	} catch (InvalidRequestException ire) {
        		JOptionPane.showMessageDialog(null, "Failed deleting column family: " + columnFamily.getName() + "\n" + ire.getWhy());
        		
        	} catch (Exception e) {
        		JOptionPane.showMessageDialog(null, "Failed deleting column family: " + columnFamily.getName() + "\n" + e.getMessage());
        	}
        }
        
        controller.refresh(client);
	}

}
