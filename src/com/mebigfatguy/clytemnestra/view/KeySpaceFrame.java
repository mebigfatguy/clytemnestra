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

import java.text.MessageFormat;

import javax.swing.JFrame;

import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Bundle;

public class KeySpaceFrame extends JFrame {

	public KeySpaceFrame(KsDef keySpace) {
		String title = Bundle.getString(Bundle.Key.KeySpaceTitle);
		setTitle(MessageFormat.format(title,  keySpace.getName()));
		setSize(200, 200);
	}
}
