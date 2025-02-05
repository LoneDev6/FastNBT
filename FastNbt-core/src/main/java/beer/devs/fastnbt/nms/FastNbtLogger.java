package beer.devs.fastnbt.nms;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class FastNbtLogger
{
    private static final Logger logger = new InternalLogger("FastNbt");

    public static void log(String message)
    {
        logger.info("[FastNbt] " + message);
    }

    public static void error(String message)
    {
        logger.severe("[FastNbt] " + message);
    }

    public static class InternalLogger extends Logger
    {
        private String prefix;

        public InternalLogger(@NotNull String prefix)
        {
            super(prefix.getClass().getCanonicalName(), null);
            setLevel(Level.ALL);
        }

        @Override
        public void log(@NotNull LogRecord logRecord)
        {
            logRecord.setMessage(prefix + logRecord.getMessage());
            super.log(logRecord);
        }

    }
}
