import data.record.Record;
import parser.FilesParser;
import parser.ScheduleParser;

import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class Main {

    private static final String FILES_DIRECTORY = "/Users/vadim/Desktop/exams";

    public static void main(String[] args) throws Exception {
        ScheduleParser parser = new FilesParser(FILES_DIRECTORY);

        List<Record> allRecords = parser.parse();

        for (Record record : allRecords) {
            record.printQuery();
        }
    }
}
