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
package com.mebigfatguy.clytemnestra;

import java.awt.Dimension;

import javax.swing.JComponent;

public class SwingUtils {

    private SwingUtils() {
    }

    public static void sizeUniformly(JComponent... components) {

        for (JComponent c : components) {
            c.setPreferredSize(null);
            c.setMinimumSize(null);
            c.setMaximumSize(null);
        }

        int width = 0;
        int height = 0;

        for (JComponent c : components) {
            Dimension d = c.getPreferredSize();
            if (d.width > width) {
                width = d.width;
            }
            if (d.height > height) {
                height = d.height;
            }
        }

        for (JComponent c : components) {
            Dimension d = c.getPreferredSize();
            d.width = width;
            d.height = height;
            c.setPreferredSize(d);
            c.setMinimumSize(d);
            c.setMaximumSize(d);
        }
    }
}
