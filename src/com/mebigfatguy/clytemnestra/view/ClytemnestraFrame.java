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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.actions.ConnectAction;
import com.mebigfatguy.clytemnestra.actions.CreateKeySpaceAction;
import com.mebigfatguy.clytemnestra.actions.DeleteKeySpaceAction;
import com.mebigfatguy.clytemnestra.actions.DisconnectAction;
import com.mebigfatguy.clytemnestra.actions.OpenKeySpaceAction;
import com.mebigfatguy.clytemnestra.controllers.Controller;

public class ClytemnestraFrame extends JFrame {

    private static final long serialVersionUID = -4484710448523666662L;

    private JMenu databasesMenu;
    private JMenuItem connectItem;
    private JMenuItem disconnectItem;
    private JMenu keySpacesMenu;
    private JMenuItem openKeySpaceItem;
    private JMenuItem createKeySpaceItem;
    private JMenuItem deleteKeySpaceItem;
    private final Mediator mediator = new Mediator();
    private KeySpacesPanel keySpacesPanel;

    public ClytemnestraFrame() {
        super(Bundle.getString(Bundle.Key.Title));
        initComponents();
        initMenus();
        initListeners();
        setSize(800, 600);
    }

    private void initComponents() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(4, 4));

        keySpacesPanel = new KeySpacesPanel(mediator);
        cp.add(keySpacesPanel, BorderLayout.CENTER);
    }

    private void initMenus() {
        JMenuBar mb = new JMenuBar();
        databasesMenu = new JMenu(Bundle.getString(Bundle.Key.Servers));
        connectItem = new JMenuItem(new ConnectAction(mediator));
        databasesMenu.add(connectItem);
        disconnectItem = new JMenuItem(new DisconnectAction(mediator));
        disconnectItem.setEnabled(false);
        databasesMenu.add(disconnectItem);
        mb.add(databasesMenu);

        keySpacesMenu = new JMenu(Bundle.getString(Bundle.Key.KeySpaces));
        createKeySpaceItem = new JMenuItem(new CreateKeySpaceAction(mediator));
        keySpacesMenu.add(createKeySpaceItem);
        openKeySpaceItem = new JMenuItem(new OpenKeySpaceAction(mediator));
        keySpacesMenu.add(openKeySpaceItem);
        keySpacesMenu.addSeparator();
        deleteKeySpaceItem = new JMenuItem(new DeleteKeySpaceAction(mediator));
        keySpacesMenu.add(deleteKeySpaceItem);
        keySpacesMenu.setEnabled(false);
        mb.add(keySpacesMenu);

        setJMenuBar(mb);
    }

    private void initListeners() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class Mediator implements Context {
        private Cassandra.Client client;
        private String address;
        private List<KsDef> selectedKeySpaces;

        @Override
        public void setServerAddress(String serverAddress) {
            address = serverAddress;
        }

        @Override
        public String getServerAddress() {
            return address;
        }

        @Override
        public void setClient(Client cassandraClient) {
            client = cassandraClient;
            connectItem.setEnabled(client == null);
            disconnectItem.setEnabled(client != null);
            Controller ksController = keySpacesPanel.getController();
            ksController.refresh(client);
            keySpacesMenu.setEnabled(client!=null);
        }

        @Override
        public Client getClient() {
            return client;
        }

        @Override
        public void setSelectedKeySpaces(List<KsDef> keySpaces) {
            selectedKeySpaces = new ArrayList<KsDef>(keySpaces);
            boolean hasSelection = selectedKeySpaces.size() > 0;
            openKeySpaceItem.setEnabled(hasSelection);
            deleteKeySpaceItem.setEnabled(hasSelection);
        }
        
        public void refreshKeySpaces() {
        	keySpacesPanel.refresh();
        }
    }
}
