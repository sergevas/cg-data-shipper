package dev.sergevas.iot.cg.data.shipper.datalogger.api.boundary;

import dev.sergevas.iot.cg.data.shipper.datalogger.api.controller.DataLoggerRequestBuilder;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
@QuarkusTestResource(WiremockDataLoggerApi.class)
class DataLoggerApiTest {

    @Inject
    @RestClient
    DataLoggerApi dataLoggerApi;

    @Test
    void postSensorData() {
        this.dataLoggerApi.postSensorData(new DataLoggerRequestBuilder()
                .sensor("BME280")
                .isoTime("2021-09-26T12:08:41.036071Z")
                .cpuTemperature(42.31)
                .temperature(20.50684803207987)
                .pressure(98912.31262893083)
                .humidity(61.19329773080077)
                .build());
    }
}
