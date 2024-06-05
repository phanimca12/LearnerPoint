package com.lp.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.introspect.Annotated;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;

public class JaxbJsonCompatibleNamingStrategy extends PropertyNamingStrategy
{
  @Override
  public String nameForField( final MapperConfig<?> config, final AnnotatedField field, final String defaultName )
  {
    return translate( field, defaultName );
  }

  @Override
  public String nameForGetterMethod( final MapperConfig<?> config, final AnnotatedMethod method, final String defaultName )
  {
    return translate( method, defaultName );
  }

  @Override
  public String nameForSetterMethod( final MapperConfig<?> config, final AnnotatedMethod method, final String defaultName )
  {
    return translate( method, defaultName );
  }

  private static String translate( final Annotated annotated, final String defaultName )
  {
    // Given JsonProperty has a higher priority
    if ( annotated.getAnnotation( JsonProperty.class ) != null )
      return defaultName;

    // prepend XmlAttribute with @
    if ( annotated.getAnnotation( XmlAttribute.class ) != null )
      return "@" + defaultName;
    else if ( annotated.getAnnotation( XmlValue.class ) != null )
      return "$";
    return defaultName;
  }

}
