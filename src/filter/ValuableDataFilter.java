package filter;

import data.raw.TableString;
import filter.Filter;

import java.util.ArrayList;
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

                List<TableString> valuableData = new ArrayList<>();
                for (TableString string : rawData.subList(i, rawData.size())) {
                    if (!string.getString().equals(" ")) {
                        int lastOffset = 0;
                        if (valuableData.size() > 0) {
                            lastOffset = valuableData.get(valuableData.size() - 1).getTabOffset();
                        }
                        if (string.getTabOffset() == lastOffset) {
                            TableString last = valuableData.remove(valuableData.size() - 1);
                            valuableData.add(new TableString(last.getString() + string.getString(), lastOffset));
                        } else {
                            valuableData.add(string);
                        }
                    }
                }
                return valuableData;
            }
        }
        return rawData;
    }
}
