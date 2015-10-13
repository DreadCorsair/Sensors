import java.util.ArrayList;

public class Program
{
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/export_mybase";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    public static void main(String [ ] args)
    {
        DatabaseHandler database = new DatabaseHandler(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        ArrayList<Point> akhz1 = database.GetAkhz1();

        for(int i = 0; i < akhz1.size(); i++)
        {
            System.out.println(i + " " + akhz1.get(i).toString());
        }
    }
}