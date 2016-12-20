import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public abstract class Record {
    private String institute;
    private String startTime;
    private String weekDay;
    private String name;
    private String group;
    private String type;
    private List<String> teachers = new ArrayList<>();
    private List<String> rawData;

    public Record(List<String> rawData) {
        this.rawData = rawData;
    }
}
