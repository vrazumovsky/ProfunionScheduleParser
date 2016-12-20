import com.rtfparserkit.parser.IRtfListener;
import com.rtfparserkit.rtf.Command;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 20/12/16.
 */
public class RtfListener implements IRtfListener {

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
