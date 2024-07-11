package com.lp.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public interface IPDFConversion
{
  public ByteArrayOutputStream convertWORDToPDF( InputStream is ) throws Exception;

  public ByteArrayOutputStream convertJPEGToPDF( InputStream is ) throws Exception;

  public ByteArrayOutputStream convertHTMLToPDF( InputStream is ) throws Exception;

  public ByteArrayOutputStream convertExcelToPDF( InputStream is ) throws Exception;

  public ByteArrayOutputStream convertPPToPDF( InputStream is ) throws Exception;
}
