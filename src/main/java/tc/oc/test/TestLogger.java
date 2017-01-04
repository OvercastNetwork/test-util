package tc.oc.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * A {@link Logger} that asserts if an error is logged,
 * and retains all {@link LogRecord}s for tests to examine.
 */
public class TestLogger extends Logger {

    final Level failureLevel;
    final List<LogRecord> records = new ArrayList<>();
    final List<LogRecord> recordsView = Collections.unmodifiableList(records);

    public TestLogger() {
        this(Level.SEVERE);
    }

    public TestLogger(Level failureLevel) {
        super(null, null);
        this.failureLevel = failureLevel;
        setUseParentHandlers(false);

        addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                if(failureLevel.intValue() <= record.getLevel().intValue()) {
                    fail(record.getMessage());
                }
                records.add(record);
            }

            @Override public void flush() {}
            @Override public void close() throws SecurityException { }
        });
    }

    public List<LogRecord> records() {
        return recordsView;
    }
}
