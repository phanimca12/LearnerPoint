package com.lp.constant;

public interface PathStrings
{
  public interface Base64ConversionPaths
  {
    public static final String stringEncode = "/stringencode";
    public static final String stringDecode = "/stringdecode";
    public static final String urlEncode    = "/urlencode";
    public static final String urlDecode    = "/urldecode";
  }

  public interface XMLConversionPaths
  {
    public static final String xmlToXSD = "/xmltoxsd";
    public static final String xmlToJSON = "/xmltojson";
    public static final String JSONToXML = "/jsontoxml";
    public static final String JSONToYAML = "/jsontoyaml";
    public static final String YAMLTOJSON = "/yamltojson";
    public static final String CSVToJSON  = "/csvtojson";
    public static final String CSVToXML   = "/csvtoxml";

  }

  public interface JAXBConversionPaths
  {
    public static final String XSD_TO_OBJECT = "/xsdtoobj";
    public static final String JSON_TO_OBJECT = "/jsontoobj";
  }

  public interface PDFConversionPaths
  {
    public static final String WORD_TO_PDF = "/wordtopdf";
    public static final String JPEG_TO_PDF = "/jpegtopdf";
    public static final String HTML_TO_PDF = "/htmltopdf";
    public static final String EXCEL_TO_PDF = "/exceltopdf";
    public static final String PP_TO_PDF    = "/pptopdf";

  }
}
