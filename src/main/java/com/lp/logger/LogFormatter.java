// ******************************************************************************
// LogFormatter.java
// ******************************************************************************
package com.lp.logger;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.apache.commons.lang3.time.FastDateFormat;


public class LogFormatter extends Formatter
{
  private final Date           m_date = new Date();
  private final FastDateFormat m_df   = FastDateFormat.getInstance( "MM-dd-yyyy" );
  private final FastDateFormat m_tf   = FastDateFormat.getInstance( "HH:mm:ss:SSSS" );

  /**
   * Method invoked to format the LogRecord data into a message string
   * <p>
   *
   * @param record
   *          <p>
   * @return String
   */
  @Override
  public String format( final LogRecord record )
  {
    final StringBuilder strMsg = new StringBuilder();

    m_date.setTime( record.getMillis() );

    strMsg.append( m_df.format( m_date ) )
          .append( '\t' )
          .append( m_tf.format( m_date ) )
          .append( '\t' )
          .append( "THREAD_" )
          .append( record.getThreadID() )
          .append( '\t' )
          .append( record.getLevel().toString() )
          .append( '\t' )
          .append( record.getSourceClassName() )
          .append( '\t' )
          .append( record.getSourceMethodName() )
          .append( '\t' )
          .append( record.getMessage() )
          .append( '\n' );

    return strMsg.toString();
  }
}
