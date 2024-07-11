package com.lp.controller;

import static com.lp.constant.Constants.INPUT_MISSING;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.aspose.words.License;
import com.lp.constant.Constants;
import com.lp.constant.PathStrings;
import com.lp.exception.LearnersPointException;
import com.lp.logger.ConsoleLogger;
import com.lp.service.IPDFConversion;
import com.lp.service.PDFConversionService;
import com.sun.jersey.multipart.FormDataParam;

@Path( "/pdfconversion" )
public class PDFConversion
{
  private static final Logger myLogger      = ConsoleLogger.getInstance();

  IPDFConversion              pdfConversion = new PDFConversionService();

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.PDFConversionPaths.WORD_TO_PDF )
  public Response convertWORDToPDF( @FormDataParam( "file" ) InputStream is ) throws Exception
  {
    if ( null != is )
    {
      loadLicense();
      return buildResponse( pdfConversion.convertWORDToPDF( is ), Constants.APPLICATION_PDF );
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.PDFConversionPaths.JPEG_TO_PDF )
  public Response convertJPEGToPDF( @FormDataParam( "file" ) InputStream is ) throws Exception
  {
    if ( null != is )
    {
      loadLicense();
      return buildResponse( pdfConversion.convertJPEGToPDF( is ), Constants.APPLICATION_PDF );
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.PDFConversionPaths.HTML_TO_PDF )
  public Response convertHTMLToPDF( @FormDataParam( "file" ) InputStream is ) throws Exception
  {
    if ( null != is )
    {
      loadLicense();
      return buildResponse( pdfConversion.convertHTMLToPDF( is ), Constants.APPLICATION_PDF );
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.PDFConversionPaths.EXCEL_TO_PDF )
  public Response convertExcelToPDF( @FormDataParam( "file" ) InputStream is ) throws Exception
  {
    if ( null != is )
    {
      loadLicense();
      return buildResponse( pdfConversion.convertExcelToPDF( is ), Constants.APPLICATION_PDF );
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.PDFConversionPaths.PP_TO_PDF )
  public Response convertPPToPDF( @FormDataParam( "file" ) InputStream is ) throws Exception
  {
    if ( null != is )
    {
      loadLicense();
      return buildResponse( pdfConversion.convertPPToPDF( is ), Constants.APPLICATION_PDF );
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
            throw new LearnersPointException( INPUT_MISSING );
          }
        }

      }
    }
    throw new LearnersPointException( INPUT_MISSING );

  }

  public static void loadLicense()
  {
    final long start = System.currentTimeMillis();
    final License lic = new License();
    try
    {
      lic.setLicense( PDFConversion.class.getResourceAsStream( "/controller/Aspose.Words.Product.Family.lic" ) );
      loadPDFLicense();

    }
    catch ( final Exception e )
    {
      myLogger.logp( Level.SEVERE, PDFConversion.class.getSimpleName(), "loadLicense", e.getMessage(), e );
    }
    finally
    {
      if ( myLogger.isLoggable( Level.INFO ) )
        myLogger.logp( Level.INFO, PDFConversion.class.getSimpleName(), "loadLicense",
                       "Duration = " + ( System.currentTimeMillis() - start ) + "ms." );
    }
  }

  private static void loadPDFLicense() throws Exception
  {
    final com.aspose.pdf.License pdfLic = new com.aspose.pdf.License();
    pdfLic.setLicense( PDFConversion.class.getResourceAsStream( "/controller/Aspose.Pdf.lic" ) );
  }

}
