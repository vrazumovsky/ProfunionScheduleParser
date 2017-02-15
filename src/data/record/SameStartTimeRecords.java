package data.record;


import data.raw.TableString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
        if (evenOddCheck()) {
            return createEvenOddLessonRecords();
        } else {
            return createSimpleLessonRecords();
        }
    }

    private List<Record> createEvenOddLessonRecords() {
        int secondPartIndex = calculateRawDataSecondPartIndex();
        List<TableString> rawDataFirst = rawData.subList(0, secondPartIndex);
        List<Record> records = new SameStartTimeRecords(institute, rawDataFirst).createLessonRecords();
        List<TableString> rawDataSecond = createRawDataSecondPart(secondPartIndex);
        records.addAll(new SameStartTimeRecords(institute, rawDataSecond).createLessonRecords());
        return records;
    }

    private List<TableString> createRawDataSecondPart(int secondPartIndex) {
        List<TableString> rawDataSecond = new ArrayList<>();
        rawDataSecond.add(rawData.get(0));
        rawDataSecond.addAll(rawData.subList(secondPartIndex, rawData.size()));
        return rawDataSecond;
    }

    private int calculateRawDataSecondPartIndex() {
        //skip week day and first start time
        for (int i = 2; i < rawData.size(); i++) {
            if (isStartTime(rawData.get(i).getString())) {
                return i;
            }
        }
        throw new IllegalStateException();
    }



    private List<Record> createSimpleLessonRecords() {
        initializeFirstColumnKey();
        prepareScheduleMap();
        extractLessonRecordsFromMap();
        return records;
    }


    static Pattern pattern = Pattern.compile("[0-9]{2}[\\.][0-9]{2}");

    private boolean isStartTime(String s) {
        return pattern.matcher(s).matches();
    }


    private boolean evenOddCheck() {
        boolean even = false;
        boolean odd = false;
        for (TableString tableString : rawData) {
            String string = tableString.getString();
            if (string.equalsIgnoreCase("неч")) {
                odd = true;
            } else if (string.equalsIgnoreCase("чет")) {
                even = true;
            }
            if (even && odd) {
                return true;
            }
        }
        return even && odd;
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
        List<TableString> subList = rawData.subList(2, rawData.size());
        for (int i = 0; i < subList.size(); i++) {
            Integer key = subList.get(i).getTabOffset();
            if (!rawDataTable.containsKey(key)) {
                rawDataTable.put(key, new ArrayList<>());
            }
            //crutch, it occurs when there are errors in data (they should be fixed)
            if (key.equals(firstColumnKey)) {
                throw new IllegalStateException();
            } else {
                rawDataTable.get(key).add(subList.get(i));
            }
        }
    }

    private void initializeFirstColumnKey() {
        firstColumnKey = rawData.get(0).getTabOffset();
    }
}
