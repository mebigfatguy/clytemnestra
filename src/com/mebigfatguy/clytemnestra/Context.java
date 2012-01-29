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

import java.util.List;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.KsDef;
import org.apache.thrift.transport.TTransport;


public interface Context {

    void setServerAddress(String serverAddress);

    String getServerAddress();
    
    void setTransport(TTransport transport);

    void setClient(Cassandra.Client client);

    Cassandra.Client getClient();

    void setSelectedKeySpaces(List<KsDef> keySpaces);
    
    List<KsDef> getSelectedKeySpaces();
    
    void refreshKeySpaces();
    
    void setStressTestData(StressTestData data);
    
    StressTestData getStressTestData();
}
