package dev.sergevas.iot.cg.data.shipper.function.boundary;

import dev.sergevas.iot.cg.data.shipper.function.controller.DataTransformService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
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

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response postReadings(byte[] readings) {
        logger.info("Have got sensor readings: " + new String(readings, Charset.forName("UTF-8")));
        return Response
                .noContent()
                .status(Response.Status.ACCEPTED)
                .build();
    }
}
