package filter;

import data.raw.TableString;
import filter.Filter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class ValuableDataFilter implements Filter {
    private static final List<String> institutes;

    static {
        String[] institutesArray = new String[] {"Институт прокуратуры РФ", "Политология", "Институт юстиции",
                "Экономика (Налоги и налогообложение)", "Политология", "Институт законотворчества",
                "Правоохранительная деятельность", "Институт правоохранительной деятельности",
                "Правовое обеспечение национальной безопасности", "безопасности", " безопасности"};
        institutes = Arrays.asList(institutesArray);
    }


    public List<TableString> filter(List<TableString> rawData) {
        for (int i = 0; i < rawData.size(); i++) {
            TableString tableString = rawData.get(i);
            if (tableString.matchesOneOf(institutes)) {
                if (tableString.getString().equals("безопасности") || tableString.getString().equals(" безопасности")) {
                    tableString.setString("Правовое обеспечение национальной безопасности");
                }
                return rawData.subList(i, rawData.size());
            }
        }
        return rawData;
    }
}
