package navymail.util;

import java.io.File;
import java.io.PrintStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import navymail.util.log.StatusLogger;
import navymail.util.Environment;
import navymail.util.log.Loggable;
import navymail.util.log.LoggingOutputStream;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;


 
public class AppServletContextListener extends Loggable implements ServletContextListener{
 
	private static final boolean DEBUG = true;
	
	@Override
	public void contextInitialized(ServletContextEvent arg) {
		System.out.println("ServletContextListener started");	
		new Thread(new StatusLogger()).start();
		if(DEBUG){
			Logger logger = Logger.getRootLogger();
			logger.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
			logger.setLevel(Level.ALL);
		}else
			try {
				PatternLayout layout = new PatternLayout("%d{dd MMM yyyy HH:mm:ss,SSS} %-5p [%t]: %m%n");
				
				File workdir = new File(Environment.getInstance().getWorkingDir());
				
	    		Logger log4jErr = Logger.getLogger("log_err");
		  		File errorFile = new File(workdir + File.separator + "err.log");
		  		if(!errorFile.exists()) errorFile.createNewFile(); 
	    		log4jErr.addAppender(new RollingFileAppender(layout, errorFile.getAbsolutePath(), true));
	    		log4jErr.setLevel(Level.ALL);
		  		System.setErr(new PrintStream(new LoggingOutputStream(log4jErr, log4jErr.getLevel())));
		  		
		  		Logger log4jOut = Logger.getLogger("log_out");
		  		File outputFile = new File(workdir + File.separator + "out.log");
		  		if(!outputFile.exists()) outputFile.createNewFile(); 
	    		log4jOut.addAppender(new RollingFileAppender(layout, outputFile.getAbsolutePath(), true));
	    		log4jOut.setLevel(Level.ALL);
		  		System.setOut(new PrintStream(new LoggingOutputStream(log4jOut, log4jOut.getLevel())));
			} catch (Exception e) {
				logger.error(e);
			}
		
		System.err.println("- - - - - - - - - - [ SERVER START ] - - - - - - - - - -");
		System.out.println("- - - - - - - - - - [ SERVER START ] - - - - - - - - - -");
	}
 
	@Override
	public void contextDestroyed(ServletContextEvent arg) {
		System.out.println("ServletContextListener destroyed");
	}
}
