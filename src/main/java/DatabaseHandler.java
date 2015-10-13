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

    private static final int DATE_COLUMN  = 1;
    private static final int MSEC_COLUMN  = 2;
    private static final int VALUE_COLUMN = 3;

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

            while(_rs.next())
            {
                try
                {
                    long dateInMs = _rs.getDate(DATE_COLUMN).getTime();
                    long timeInMs = _rs.getTime(DATE_COLUMN).getTime();
                    long ms = _rs.getInt(MSEC_COLUMN);

                    long time = dateInMs + timeInMs + ms;
                    float value = _rs.getFloat(VALUE_COLUMN);

                    Point point = new Point(time, value);

                    pointList.add(point);
                }
                catch(NullPointerException nullEx)
                {
                    nullEx.printStackTrace();
                }
            }
        }
        catch(SQLException sqlEx) { sqlEx.printStackTrace(); }
        finally
        {
            try { _con.close();  } catch(SQLException se) { }
            try { _stmt.close(); } catch(SQLException se) { }
            try { _rs.close();   } catch(SQLException se) { }
        }

        return pointList;
    }
}