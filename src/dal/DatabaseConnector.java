package dal;

import com.microsoft.sqlserver.jdbc.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnector {


    private SQLServerDataSource dataSource;

    public DatabaseConnector() throws IOException
    {
        Properties props = new Properties();
        props.load(new FileReader("src/dal/DataAccess.txt"));
        dataSource = new SQLServerDataSource();
        dataSource.setDatabaseName(props.getProperty("database"));
        dataSource.setUser(props.getProperty("username"));
        dataSource.setPassword(props.getProperty("pw"));
        dataSource.setServerName(props.getProperty("server"));

    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }


    public static void main(String[] args)  throws Exception{


    }

}
