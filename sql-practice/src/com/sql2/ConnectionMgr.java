package com.sql2;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionMgr {
    /* jdbc:mariadb://hostname:port/databaseName?user=username&password=password


        3306

     */

    private static ConnectionMgr connectionMgr;
    private static Connection connection;

    // private constructor makes "singleton"
    private ConnectionMgr() {

    }
    public static Connection getConnection() {
        if (connection == null) {
            connection = connect();
        }
        return connection;
    }

    private static Connection connect() {
        // connection logic here

        /* jdbc:mariadb://hostname:port/databaseName?user=username&password=password


        3306

     */
        try {
            Properties props = new Properties();
            FileReader fr = new FileReader("src/main/resources/jdbc.properties");
            props.load(fr);
//jdbc:mariadb://hostname:port/databaseName?user=username&password=password
            String connectionString = "jdbc:mariadb://" +
                    props.getProperty("hostname") + ":" +
                    props.getProperty("port") + "/" +
                    props.getProperty("dbname") + "?user=" +
                    props.getProperty("username") + "&password=" +
                    props.getProperty("password");

            //Class.forName("org.mariadb.jdbc.Driver");

            connection = DriverManager.getConnection(connectionString);

            System.out.println(connectionString);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


        return connection;


    }

}
