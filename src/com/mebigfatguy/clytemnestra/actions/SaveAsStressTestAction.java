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
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.StressTestData;

public class SaveAsStressTestAction extends AbstractAction {

	private static final long serialVersionUID = -5234843551903057895L;
	
	private Context context;
	private File stressDir;
	
	public SaveAsStressTestAction(Context ctxt) {
		super(Bundle.getString(Bundle.Key.SaveAsStressTest));
        context = ctxt;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			JFileChooser chooser = new JFileChooser(stressDir);
			int option = chooser.showSaveDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				StressTestData data = context.getStressTestData();
				data.setFile(f);
				data.writeToFile();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,  e.getMessage());
		}
	}
}
