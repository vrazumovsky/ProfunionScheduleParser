package data.record;

import data.raw.TableString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by vadim on 20/12/16.
 */
public class ExamRecords {

    private static final Pattern DATE_PATTERN = Pattern.compile("[0-9]+[-][а-яА-Я]+[-][0-9]+");
    private List<TableString> rawData;

    private List<Record> records = new ArrayList<>();
    private String institute;
    private List<String> groups = new ArrayList<>();
    private int recordsStartIndex = 0;
    private List<Integer> offsets = new ArrayList<>();


    public ExamRecords(List<TableString> rawData) {
        this.rawData = rawData;
    }

    public List<Record> createRecords() {
        extractInstitute();
        extractGroups();
        extractExamRecords();
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
                records.addAll(sameStartTimeRecords.createRecords());
                firstIndex = i;
            }
        }
    }

    private void extractGroups() {
        for (int i = 1; i < rawData.size(); i++) {
            if (stringWithIndexMatches(i)) {
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
}
