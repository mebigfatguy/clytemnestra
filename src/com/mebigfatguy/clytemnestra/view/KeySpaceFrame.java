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

import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;

public class KeySpaceFrame extends JFrame {

	private static final long serialVersionUID = 2235197069258018271L;
	
	private Context context;
	private KsDef keySpace;
	private ColumnFamiliesPanel columnFamiliesPanel;
	
	public KeySpaceFrame(Context ctxt, KsDef ks) {
		context = ctxt;
		keySpace = ks;
		String title = Bundle.getString(Bundle.Key.KeySpaceTitle);
		setTitle(MessageFormat.format(title,  keySpace.getName()));
		
        initComponents();
        initMenus();
        initListeners();
        
        columnFamiliesPanel.getController().refresh(ctxt.getClient());
        
		pack();
	}


	private void initComponents() {
		Container cp = getContentPane();
        cp.setLayout(new BorderLayout(4, 4));

        columnFamiliesPanel = new ColumnFamiliesPanel(keySpace, context);
        cp.add(columnFamiliesPanel, BorderLayout.CENTER);
	}
	

	private void initMenus() {
	}
	
	private void initListeners() {
	}
}
