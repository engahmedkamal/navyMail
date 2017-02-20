package navymail.util.log;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import navymail.util.Environment;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Loggable {

	private static Map<Class<?>, Logger> loggers = new ConcurrentHashMap<Class<?>, Logger>();
	protected static Logger logger;

	public Loggable() {
		logger = loggers.get(getClass());
		if (logger == null) {
			DailyRollingFileAppender fa = new DailyRollingFileAppender();
			fa.setName("FileLogger");
			Environment env = Environment.getInstance();
			
			String s = env.getWorkingDir()
					+ File.separator + "logs" + File.separator
				+ getClass().getSimpleName();
     		System.out.println(s);
			fa.setFile(s);
			fa.setLayout(new PatternLayout(
					"%d{yyyy-MM-dd HH:mm:ss,SSS}-%t-%x-%-5p-: %m%n"));
			fa.setThreshold(Level.ALL);
			fa.setAppend(true);
			fa.activateOptions();
			logger = Logger.getLogger(getClass());
			logger.addAppender(fa);
			loggers.put(getClass(), logger);
		}
	}

	public void setLogger(Logger log) {
		logger = log;
		loggers.put(getClass(), logger);
	}
}
