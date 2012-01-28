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

public class ConnectionDialog extends JDialog {

    private static final long serialVersionUID = -4577114903079737910L;
	private JTextField serverField;
    private JTextField portField;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JCheckBox randomServerCheckBox;
    private JButton okButton;
    private JButton cancelButton;
    private boolean isOK;

    public ConnectionDialog() {
        setTitle(Bundle.getString(Bundle.Key.ConnectToServer));
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

        p.setLayout(new FormLayout("6dlu, pref, 5dlu, 200px, 6dlu", "6dlu, pref, 4dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref, 6dlu"));

        serverField = new JTextField(20);
        serverField.setText("localhost");
        FormHelper.addFormRow(p, Bundle.Key.Server, serverField, 2);        

        portField = new JTextField(20);
        portField.setDocument(new IntegerDocument());
        portField.setText("9160");
        FormHelper.addFormRow(p, Bundle.Key.Port, portField, 4);
        
        randomServerCheckBox = new JCheckBox(Bundle.getString(Bundle.Key.UseRandomServer));
        CellConstraints cc = new CellConstraints();
        p.add(randomServerCheckBox, cc.xyw(2, 6, 4));

        userNameField = new JTextField(20);
        FormHelper.addFormRow(p, Bundle.Key.UserName, userNameField, 8);  
        
        passwordField = new JPasswordField(20);
        FormHelper.addFormRow(p, Bundle.Key.Password, passwordField, 10);  

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
        return isOK;
    }

    public String getHost() {
        return serverField.getText();
    }

    public int getPort() {
        return Integer.parseInt(portField.getText());
    }
    
    public boolean isUseRandomServer() {
    	return randomServerCheckBox.isSelected();
    }

    public String getUserName() {
        return userNameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
