package com.lp.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

public class Base64Conversion implements IBase64UitilyService
{

  @Override
  public String encodeStringToBase64( String data )
  {
    return Base64.getEncoder().encodeToString( data.getBytes() );
  }

  @Override
  public String decodeStringFromBase64( String data )
  {
    return new String( Base64.getDecoder().decode( data ) );
  }

  @Override
  public String encodeURL( String data ) throws UnsupportedEncodingException
  {
    return new String( URLEncoder.encode( data, "UTF-8" ) );
  }

  @Override
  public String decodeURL( String data ) throws UnsupportedEncodingException
  {
    return new String( URLDecoder.decode( data, "UTF-8" ) );
  }

}
