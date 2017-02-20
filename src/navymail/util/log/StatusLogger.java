package navymail.util.log;

import navymail.util.ConnectionHandler;


public class StatusLogger extends Loggable implements Runnable{

	private static final long PERIOD = 1000 * 10;

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(PERIOD);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.fatal("Memory:" + (Runtime.getRuntime().freeMemory()/1024/1024) + "/" + (Runtime.getRuntime().maxMemory()/1024/1024));
			logger.fatal(("DB:" + ConnectionHandler.getInstance().getFreeConnectionCount() + "/" + ConnectionHandler.getInstance().getLeasedConnectionCount() + "/" + ConnectionHandler.getInstance().getConnectionCount()));
		}
	}
}
