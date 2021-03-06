package parser;


import data.record.Record;
import data.record.Records;
import filter.ValuableDataFilter;

import java.io.File;
import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class ExamRtfFileParser extends RtfFileParser {

    public ExamRtfFileParser(File file) {
        super(file);
    }


    @Override
    protected List<Record> createRecords() {
        rawData = new ValuableDataFilter().filter(rawData);
        return  new Records(rawData).createExamRecords();
    }
}
