package dev.sergevas.iot.cg.data.shipper.function.boundary;

import dev.sergevas.iot.cg.data.shipper.datalogger.api.boundary.DataLoggerApi;
import dev.sergevas.iot.cg.data.shipper.function.controller.DataTransformService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.Charset;

@Path("/")
public class DataShipperResource {

    @Inject
    Logger logger;

    @Inject
    DataTransformService dataTransformService;

    @Inject
    @RestClient
    DataLoggerApi dataLoggerApi;

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response postReadings(byte[] readings) {
        String marshalledReadingsData = new String(readings, Charset.forName("UTF-8"));
        logger.info("Have got sensor readings: " + marshalledReadingsData);
        JsonObject request = dataTransformService.toDataLoggerRequest(marshalledReadingsData);
        logger.info("sendDataLoggerRequestStart: " + request);
        Response response = dataLoggerApi.postSensorData(request);
        logger.info("sendDataLoggerRequestComplete: " + response);
        return Response
                .noContent()
                .status(Response.Status.ACCEPTED)
                .build();
    }
}
