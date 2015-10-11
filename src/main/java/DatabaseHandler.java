import java.sql.*;

public class DatabaseHandler
{
    private String _url;
    private String _user;
    private String _password;

    private Connection _con;
    private Statement _stmt;
    private ResultSet _rs;

    public DatabaseHandler(String url, String user, String password)
    {
        _url = url;
        _user = user;
        _password = password;
    }

    public void ExecuteQuery(String query)
    {
        try
        {
            _con = DriverManager.getConnection(_url, _user, _password);
            _stmt = _con.createStatement();
            _rs = _stmt.executeQuery(query);

            while (_rs.next())
            {
                System.out.println(_rs.getInt(1));
            }
        }
        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
        }
        finally
        {
            try { _con.close(); } catch(SQLException se) { }
            try { _stmt.close(); } catch(SQLException se) { }
            try { _rs.close(); } catch(SQLException se) { }
        }
    }
}