package dev.sergevas.iot.cg.data.shipper.adapter.in.web;

import dev.sergevas.iot.cg.data.shipper.adapter.out.web.DataLoggerApi;
import dev.sergevas.iot.cg.data.shipper.application.servce.DataTransformService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
