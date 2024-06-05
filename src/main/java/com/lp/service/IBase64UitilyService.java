package com.lp.service;

import java.io.UnsupportedEncodingException;

public interface IBase64UitilyService
{
  public String encodeStringToBase64( String data );

  public String decodeStringFromBase64( String data );

  public String encodeURL( String data ) throws UnsupportedEncodingException;

  public String decodeURL( String data ) throws UnsupportedEncodingException;
}
