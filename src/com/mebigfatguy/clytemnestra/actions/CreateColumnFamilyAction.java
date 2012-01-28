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
package com.mebigfatguy.clytemnestra.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.FrameManager;
import com.mebigfatguy.clytemnestra.view.CreateColumnFamilyDialog;
import com.mebigfatguy.clytemnestra.view.KeySpaceFrame;

public class CreateColumnFamilyAction extends AbstractAction {

	private static final long serialVersionUID = 6055469380789382015L;
	
	private final Context context;
    private final KsDef keySpace;

    public CreateColumnFamilyAction(Context ctxt, KsDef ks) {
        super(Bundle.getString(Bundle.Key.CreateColumnFamily));
        context = ctxt;
        keySpace = ks;
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
    	try {
	        CreateColumnFamilyDialog cfDialog = new CreateColumnFamilyDialog();
	        cfDialog.setLocationRelativeTo(null);
	        cfDialog.setModal(true);
	        cfDialog.setVisible(true);
	        
	        if (cfDialog.isOK()) {
	        	Cassandra.Client client = context.getClient();
	        	CfDef cfDef = new CfDef();
	        	cfDef.setKeyspace(keySpace.getName());
	        	cfDef.setName(cfDialog.getColumnFamilyName());
	        	cfDef.setColumn_type(cfDialog.getColumnFamilyType().name());
	        	cfDef.setComparator_type(cfDialog.getComparatorType());
	        	client.set_keyspace(keySpace.getName());
	        	client.system_add_column_family(cfDef);
	        	KeySpaceFrame ksFrame = (KeySpaceFrame) FrameManager.getKeySpaceFrame(keySpace);
	        	ksFrame.refreshColumnFamilies();
	        }
    	} catch (InvalidRequestException ire) {
    		JOptionPane.showMessageDialog(null, ire.getWhy());
    	} catch (Exception te) {
    		JOptionPane.showMessageDialog(null, te.getMessage());
    	}	
    }
}
