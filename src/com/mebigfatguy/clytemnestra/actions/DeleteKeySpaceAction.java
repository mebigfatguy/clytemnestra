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
import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.FrameManager;

public class DeleteKeySpaceAction extends AbstractAction {

    private final Context context;

    public DeleteKeySpaceAction(Context ctxt) {
        super(Bundle.getString(Bundle.Key.DeleteKeySpace));
        context = ctxt;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    	Cassandra.Client client = context.getClient();
        List<KsDef> keySpaces = context.getSelectedKeySpaces();
        for (KsDef keySpace : keySpaces) {
        	try {
        		client.system_drop_keyspace(keySpace.getName());
        		List<JFrame> frames = FrameManager.findKeySpaceDependentFrames(keySpace);
        		for (JFrame f : frames) {
        			f.dispose();
        		}
        	} catch (Exception e) {
        		JOptionPane.showMessageDialog(null, "Failed deleting keyspace: " + keySpace.getName() + "\n" + e.getMessage());
        	}
        }
        context.refreshKeySpaces();

    }

}
