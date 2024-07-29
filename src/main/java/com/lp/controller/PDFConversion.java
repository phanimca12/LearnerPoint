package com.lp.controller;

import static com.lp.constant.Constants.INPUT_MISSING;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.FileUtils;

import com.aspose.words.License;
import com.ip.util.FileUtil;
import com.lp.constant.Constants;
import com.lp.constant.PathStrings;
import com.lp.exception.LearnersPointException;
import com.lp.logger.ConsoleLogger;
import com.lp.service.IPDFConversion;
import com.lp.service.PDFConversionService;
import com.lp.service.XMLConversionService;
import com.sun.jersey.multipart.FormDataBodyPart;
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
  public Response convertWORDToPDF( @FormDataParam( "files" ) List<FormDataBodyPart> fileParts ) throws Exception
  {
    java.nio.file.Path sourceFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_process"
        + FileUtil.getCurrentDateTime() );
    java.nio.file.Path zipFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_converted.zip"
        + FileUtil.getCurrentDateTime() );
    loadLicense();
    if ( null != fileParts )
    {
      if ( fileParts.size() > 1 )
      {
        return processRequest( fileParts, sourceFolderPath, zipFolderPath, ".pdf", "convertWORDToPDF" );
      }
      else
      {
        return buildStreamResponse( pdfConversion.convertWORDToPDF( fileParts.get( 0 )
                                                                             .getEntityAs( InputStream.class ) ),
                                    Constants.APPLICATION_PDF );
      }
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
  public Response convertJPEGToPDF( @FormDataParam( "files" ) List<FormDataBodyPart> fileParts ) throws Exception
  {
    java.nio.file.Path sourceFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_process"
        + FileUtil.getCurrentDateTime() );
    java.nio.file.Path zipFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_converted.zip"
        + FileUtil.getCurrentDateTime() );
    loadLicense();
    if ( null != fileParts )
    {
      if ( fileParts.size() > 1 )
      {
        return processRequest( fileParts, sourceFolderPath, zipFolderPath, ".pdf", "convertJPEGToPDF" );
      }
      else
      {
        return buildStreamResponse( pdfConversion.convertJPEGToPDF( fileParts.get( 0 )
                                                                             .getEntityAs( InputStream.class ) ),
                                    Constants.APPLICATION_PDF );
      }
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
  public Response convertHTMLToPDF( @FormDataParam( "files" ) List<FormDataBodyPart> fileParts ) throws Exception
  {
    java.nio.file.Path sourceFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_process"
        + FileUtil.getCurrentDateTime() );
    java.nio.file.Path zipFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_converted.zip"
        + FileUtil.getCurrentDateTime() );
    loadLicense();
    if ( null != fileParts )
    {
      if ( fileParts.size() > 1 )
      {
        return processRequest( fileParts, sourceFolderPath, zipFolderPath, ".pdf", "convertHTMLToPDF" );
      }
      else
      {
        return buildStreamResponse( pdfConversion.convertHTMLToPDF( fileParts.get( 0 )
                                                                             .getEntityAs( InputStream.class ) ),
                                    Constants.APPLICATION_PDF );
      }
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
  public Response convertExcelToPDF( @FormDataParam( "files" ) List<FormDataBodyPart> fileParts ) throws Exception
  {
    java.nio.file.Path sourceFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_process"
        + FileUtil.getCurrentDateTime() );
    java.nio.file.Path zipFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_converted.zip"
        + FileUtil.getCurrentDateTime() );
    loadLicense();
    if ( null != fileParts )
    {
      if ( fileParts.size() > 1 )
      {
        return processRequest( fileParts, sourceFolderPath, zipFolderPath, ".pdf", "convertExcelToPDF" );
      }
      else
      {
        return buildStreamResponse( pdfConversion.convertExcelToPDF( fileParts.get( 0 )
                                                                              .getEntityAs( InputStream.class ) ),
                                    Constants.APPLICATION_PDF );
      }
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
  public Response convertPPToPDF( @FormDataParam( "files" ) List<FormDataBodyPart> fileParts ) throws Exception
  {
    java.nio.file.Path sourceFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_process"
        + FileUtil.getCurrentDateTime() );
    java.nio.file.Path zipFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_converted.zip"
        + FileUtil.getCurrentDateTime() );
    loadLicense();
    if ( null != fileParts )
    {
      if ( fileParts.size() > 1 )
      {
        return processRequest( fileParts, sourceFolderPath, zipFolderPath, ".pdf", "convertPPToPDF" );
      }
      else
      {
        return buildStreamResponse( pdfConversion.convertPPToPDF( fileParts.get( 0 ).getEntityAs( InputStream.class ) ),
                                    Constants.APPLICATION_PDF );
      }
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.PDFConversionPaths.PDF_TO_WORD )
  public Response convertPDTToWORD( @FormDataParam( "files" ) List<FormDataBodyPart> fileParts ) throws Exception
  {
    java.nio.file.Path sourceFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_process"
        + FileUtil.getCurrentDateTime() );
    java.nio.file.Path zipFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_converted.zip"
        + FileUtil.getCurrentDateTime() );
    loadLicense();
    if ( null != fileParts )
    {
      if ( fileParts.size() > 1 )
      {
        return processRequest( fileParts, sourceFolderPath, zipFolderPath, ".docx", "convertPDFToWord" );
      }
      else
      {
        return buildStreamResponse( pdfConversion.convertPDFToWord( fileParts.get( 0 )
                                                                             .getEntityAs( InputStream.class ) ),
                                    Constants.APPLICATION_DOCX );
      }
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.PDFConversionPaths.PDF_TO_JPG )
  public Response convertPDTToJPG( @FormDataParam( "files" ) List<FormDataBodyPart> fileParts ) throws Exception
  {
    java.nio.file.Path sourceFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_process"
        + FileUtil.getCurrentDateTime() );
    java.nio.file.Path zipFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_converted.zip"
        + FileUtil.getCurrentDateTime() );
    loadLicense();
    if ( null != fileParts )
    {
      if ( fileParts.size() > 1 )
      {
        return processRequest( fileParts, sourceFolderPath, zipFolderPath, ".jpg", "convertPDFToJPG" );
      }
      else
      {
        return buildStreamResponse( pdfConversion.convertPDFToJPG( fileParts.get( 0 )
                                                                            .getEntityAs( InputStream.class ) ),
                                    Constants.JPG );
      }
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.PDFConversionPaths.PDF_TO_PPT )
  public Response convertPDTToPPT( @FormDataParam( "files" ) List<FormDataBodyPart> fileParts ) throws Exception
  {
    java.nio.file.Path sourceFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_process"
        + FileUtil.getCurrentDateTime() );
    java.nio.file.Path zipFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_converted.zip"
        + FileUtil.getCurrentDateTime() );
    loadLicense();
    if ( null != fileParts )
    {
      if ( fileParts.size() > 1 )
      {
        return processRequest( fileParts, sourceFolderPath, zipFolderPath, ".pptx", "convertPDFToPPT" );
      }
      else
      {
        return buildStreamResponse( pdfConversion.convertPDFToPPT( fileParts.get( 0 )
                                                                            .getEntityAs( InputStream.class ) ),
                                    Constants.PPTX );
      }
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.PDFConversionPaths.PDF_TO_EXCEL )
  public Response convertPDTToExcel( @FormDataParam( "files" ) List<FormDataBodyPart> fileParts ) throws Exception
  {
    java.nio.file.Path sourceFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_process"
        + FileUtil.getCurrentDateTime() );
    java.nio.file.Path zipFolderPath = Paths.get( "C:\\phani_awd\\Images\\learnerspoint_converted.zip"
        + FileUtil.getCurrentDateTime() );
    loadLicense();
    if ( null != fileParts )
    {
      if ( fileParts.size() > 1 )
      {
        return processRequest( fileParts, sourceFolderPath, zipFolderPath, ".xlsx", "convertPDFToExcel" );
      }
      else
      {
        return buildStreamResponse( pdfConversion.convertPDFToExcel( fileParts.get( 0 )
                                                                              .getEntityAs( InputStream.class ) ),
                                    Constants.XLSX );
      }
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  @POST
  @Consumes( MediaType.MULTIPART_FORM_DATA )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.PDFConversionPaths.MERGEPDF )
  public Response mergePDF( @FormDataParam( "files" ) List<FormDataBodyPart> fileParts ) throws Exception
  {
    loadLicense();
    if ( null != fileParts )
    {
      try ( ByteArrayOutputStream outputStream = new ByteArrayOutputStream() )
      {
        com.aspose.pdf.Document mergedDocument = new com.aspose.pdf.Document();

        fileParts.forEach( file ->
        {
          InputStream is = file.getEntityAs( InputStream.class );
          try
          {
            pdfConversion.mergePDF( mergedDocument, is );
          }
          catch ( Exception e )
          {
            // TODO Auto-generated catch block
            myLogger.logp( Level.SEVERE, XMLConversionService.class.getName(), "mergePDF", "Merging PDS" );
          }

        } );

        mergedDocument.save( outputStream );
        return buildStreamResponse( outputStream, Constants.APPLICATION_PDF );
      }
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

  private static Response buildZipResponse( File zipFile,
                                            java.nio.file.Path sourceDir,
                                            java.nio.file.Path destDir ) throws Exception
  {
    if ( zipFile != null )
    {
      try
      {

        return Response.ok( FileUtils.readFileToByteArray( zipFile ) )
                       .header( "Content-Disposition", "attachment; filename=\"filename.zip\"" )
                       .build();
      }
      catch ( final Exception e )
      {
        throw e;
      }
      finally
      {
        FileUtil.cleanUPDir( sourceDir.toString() );
        if ( Files.exists( destDir ) )
        {
          Files.delete( destDir );
        }
      }

    }
    throw new LearnersPointException( INPUT_MISSING );

  }

  private static Response buildStreamResponse( final ByteArrayOutputStream outputStream, final String contentType )
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

  private String getFileName( FormDataBodyPart part )
  {
    com.sun.jersey.core.header.ContentDisposition fileDispositions = part.getContentDisposition();
    return fileDispositions.getFileName() != null ? fileDispositions.getFileName().split( "\\." )[ 0 ] : null;
  }

  private Response processRequest( List<FormDataBodyPart> fileParts,
                                   java.nio.file.Path sourceFolderPath,
                                   java.nio.file.Path zipFolderPath,
                                   String fileType,
                                   String methodName ) throws Exception
  {
    Class<?> clazz = Class.forName( "com.lp.service.PDFConversionService" );
    Method method = clazz.getMethod( methodName, InputStream.class );
    Object instance = clazz.getDeclaredConstructor().newInstance();
    fileParts.forEach( part ->
    {
      InputStream is = part.getEntityAs( InputStream.class );
      try ( ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream)method.invoke( instance, is ) )
      {
        FileUtil.writeStreamToFile( byteArrayOutputStream, sourceFolderPath.toString(), getFileName( part ), fileType );
      }
      catch ( Exception e )
      {
        myLogger.logp( Level.SEVERE, PDFConversion.class.getName(), methodName, methodName );
      }
    } );

    FileUtil.zipFolder( sourceFolderPath, zipFolderPath );
    File zipFile = new File( zipFolderPath.toString() );
    return buildZipResponse( zipFile, sourceFolderPath, zipFolderPath );
  }

}
