package by.fpmibsu.PCBuilder.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ConnectionCreator {
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;
    static{
        try {
            properties.load(new FileReader(ConnectionCreator.class.getResource("/").getPath() + "../database.properties"));
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        DATABASE_URL = "jdbc:mysql://localhost:3306/pcbuilder";
    }
    private ConnectionCreator() {}
    public static Connection createConnection() throws SQLException{
        return DriverManager.getConnection(DATABASE_URL, "root","root");
    }
}
