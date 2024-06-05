package com.lp.exception;

public interface IBaseException {
	public String getErrorCode();

	public String getDocUri();

	public String getMessage();

	public String[] getParams();
	
  public StackTraceElement[] getStackTrace();
}
