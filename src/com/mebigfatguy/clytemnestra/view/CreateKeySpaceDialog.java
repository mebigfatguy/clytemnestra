package com.mebigfatguy.clytemnestra.view;

import java.awt.BorderLayout;
import java.awt.Container;

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

public class CreateKeySpaceDialog extends JDialog {

	private boolean isOK = false;
	private JTextField keySpaceName;
	private JButton okButton;
	private JButton cancelButton;

	public CreateKeySpaceDialog() {
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
		return false;
	}

	public String getStrategyClass() {
		return "";
	}

	public String getReplicationFactor() {
		return "1";
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

        JLabel keyspaceLabel = new JLabel(Bundle.getString(Bundle.Key.KeySpaceName));
        p.add(keyspaceLabel, cc.xy(2, 2));

        keySpaceName = new JTextField(20);
        p.add(keySpaceName, cc.xy(4, 2));

        keyspaceLabel.setLabelFor(keySpaceName);

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

	}
}
