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
package com.mebigfatguy.clytemnestra;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;

public class FormHelper {

	private FormHelper() {
	}
	
	public static void addFormRow(JPanel p, Bundle.Key key, JComponent component, int formY) {
		
		JLabel label = new JLabel(Bundle.getString(key));
        CellConstraints cc = new CellConstraints();
        p.add(label, cc.xy(2, formY));

        p.add(component, cc.xy(4, formY));
        
        label.setLabelFor(component);
	}
	
	public static void addFormItem(JPanel p, JComponent component, int formX, int formY) {
	    CellConstraints cc = new CellConstraints();
        p.add(component, cc.xy(formX, formY));
	}
}
