package parser;


import data.record.Record;
import data.record.Records;
import filter.ValuableDataFilter;

import java.io.File;
import java.util.List;

/**
 * Created by vadim on 27/01/17.
 */
public class LessonsRtfFileParser extends RtfFileParser {

    public LessonsRtfFileParser(File file) {
        super(file);
    }


    @Override
    protected List<Record> createRecords() {
        rawData = new ValuableDataFilter().filter(rawData);
        return  new Records(rawData).createLessonRecords();
    }
}
