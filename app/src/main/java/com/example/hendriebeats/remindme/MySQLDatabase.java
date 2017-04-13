package com.example.hendriebeats.remindme;

/**
 * Created by Peter on 4/13/2017.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
/**
 * A class used to create a connection to a MySQL database and pass through SQL statements
 * in order to read and update data in the database.
 *
 * This class also provides failsafes against errors in programs accessing the database
 * by providing functionality for transactions and rollbacks.
 *
 * As an exercise this class should be expanded to accept query strings containing user
 * input without allowing the user to maliciously alter data or accidentally delete data
 * due to malformed variables in the query strings (SQL injection attacks).
 *
 * @author Mike Carpenter
 */
public class MySQLDatabase
{
    // Constants
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    // Attributes for connection parameters
    private String     username;
    private String     password;
    private String     uri;
    private Connection conn;

    /**
     * Constructs a new instance of the MySQLDatabase class in order to provide
     * connectivity to the specified database with the provided credentials.
     *
     * @param username   The username authorized to connect to the database.
     * @param password   The password used to authenticate with the specified username.
     * @param uri        The connection string indicating the location of the database
     *                   with the format "jdbc:mysql://[HOST]/[DATABASE]"
     */
    public MySQLDatabase(String username, String password, String uri)
    {
        // Set connection parameters
        this.username = username;
        this.password = password;
        this.uri      = uri;
    }

    /**
     * Initializes the connection to the MySQL database as specified at construction.
     * @return true if connection successful, false if failure.
     */
    public boolean connect()
    {
        try
        {
            Class.forName(DRIVER);	// Load the driver.

            // Attempt to connect
            conn = DriverManager.getConnection(uri, username, password);

            // Return true if connection success
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace(); //remove later
            // Return false if connection failure (i.e. Exception thrown at conn creation)
            return false;
        }
    }

    /**
     * Disconnects from the database and terminates the connection.
     * If connection is not open, does nothing.
     */
    public void disconnect()
    {
        try
        {
            if(isOpen()) conn.close();
            conn = null;
        }
        catch(Exception e)
        {
            return;
        }
    }

    /**
     * Checks to see whether there is an active connection open to the database.
     * @return true if an active connection exists, false if not.
     */
    public boolean isOpen()
    {
        if(conn == null) return false;

        try
        {
            // Query the Connection object to check if it is closed, return the opposite
            // result as returned from the method.
            return !conn.isClosed();
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * Executes an SQL query on the database to which this object is connected and returns
     * the data in a format useful to Java applications.
     *
     * @param  sql The SQL query to be executed. Should be a SELECT statement.
     * @return A two-dimensional array of Strings representing the tabular data
     *         contained in the ResultSet returned from the query execution. The first
     *         dimension of the array contains full rows, with index 0 containing the
     *         column names. The second dimension represents individual cells within
     *         a row. Will return null if an error occurs.
     * @throws SQLException
     */
    public String[][] getData(String sql, String... strs) throws SQLException	{

        // Declaring the objects needed to run the statement before the beginning of
        // the try block in case they need to be accessed outside the block.
        Statement stmnt = prepare(sql, strs);
        ResultSet rs;

        try
        {
            //Initialize everything
            stmnt = conn.createStatement();
            PreparedStatement ps = prepare(sql, strs[0]);
            rs = ps.executeQuery();
            return formatResults(rs);

        }
        catch(SQLException sqle)
        {
            // Returns null if error occurs so no invalid or incomplete data is passed to
            // an upper layer. Classes calling this method must be prepared to handle a
            // null object in the event of error.
            return null;
        }
    }

    /**
     * Changes data in the database. Used to pass UPDATE, DELETE, or ALTER statements.
     * @param  sql The statement to be executed
     * @return true if the statement succeeds, false if not.
     */
    public boolean setData(String sql)
    {
        Statement stmnt;

        try
        {
            stmnt = conn.createStatement();
            return stmnt.execute(sql);
        }
        catch(Exception sqle)
        {
            System.out.println(sqle.getMessage());
            return false;
        }
    }

    /**
     * Begins a transaction for the purpose of executing multiple statements as part of
     * a single task. In the event of a failure of one or more statements in the task,
     * this method allows them to be undone with no harm to the database.
     *
     * @return true if transaction successfully started.
     */
    public boolean startTrans()
    {
        try
        {
            // Changes to the database will not automatically commit.
            conn.setAutoCommit(false);
            return true;
        }
        catch(SQLException sqle)
        {
            return false;
        }
    }

    /**
     * Ends the transaction started in the startTrans() method. Will commit all changes
     * since the transaction began.
     *
     * @return true if transaction successfully ended and changes committed.
     */
    public boolean endTrans()
    {
        try
        {
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        }
        catch(SQLException sqle)
        {
            return false;
        }
    }

    /**
     * Ends the transaction started in the startTrans() method and will undo all changes
     * since the transaction began.
     *
     * @return true if transaction successfully ended and changes reversed.
     */
    public boolean rollbackTrans()
    {
        try
        {
            conn.rollback();
            conn.setAutoCommit(true);
            return true;
        }
        catch(SQLException sqle)
        {
            return false;
        }
    }

    /**
     * Formats the tabular data in a ResultSet into a two-dimensional ArrayList of Strings.
     * @param rs The ResultSet containing the data to be formatted.
     * @return A two-dimensional array of Strings representing the tabular data
     *         contained in the ResultSet returned from the query execution. The first
     *         dimension of the array contains full rows, with index 0 containing the
     *         column names. The second dimension represents individual cells within
     *         a row. Will return null if an error occurs.
     */
    private String[][] formatResults(ResultSet rs)
    {
        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();

            ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
            ArrayList<String> colNms = new ArrayList<String>();

            // Get column names
            for(int i=1; i<rsmd.getColumnCount()+1; i++)
            {
                colNms.add(rsmd.getColumnName(i));
            }

            rows.add(colNms);	        // Add in the column names at index 0 of the ArrayList.

            if(!rs.next()) return null;	// If ResultSet empty, nothing to return

            do
            {
                // Prepare the current row to be added.
                ArrayList<String> cols = new ArrayList<String>();

                for(int i=1; i<=rsmd.getColumnCount(); i++)
                {
                    cols.add(rs.getString(i));	// Populate the row with values.
                }

                rows.add(cols);
            }
            while(rs.next());	// Continue until there is nothing left in the ResultSet.

            rs.close();		    // We are finished with the ResultSet, close it up.

            // Convert 2D ArrayList into standard 2D array
            String[][] result = new String[rows.size()][];
            for(int i=0; i<rows.size(); i++)
            {
                ArrayList<String> currentRow = rows.get(i);
                result[i] = currentRow.toArray(new String[currentRow.size()]);
            }

            return result;	    // Results are all compiled, return it.
        }
        catch(SQLException sqle)
        {
            return null;
        }
    }

    private PreparedStatement prepare(String sql, String... strs) throws SQLException{//"String strs" was "String... strs"

        String input = strs[0];

        PreparedStatement updateBooks = (PreparedStatement) conn.prepareStatement(sql);
        //updateBooks.setString(1, input);
        updateBooks.setDouble(1, Double.parseDouble(strs[0]));
        return updateBooks;
    }
	/*
	private String[][] getData(String sql, String... strs){
		return null;
	}//*/
}