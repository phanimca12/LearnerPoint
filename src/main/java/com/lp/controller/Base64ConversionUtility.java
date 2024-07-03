package com.lp.controller;

import static com.lp.constant.Constants.INPUT_MISSING;
import static com.lp.constant.Constants.RESPONSE_GENERATION_ERROR_MSG;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.lp.constant.PathStrings;
import com.lp.exception.LearnersPointException;
import com.lp.logger.ConsoleLogger;
import com.lp.model.Base64ConversionModel;
import com.lp.service.Base64Conversion;
import com.lp.service.IBase64UitilyService;

@Path( "/conversion" )
public class Base64ConversionUtility
{
  protected IBase64UitilyService service  = new Base64Conversion();

  private final Logger myLogger = ConsoleLogger.getInstance();

  @POST
  @Consumes( MediaType.APPLICATION_JSON )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.Base64ConversionPaths.stringEncode )
  public Response getStringEncoding( Base64ConversionModel request )
  {
    if ( null != request.getDataToConvert() )
    {
      String response = service.encodeStringToBase64( request.getDataToConvert() );

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
  @Path( PathStrings.Base64ConversionPaths.stringDecode )
  public Response getStringDecoding( Base64ConversionModel request )
  {
    if ( null != request.getDataToConvert() )
    {
      String response = service.decodeStringFromBase64( request.getDataToConvert() );
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
  @Path( PathStrings.Base64ConversionPaths.urlEncode )
  public Response getUrlEncoding( Base64ConversionModel request ) throws UnsupportedEncodingException
  {
    if ( null != request.getDataToConvert() )
    {
      String response = service.encodeURL( request.getDataToConvert() );
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
  @Path( PathStrings.Base64ConversionPaths.urlDecode )
  public Response getUrlDecoding( Base64ConversionModel request ) throws UnsupportedEncodingException
  {
    if ( null != request.getDataToConvert() )
    {
      String response = service.decodeURL( request.getDataToConvert() );
      return Response.ok().entity( response ).build();
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  private static Response buildResponse( final ByteArrayOutputStream outputStream, final String contentType )
  {
    if ( outputStream != null )
    {
      try
      {
        final StreamingOutput streamingOutput = out -> out.write( outputStream.toByteArray() );

        return Response.ok( streamingOutput )
                       .header( "content-disposition", "attachment;" )
                       .header( "Content-Type", contentType )
                       .build();
      }
      catch ( final Exception e )
      {
        throw e;
      }
      finally
      {
        if ( outputStream != null )
        {
          try
          {
            outputStream.close();
          }
          catch ( final IOException e )
          {
            throw new LearnersPointException( RESPONSE_GENERATION_ERROR_MSG );
          }
        }

      }
    }
    throw new LearnersPointException( RESPONSE_GENERATION_ERROR_MSG );

  }

}
