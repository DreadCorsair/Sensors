import java.util.ArrayList;

public class Program
{
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/export_mybase";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    public static void main(String [ ] args)
    {
        DatabaseHandler database = new DatabaseHandler(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
//        ArrayList<Point> pl = database.ExecuteQuery("SELECT Sample_TDate_1, Sample_MSec_1, Sample_Value_1 FROM akhz1_data_50 WHERE Signal_Index=1");//("select count(*) from amper_50");

        ArrayList<Point> result = new ArrayList<Point>();

        for(int i = 50; i < 62; i++)
        {
            for(int j = 1; j < 37; j++)
            {
                StringBuilder builder = new StringBuilder();
                builder.append("SELECT Sample_TDate_");
                builder.append(j);
                builder.append(", Sample_MSec_");
                builder.append(j);
                builder.append(", Sample_Value_");
                builder.append(j);
                builder.append(" FROM akhz1_data_");
                builder.append(i);
                builder.append(" WHERE Signal_Index=1");

                String query = builder.toString();
                System.out.println(query);

                ArrayList<Point> pl = database.ExecuteQuery(query);

                result.addAll(pl);
            }
        }

        for(int i = 0; i < result.size(); i++)
        {
            System.out.println(i + " " + result.get(i).time + " " + result.get(i).value);
        }
    }
}