package com.lp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wiztools.xsdgen.ParseException;
import org.wiztools.xsdgen.XsdGen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.lp.model.JaxbObjectModel;

public class XMLConversionService implements IXmlConversion
{
  String testData = "<library><book><title>Harry Potter</title><author>J.K. Rowling</author><price>29.99</price></book><book><title>The Hobbit</title><author>J.R.R. Tolkien</author><price>24.95</price></book></library>";

  @Override
  public String convertXMLToXSD( String input, String designType ) throws Exception
  {
    return getXmlSchema( input );
  }

  private String getXmlSchema( String inputData ) throws IOException, ParseException
  {
    File outputFile = new File( "tempfile.xml" );
    try ( ByteArrayOutputStream boas = new ByteArrayOutputStream() )
    {

      if ( outputFile.exists() )
      {
        outputFile.delete();
      }
      FileUtils.writeStringToFile( outputFile, inputData, Charset.forName( "UTF-8" ) );
      XsdGen gen = new XsdGen();
      gen.parse( outputFile );
      gen.write( boas );
      outputFile.delete();
      return boas.toString( StandardCharsets.UTF_8 ).replace( "mixed=\"true\"", "" );
    }
  }

  @Override
  public String convertXMLToJSON( String input ) throws Exception
  {

    XmlMapper xmlMapper = new XmlMapper();
    JsonNode jsonNode = xmlMapper.readTree( input.getBytes() );
    return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString( jsonNode );
  }

  @Override
  public String convertJSONToXML( String input ) throws Exception
  {
    JsonNode jsonNode = new ObjectMapper().readTree( input );
    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.configure( ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true );
    return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString( jsonNode );
  }

  @Override
  public String convertJSONToYAML( String input ) throws Exception
  {
    JsonNode jsonNode = new ObjectMapper().readTree( input );
    return new YAMLMapper().writerWithDefaultPrettyPrinter().writeValueAsString( jsonNode );
  }

  @Override
  public String convertYAMLTOJSON( String input ) throws Exception
  {
    JsonNode jsonNode = new YAMLMapper().readTree( input );
    return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString( jsonNode );
  }

  @Override
  public String convertCSVToJSON( InputStream is ) throws Exception
  {
    CSVParser csvParser = new CSVParser( new InputStreamReader( is ), CSVFormat.DEFAULT.withFirstRecordAsHeader() );
    return convertCsvToJson( csvParser.getRecords() );
  }

  private static String convertCsvToJson( List<CSVRecord> records ) throws IOException
  {
    ObjectMapper objectMapper = new ObjectMapper();
    List<Object> jsonObjects = new ArrayList<>();
    for ( CSVRecord record : records )
    {
      java.util.Map<String, String> jsonObject = new java.util.LinkedHashMap<>();
      for ( String header : record.getParser().getHeaderNames() )
      {
        jsonObject.put( header, record.get( header ) );
      }
      jsonObjects.add( jsonObject );
    }
    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString( jsonObjects );
  }

  @Override
  public String convertCSVToXML( InputStream is ) throws Exception
  {
    CSVParser csvParser = new CSVParser( new InputStreamReader( is ), CSVFormat.DEFAULT.withFirstRecordAsHeader() );
    Document document = convertCsvToXml( csvParser.getRecords() );
    return convertDocumentToString( document );
  }

  private String convertDocumentToString( Document document ) throws TransformerException
  {
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "no" );
    transformer.setOutputProperty( OutputKeys.METHOD, "xml" );
    transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
    StringWriter writer = new StringWriter();
    transformer.transform( new DOMSource( document ), new StreamResult( writer ) );
    return writer.toString();
  }

  private static Document convertCsvToXml( Iterable<CSVRecord> records ) throws Exception
  {
    // Create XML document
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Create root element
    Element rootElement = doc.createElement( "records" );
    doc.appendChild( rootElement );

    // Convert each CSV record to XML elements
    for ( CSVRecord record : records )
    {
      Element recordElement = doc.createElement( "record" );
      rootElement.appendChild( recordElement );

      for ( String header : record.getParser().getHeaderNames() )
      {
        String value = record.get( header );
        Element fieldElement = doc.createElement( header );
        fieldElement.appendChild( doc.createTextNode( value ) );
        recordElement.appendChild( fieldElement );
      }
    }

    return doc;
  }

  @Override
  public List<JaxbObjectModel> convertXsdToObject( String xsd ) throws IOException, InterruptedException
  {
    List<JaxbObjectModel> modelList = null;
    File tempFile = File.createTempFile( "temp", ".xsd" );
    FileWriter writer = new FileWriter( tempFile );
    writer.write( xsd );
    writer.close();

    ProcessBuilder processBuilder = new ProcessBuilder( "xjc", tempFile.getAbsolutePath() );
    processBuilder.redirectErrorStream( true ); // Redirect error stream to output stream

    Process process = processBuilder.start();

    int exitCode = process.waitFor();
    if ( exitCode == 0 )
    {
      tempFile.delete();
      String folderPath = "generated/";
      modelList = readJavaObjects( folderPath );
      cleanUPDir( folderPath );
    }
    return modelList;
  }

  private List<JaxbObjectModel> readJavaObjects( String dirPath )
  {
    List<JaxbObjectModel> modelList = new ArrayList();
    File directory = new File( dirPath );
    if ( directory.exists() )
    {
      File[] files = directory.listFiles();
      if ( files != null )
      {
        for ( File file : files )
        {
          if ( file.isFile() )
          {
            JaxbObjectModel model = new JaxbObjectModel();
            model.setObjectName( file.getName() );
            try ( BufferedReader reader = new BufferedReader( new FileReader( file ) ) )
            {
              StringBuilder builder = new StringBuilder();
              String line;
              while ( ( line = reader.readLine() ) != null )
              {
                builder.append( line ).append( "\n" );
              }
              System.out.println( builder.toString() );
              model.setObjectDescription( builder.toString() );
              reader.close();
            }
            catch ( IOException e )
            {
              e.printStackTrace();
            }
            modelList.add( model );
          }
        }
      }
    }
    return modelList;
  }

  private void cleanUPDir( String dirPath ) throws IOException
  {
    Path direPath = Paths.get( dirPath );
    Files.walk( direPath )
         .sorted( Comparator.reverseOrder() ) // Reverse order for deleting innermost files first
         .map( Path::toFile )
         .forEach( File::delete );

  }
}
