package com.lp.controller;

import static com.lp.constant.Constants.INPUT_MISSING;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lp.constant.PathStrings;
import com.lp.exception.LearnersPointException;
import com.lp.model.JaxbObjectModel;
import com.lp.model.XMLConversionModel;
import com.lp.service.IXmlConversion;
import com.lp.service.XMLConversionService;

@Path( "/jaxb" )
public class JAXBController
{

  protected IXmlConversion service = new XMLConversionService();

  @POST
  @Consumes( MediaType.APPLICATION_JSON )
  @Produces( MediaType.APPLICATION_JSON )
  @Path( PathStrings.JAXBConversionPaths.XSD_TO_OBJECT )
  public Response convertXSDToJavaObj( XMLConversionModel request ) throws Exception
  {
    if ( null != request.getRequestData() )
    {
      String response = service.convertXMLToXSD( request.getRequestData(), request.getDesignType() );
      List<JaxbObjectModel> modelList = service.convertXsdToObject( response );
      return Response.ok().entity( modelList ).build();
    }
    else
    {
      throw new LearnersPointException( INPUT_MISSING );
    }
  }

}
