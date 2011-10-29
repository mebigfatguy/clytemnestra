/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mebigfatguy.clytemnestra.controllers;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.KsDef;

public class KeySpacesController implements Controller {

    private final JList list;
    private final DefaultListModel model;

    public KeySpacesController(JList ksList, DefaultListModel ksModel) {
        list = ksList;
        model = ksModel;
    }

    @Override
    public void refresh(Cassandra.Client client) {
        try {
            final List<KsDef> keySpaces = client.describe_keyspaces();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    model.removeAllElements();
                    for (KsDef keySpace : keySpaces) {
                        model.addElement(keySpace.getName());
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(list, e.getMessage());
        }

    }
}

