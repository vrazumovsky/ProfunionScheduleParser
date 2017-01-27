package data.record;

import data.Teacher;
import data.raw.TableString;

import java.util.List;

/**
 * Created by vadim on 27/01/17.
 */
public class Lesson extends Record {

    public static Lesson createLesson(List<TableString> rawData, String institute) {
        Lesson lesson = new Lesson();

        lesson.institute = institute;
        lesson.weekDay = rawData.get(0).getString();
        lesson.startTime = rawData.get(1).getString();
        lesson.name = rawData.get(2).getString();
        lesson.type = rawData.get(3).getString();;
        lesson.tabOffset = rawData.get(3).getTabOffset();

        for (TableString tableString : rawData.subList(5, rawData.size())) {
            String[] teacherAndCabinet = Record.splitTeacherAndCabinet(tableString.getString());
            Teacher teacher = new Teacher(teacherAndCabinet[0], teacherAndCabinet[1]);
            lesson.teachers.add(teacher);
        }

        return lesson;
    }


    @Override
    public void printQuery() {

    }
}
