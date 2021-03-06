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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.cassandra.thrift.KsDef;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;

import com.jgoodies.forms.layout.FormLayout;
import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.FormHelper;
import com.mebigfatguy.clytemnestra.StressTestData;
import com.mebigfatguy.clytemnestra.actions.ConnectAction;
import com.mebigfatguy.clytemnestra.actions.CreateKeySpaceAction;
import com.mebigfatguy.clytemnestra.actions.DeleteKeySpaceAction;
import com.mebigfatguy.clytemnestra.actions.DisconnectAction;
import com.mebigfatguy.clytemnestra.actions.NewStressTestAction;
import com.mebigfatguy.clytemnestra.actions.OpenKeySpaceAction;
import com.mebigfatguy.clytemnestra.actions.OpenStressTestAction;
import com.mebigfatguy.clytemnestra.actions.QuitAction;
import com.mebigfatguy.clytemnestra.controllers.Controller;

public class ClytemnestraFrame extends JFrame {

    private static final long serialVersionUID = -4484710448523666662L;

    private JMenu databasesMenu;
    private JMenuItem connectItem;
    private JMenuItem disconnectItem;
    private JMenuItem quitItem;
    private JMenu keySpacesMenu;
    private JMenuItem openKeySpaceItem;
    private JMenuItem createKeySpaceItem;
    private JMenuItem deleteKeySpaceItem;
    private JMenu stressMenu;
    private JMenuItem newStressTestItem;
    private JMenuItem openStressTestItem;
    private final Mediator mediator = new Mediator();
    private JTextField clusterNameField;
    private JTextField partitionerField;
    private JTextField snitchField;
    private JTextField versionField;
    private JPanel descriptionPanel;
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

        descriptionPanel = createDescriptionPanel();
        cp.add(descriptionPanel, BorderLayout.NORTH);
        
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
        databasesMenu.addSeparator();
        quitItem = new JMenuItem(new QuitAction(mediator));
        databasesMenu.add(quitItem);
        mb.add(databasesMenu);

        keySpacesMenu = new JMenu(Bundle.getString(Bundle.Key.KeySpaces));
        createKeySpaceItem = new JMenuItem(new CreateKeySpaceAction(mediator));
        keySpacesMenu.add(createKeySpaceItem);
        openKeySpaceItem = new JMenuItem(new OpenKeySpaceAction(mediator));
        openKeySpaceItem.setEnabled(false);
        keySpacesMenu.add(openKeySpaceItem);
        keySpacesMenu.addSeparator();
        deleteKeySpaceItem = new JMenuItem(new DeleteKeySpaceAction(mediator));
        deleteKeySpaceItem.setEnabled(false);
        keySpacesMenu.add(deleteKeySpaceItem);
        keySpacesMenu.setEnabled(false);
        mb.add(keySpacesMenu);
        
        stressMenu = new JMenu(Bundle.getString(Bundle.Key.StressTest));
        newStressTestItem = new JMenuItem(new NewStressTestAction(mediator));
        stressMenu.add(newStressTestItem);
        openStressTestItem = new JMenuItem(new OpenStressTestAction(mediator));
        stressMenu.add(openStressTestItem);
        stressMenu.setEnabled(false);
        mb.add(stressMenu);

        setJMenuBar(mb);
    }

    private void initListeners() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private JPanel createDescriptionPanel() {
    	JPanel p = new JPanel();
    	
        p.setLayout(new FormLayout("6dlu, pref, 5dlu, pref:grow, 6dlu", "8dlu, pref, 6dlu, pref, 6dlu, pref, 6dlu, pref, 8dlu"));
        
        clusterNameField = new JTextField();
        clusterNameField.setEditable(false);
        FormHelper.addFormRow(p, Bundle.Key.ClusterName, clusterNameField, 2);
        
        partitionerField = new JTextField();
        partitionerField.setEditable(false);
        FormHelper.addFormRow(p, Bundle.Key.Partitioner, partitionerField, 4);
        
        snitchField = new JTextField();
        snitchField.setEditable(false);
        FormHelper.addFormRow(p, Bundle.Key.Snitch, snitchField, 6); 

        versionField = new JTextField();
        versionField.setEditable(false);
        FormHelper.addFormRow(p, Bundle.Key.Version, versionField, 8); 
        
        return p;
    }
    
    private void populateDetails(Cassandra.Client client) {
    	try {
    		if (client == null) {
    			clusterNameField.setText("");
    			partitionerField.setText("");
    			snitchField.setText("");
    			versionField.setText("");
    		} else {
	    		clusterNameField.setText(client.describe_cluster_name());
	    		partitionerField.setText(client.describe_partitioner());
	    		snitchField.setText(client.describe_snitch());
	    		versionField.setText(client.describe_version());
    		}
    	} catch (TException te) {
    		JOptionPane.showMessageDialog(this, te.getMessage());
    	}
    }

    class Mediator implements Context {
    	private TTransport transport;
        private Cassandra.Client client;
        private String address;
        private List<KsDef> selectedKeySpaces;
        private StressTestData stressData;

        @Override
        public void setServerAddress(String serverAddress) {
            address = serverAddress;
        }

        @Override
        public String getServerAddress() {
            return address;
        }

        @Override
		public void setTransport(TTransport xport) {
        	if (transport != null) {
        		transport.close();
        	}
        	transport = xport;
        }
        
        @Override
        public void setClient(Client cassandraClient) {
            client = cassandraClient;
            connectItem.setEnabled(client == null);
            disconnectItem.setEnabled(client != null);
            Controller<KsDef> ksController = keySpacesPanel.getController();
            ksController.refresh(client);
            keySpacesMenu.setEnabled(client!=null);
            stressMenu.setEnabled(client!=null);
            
            populateDetails(client);
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
        
        @Override
		public List<KsDef> getSelectedKeySpaces() {
        	return keySpacesPanel.getSelectedKeySpaces();
        }
        
        @Override
		public void refreshKeySpaces() {
        	keySpacesPanel.refresh();
        }
        
        public void setStressTestData(StressTestData data) {
        	stressData = data;
        	newStressTestItem.setEnabled(stressData == null);
        	openStressTestItem.setEnabled(stressData == null);
        }
        
        public StressTestData getStressTestData() {
        	return stressData;
        }
    }
}
