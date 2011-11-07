package com.mebigfatguy.clytemnestra.view;

import javax.swing.JDialog;

public class CreateKeySpaceDialog extends JDialog {

	private boolean isOK = false;
	
	public CreateKeySpaceDialog() {
		
	}
	
	public boolean isOK() {
		return isOK;
	}

	public String getKeySpaceName() {
		return "";
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
}
