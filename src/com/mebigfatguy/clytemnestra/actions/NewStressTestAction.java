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

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.FrameManager;
import com.mebigfatguy.clytemnestra.StressTestData;
import com.mebigfatguy.clytemnestra.view.CreateStressTestDialog;
import com.mebigfatguy.clytemnestra.view.RunStressTestFrame;

public class NewStressTestAction extends AbstractAction {

	private static final long serialVersionUID = -1437702043067066266L;
	private Context context;
	
	public NewStressTestAction(Context ctxt) {
		super(Bundle.getString(Bundle.Key.NewStressTest));
        context = ctxt;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CreateStressTestDialog cstd = new CreateStressTestDialog();
		cstd.setLocationRelativeTo(null);
		cstd.setModal(true);
		cstd.setVisible(true);
		if (cstd.isOK()) {
			context.setStressTestData(new StressTestData(cstd.getNumKeySpaces(), cstd.getMaxColumnFamiliesPerKeySpace()));
			RunStressTestFrame f = new RunStressTestFrame(context);
			f.setLocationRelativeTo(null);
			FrameManager.setStressTestFrame(f);
			f.setVisible(true);
		}
	}
}
