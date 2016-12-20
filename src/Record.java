import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by vadim on 20/12/16.
 */
public class Record {
    protected String institute;
    protected String startTime;
    protected String weekDay;
    protected String name;
    protected String group;
    protected String type;
    protected String tabOffset;
    protected List<String> teachers = new ArrayList<>();


    @Override
    public String toString() {
        return "Record{" +
                "institute='" + institute + '\'' +
                ", startTime='" + startTime + '\'' +
                ", weekDay='" + weekDay + '\'' +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", teachers=" + teachers +
                '}';
    }
}
