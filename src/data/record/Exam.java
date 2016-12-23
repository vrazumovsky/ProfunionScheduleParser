package data.record;

import data.Teacher;
import data.raw.TableString;

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
            String[] teacherAndCabinet = Record.splitTeacherAndCabinet(tableString.getString());
            Teacher teacher = new Teacher(teacherAndCabinet[0], teacherAndCabinet[1]);
            teachers.add(teacher);
        }
    }

    private List<TableString> filter(List<TableString> rawData) {
        rawData.remove(4); //must have hardcode :)
        return rawData;
    }

    public void printQuery() {

        String teacherNames = "";
        String cabinets = "";
        for (Teacher teacher : teachers) {
            teacherNames += teacher.getName() + "\n";
            cabinets += teacher.getCabinet() + "\n";
        }

        teacherNames = teacherNames.substring(0, teacherNames.length() - 1);
        cabinets = cabinets.substring(0, cabinets.length() - 1);

        System.out.println("INSERT INTO `DATABASE_TABLE_EXAMS` (INSTITUTE_COLUMN, GROUP_COLUMN, START_TIME_COLUMN, " +
                "DATE_COLUMN, EXAM_TYPE_COLUMN, EXAM_NAME_COLUMN, TEACHER_COLUMN, CABINET_COLUMN, DAY_OF_WEEK_COLUMN) " +
                "VALUES ('" + institute + "', '" + group + "', '" + startTime + "', '" + date + "', 'экзамен', '" +
                name + "', '" + teacherNames + "', '" + cabinets + "', '" + weekDay + "');\n");
    }


    @Override
    public String toString() {
        return "Exam{" +
                super.toString() +
                "date='" + date + '\'' +
                '}';
    }
}
