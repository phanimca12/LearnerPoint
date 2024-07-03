package com.lp.controller;

import static com.lp.constant.Constants.INPUT_MISSING;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lp.constant.PathStrings;
import com.lp.exception.LearnersPointException;
import com.lp.model.XMLConversionModel;
import com.lp.service.IXmlConversion;
import com.lp.service.XMLConversionService;
import com.sun.jersey.multipart.FormDataParam;

@Path( "/xmlconversion" )
public class XMLConversion
{

  protected IXmlConversion service = new XMLConversionService();

  @POST
  @Consumes( MediaType.APPLICATION_JSON )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.XMLConversionPaths.xmlToXSD )
  public Response getXMLToXSD( XMLConversionModel request ) throws Exception
  {
    if ( null != request.getRequestData() )
    {
      String response = service.convertXMLToXSD( request.getRequestData(), request.getDesignType() );
      return Response.ok().entity( response ).build();
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.APPLICATION_JSON )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.XMLConversionPaths.xmlToJSON )
  public Response getXMLToJSON( XMLConversionModel request ) throws Exception
  {
    if ( null != request.getRequestData() )
    {
      String response = service.convertXMLToJSON( request.getRequestData() );
      return Response.ok().entity( response ).build();
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.APPLICATION_JSON )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.XMLConversionPaths.JSONToXML )
  public Response getJSONToXML( XMLConversionModel request ) throws Exception
  {
    if ( null != request.getRequestData() )
    {
      String response = service.convertJSONToXML( request.getRequestData() );
      return Response.ok().entity( response ).build();
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.APPLICATION_JSON )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.XMLConversionPaths.JSONToYAML )
  public Response getJSONToYAML( XMLConversionModel request ) throws Exception
  {
    if ( null != request.getRequestData() )
    {
      String response = service.convertJSONToYAML( request.getRequestData() );
      return Response.ok().entity( response ).build();
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.APPLICATION_JSON )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.XMLConversionPaths.YAMLTOJSON )
  public Response getYAMLToJSON( XMLConversionModel request ) throws Exception
  {
    if ( null != request.getRequestData() )
    {
      String response = service.convertYAMLTOJSON( request.getRequestData() );
      return Response.ok().entity( response ).build();
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.XMLConversionPaths.CSVToJSON )
  public Response getCSVToJSON( @FormDataParam( "file" ) InputStream is ) throws Exception
  {
    if ( null != is )
    {
      String response = service.convertCSVToJSON( is );
      return Response.ok().entity( response ).build();
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.XMLConversionPaths.CSVToXML )
  public Response getCSVToXML( @FormDataParam( "file" ) InputStream is ) throws Exception
  {
    if ( null != is )
    {
      String response = service.convertCSVToXML( is );
      return Response.ok().entity( response ).build();
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

}
