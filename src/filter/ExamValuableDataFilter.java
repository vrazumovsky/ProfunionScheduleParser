package filter;

import data.raw.TableString;

import java.util.List;

/**
 * Created by vadim on 23/12/16.
 */
public class ExamValuableDataFilter implements Filter {
    @Override
    public List<TableString> filter(List<TableString> data) {
        data.remove(4); //must have hardcode :)
        return data;
    }
}
