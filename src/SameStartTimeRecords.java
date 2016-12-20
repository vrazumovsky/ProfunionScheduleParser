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

    public SameStartTimeRecords(String institute, List<TableString> rawData) {
        this.institute = institute;
        this.rawData = rawData;
    }

    public List<Record> createRecords() {
        List<Record> records = new ArrayList<>();

        Integer firstColumnKey = rawData.get(0).getTabOffset();

        Map<Integer, List<TableString>> rawDataTable = new HashMap<>();
        for (TableString tableString : rawData) {
            Integer key = tableString.getTabOffset();
            if (!rawDataTable.containsKey(key)) {
                rawDataTable.put(key, new ArrayList<>());
            }
            rawDataTable.get(key).add(tableString);
        }

        for (Map.Entry<Integer, List<TableString>> tableStrings : rawDataTable.entrySet()) {
            if (tableStrings.getKey().equals(firstColumnKey)) {
                continue;
            }
            List<TableString> data = new ArrayList<>();
            data.addAll(rawDataTable.get(firstColumnKey));
            data.addAll(tableStrings.getValue());
            Record record = new Exam(data, institute);
            records.add(record);
        }

        System.out.println(records);

        return records;
    }
}
