import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by vadim on 20/12/16.
 */
public class Records {

    private static final Pattern DATE_PATTERN = Pattern.compile("[0-9]+[-][а-яА-Я]+[-][0-9]+");
    private List<TableString> rawData;

    public Records(List<TableString> rawData) {
        this.rawData = rawData;
    }

    public List<Record> createRecords() {
        List<Record> records = new ArrayList<>();

        String institute = rawData.get(0).getString();
        List<String> groups = new ArrayList<>();


        int recordsStartIndex = 0;

        for (int i = 1; i < rawData.size(); i++) {
            if (stringWithIndexMatches(i)) {
                recordsStartIndex = i;
                break;
            } else {
                groups.add(rawData.get(i).getString());
            }
        }

        int firstIndex = recordsStartIndex;
        for (int i = recordsStartIndex + 1; i < rawData.size(); i++) {
            if (stringWithIndexMatches(i) || i == rawData.size() - 1) {
                SameStartTimeRecords sameStartTimeRecords =
                        new SameStartTimeRecords(institute, rawData.subList(firstIndex, i == rawData.size() - 1 ? rawData.size() : i));
                records.addAll(sameStartTimeRecords.createRecords());
                firstIndex = i;
            }
        }

        return records;
    }

    private boolean stringWithIndexMatches(int i) {
        return DATE_PATTERN.matcher(rawData.get(i).getString()).matches();
    }
}
