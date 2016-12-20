import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by vadim on 20/12/16.
 */
public abstract class Record {
    protected String institute;
    protected String startTime;
    protected String weekDay;
    protected String name;
    protected String group;
    protected String type;
    protected int tabOffset;
    protected List<Teacher> teachers = new ArrayList<>();


    @Override
    public String toString() {
        return "Record{" +
                "institute='" + institute + '\'' +
                ", startTime='" + startTime + '\'' +
                ", weekDay='" + weekDay + '\'' +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", teachers=" + teachers +
                ", taboffset=" + tabOffset +
                '}';
    }

    protected static String[] splitTeacherAndCabinet(String teacherAndCabinet) {
        String[] words = teacherAndCabinet.split(" ");
        Pattern pattern = Pattern.compile("[0-9]*[/]([0-9]+|чит.з|акт.з)");
        String teacher = "";
        String cabinet = "";

        for (int i = 0; i < words.length; i++) {
            if (pattern.matcher(words[i]).matches()) {
                cabinet = words[i];
                for (int j = 0; j < i; j++) {
                    teacher += words[j] + " ";
                }
                break;
            }
        }
        return new String[] {teacher.trim(), cabinet};
    }

    public abstract void printQuery();
}
