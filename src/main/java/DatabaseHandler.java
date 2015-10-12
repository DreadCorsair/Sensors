import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<Point> ExecuteQuery(String query)
    {
        ArrayList<Point> pointList = new ArrayList<Point>();

        try
        {
            _con = DriverManager.getConnection(_url, _user, _password);
            _stmt = _con.createStatement();
            _rs = _stmt.executeQuery(query);

            while (_rs.next())
            {
                try
                {
                    long t = _rs.getDate(1).getTime() + _rs.getTime(1).getTime() + _rs.getInt(2);
                    double v = _rs.getDouble(3);

                    Point point = new Point();
                    point.time = t;
                    point.value = v;

                    pointList.add(point);
                }
                catch (NullPointerException nullEx)
                {
                    nullEx.printStackTrace();
                }
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

        return pointList;
    }
}