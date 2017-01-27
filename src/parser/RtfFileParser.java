package parser;

import com.rtfparserkit.parser.IRtfListener;
import com.rtfparserkit.rtf.Command;
import data.raw.TableString;
import data.record.Record;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 27/01/17.
 */
public abstract class RtfFileParser implements ScheduleParser {

    protected int currentTabIndex = -1;
    protected List<Integer> offsets = new ArrayList<>();
    protected List<TableString> rawData = new ArrayList<>();

    protected File file;
    protected List<Record> records = new ArrayList<>();

    public RtfFileParser(File file) {
        this.file = file;
        System.out.println(file);
    }


    protected class RtfListener implements IRtfListener {

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
