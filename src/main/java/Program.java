import java.sql.*;

public class Program
{
    private static final String URL = "jdbc:mysql://localhost:3306/export_mybase";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connection _con;
    private static Statement _stmt;
    private static ResultSet _rs;

    public static void main(String [ ] args)
    {
        String query = "select count(*) from amper_50";

        try
        {
            _con = DriverManager.getConnection(URL, USER, PASSWORD);
            _stmt = _con.createStatement();
            _rs = _stmt.executeQuery(query);

            while(_rs.next())
            {
                int count = _rs.getInt(1);
                System.out.println("Total number of amper in the table : " + count);
            }
        }
        catch(SQLException sqlEx)
        {
            sqlEx.printStackTrace();
        }
        finally
        {
            try { _con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { _stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { _rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }
}