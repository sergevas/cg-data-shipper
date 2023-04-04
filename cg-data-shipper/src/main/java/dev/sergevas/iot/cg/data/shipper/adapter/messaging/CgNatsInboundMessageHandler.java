package dev.sergevas.iot.cg.data.shipper.adapter.messaging;

import dev.sergevas.iot.cg.data.shipper.datalogger.api.boundary.DataLoggerApi;
import dev.sergevas.iot.cg.data.shipper.function.control.DataTransformService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
@RegisterForReflection
@Named("cgNatsInboundMessageHandler")
public class CgNatsInboundMessageHandler {

    @Inject
    Logger logger;
    @Inject
    DataTransformService dataTransformService;
    @Inject
    @RestClient
    DataLoggerApi dataLoggerApi;

    public void handle(byte[] readings) {
        String marshalledReadingsData = new String(readings, StandardCharsets.UTF_8);
        logger.info("Have got sensor readings: " + marshalledReadingsData);
        JsonObject request = dataTransformService.toDataLoggerRequest(marshalledReadingsData);
        logger.info("sendDataLoggerRequestStart: " + request);
        try (Response response = dataLoggerApi.postSensorData(request)) {
            logger.info("sendDataLoggerRequestComplete: " + response);
        }
    }
}
