package data.record;

import data.Teacher;
import data.raw.TableString;
import filter.TeacherPositionFilter;

import java.io.*;
import java.util.List;

/**
 * Created by vadim on 27/01/17.
 */
public class Lesson extends Record {

    enum EvenOdd {
        EVEN("чет", 2), ODD("неч", 1), ALL("all", 0);

        private String name;
        private int value;

        EvenOdd(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        static EvenOdd createValue(String string) {
            if (string.equalsIgnoreCase(ODD.getName())) {
                return ODD;
            } else if (string.equalsIgnoreCase(EVEN.getName())) {
                return EVEN;
            } else if (string.equalsIgnoreCase(ALL.getName())) {
                return ALL;
            } else {
                throw new IllegalArgumentException();
            }
        }

    }

    public static final String DIRECTORY = "/Users/vadim/Desktop/exams/1.txt";

    private EvenOdd evenOdd;

    public static Lesson createLesson(List<TableString> rawData, String institute) {
        Lesson lesson = new Lesson();

        lesson.institute = institute;
        lesson.weekDay = rawData.get(0).getString();
        lesson.startTime = rawData.get(1).getString();
        lesson.evenOdd = EvenOdd.createValue(rawData.get(2).getString());
        lesson.name = rawData.get(3).getString();
        lesson.type = rawData.get(4).getString();;
        lesson.tabOffset = rawData.get(5).getTabOffset();

        List<TableString> teachersAndCabinets = new TeacherPositionFilter().filter(rawData.subList(6, rawData.size()));

        for (TableString tableString : teachersAndCabinets) {
            String[] teacherAndCabinet = Record.splitTeacherAndCabinet(tableString.getString());
            Teacher teacher = new Teacher(teacherAndCabinet[0], teacherAndCabinet[1]);
            lesson.teachers.add(teacher);
        }

        return lesson;
    }


    @Override
    public void printQuery() throws IOException {
        String teacherNames = "";
        String cabinets = "";
        for (Teacher teacher : teachers) {
            teacherNames += teacher.getName() + "\n";
            cabinets += teacher.getCabinet() + "\n";
        }

        if (teachers.size() > 0) {
            teacherNames = teacherNames.substring(0, teacherNames.length() - 1);
            cabinets = cabinets.substring(0, cabinets.length() - 1);
        }

        FileWriter fw = new FileWriter(DIRECTORY, true);
        BufferedWriter bw = new BufferedWriter(fw);


        PrintWriter out = new PrintWriter(bw);


        out.println("INSERT INTO `DATABASE_TABLE_SCHEDULE` (INSTITUTE_COLUMN, GROUP_COLUMN, START_TIME_COLUMN, " +
                "LESSON_TYPE_COLUMN, LESSON_NAME_COLUMN, TEACHER_COLUMN, CABINET_COLUMN, DAY_OF_WEEK_COLUMN, EVEN_ODD_COLUMN) " +
                "VALUES ('" + institute + "', '" + group + "', '" + startTime + "', '" + type + "', '" +
                name + "', '" + teacherNames + "', '" + cabinets + "', '" + weekDay + "','" + evenOdd.getValue() + "');\n");
        out.flush();
        out.close();
    }
}
