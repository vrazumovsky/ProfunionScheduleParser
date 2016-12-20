import com.rtfparserkit.parser.IRtfListener;
import com.rtfparserkit.parser.IRtfParser;
import com.rtfparserkit.parser.IRtfSource;
import com.rtfparserkit.parser.RtfStreamSource;
import com.rtfparserkit.parser.standard.StandardRtfParser;
import com.rtfparserkit.rtf.Command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class RtfFileParser implements ScheduleParser {

    private File file;
    private List<Record> records = new ArrayList<>();

    public RtfFileParser(File file) {
        this.file = file;
    }


    @Override
    public List<Record> parse() {
        InputStream is = null;
        try {
            is = new FileInputStream(file);

            IRtfSource source = new RtfStreamSource(is);
            IRtfParser parser = new StandardRtfParser();
            IRtfListener listener = new RtfListener();
            parser.parse(source, listener);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return records;
    }

    private class RtfListener implements IRtfListener {

        @Override
        public void processDocumentStart() {
            System.out.println("processDocumentStart");
        }

        @Override
        public void processDocumentEnd() {
            System.out.println("processDocumentEnd");
        }

        @Override
        public void processGroupStart() {}

        @Override
        public void processGroupEnd() {}

        @Override
        public void processCharacterBytes(byte[] bytes) {}

        @Override
        public void processBinaryBytes(byte[] bytes) {}

        @Override
        public void processString(String s) {

        }

        @Override
        public void processCommand(Command command, int i, boolean b, boolean b1) {

        }

    }
}
