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
package com.mebigfatguy.clytemnestra;

import java.util.ResourceBundle;

public class Bundle {
    public enum Key {
        Title("title"),
        OK("ok"),
        Cancel("cancel"),
        Servers("servers"),
        ConnectToServer("connecttoserver"),
        Server("server"),
        Port("port"),
        UserName("username"),
        Password("password"),
        Connect("connect"),
        Disconnect("disconnect"),
        KeySpace("keyspace"),
        ReplicationFactor("replicationfactor"),
        StrategyClass("strategyclass");

        private String id;

        Key(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }
    };

    private static ResourceBundle bundle = ResourceBundle.getBundle("com/mebigfatguy/clytemnestra/resources/resource");

    private Bundle() {
    }

    public static String getString(Key key) {
        return bundle.getString(key.id());
    }
}
