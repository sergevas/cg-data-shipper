/**
 * OpenAPI spec for Raspberry Pi #growlab resources:
 * - GET temperature, humidity, and pressure readings
 * - GET light intensity readings
 * - GET RPi Camera module image
 * - GET RPi Camera module current mode
 * - PUT to set RPi Camera module current mode
 * - GET Health checks
 *
 * The version of the OpenAPI document: 1.0.0
 */

package dev.sergevas.iot.cg.data.shipper.adapter.out.web;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper implements ResponseExceptionMapper<ApiException> {

  @Inject
  Logger logger;

  @Override
  public boolean handles(int status, MultivaluedMap<String, Object> headers) {
    return status >= 400;
  }

  @Override
  public ApiException toThrowable(Response response) {
    logger.error(response);
    return new ApiException(response);
  }
}
