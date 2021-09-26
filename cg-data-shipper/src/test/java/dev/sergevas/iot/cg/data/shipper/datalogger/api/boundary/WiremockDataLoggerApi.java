package dev.sergevas.iot.cg.data.shipper.datalogger.api.boundary;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockDataLoggerApi implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        stubFor(post(urlEqualTo("/"))
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(containing("{\"sensor\":\"BME280\",\"iso_time\":\"2021-09-26T12:08:41.036071Z\"," +
                        "\"cpu_temperature\":\"42.31\",\"temperature\":\"20.50684803207987\"," +
                        "\"pressure\":\"98912.31262893083\",\"humidity\":\"61.19329773080077\"}"))
                .willReturn(aResponse()
                        .withStatus(200)));

        return Collections.singletonMap("data-logger-api/mp-rest/url", wireMockServer.baseUrl());
    }

    @Override
    public void stop() {
        Optional.ofNullable(this.wireMockServer).ifPresent(WireMockServer::stop);
    }
}
