import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class Exam extends Record {

    String date;

    public Exam(List<TableString> rawData, String institute) {
        this.institute = institute;
        rawData = filter(rawData);
        date = rawData.get(0).getString();
        weekDay = rawData.get(1).getString();
        startTime = rawData.get(2).getString();
        name = rawData.get(3).getString();
        type = "экзамен";
        tabOffset = rawData.get(3).getTabOffset();

        for (TableString tableString : rawData.subList(4, rawData.size())) {
            String[] teacherAndCabinet = splitTeacherAndCabinet(tableString.getString());
            Teacher teacher = new Teacher(teacherAndCabinet[0], teacherAndCabinet[1]);
            teachers.add(teacher);
        }
    }

    private List<TableString> filter(List<TableString> rawData) {
        rawData.remove(4); //must have hardcode :)
        return rawData;
    }


    @Override
    public String toString() {
        return "Exam{" +
                super.toString() +
                "date='" + date + '\'' +
                '}';
    }
}
