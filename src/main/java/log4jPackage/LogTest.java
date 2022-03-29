package log4jPackage;

import org.apache.logging.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogTest {
    private static final Logger log1 = Logger.getLogger(LogTest.class);

    public static void main(String[] args) {

        log1.trace("trace logggggg");
        log1.info("info log");
        log1.error("error log");



    }
}
