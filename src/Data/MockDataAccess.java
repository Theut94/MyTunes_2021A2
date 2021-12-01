package Data;

import com.microsoft.sqlserver.jdbc.*;
//import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
//import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class MockDataAccess {


    private SQLServerDataSource dataSource;

    private MockDataAccess() throws IOException
    {
        Properties props = new Properties();
        props.load(new FileReader("Data/DataAcess.txt"));
        dataSource = new SQLServerDataSource();
        dataSource.setDatabaseName(props.getProperty("database"));
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("pw"));
        dataSource.setServerName(props.getProperty("server"));
    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }

    public static void main(String[] args)  throws IOException{
        MockDataAccess DA = new MockDataAccess();
        System.out.println("Hello World!");
    }

}
