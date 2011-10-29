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
package com.mebigfatguy.clytemnestra.model;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * a JTextComponent model class that only allows Integer values
 */
public class IntegerDocument extends PlainDocument {

    private static final long serialVersionUID = -6755728406523769124L;

    /**
     * intercepts string insertions to make sure that the values to be put into
     * a text component is only an integer value
     *
     * @param pos
     *            where the text is being inserted
     * @param insertStr
     *            the new text that was typed
     * @param atts
     *            the attributes for the text (unused)
     */
    @Override
    public void insertString(int pos, String insertStr, AttributeSet atts) throws BadLocationException {
        StringBuilder text = new StringBuilder(getText(0, getLength()));
        try {
            text.insert(pos, insertStr);
            Integer.parseInt(text.toString());
            super.insertString(pos, insertStr, atts);
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
