package pro.sandiao.mcqqbot.botconsolecommand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class SystemPrintStream extends PrintStream {

    private PrintStream oldps;

    public SystemPrintStream(ByteArrayOutputStream newps, PrintStream oldps) throws UnsupportedEncodingException {
        super(newps, true, "UTF-8");
        this.oldps = oldps;
    }
    
    @Override
    public void write(byte[] b) throws IOException {
        oldps.write(b);
        super.write(b);
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        oldps.write(buf, off, len);
        super.write(buf, off, len);
    }

    @Override
    public void write(int b) {
        oldps.write(b);
        super.write(b);
    }
}
