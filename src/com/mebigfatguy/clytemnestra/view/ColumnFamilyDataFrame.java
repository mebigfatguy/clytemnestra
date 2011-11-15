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
package com.mebigfatguy.clytemnestra.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.text.MessageFormat;

import javax.swing.JFrame;

import org.apache.cassandra.thrift.CfDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;

public class ColumnFamilyDataFrame extends JFrame {
	
	private final Context context;
	private final CfDef columnFamily;
	private ColumnFamilyDataPanel columnFamilyDataPanel;
	
	public ColumnFamilyDataFrame(Context ctxt, CfDef cf) {
		context = ctxt;
		columnFamily = cf;
		
		String title = Bundle.getString(Bundle.Key.ColumnFamilyTitle);
		setTitle(MessageFormat.format(title,  columnFamily.getName()));
		
        initComponents();
        initMenus();
        initListeners();
        
        columnFamilyDataPanel.getController().refresh(ctxt.getClient());
		
		pack();
	}

	private void initComponents() {
		Container cp = getContentPane();
        cp.setLayout(new BorderLayout(4, 4));

        columnFamilyDataPanel = new ColumnFamilyDataPanel(columnFamily, context);
        cp.add(columnFamilyDataPanel, BorderLayout.CENTER);
	}

	private void initMenus() {
	}

	private void initListeners() {
	}
}
