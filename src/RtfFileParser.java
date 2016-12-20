import java.io.File;
import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class RtfFileParser implements ScheduleParser {

    private File file;

    public RtfFileParser(File file) {
        this.file = file;
    }


    @Override
    public List<Record> parse() {
        //// TODO: 20/12/16
        return null;
    }
}
