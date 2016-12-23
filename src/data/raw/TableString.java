package data.raw;

import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */

public class TableString {
    private String string;
    private int tabOffset;

    public TableString(String string, int tabOffset) {
        this.string = string;
        this.tabOffset = tabOffset;
    }

    public boolean matchesOneOf(List<String> strings) {
        return strings.contains(string);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getTabOffset() {
        return tabOffset;
    }

    @Override
    public String toString() {
        return "data.raw.TableString{" +
                "string='" + string + '\'' +
                ", tabOffset=" + tabOffset +
                '}';
    }
}