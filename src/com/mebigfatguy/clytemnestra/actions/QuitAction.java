package com.mebigfatguy.clytemnestra.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;

public class QuitAction extends AbstractAction {

	private static final long serialVersionUID = -2816229484901472214L;
	private Context context;
	
	public QuitAction(Context ctxt) {
		super(Bundle.getString(Bundle.Key.Quit));
        context = ctxt;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO: Save on exit
		System.exit(0);
	}

}
