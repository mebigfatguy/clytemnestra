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
import java.text.MessageFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Context;
import com.mebigfatguy.clytemnestra.Strings;

public class KeySpacesPanel extends JPanel {

    private static final long serialVersionUID = 291151369935073424L;

    private final Context context;
    private JList keySpaceList;

    public KeySpacesPanel(Context ctxt) {
        context = ctxt;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(4, 4));

        JLabel keySpacesLabel;

        String addr = context.getServerAddress();
        if (Strings.isEmpty(addr)) {
            keySpacesLabel = new JLabel(Bundle.getString(Bundle.Key.KeySpaces));
        } else {
            String fmtString = Bundle.getString(Bundle.Key.KeySpaces);
            keySpacesLabel = new JLabel(MessageFormat.format(fmtString,  context.getServerAddress()));
        }
        add(keySpacesLabel, BorderLayout.NORTH);
        keySpaceList = new JList();
        add(new JScrollPane(keySpaceList), BorderLayout.CENTER);
        keySpacesLabel.setLabelFor(keySpaceList);

        setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
    }
}
