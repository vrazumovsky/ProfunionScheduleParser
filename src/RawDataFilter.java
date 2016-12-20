import java.util.Arrays;
import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class RawDataFilter {
    private static final List<String> institutes;

    static {
        String[] institutesArray = new String[] {"Институт прокуратуры РФ", "Политология", "Институт юстиции",
                "Экономика (Налоги и налогообложение)", "Политология", "Институт законотворчества",
                "Правоохранительная деятельность", "Институт правоохранительной деятельности",
                "Правовое обеспечение национальной безопасности", "безопасности", " безопасности"};
        institutes = Arrays.asList(institutesArray);
    }

    private List<TableString> rawData;

    public RawDataFilter(List<TableString> rawData) {
        this.rawData = rawData;
    }

    public List<TableString> filter() {
        for (int i = 0; i < rawData.size(); i++) {
            if (rawData.get(i).matchesOneOf(institutes)) {
                return rawData.subList(i, rawData.size());
            }
        }
        return rawData;
    }
}
