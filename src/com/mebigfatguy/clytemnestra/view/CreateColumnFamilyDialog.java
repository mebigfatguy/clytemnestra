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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.SwingUtils;

public class CreateColumnFamilyDialog extends JDialog {

	private static final long serialVersionUID = 5527764168898313536L;
	
	private boolean isOK = false;
	private JTextField columnFamilyName;
	private JButton okButton;
	private JButton cancelButton;

	public CreateColumnFamilyDialog() {
		initComponents();
		initListeners();
		pack();
	}

	public boolean isOK() {
		return isOK;
	}

	public String getColumnFamilyName() {
		return columnFamilyName.getText();
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
        p.setLayout(new BorderLayout(4, 4));

        p.setLayout(new FormLayout("6dlu, pref, 5dlu, 200px, 6dlu", "6dlu, pref, 4dlu, pref, 8dlu, pref, 4dlu, pref, 4dlu, pref, 6dlu"));
        CellConstraints cc = new CellConstraints();

        JLabel columnFamilyLabel = new JLabel(Bundle.getString(Bundle.Key.ColumnFamily));
        p.add(columnFamilyLabel, cc.xy(2, 2));

        columnFamilyName = new JTextField(20);
        p.add(columnFamilyName, cc.xy(4, 2));

        columnFamilyLabel.setLabelFor(columnFamilyName);

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

}