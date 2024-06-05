package com.lp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "errorModel" )
@XmlAccessorType( XmlAccessType.NONE )
public class ErrorModel
{
  @XmlElement( name = "message" )
  private String errorMessage;
  @XmlElement( name = "uri" )
  private String uri;

  public ErrorModel()
  {
  }

  public ErrorModel( final String errorMessage, final String uri )
  {
    this.errorMessage = errorMessage;
    this.uri = uri;
  }

  public String getErrorMessage()
  {
    return errorMessage;
  }

  public void setErrorMessage( final String errorMessage )
  {
    this.errorMessage = errorMessage;
  }

  public String getUri()
  {
    return uri;
  }

  public void setUri( final String uri )
  {
    this.uri = uri;
  }

}
