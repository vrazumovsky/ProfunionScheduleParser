import java.io.File;
import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class FilesParser implements ScheduleParser {

    private File directory;

    public FilesParser(String directoryPath) {
        directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Directory doesn't exist: " + directoryPath);
        }
    }

    @Override
    public List<Record> parse() {
        return null;
    }
}
