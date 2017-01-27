package data.record;


import data.raw.TableString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vadim on 20/12/16.
 */
public class SameStartTimeRecords {

    private String institute;
    private List<TableString> rawData;
    private final List<Record> records = new ArrayList<>();
    private final Map<Integer, List<TableString>> rawDataTable = new HashMap<>();
    private Integer firstColumnKey;

    public SameStartTimeRecords(String institute, List<TableString> rawData) {
        this.institute = institute;
        this.rawData = new ArrayList<>();
        this.rawData.addAll(rawData);
    }

    public List<Record> createExamRecords() {
        initializeFirstColumnKey();
        prepareScheduleMap();
        extractExamRecordsFromMap();
        System.out.println(records);

        return records;
    }

    public List<Record> createLessonRecords() {
        initializeFirstColumnKey();
        prepareScheduleMap();
        extractLessonRecordsFromMap();
        System.out.println(records);

        return records;
    }

    private void extractExamRecordsFromMap() {
        for (Map.Entry<Integer, List<TableString>> tableStrings : rawDataTable.entrySet()) {
            if (tableStrings.getKey().equals(firstColumnKey)) {
                continue;
            }
            List<TableString> data = new ArrayList<>();
            data.addAll(rawDataTable.get(firstColumnKey));
            data.addAll(tableStrings.getValue());
            Record record = Exam.createExam(data, institute);
            records.add(record);
        }
    }

    private void extractLessonRecordsFromMap() {
        for (Map.Entry<Integer, List<TableString>> tableStrings : rawDataTable.entrySet()) {
            if (tableStrings.getKey().equals(firstColumnKey)) {
                continue;
            }
            List<TableString> data = new ArrayList<>();
            data.addAll(rawDataTable.get(firstColumnKey));
            data.addAll(tableStrings.getValue());
            if (data.size() < 5) {
                //do nothing
            } else {
                Record record = Lesson.createLesson(data, institute);
                records.add(record);
            }
        }
    }

    private void prepareScheduleMap() {
        rawDataTable.put(firstColumnKey, rawData.subList(0, 2));
        for (TableString tableString : rawData.subList(2, rawData.size())) {
            Integer key = tableString.getTabOffset();
            if (!rawDataTable.containsKey(key)) {
                rawDataTable.put(key, new ArrayList<>());
            }
            rawDataTable.get(key).add(tableString);
        }
    }

    private void initializeFirstColumnKey() {
        firstColumnKey = rawData.get(0).getTabOffset();
    }
}
