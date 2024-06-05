package com.lp.exception;

import javax.ws.rs.core.Response;

public class LearnersPointException extends RuntimeException implements IBaseException
{

  private static final long serialVersionUID = 7718828512143293558L;

  private String            errorCode;
  private String[]          params;
  private Throwable         throwable        = null;

  public Throwable getException()
  {
    return throwable;
  }

  public LearnersPointException( final String message, final Response.Status status, final String... parameters )
  {
    super( message );
    errorCode = status.getReasonPhrase();
    params = parameters;
  }

  public LearnersPointException( final String message )
  {
    super( message );
    errorCode = Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
  }

  public LearnersPointException( final Throwable throwable )
  {
    super( throwable.getMessage() );
    this.throwable = throwable;
  }

  @Override
  public String getErrorCode()
  {
    // TODO Auto-generated method stub
    return errorCode;
  }

  @Override
  public String getDocUri()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String[] getParams()
  {
    // TODO Auto-generated method stub
    return params;
  }

}
