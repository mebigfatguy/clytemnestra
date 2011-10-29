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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.actions.ConnectAction;
import com.mebigfatguy.clytemnestra.actions.DisconnectAction;

public class ClytemnestraFrame extends JFrame {

    private JMenuItem connectItem;
    private JMenuItem disconnectItem;
    private Mediator mediator = new Mediator();

    public ClytemnestraFrame() {
        super(Bundle.getString(Bundle.Key.Title));
        initComponents();
        initMenus();
        initListeners();
        setSize(800, 600);
    }
    
    private void initComponents() { 
    }
    
    private void initMenus() {
        JMenuBar mb = new JMenuBar();
        JMenu databasesMenu = new JMenu(Bundle.getString(Bundle.Key.Servers));
        connectItem = new JMenuItem(new ConnectAction(mediator));
        databasesMenu.add(connectItem);
        disconnectItem = new JMenuItem(new DisconnectAction(mediator));
        disconnectItem.setEnabled(false);
        mb.add(databasesMenu);
        databasesMenu.add(disconnectItem);

        setJMenuBar(mb);
    }
    
    private void initListeners() { 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    class Mediator implements Context {
        
    }
}
