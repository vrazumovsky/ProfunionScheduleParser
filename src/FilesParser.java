import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class FilesParser implements ScheduleParser {

    private File directory;
    private List<Record> recordsFromAllFiles = new ArrayList<>();

    public FilesParser(String directoryPath) {
        directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Directory doesn't exist: " + directoryPath);
        }
    }

    @Override
    public List<Record> parse() {
        for (File file : directory.listFiles()) {
            ScheduleParser parser = new RtfFileParser(file);
            recordsFromAllFiles.addAll(parser.parse());
        }
        return recordsFromAllFiles;
    }
}
