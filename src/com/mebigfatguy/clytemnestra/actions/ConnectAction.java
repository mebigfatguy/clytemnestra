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
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.apache.cassandra.thrift.AuthenticationRequest;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.view.ConnectionDialog;

public class ConnectAction extends AbstractAction {

    private static final long serialVersionUID = 3867581530691421270L;
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
                String host = cd.getHost();
                int port = cd.getPort();
                String userName = cd.getUserName();
                String password = cd.getPassword();
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
            }
        } catch (Exception uhe) {
            JOptionPane.showMessageDialog(null, uhe.getMessage());
        }

    }
}
