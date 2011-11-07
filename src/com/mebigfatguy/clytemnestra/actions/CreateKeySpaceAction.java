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
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.view.CreateKeySpaceDialog;

public class CreateKeySpaceAction extends AbstractAction {

    private final Context context;

    public CreateKeySpaceAction(Context ctxt) {
        super(Bundle.getString(Bundle.Key.CreateKeySpace));
        context = ctxt;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	try {
	        CreateKeySpaceDialog ksDialog = new CreateKeySpaceDialog();
	        ksDialog.setLocationRelativeTo(null);
	        ksDialog.setModal(true);
	        ksDialog.setVisible(true);
	        
	        if (ksDialog.isOK()) {
	        	Cassandra.Client client = context.getClient();
	        	KsDef ksDef = new KsDef();
	        	ksDef.setName(ksDialog.getKeySpaceName());
	        	ksDef.setDurable_writes(ksDialog.getDurableWrites());
	        	ksDef.setStrategy_class(ksDialog.getStrategyClass());
	        	Map<String, String> strategyOptions = new HashMap<String, String>();
	        	strategyOptions.put("replication_factor", ksDialog.getReplicationFactor());
	        	ksDef.setStrategy_options(strategyOptions);
	        	client.system_add_keyspace(ksDef);
	        }
    	} catch (Exception te) {
    		JOptionPane.showMessageDialog(null, te.getMessage());
    	}
    }

}
