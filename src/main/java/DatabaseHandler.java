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
    private final int NUMBER_OF_VALUES_IN_TABLE = 36;

    public DatabaseHandler(String url, String user, String password)
    {
        _url = url;
        _user = user;
        _password = password;
    }

    public ArrayList<Point> GetAkhz1()
    {
        final int firstTable = 50;
        final int lastTable = 61;

        ArrayList<Point> akhz1 = new ArrayList<Point>();
        GeneratePointList("akhz1_data", firstTable, lastTable, akhz1);

        akhz1.sort(new TimeComparator());

        return akhz1;
    }

    private void GeneratePointList(String tableNamePrefix, int startTablePostfix, int finishTablePostfix, ArrayList<Point> toList)
    {
        for(int i = startTablePostfix; i <= finishTablePostfix; i++)
        {
            for(int j = 1; j <= NUMBER_OF_VALUES_IN_TABLE; j++)
            {
                String query = String.format("SELECT Sample_TDate_%d, Sample_MSec_%d, Sample_Value_%d " +
                        "FROM %s_%d WHERE Signal_Index=1", j, j, j, tableNamePrefix, i);

                toList.addAll(GetPointsFromTable(query));
            }
        }
    }

    private ArrayList<Point> GetPointsFromTable(String query)
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