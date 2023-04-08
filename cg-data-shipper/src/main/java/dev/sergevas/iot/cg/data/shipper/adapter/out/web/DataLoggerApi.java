/**
 * OpenAPI spec for Raspberry Pi #growlab resources:
 * - GET temperature, humidity, and pressure readings
 * - GET light intensity readings
 * - GET RPi Camera module image
 * - GET RPi Camera module current mode
 * - PUT to set RPi Camera module current mode
 * - GET Health checks
 * <p>
 * The version of the OpenAPI document: 1.0.0
 */

package dev.sergevas.iot.cg.data.shipper.adapter.out.web;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import static java.util.Base64.getEncoder;
import static org.eclipse.microprofile.config.ConfigProvider.getConfig;

@RegisterRestClient(configKey = "data-logger-api")
@ClientHeaderParam(name = "Authorization", value = "{lookupAuth}")
public interface DataLoggerApi {

    /**
     * Posts sensor readings to the Data Logger
     */
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @Retry(delay = 0, maxDuration = 11000, jitter = 500, maxRetries = 2)
    Response postSensorData(JsonObject request) throws ApiException, ProcessingException;

    default String lookupAuth() {
        var user = getConfig().getValue("submit-sample.basic.auth.user", String.class);
        var password = getConfig().getValue("submit-sample.basic.auth.password", String.class);
        return "Basic " + getEncoder().encodeToString((user + ":" + password).getBytes());
    }
}
