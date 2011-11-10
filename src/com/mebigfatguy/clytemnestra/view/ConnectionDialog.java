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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.SwingUtils;
import com.mebigfatguy.clytemnestra.model.IntegerDocument;

public class ConnectionDialog extends JDialog {

    private static final long serialVersionUID = -4577114903079737910L;
	private JTextField serverField;
    private JTextField portField;
    private JTextField userNameField;
    private JPasswordField passwordField;
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
        p.setLayout(new BorderLayout(4, 4));

        p.setLayout(new FormLayout("6dlu, pref, 5dlu, 200px, 6dlu", "6dlu, pref, 4dlu, pref, 20dlu, pref, 4dlu, pref, 6dlu"));
        CellConstraints cc = new CellConstraints();

        JLabel serverLabel = new JLabel(Bundle.getString(Bundle.Key.Server));
        p.add(serverLabel, cc.xy(2, 2));

        serverField = new JTextField(20);
        serverField.setText("localhost");
        p.add(serverField, cc.xy(4, 2));

        serverLabel.setLabelFor(serverField);

        JLabel portLabel = new JLabel(Bundle.getString(Bundle.Key.Port));
        p.add(portLabel, cc.xy(2, 4));

        portField = new JTextField(20);
        portField.setDocument(new IntegerDocument());
        portField.setText("9160");
        p.add(portField, cc.xy(4, 4));

        portLabel.setLabelFor(portField);

        JLabel userNameLabel = new JLabel(Bundle.getString(Bundle.Key.UserName));
        p.add(userNameLabel, cc.xy(2, 6));

        userNameField = new JTextField(20);
        p.add(userNameField, cc.xy(4, 6));

        userNameLabel.setLabelFor(userNameField);

        JLabel passwordLabel = new JLabel(Bundle.getString(Bundle.Key.Password));
        p.add(passwordLabel, cc.xy(2, 8));

        passwordField = new JPasswordField(20);
        p.add(passwordField, cc.xy(4, 8));

        passwordLabel.setLabelFor(passwordField);

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

    public String getUserName() {
        return userNameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
