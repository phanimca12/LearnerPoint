package com.lp.service;

import java.io.InputStream;
import java.util.List;

import com.lp.model.JaxbObjectModel;

public interface IXmlConversion
{
  public String convertXMLToXSD( String input, String designType ) throws Exception;

  public String convertXMLToJSON( String input ) throws Exception;

  public String convertJSONToXML( String input ) throws Exception;
  
  public String convertJSONToYAML( String input ) throws Exception;

  public String convertYAMLTOJSON( String input ) throws Exception;

  public String convertCSVToJSON( InputStream is ) throws Exception;

  public String convertCSVToXML( InputStream is ) throws Exception;

  public List<JaxbObjectModel> convertXsdToObject( String xsd ) throws Exception;
}
