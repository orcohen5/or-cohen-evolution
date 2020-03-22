package evolution.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private static Logger logger = LogManager.getLogger(LoggerUtil.class);

    public static void logData(String data) {
        logger.info(data);
    }
    
}
