package filter;

import data.raw.TableString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vadim on 15/02/17.
 */
public class TeacherPositionFilter implements Filter {

    private String[] positions = new String[]{"доцент", "преподаватель", "старший преподаватель", "профессор"};
    private List<String> positionsList = Arrays.asList(positions);

    @Override
    public List<TableString> filter(List<TableString> data) {
        List<TableString> tempData = new ArrayList<>();
        tempData.addAll(data);

        List<TableString> objectsToDelete = new ArrayList<>();

        for (TableString tableString : tempData) {
            if (tableString.matchesOneOf(positionsList)) {
                objectsToDelete.add(tableString);
            }
        }

        for (TableString tableString : objectsToDelete) {
            tempData.remove(tableString);
        }
        return tempData;
    }
}
