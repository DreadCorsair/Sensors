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
    private static final int NUMBER_OF_VALUES_IN_TABLE = 36;

    public DatabaseHandler(String url, String user, String password)
    {
        _url = url;
        _user = user;
        _password = password;
    }

    public ArrayList<Point> GetAkhz1()
    {
        final int firstTablePostfix = 50;
        final int lastTablePostfix = 61;

        ArrayList<Point> akhz1 = new ArrayList<Point>();
        FillTable(akhz1, "akhz1_data", firstTablePostfix, lastTablePostfix);

        akhz1.sort(new TimeComparator());

        return akhz1;
    }

    private void FillTable(ArrayList<Point> toList, String tableNamePrefix, int firstTablePostfix, int lastTablePostfix)
    {
        for(int currentTablePostfix = firstTablePostfix; currentTablePostfix <= lastTablePostfix; currentTablePostfix++)
        {
            for(int currentNumberOfValues = 1; currentNumberOfValues <= NUMBER_OF_VALUES_IN_TABLE; currentNumberOfValues++)
            {
                String query = String.format("SELECT Sample_TDate_%d, Sample_MSec_%d, Sample_Value_%d FROM %s_%d WHERE Signal_Index=1",
                        currentNumberOfValues, currentNumberOfValues, currentNumberOfValues, tableNamePrefix, currentTablePostfix);

                CopyPointsFromTable(query, toList);
            }
        }
    }

    private void CopyPointsFromTable(String query, ArrayList<Point> toList)
    {
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

                    toList.add(new Point(time, value));
                }
                catch(NullPointerException nullEx) { nullEx.printStackTrace(); }
            }
        }
        catch(SQLException sqlEx) { sqlEx.printStackTrace(); }
        finally
        {
            try { _con.close();  } catch(SQLException se) { }
            try { _stmt.close(); } catch(SQLException se) { }
            try { _rs.close();   } catch(SQLException se) { }
        }
    }
}