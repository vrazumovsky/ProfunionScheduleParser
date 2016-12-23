package filter;

import data.raw.TableString;

import java.util.List;

/**
 * Created by vadim on 23/12/16.
 */
public interface Filter {
    List<TableString> filter(List<TableString> data);
}
