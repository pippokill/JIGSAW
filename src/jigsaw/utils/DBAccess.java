/**
   Copyright (c) 2012, the JIGSAW AUTHORS.

   All rights reserved.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions are
   met:

 * Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above
   copyright notice, this list of conditions and the following
   disclaimer in the documentation and/or other materials provided
   with the distribution.

 * Neither the name of the University of Pittsburgh nor the names
   of its contributors may be used to endorse or promote products
   derived from this software without specific prior written
   permission.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
   A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
   PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
   NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 **/
package jigsaw.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pierpaolo
 */
public class DBAccess {

    private Properties connectionProperties;
    private Connection connection;

    public DBAccess(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    /**
     * Connect to database
     *
     * @throws MetaException Exception
     * @return Connection object
     */
    public void connect() throws Exception {
        String driver = connectionProperties.getProperty("mwn.driver");
        Class.forName(driver).newInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(connectionProperties.getProperty("mwn.url"));
        sb.append(connectionProperties.getProperty("mwn.address"));
        sb.append(":");
        sb.append(connectionProperties.getProperty("mwn.port"));
        sb.append("/");
        sb.append(connectionProperties.getProperty("mwn.schema"));
        Properties connProp = new Properties();
        connProp.put("user", connectionProperties.getProperty("mwn.user"));
        connProp.put("password", connectionProperties.getProperty("mwn.password"));
        if (connectionProperties.containsKey("mwn.characterEncoding")) {
            connProp.put("characterEncoding", connectionProperties.getProperty("mwn.characterEncoding"));
        }
        connection = DriverManager.getConnection(sb.toString(), connProp);
        Logger.getLogger(DBAccess.class.getName()).log(Level.INFO, "Connected to {0}", driver);
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }
}
