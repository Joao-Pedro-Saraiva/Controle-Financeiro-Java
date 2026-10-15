package com.saraiva.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private String url = "jdbc:mysql://localhost:3306/financeiro";
    private String user = "root";
    private String password = "";
    public Connection getConnect(){
        try{
            return DriverManager.getConnection(url, user, password);
        } catch(SQLException e){
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
}