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
package com.mebigfatguy.clytemnestra.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Pair;
import com.mebigfatguy.clytemnestra.cassandra.ReplicationStrategy;

public class StrategicOptionsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 5169032436825165909L;

	private enum Columns {
		Key, Value
	};

	private List<Pair<String, String>> options = new ArrayList<Pair<String, String>>();

	public StrategicOptionsTableModel(String selectedStrategy) {
		ReplicationStrategy strategy = ReplicationStrategy.valueOf(ReplicationStrategy.class, selectedStrategy);
		if (strategy != null) {
			Pair<String, String>[] strategyOptions = strategy.getRequiredOptions();
			for (Pair<String, String> option : strategyOptions) {
				options.add(option);
			}
		}
	}
	
	public Map<String, String> getStrategicOptions() {
		Map<String, String> stratOptions = new HashMap<String, String>();
		for (Pair<String, String> pair : options) {
			stratOptions.put(pair.getKey(), pair.getValue());
		}
		return stratOptions;
	}
	
	@Override
	public int getRowCount() {
		return options.size();
	}

	@Override
	public int getColumnCount() {
		return Columns.values().length;
	}

	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Pair<String, String> option = options.get(rowIndex);
		option.setValue((String) value);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return Columns.values()[columnIndex] == Columns.Value;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Pair<String, String> pair = options.get(rowIndex);
		switch (Columns.values()[columnIndex]) {
			case Key:
				return pair.getKey();

			case Value:
				return pair.getValue();
				
			default:
				return "";
		}
	}
	
    @Override
    public String getColumnName(int column) {
        switch (Columns.values()[column]) {
            case Key:
                return Bundle.getString(Bundle.Key.Option);

            case Value:
            	return Bundle.getString(Bundle.Key.Value);
            	
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
    	return String.class;
    }


}
