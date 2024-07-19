package com.ip.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil
{
  public static void zipFolder( Path sourceFolderPath, Path zipPath ) throws IOException
  {
    try ( ZipOutputStream zipOutputStream = new ZipOutputStream( Files.newOutputStream( zipPath ) );
        Stream<Path> paths = Files.walk( sourceFolderPath ) )
    {
      paths.filter( Files::isRegularFile ).forEach( path ->
      {
        ZipEntry zipEntry = new ZipEntry( sourceFolderPath.relativize( path ).toString() );
        try
        {
          zipOutputStream.putNextEntry( zipEntry );
          Files.copy( path, zipOutputStream );
          zipOutputStream.closeEntry();
        }
        catch ( IOException e )
        {
          System.err.println( e );
        }
      } );
    }
  }

  public static void writeStreamToFile( ByteArrayOutputStream byteArrayOutputStream,
                                        String folderPath,
                                        String fileName,
                                        String fileType ) throws IOException
  {
    java.nio.file.Path directoryPath = Paths.get( folderPath );
    Files.createDirectories( directoryPath );

    File outputFile = new File( directoryPath.toFile(), fileName + fileType );

    try ( OutputStream outputStream = new FileOutputStream( outputFile ) )
    {
      byteArrayOutputStream.writeTo( outputStream );
      byteArrayOutputStream.close();
    }
    catch ( IOException e )
    {
      throw new IOException( "Failed to write file: " + e.getMessage() );
    }
  }

  public static void cleanUPDir( String dirPath ) throws IOException
  {
    Path direPath = Paths.get( dirPath );
    if ( Files.exists( direPath ) && Files.isDirectory( direPath ) )
    {
      Files.walk( direPath )
           .sorted( Comparator.reverseOrder() ) // Reverse order for deleting innermost files first
           .map( Path::toFile )
           .forEach( File::delete );
    }

  }

  public static String getCurrentDateTime()
  {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyyMMdd_HHmmssSSS" );
    return now.format( formatter );
  }
}
