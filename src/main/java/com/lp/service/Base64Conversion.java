package com.lp.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lp.logger.ConsoleLogger;

public class Base64Conversion implements IBase64UitilyService
{
  private static final Logger myLogger = ConsoleLogger.getInstance();

  @Override
  public String encodeStringToBase64( String data )
  {
    myLogger.logp( Level.INFO, Base64Conversion.class.getName(), "encodeStringToBase64", "Fetching encoded string" );
    return Base64.getEncoder().encodeToString( data.getBytes() );
  }

  @Override
  public String decodeStringFromBase64( String data )
  {
    myLogger.logp( Level.INFO, Base64Conversion.class.getName(), "decodeStringFromBase64", "Fetching decoded string" );
    return new String( Base64.getDecoder().decode( data ) );
  }

  @Override
  public String encodeURL( String data ) throws UnsupportedEncodingException
  {
    myLogger.logp( Level.INFO, Base64Conversion.class.getName(), "encodeURL", "Fetching encoded URL" );
    return new String( URLEncoder.encode( data, "UTF-8" ) );
  }

  @Override
  public String decodeURL( String data ) throws UnsupportedEncodingException
  {
    myLogger.logp( Level.INFO, Base64Conversion.class.getName(), "decodeURL", "Fetching decoded URL" );
    return new String( URLDecoder.decode( data, "UTF-8" ) );
  }

}
