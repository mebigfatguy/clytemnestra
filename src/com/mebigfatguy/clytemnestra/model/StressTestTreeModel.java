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
package com.mebigfatguy.clytemnestra.model;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.mebigfatguy.clytemnestra.StressTestData;
import com.mebigfatguy.clytemnestra.StressTestData.ColumnFamilyData;
import com.mebigfatguy.clytemnestra.StressTestData.KeySpaceData;

public class StressTestTreeModel implements TreeModel {

	private static final Object ROOT = new Object();
	private StressTestData stressData;
	
	public StressTestTreeModel(StressTestData data) {
		stressData = data;
	}
	
	@Override
	public Object getRoot() {
		return ROOT;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent == ROOT) {
			return stressData.getKeySpaceData().get(index);
		} else if (parent instanceof KeySpaceData) {
			KeySpaceData ksParent = (KeySpaceData) parent;
			return ksParent.getColumnFamilyData().get(index);
		}
		
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent == ROOT) {
			return stressData.getKeySpaceData().size();
		} else if (parent instanceof KeySpaceData) {
			KeySpaceData ksParent = (KeySpaceData) parent;
			return ksParent.getColumnFamilyData().size();
		}
		
		return 0;
	}

	@Override
	public boolean isLeaf(Object node) {
		return (node != ROOT) && (!(node instanceof KeySpaceData)) && (!(node instanceof ColumnFamilyData));
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent == ROOT) {
		    return stressData.getKeySpaceData().indexOf(child);
		} else if (parent instanceof KeySpaceData) {
		    KeySpaceData ksParent = (KeySpaceData) parent;
		    return ksParent.getColumnFamilyData().indexOf(child);
		}
		
		return -1;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
	}
}
