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
package com.mebigfatguy.clytemnestra.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.FormHelper;
import com.mebigfatguy.clytemnestra.SwingUtils;
import com.mebigfatguy.clytemnestra.model.IntegerDocument;

public class CreateStressTestDialog extends JDialog {

	private JTextField keySpacesField;
	private JTextField maxColumnFamiliesPerKeySpaceField;
	private JTextField maxColumnsPerColumnFamilyField;
	private JTextField createPercentageField;
	private JTextField updatePercentageField;
	private JTextField readPercentageField;
	private JButton okButton;
	private JButton cancelButton;
	private boolean isOK;
	
	public CreateStressTestDialog() {
		setTitle(Bundle.getString(Bundle.Key.CreateStressTest));
        initComponents();
        initListeners();
        pack();
        isOK = false;
	}

	private void initComponents() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(4, 4));

        cp.add(createFormPanel(), BorderLayout.CENTER);
        cp.add(createCtrlPanel(), BorderLayout.SOUTH);

        getRootPane().setDefaultButton(okButton);
    }
	
    private JPanel createFormPanel() {
        JPanel p = new JPanel();

        p.setLayout(new FormLayout("6dlu, pref, 5dlu, 80, 6dlu", "6dlu, pref, 4dlu, pref, 4dlu, pref, 10dlu, pref, 4dlu, pref, 4dlu, pref, 6dlu"));

        keySpacesField = new JTextField(6);
        keySpacesField.setText("2");
        FormHelper.addFormRow(p, Bundle.Key.KeySpaces, keySpacesField, 2);        

        maxColumnFamiliesPerKeySpaceField = new JTextField(6);
        maxColumnFamiliesPerKeySpaceField.setDocument(new IntegerDocument());
        maxColumnFamiliesPerKeySpaceField.setText("4");
        FormHelper.addFormRow(p, Bundle.Key.MaxColumnFamiliesPerKeySpace, maxColumnFamiliesPerKeySpaceField, 4);
        
        maxColumnsPerColumnFamilyField = new JTextField(6);
        maxColumnsPerColumnFamilyField.setDocument(new IntegerDocument());
        maxColumnsPerColumnFamilyField.setText("30");
        FormHelper.addFormRow(p, Bundle.Key.MaxColumnsPerColumnFamily, maxColumnsPerColumnFamilyField, 6);

    	createPercentageField = new JTextField(6);
    	createPercentageField.setDocument(new IntegerDocument());
    	createPercentageField.setText("60");
        FormHelper.addFormRow(p, Bundle.Key.CreatePercentage, createPercentageField, 8);
        
    	updatePercentageField = new JTextField(6);
    	updatePercentageField.setDocument(new IntegerDocument());
    	updatePercentageField.setText("20");
        FormHelper.addFormRow(p, Bundle.Key.UpdatePercentage, updatePercentageField, 10);

        readPercentageField = new JTextField(6);
        readPercentageField.setDocument(new IntegerDocument());
        readPercentageField.setText("20");
        FormHelper.addFormRow(p, Bundle.Key.ReadPercentage, readPercentageField, 12);

        p.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        return p;
    }

    private JPanel createCtrlPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        p.add(Box.createHorizontalGlue());

        cancelButton = new JButton(Bundle.getString(Bundle.Key.Cancel));
        p.add(cancelButton);
        p.add(Box.createHorizontalStrut(20));

        okButton = new JButton(Bundle.getString(Bundle.Key.OK));
        p.add(okButton);
        p.add(Box.createHorizontalStrut(10));

        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        SwingUtils.sizeUniformly(okButton, cancelButton);

        return p;
    }
    
	private void initListeners() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                isOK = true;
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                isOK = false;
            }
        });
	}

	public boolean isOK() {
		return false;
	}
}
