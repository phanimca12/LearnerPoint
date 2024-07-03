package com.lp.model;

public class XMLConversionModel
{
  public String getRequestData()
  {
    return requestData;
  }

  public void setRequestData( String requestData )
  {
    this.requestData = requestData;
  }

  public String getDesignType()
  {
    return designType;
  }

  public void setDesignType( String designType )
  {
    this.designType = designType;
  }

  public String getResponseData()
  {
    return responseData;
  }

  public void setResponseData( String responseData )
  {
    this.responseData = responseData;
  }
  String requestData;
  String designType;
  String responseData;
}
