package by.teachmeskills.jdbc_connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnection {

    private Connection connection;

    public Connection getConnection(){
        Properties props = new Properties();

        Connection conn = null;
        try(InputStream in = JdbcConnection.class.getClassLoader().getResourceAsStream("database.properties")){
            props.load(in);
            conn= DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("user"),
                    props.getProperty("password")
            );
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
