/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mebigfatguy.clytemnestra.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.FormHelper;
import com.mebigfatguy.clytemnestra.SwingUtils;
import com.mebigfatguy.clytemnestra.cassandra.ReplicationStrategy;
import com.mebigfatguy.clytemnestra.model.StrategicOptionsTableModel;

public class CreateKeySpaceDialog extends JDialog {

	private static final long serialVersionUID = 1695811618255955496L;
	
	private boolean isOK = false;
	private JTextField keySpaceName;
	private JRadioButton durableWritesRadio;
	private JComboBox strategyClassCombo;
	private JTable optionsTable;
	private JButton okButton;
	private JButton cancelButton;

	public CreateKeySpaceDialog() {
		setTitle(Bundle.getString(Bundle.Key.CreateKeySpace));
		initComponents();
		initListeners();
		pack();
	}

	public boolean isOK() {
		return isOK;
	}

	public String getKeySpaceName() {
		return keySpaceName.getText();
	}

	public boolean getDurableWrites() {
		return durableWritesRadio.isSelected();
	}

	public String getStrategyClass() {
		return (String)strategyClassCombo.getSelectedItem();
	}
	
	public Map<String, String> getStrategyOptions() {
		return ((StrategicOptionsTableModel) optionsTable.getModel()).getStrategicOptions();
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

        p.setLayout(new FormLayout("6dlu, pref, 5dlu, 200px, 6dlu", "6dlu, pref, 4dlu, pref, 8dlu, pref, 4dlu, pref, 4dlu, pref, 6dlu"));
        CellConstraints cc = new CellConstraints();
        
        keySpaceName = new JTextField(20);
        FormHelper.addFormRow(p, Bundle.Key.KeySpaceName, keySpaceName, 2);

        durableWritesRadio = new JRadioButton(Bundle.getString(Bundle.Key.DurableWrites));
        p.add(durableWritesRadio, cc.xyw(2, 4, 4));
        
        strategyClassCombo = new JComboBox();
        for (ReplicationStrategy strategy : ReplicationStrategy.values()) {
            strategyClassCombo.addItem(strategy.name());        	
        }
        strategyClassCombo.setEditable(true);
        FormHelper.addFormRow(p, Bundle.Key.StrategyClass, strategyClassCombo, 6);
        
        JLabel optionsLabel = new JLabel(Bundle.getString(Bundle.Key.StrategicOptions));
        p.add(optionsLabel, cc.xy(2, 8));
        
        optionsTable = new JTable(new StrategicOptionsTableModel((String) strategyClassCombo.getSelectedItem()));
        p.add(new JScrollPane(optionsTable), cc.xyw(2, 10, 4));
        Dimension dim = optionsTable.getPreferredSize();
        dim.height = 100;
        optionsTable.setPreferredScrollableViewportSize(dim);
        
        optionsLabel.setLabelFor(optionsTable);

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
        
        strategyClassCombo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    ((StrategicOptionsTableModel) optionsTable.getModel()).resetOptions((String) e.getItem());
                }
            }
        });
	}
}
