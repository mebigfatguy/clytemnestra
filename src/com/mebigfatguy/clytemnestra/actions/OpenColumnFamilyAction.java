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

import org.apache.cassandra.thrift.CfDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.FrameManager;
import com.mebigfatguy.clytemnestra.controllers.Controller;
import com.mebigfatguy.clytemnestra.view.ColumnFamilyFrame;

public class OpenColumnFamilyAction extends AbstractAction {

    private static final long serialVersionUID = -2451127999419582272L;
	private final Context context;
	private final Controller<CfDef> controller;

    public OpenColumnFamilyAction(Context ctxt, Controller<CfDef> ctrlr) {
        super(Bundle.getString(Bundle.Key.OpenColumnFamily));
        context = ctxt;
        controller = ctrlr;
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
        List<CfDef> columnFamilies = controller.getSelectedItems();
        for (CfDef columnFamily : columnFamilies) {
        	JFrame f = FrameManager.getColumnFamilyFrame(columnFamily);
        	if (f != null) {
        		f.toFront();
        	} else {
	        	f = new ColumnFamilyFrame(context, columnFamily);
	        	f.setLocationRelativeTo(null);
	        	f.setVisible(true);
	        	FrameManager.setColumnFamilyFrame(columnFamily, f);
        	}
        }
	}

}
