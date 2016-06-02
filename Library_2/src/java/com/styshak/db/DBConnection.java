/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.styshak.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Sergey
 */
public class DBConnection {

    private static DataSource ds;
    
    static {
        try {
            ds = (DataSource) new InitialContext().lookup("jdbc/Library");
        }
        catch (NamingException e) { 
            System.out.println("'jdbc/Library' not found in JNDI");
        }
    }

    private DBConnection() {

    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
