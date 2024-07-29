package com.lp.service;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.pdf.DocSaveOptions;
import com.aspose.pdf.ExcelSaveOptions;
import com.aspose.pdf.HtmlLoadOptions;
import com.aspose.pdf.Image;
import com.aspose.pdf.Page;
import com.aspose.pdf.PptxSaveOptions;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.TextFragmentAbsorber;
import com.aspose.pdf.devices.JpegDevice;
import com.aspose.pdf.devices.Resolution;
import com.aspose.slides.PdfOptions;
import com.aspose.slides.PdfTextCompression;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.PdfCompliance;
import com.aspose.words.SaveFormat;

public class PDFConversionService implements IPDFConversion
{
  public static String EVALUATION_TEXT       = "Evaluation Only. Created with Aspose.Cells for Java.Copyright 2003 - 2022 Aspose Pty Ltd.";
  public static String PPT_EVALUATION_TEXT_1 = "Evaluation only.";
  public static String PPT_EVALUATION_TEXT_2 = "Created with Aspose.Slides for Java 24.6.";
  public static String PPT_EVALUATION_TEXT_3 = "Copyright 2004-2024 Aspose Pty Ltd.";

  @Override
  public ByteArrayOutputStream convertWORDToPDF( InputStream is ) throws Exception
  {

    try ( ByteArrayOutputStream output = new ByteArrayOutputStream() )

    {
      Document document = new Document( is );
      document.save( output, SaveFormat.PDF );

      return output;
    }

  }

  @Override
  public ByteArrayOutputStream convertJPEGToPDF( InputStream is ) throws Exception
  {
    try ( ByteArrayOutputStream output = new ByteArrayOutputStream() )

    {
      com.aspose.pdf.Document document = new com.aspose.pdf.Document();
      Page page = document.getPages().add();
      Image image = new Image();
      image.setImageStream( is );
      page.getParagraphs().add( image );
      document.save( output );
      document.dispose();
      return output;
    }
  }

  @Override
  public ByteArrayOutputStream convertHTMLToPDF( InputStream is ) throws Exception
  {
    try ( ByteArrayOutputStream output = new ByteArrayOutputStream() )

    {
      HtmlLoadOptions loadOptions = new HtmlLoadOptions();
      com.aspose.pdf.Document document = new com.aspose.pdf.Document( is, loadOptions );
      document.save( output );
      document.dispose();
      return output;
    }
  }

  @Override
  public ByteArrayOutputStream convertExcelToPDF( InputStream is ) throws Exception
  {
    try ( ByteArrayOutputStream output = new ByteArrayOutputStream() )

    {
      byte[] bytes = IOUtils.toByteArray( is );
      Workbook workbook = new Workbook( new ByteArrayInputStream( bytes ) );
      PdfSaveOptions pdfSaveOptions = new PdfSaveOptions( SaveFormat.PDF );
      pdfSaveOptions.setCompliance( PdfCompliance.PDF_A_1_B );
      pdfSaveOptions.setOnePagePerSheet( true );

      workbook.save( output, pdfSaveOptions );

      // Save the modified PDF document
      removeEvaluationText( output, EVALUATION_TEXT );
      return output;
    }
  }

  private void removeEvaluationText( ByteArrayOutputStream output, String evalText ) throws IOException
  {
    TextFragmentAbsorber absorber = new TextFragmentAbsorber( evalText.toString() );

    try ( ByteArrayInputStream inputStream = new ByteArrayInputStream( output.toByteArray() ) )
    {

      com.aspose.pdf.Document document = new com.aspose.pdf.Document( inputStream );
      document.getPages().accept( absorber );

      // Iterate through text fragments
      for ( TextFragment textFragment : absorber.getTextFragments() )
      {
        // Remove the text fragment
        textFragment.getTextState().setFontSize( 0 ); // Set font size to 0 to effectively remove the text
      }
      document.save( output );
      document.dispose();
    }
  }

  @Override
  public ByteArrayOutputStream convertPPToPDF( InputStream is ) throws Exception
  {
    try ( ByteArrayOutputStream output = new ByteArrayOutputStream() )
    {
      byte[] bytes = IOUtils.toByteArray( is );
      Presentation presentation = new Presentation( new ByteArrayInputStream( bytes ) );

      PdfOptions pdfOptions = new PdfOptions();

      // Sets the quality for JPG images
      pdfOptions.setJpegQuality( (byte)90 );

      // Sets DPI for images
      pdfOptions.setSufficientResolution( 300 );

      // Sets the behavior for metafiles
      pdfOptions.setSaveMetafilesAsPng( true );

      // Sets the text compression level for textual content
      pdfOptions.setTextCompression( PdfTextCompression.Flate );

      // Defines the PDF compliance mode
      pdfOptions.setCompliance( PdfCompliance.PDF_15 );
      presentation.save( output, com.aspose.slides.SaveFormat.Pdf, pdfOptions );

      String[] arrString = { PPT_EVALUATION_TEXT_1, PPT_EVALUATION_TEXT_2, PPT_EVALUATION_TEXT_3 };

      for ( String text : arrString )
      {
        removeEvaluationText( output, text );
      }
      // Dispose of the presentation
      if ( presentation != null )
        presentation.dispose();

      return output;
    }
  }

  @Override
  public ByteArrayOutputStream convertPDFToWord( InputStream is ) throws Exception
  {
    try ( ByteArrayOutputStream output = new ByteArrayOutputStream() )

    {
      com.aspose.pdf.Document document = new com.aspose.pdf.Document( is );
      // Initialize the DocSaveOptions class object to configure output word file
      DocSaveOptions docSaveOptions = new DocSaveOptions();

      // Define the type of output Word file
      docSaveOptions.setFormat( DocSaveOptions.DocFormat.DocX );

      // Set the recognition mode to Flow for enabling it for editing in future
      docSaveOptions.setMode( DocSaveOptions.RecognitionMode.Flow );

      // Set the Horizontal proximity that defines width of space between text elements as 2.5
      docSaveOptions.setRelativeHorizontalProximity( 2.5f );

      // Switch on the recognition of bullets from the source PDF
      docSaveOptions.setRecognizeBullets( true );
      document.save( output, docSaveOptions );
      document.close();
      return output;
    }
  }

  @Override
  public ByteArrayOutputStream convertPDFToJPG( InputStream is ) throws Exception
  {
    List<BufferedImage> images = new ArrayList<>();
    com.aspose.pdf.Document document = new com.aspose.pdf.Document( is );
    for ( int pageNumber = 1; pageNumber <= document.getPages().size(); pageNumber++ )
    {
      Resolution imgResolution = new Resolution( 300 );
      JpegDevice jpegDevice = new JpegDevice( imgResolution, 100 );
      try ( ByteArrayOutputStream outputStream = new ByteArrayOutputStream() )
      {
        jpegDevice.process( document.getPages().get_Item( pageNumber ), outputStream );
        BufferedImage image = ImageIO.read( new java.io.ByteArrayInputStream( outputStream.toByteArray() ) );
        images.add( image );
        image.flush();
      }
    }
    document.close();
    return getJpegStream( images );

  }

  private ByteArrayOutputStream getJpegStream( List<BufferedImage> images ) throws IOException
  {
    try ( ByteArrayOutputStream combinedOutputStream = new ByteArrayOutputStream() )

    {
      BufferedImage combinedImage = combineImages( images );
      ImageIO.write( combinedImage, "jpg", combinedOutputStream );
      combinedImage.flush();
      return combinedOutputStream;
    }
  }

  private static BufferedImage combineImages( List<BufferedImage> images )
  {
    int width = 0;
    int height = 0;

    // Calculate the dimensions of the combined image
    for ( BufferedImage image : images )
    {
      width = Math.max( width, image.getWidth() );
      height += image.getHeight();
    }

    // Create a new BufferedImage with the combined dimensions
    BufferedImage combinedImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
    Graphics g = combinedImage.getGraphics();

    // Draw each image onto the combined image
    int currentHeight = 0;
    for ( BufferedImage image : images )
    {
      g.drawImage( image, 0, currentHeight, null );
      currentHeight += image.getHeight();
    }
    g.dispose();

    return combinedImage;
  }

  @Override
  public ByteArrayOutputStream convertPDFToPPT( InputStream is ) throws Exception
  {
    try ( ByteArrayOutputStream outputStream = new ByteArrayOutputStream() )

    {
      com.aspose.pdf.Document document = new com.aspose.pdf.Document( is );
      PptxSaveOptions pptx_save = new PptxSaveOptions();
      document.setEmbedStandardFonts( false );
      document.save( outputStream, pptx_save );
      document.close();
      return outputStream;
    }

  }



  @Override
  public ByteArrayOutputStream convertPDFToExcel( InputStream is ) throws Exception
  {
    try ( ByteArrayOutputStream outputStream = new ByteArrayOutputStream() )

    {
      com.aspose.pdf.Document document = new com.aspose.pdf.Document( is );
      // Set ExcelSaveOptions
      ExcelSaveOptions saveOptions = new ExcelSaveOptions();
      saveOptions.setFormat( ExcelSaveOptions.ExcelFormat.XLSX );
      saveOptions.setInsertBlankColumnAtFirst( false );
      // Convert the PDF to XLSX format
      document.save( outputStream, saveOptions );

      return outputStream;
    }
  }

  @Override
  public void mergePDF( com.aspose.pdf.Document document, InputStream is ) throws Exception
  {
  
    com.aspose.pdf.Document pdfDocument = new com.aspose.pdf.Document( is );
    // Append all pages from the loaded PDF document
    document.getPages().add( pdfDocument.getPages() );
  }

}
