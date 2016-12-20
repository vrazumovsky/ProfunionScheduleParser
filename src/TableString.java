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


    @Override
    public String toString() {
        return "TableString{" +
                "string='" + string + '\'' +
                ", tabOffset=" + tabOffset +
                '}';
    }
}