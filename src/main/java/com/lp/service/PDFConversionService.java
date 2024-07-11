package com.lp.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.pdf.HtmlLoadOptions;
import com.aspose.pdf.Image;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.TextFragmentAbsorber;
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

}
