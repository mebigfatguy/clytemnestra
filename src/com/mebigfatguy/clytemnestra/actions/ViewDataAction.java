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

import javax.swing.AbstractAction;

import org.apache.cassandra.thrift.CfDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.FrameManager;
import com.mebigfatguy.clytemnestra.view.ColumnFamilyDataFrame;

public class ViewDataAction extends AbstractAction {

	private static final long serialVersionUID = 7220688796775067879L;
	private final Context context;
	private final CfDef columnFamily;

    public ViewDataAction(Context ctxt, CfDef cf) {
        super(Bundle.getString(Bundle.Key.ViewData));
        context = ctxt;
        columnFamily = cf;
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		ColumnFamilyDataFrame f = new ColumnFamilyDataFrame(context, columnFamily);
		f.setLocationRelativeTo(null);
		
		FrameManager.setColumnFamilyFrame(columnFamily, f);
		f.setVisible(true);	
	}
}