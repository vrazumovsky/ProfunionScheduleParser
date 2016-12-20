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

    private int currentTabIndex = -1;
    private List<Integer> offsets = new ArrayList<>();
    private List<TableString> rawData = new ArrayList<>();
    private List<String> strings = new ArrayList<>();

    private File file;
    private List<Record> records = new ArrayList<>();

    public RtfFileParser(File file) {
        this.file = file;
        System.out.println(file);
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

        if (rawData.size() == 0) {
            return records;
        }

        rawData = new RawDataFilter(rawData).filter();

//        System.out.println(rawData);

        records = new Records(rawData).createRecords();

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
            if (currentTabIndex == -1) {
                return;
            }
            System.out.println(s);
            if (offsets.size() != 0) {
                rawData.add(new TableString(s, offsets.get(currentTabIndex)));
            }
        }

        @Override
        public void processCommand(Command command, int i, boolean b, boolean b1) {
            String commandName = command.getCommandName();

            switch (commandName) {
                //new paragraph
                case "par":
                    if (currentTabIndex != -1) {
                        offsets.clear();
                        currentTabIndex = -1;
                    }
                    break;

                case "tab":
                    //next tab
                    currentTabIndex++;
                    break;

                case "tx":
                    //tab offset value
                    offsets.add(i);
                    break;
            }
        }

    }
}
