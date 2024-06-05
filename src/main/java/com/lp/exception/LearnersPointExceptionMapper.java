package com.lp.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class LearnersPointExceptionMapper extends BaseExceptionBaseMapper<IBaseException>
    implements ExceptionMapper<LearnersPointException>
{
  @Override
  public Response toResponse( LearnersPointException learnersPointException )
  {
    return super.toResponse( learnersPointException );
  }
}
