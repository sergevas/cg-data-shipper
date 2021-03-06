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

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@RegisterRestClient(configKey = "data-logger-api")
public interface DataLoggerApi {

    /**
     * Posts sensor readings to the Data Logger
     *
     */
    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response postSensorData(JsonObject request) throws ApiException, ProcessingException;

}

