//******************************************************************************
// SysOutputStreamHandler.java
//******************************************************************************
package com.lp.logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;


public class SysOutputStreamHandler extends StreamHandler
{
  private final ConsoleHandler m_stdErrHandler = new ConsoleHandler();
  
  /**
  * Class constructor
  * 
  * <p> 
  * @param os
  * @param fmt
  **/
//  @Override
  public SysOutputStreamHandler()
  {
    super( System.out, new LogFormatter() );
    
    m_stdErrHandler.setLevel( Level.ALL );
  }
  
  /**
  * Method used to publish the log message
  * 
  * <p>
  * @param  record
  **/
  @Override
  public void publish( LogRecord record )
  {
    super.publish( record );
    super.flush();
    
    if ( record.getLevel().intValue() >= Level.SEVERE.intValue() )    
    {
      m_stdErrHandler.publish( record );
      m_stdErrHandler.flush();
    }
  }  
}
