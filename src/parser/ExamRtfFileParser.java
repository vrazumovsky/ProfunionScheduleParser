package parser;

import com.rtfparserkit.parser.IRtfListener;
import com.rtfparserkit.parser.IRtfParser;
import com.rtfparserkit.parser.IRtfSource;
import com.rtfparserkit.parser.RtfStreamSource;
import com.rtfparserkit.parser.standard.StandardRtfParser;
import data.record.Record;
import data.record.Records;
import filter.ValuableDataFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
