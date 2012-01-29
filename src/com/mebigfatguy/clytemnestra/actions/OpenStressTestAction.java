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
import javax.swing.filechooser.FileFilter;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;

public class OpenStressTestAction extends AbstractAction {
	
	private Context context;
	private File stressDir;
	
	public OpenStressTestAction(Context ctxt) {
		super(Bundle.getString(Bundle.Key.OpenStressTest));
        context = ctxt;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser(stressDir);
		chooser.setFileFilter(new StressFileFilter());
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int option = chooser.showOpenDialog(null);
		if (option == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			stressDir = f.getParentFile();
		}
	}
	
	class StressFileFilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			return f.isDirectory() || f.getName().endsWith(".csf");
		}

		@Override
		public String getDescription() {
			return Bundle.getString(Bundle.Key.StressFileDescription);
		}
	}
}
