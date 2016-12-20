import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class Main {

    private static final String FILES_DIRECTORY = "/Users/vadim/Desktop/exams";

    public static void main(String[] args) {
        ScheduleParser parser = new FilesParser(FILES_DIRECTORY);

        List<Record> allRecords = parser.parse();

//        System.out.println(allRecords);

        for (Record record : allRecords) {
            record.printQuery();
        }
    }
}
