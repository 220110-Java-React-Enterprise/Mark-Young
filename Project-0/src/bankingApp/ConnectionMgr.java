package bankingApp;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

// establishes connection to AWS database through MariaDB
// uses my credentials given in a .properties file
// all shown by Kyle so I don't feel the need to document too much
public class ConnectionMgr {
    private static ConnectionMgr connectionMgr;
    private static Connection connection;
    ViewManager viewManager = ViewManager.getViewManager();
    Scanner sc;

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
        Boolean connectionSuccessful = false;
        while (!connectionSuccessful) {
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

                // System.out.println(connectionString);
                connectionSuccessful = true;

            } catch (IOException | SQLException e) {
                return null;

//                System.out.println("We are having difficulties connecting to the database. \n");
//                System.out.println("would you like to retry connection (yes/no)? ");
//
//                System.out.println("Due to these issues, we will shut down.\n" +
//                        "You may retry again later.");
            }
        }
        return connection;
    }


}
