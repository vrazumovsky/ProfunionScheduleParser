package data.record;

import data.raw.TableString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by vadim on 20/12/16.
 */
public class Records {

    private static final Pattern DATE_PATTERN = Pattern.compile("[0-9]+[-][а-яА-Я]+[-][0-9]+");
    private static final String[] weekDays = new String[]{"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
    private List<TableString> rawData;

    private List<Record> records = new ArrayList<>();
    private String institute;
    private List<String> groups = new ArrayList<>();
    private int recordsStartIndex = 0;
    private List<Integer> offsets = new ArrayList<>();


    public Records(List<TableString> rawData) {
        this.rawData = rawData;
    }

    public List<Record> createExamRecords() {
        extractInstitute();
        extractGroupsExams();
        extractExamRecords();
        extractSortedTabOffsets();
        setGroupToRecords();

        return records;
    }

    public List<Record> createLessonRecords() {
        extractInstitute();
        extractGroupsLessons();
        extractLessonRecords();
        extractSortedTabOffsets();
        setGroupToRecords();

        return records;
    }



    private void setGroupToRecords() {
        for (Record record : records) {
            int groupIndex = offsets.indexOf(record.tabOffset);
            record.group = groups.get(groupIndex);
        }
    }

    private void extractSortedTabOffsets() {
        for (Record record : records) {
            if (!offsets.contains(record.tabOffset)) {
                offsets.add(record.tabOffset);
            }
        }

        Collections.sort(offsets);
    }

    private void extractExamRecords() {
        int firstIndex = recordsStartIndex;
        for (int i = recordsStartIndex + 1; i < rawData.size(); i++) {
            if (stringWithIndexMatches(i) || i == rawData.size() - 1) {
                SameStartTimeRecords sameStartTimeRecords = new SameStartTimeRecords(
                        institute, rawData.subList(firstIndex, i == rawData.size() - 1 ? rawData.size() : i));
                records.addAll(sameStartTimeRecords.createExamRecords());
                firstIndex = i;
            }
        }
    }

    private void extractLessonRecords() {
        int firstIndex = recordsStartIndex;
        for (int i = recordsStartIndex + 1; i < rawData.size(); i++) {
            if (isWeekDay(rawData.get(i).getString()) || i == rawData.size() - 1) {
                SameStartTimeRecords sameStartTimeRecords = new SameStartTimeRecords(
                        institute, rawData.subList(firstIndex, i == rawData.size() - 1 ? rawData.size() : i));
                records.addAll(sameStartTimeRecords.createLessonRecords());
                firstIndex = i;
            }
        }
    }

    private void extractGroupsExams() {
        for (int i = 1; i < rawData.size(); i++) {
            if (stringWithIndexMatches(i)) {
                recordsStartIndex = i;
                break;
            } else {
                groups.add(rawData.get(i).getString());
            }
        }
    }

    private void extractGroupsLessons() {
        for (int i = 1; i < rawData.size(); i++) {
            if (isWeekDay(rawData.get(i).getString())) {
                recordsStartIndex = i;
                break;
            } else {
                groups.add(rawData.get(i).getString());
            }
        }
    }

    private void extractInstitute() {
        institute = rawData.get(0).getString();
    }


    private boolean stringWithIndexMatches(int i) {
        return DATE_PATTERN.matcher(rawData.get(i).getString()).matches();
    }

    private boolean isWeekDay(String s) {
        for (String weekDay : weekDays) {
            if (s.equalsIgnoreCase(weekDay)) {
                return true;
            }
        }
        return false;
    }
}
