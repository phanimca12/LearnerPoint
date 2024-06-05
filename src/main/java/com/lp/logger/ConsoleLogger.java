// ******************************************************************************
// ConsoleLogger.java
// ******************************************************************************
package com.lp.logger;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConsoleLogger
{
  private static Logger       m_logger        = null;
  private static Handler      m_streamHandler = null;

  private static final String LOGGER_NAME     = "com.lp.logger";

  /**
   * Method invoked to get the console logger singleton
   * <p>
   *
   * @return Logger
   */
  public static synchronized Logger getInstance()
  {
    if ( m_logger == null )
      try
      {
        if ( ( m_logger = Logger.getLogger( LOGGER_NAME ) ) != null )
        {
          final Handler[] handlers = m_logger.getHandlers();

          for ( final Handler handler : handlers )
            m_logger.removeHandler( handler );
          m_streamHandler = new SysOutputStreamHandler();

          m_logger.setUseParentHandlers( false );

          m_logger.addHandler( m_streamHandler );
        }
        setLevel( Level.ALL );
      }
      catch ( final Exception ex )
      {
        ex.printStackTrace();
      }
    return m_logger;
  }

  /**
   * Method invoked to set the console logging level
   * <p>
   *
   * @return ConsoleLogger
   */
  private static void setLevel( final Level level )
  {
    Logger tmpLogger = m_logger;

    while ( tmpLogger != null )
    {
      for ( final Handler handler : tmpLogger.getHandlers() )
        handler.setLevel( level );
      tmpLogger = tmpLogger.getParent();
    }
    m_logger.setLevel( level );
  }
}
