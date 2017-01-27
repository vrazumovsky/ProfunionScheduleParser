package parser;

import com.rtfparserkit.parser.IRtfListener;
import com.rtfparserkit.parser.IRtfParser;
import com.rtfparserkit.parser.IRtfSource;
import com.rtfparserkit.parser.RtfStreamSource;
import com.rtfparserkit.parser.standard.StandardRtfParser;
import com.rtfparserkit.rtf.Command;
import data.record.Record;
import data.raw.TableString;
import data.record.Records;
import filter.ValuableDataFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class ExamRtfFileParser extends RtfFileParser {

    public ExamRtfFileParser(File file) {
        super(file);
    }

    @Override
    public List<Record> parse() {
        InputStream is = null;
        try {
            is = new FileInputStream(file);

            IRtfSource source = new RtfStreamSource(is);
            IRtfParser parser = new StandardRtfParser();
            IRtfListener listener = new RtfFileParser.RtfListener();
            parser.parse(source, listener);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (rawData.size() == 0) {
            return records;
        }

        rawData = new ValuableDataFilter().filter(rawData);
        records = new Records(rawData).createExamRecords();

        return records;
    }


}
