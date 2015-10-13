import java.util.ArrayList;

public class Program
{
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/export_mybase";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    public static void main(String [ ] args)
    {
        DatabaseHandler database = new DatabaseHandler(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
//        "SELECT Sample_TDate_1, Sample_MSec_1, Sample_Value_1 FROM akhz1_data_50 WHERE Signal_Index=1"

        ArrayList<Point> result = new ArrayList<Point>();

        for(int i = 50; i < 51; i++)
        {
            for(int j = 1; j < 2; j++)
            {
                String query = String.format("SELECT Sample_TDate_%d, Sample_MSec_%d, Sample_Value_%d " +
                        "FROM akhz1_data_%d WHERE Signal_Index=1", j, j, j, i);

                System.out.println(query);

                ArrayList<Point> pl = database.ExecuteQuery(query);

                result.addAll(pl);
            }
        }

        System.out.println("SORTING...");

        result.sort(new TimeComparator());

        System.out.println("SORTED!!!!");

        for(int i = 0; i < result.size(); i++)
        {
            System.out.println(i + " " + result.get(i).toString());
        }
    }
}