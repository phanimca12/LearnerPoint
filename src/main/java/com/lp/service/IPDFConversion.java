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

  public ByteArrayOutputStream convertPDFToWord( InputStream is ) throws Exception;

  public ByteArrayOutputStream convertPDFToJPG( InputStream is ) throws Exception;

  public ByteArrayOutputStream convertPDFToPPT( InputStream is ) throws Exception;

  public ByteArrayOutputStream convertPDFToExcel( InputStream is ) throws Exception;

  public void mergePDF( com.aspose.pdf.Document document, InputStream is ) throws Exception;
}
