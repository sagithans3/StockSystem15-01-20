package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class ConnectDB
{
    public static Connection connect()
    {
        Connection connection = null ;
        try
        {
            // create a database connection
            connection = DriverManager. getConnection ( "jdbc:sqlite:stockManagement.db" );
            return connection;
        }
        catch (SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System. err .println(e.getMessage());

        }
        return null;
    }
}

