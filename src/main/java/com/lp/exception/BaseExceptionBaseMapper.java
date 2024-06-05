package com.lp.exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.lp.constant.Constants;
import com.lp.logger.ConsoleLogger;
import com.lp.model.ErrorModel;

public class BaseExceptionBaseMapper<T extends IBaseException>
{
  @Context
  protected UriInfo            uriInfo;

  @Context
  protected HttpHeaders        httpHeader;

  @Context
  protected HttpServletRequest request;

  private final Logger         myLogger = ConsoleLogger.getInstance();

  /**
   * @param exception
   * @return
   */
  public Response toResponse( final T exception )
  {

    logException( exception );

    final String errorCode = exception.getErrorCode();

    if ( Constants.PERMISSION_DENIED.equalsIgnoreCase( errorCode ) )
      return toResponse( exception, Response.Status.UNAUTHORIZED, Constants.UNAUTHORIZED_MSG );
    if ( Constants.NOT_FOUND.equalsIgnoreCase( errorCode ) )
      return toResponse( exception, Response.Status.NOT_FOUND, Constants.NOT_FOUND_MSG );
    if ( Constants.INTERNAL_SERVER_ERROR.equalsIgnoreCase( errorCode ) )
      return toResponse( exception, Response.Status.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MSG );
    if ( Constants.BAD_REQUEST.equalsIgnoreCase( errorCode ) )
      return toResponse( exception, Response.Status.BAD_REQUEST, Constants.BAD_REQUEST_MSG );
    return toResponse( exception, Response.Status.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MSG );
  }

  private Response toResponse( final T exception, final Response.Status responseCode, final String message )
  {
    final String uri = uriInfo != null ? uriInfo.getRequestUriBuilder().host( null ).scheme( null ).build().toString() : "";

    return Response.status( responseCode ).type( getMediaType() ).entity( createError( exception, message, uri ) ).build();
  }

  private ErrorModel createError( final T exception, final String message, final String uri )
  {
    return new ErrorModel( getLocalizedMessage( exception.getMessage() != null ? exception.getMessage() : message, exception.getParams() ),
                           exception.getDocUri() != null ? exception.getDocUri() : uri );
  }

  private String getLocalizedMessage( final String message, final String[] params )
  {
    String text = null;
    final Locale locale = request != null ? request.getLocale() : null;
    try
    {
      final ResourceBundle bundle = locale == null ? ResourceBundle.getBundle( "com.dstawd.exception.errors" ) : ResourceBundle.getBundle( "com.dstawd.exception.errors", locale );
      text = bundle.getString( message );

      if ( params != null )
      {
        final MessageFormat messageFormat = locale == null ? new MessageFormat( text ) : new MessageFormat( text, locale );
        text = messageFormat.format( params, new StringBuffer(), null ).toString();
      }

    }
    catch ( final MissingResourceException e )
    {
      return String.valueOf( message );
    }
    return text;
  }

  protected String getLocalizedMessage( final String message )
  {
    return getLocalizedMessage( message, null );
  }

  private void logException( final T exception )
  {
    final StringBuilder message = new StringBuilder( "\n " + exception.getClass().getName() );

    if ( exception.getDocUri() != null )
      message.append( "\n " + exception.getDocUri() );
    message.append( "\n " + exception.getErrorCode() );
    final StackTraceElement[] trace = exception.getStackTrace();
    for ( final StackTraceElement traceElement : trace )
      message.append( "\n " + traceElement );

    if ( myLogger.isLoggable( Level.SEVERE ) )
      myLogger.log( Level.SEVERE, message.toString() );
  }

  private String getMediaType()
  {
    String mediaType;
    if ( httpHeader != null && httpHeader.getAcceptableMediaTypes().contains( MediaType.APPLICATION_JSON_TYPE ) )
      mediaType = MediaType.APPLICATION_JSON;
    else
      mediaType = MediaType.APPLICATION_ATOM_XML;
    return mediaType;
  }

}
