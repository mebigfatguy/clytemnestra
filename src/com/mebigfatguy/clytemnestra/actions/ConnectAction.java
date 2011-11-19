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
package com.mebigfatguy.clytemnestra.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.apache.cassandra.thrift.AuthenticationException;
import org.apache.cassandra.thrift.AuthenticationRequest;
import org.apache.cassandra.thrift.AuthorizationException;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.view.ConnectionDialog;

public class ConnectAction extends AbstractAction {

    private static final long serialVersionUID = 3867581530691421270L;
    private static final int MAX_RANDOM_CONNECT_ATTEMPTS = 5;
	private final Context context;

    public ConnectAction(Context ctxt) {
        super(Bundle.getString(Bundle.Key.Connect));
        context = ctxt;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ConnectionDialog cd = new ConnectionDialog();
            cd.setLocationRelativeTo(null);
            cd.setModal(true);
            cd.setVisible(true);
            if (cd.isOK()) {
            	connect(cd.getHost(), cd.getPort(), cd.getUserName(), cd.getPassword());
                if (cd.isUseRandomServer()) {
                	int errorCount = 0;
                	boolean connected = false;
                	while (!connected) {
                		try {
	                		String host = getRandomHost();
	                		connect(host, cd.getPort(), cd.getUserName(), cd.getPassword());
	                		connected = true;
                		} catch (Exception ee) {
                			++errorCount;
                			if (errorCount > MAX_RANDOM_CONNECT_ATTEMPTS) {
                				return;
                			}
                		}
                	}
                }
            }
        } catch (Exception uhe) {
            JOptionPane.showMessageDialog(null, uhe.getMessage());
        }
    }
    
    private void connect(String host, int port, String userName, String password) throws TTransportException, TException, AuthorizationException, AuthenticationException {
        TTransport tr = new TFramedTransport(new TSocket(host, port));
        TProtocol proto = new TBinaryProtocol(tr);
        Cassandra.Client client = new Cassandra.Client(proto);

        tr.open();

        Map<String, String> credentials = new HashMap<String, String>();
        credentials.put("username", userName);
        credentials.put("password", password);

        AuthenticationRequest authReq = new AuthenticationRequest(credentials);
        client.login(authReq);

        context.setServerAddress(host + ":" + port);
        context.setClient(client);
        context.setTransport(tr);
    }
    
    private String getRandomHost() throws TException, InvalidRequestException {
    	Cassandra.Client client = context.getClient();
    	List<KsDef> keySpaces = client.describe_keyspaces();
    	
    	List<String> hosts = new ArrayList<String>();
    	
    	for (KsDef keySpace : keySpaces) {
    		if (!"system".equals(keySpace.getName())) {
	    		List<TokenRange> ranges = client.describe_ring(keySpace.getName());
	    		for (TokenRange range : ranges) {
	    			for (String endpoint : range.endpoints) {
	    				hosts.add(endpoint);
	    			}
	    		}
    		}
    	}
    	
    	return hosts.get((int) (Math.random() * hosts.size()));
    }
}
