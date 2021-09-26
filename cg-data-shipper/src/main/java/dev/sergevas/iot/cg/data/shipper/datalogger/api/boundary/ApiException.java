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

package dev.sergevas.iot.cg.data.shipper.datalogger.api.boundary;

import javax.ws.rs.core.Response;

public class ApiException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private Response response;

  public ApiException() {
    super();
  }

  public ApiException(Response response) {
    super("Api response has status code " + response.getStatus());
    this.response = response;
  }

  public Response getResponse() {
    return this.response;
  }
}
