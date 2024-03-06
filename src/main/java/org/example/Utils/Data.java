package org.example.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Data {


    private static String url="jdbc:mysql://localhost:3306/yummypidev";
    private static String login="root";
    private static String pawd="";
    private static Connection cnx;
    private static Data instance;
    private Data(){
        try {
            cnx = DriverManager.getConnection(url, login, pawd);
            System.out.println("succes");
        }catch  (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static Connection getCnx() {
        return cnx;
    }

    //utilise pour nconnecty mara akhw
    public static Data getInstance(){
        if (instance==null)
            instance=new Data();
        return instance;
    }
}
