package dev.sergevas.iot.cg.data.shipper.function.controller;

import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataTransformServiceTest {

    @Test
    void toRequest() {
        DataTransformService dataTransformService = new DataTransformService();
        String healthMsg = "{\"event_id\":\"1892eb49-2f3e-44e6-94a6-b72485b31dfa\",\"created_at\":\"2021-09-28T11:38:50.307868Z\"," +
                "\"read_at\":\"2021-09-28T11:38:49Z\",\"device_id\":\"0001\",\"device_name\":\"growlabv1\"," +
                "\"data\":{\"type\":\"HEALTH\",\"value\":{\"checks\":[{\"name\":\"deadlock\",\"status\":\"UP\"}," +
                "{\"data\":{\"total\":\"28.98 GB\",\"percentFree\":\"69.73%\",\"totalBytes\":31116636160,\"free\":\"20.21 GB\"," +
                "\"freeBytes\":21696647168},\"name\":\"diskSpace\",\"status\":\"UP\"},{\"data\":{\"total\":\"31.07 MB\"," +
                "\"max\":\"61.88 MB\",\"percentFree\":\"66.43%\",\"totalBytes\":32575488,\"maxBytes\":64880640,\"free\":\"10.29 MB\"," +
                "\"freeBytes\":10793312},\"name\":\"heapMemory\",\"status\":\"UP\"},{\"data\":{\"cpuTemp\":\"43.312\"}," +
                "\"name\":\"systemInfo\",\"status\":\"UP\"}],\"status\":\"UP\"}}}";
        JsonObject expectedHealthObj = Json.createObjectBuilder()
                .add("sensor", "growlabv1")
                .add("iso_time", "2021-09-28T11:38:49Z")
                .add("cpu_temperature", "43.312")
                .build();
        assertEquals(expectedHealthObj, dataTransformService.toDataLoggerRequest(healthMsg));

        String lightMsg = "{\"event_id\":\"9949c724-c0d3-4b1b-8dfc-f56bc6c86339\",\"created_at\":\"2021-09-28T11:38:30.227855Z\"," +
                "\"read_at\":\"2021-09-28T11:38:30.210143Z\",\"device_id\":\"0001\",\"device_name\":\"growlabv1\"," +
                "\"data\":{\"unit\":\"lx\",\"type\":\"LIGHT\",\"value\":\"1783.33\"}}";
        JsonObject expectedLightObj = Json.createObjectBuilder()
                .add("sensor", "growlabv1")
                .add("iso_time", "2021-09-28T11:38:30.210143Z")
                .add("light", "1783.33")
                .build();
        assertEquals(expectedLightObj, dataTransformService.toDataLoggerRequest(lightMsg));

        String tempMsg = "{\"event_id\":\"18acbe97-b681-4aa7-83e7-1ccdd85e71c7\",\"created_at\":\"2021-09-28T12:33:00.265483Z\"," +
                "\"read_at\":\"2021-09-28T12:33:00.218963Z\",\"device_id\":\"0001\",\"device_name\":\"growlabv1\"," +
                "\"data\":{\"unit\":\"°C\",\"type\":\"TEMP\",\"value\":\"23.742282452486688\"}}";
        JsonObject expectedTempObj = Json.createObjectBuilder()
                .add("sensor", "growlabv1")
                .add("iso_time", "2021-09-28T12:33:00.218963Z")
                .add("temperature", "23.742282452486688")
                .build();
        assertEquals(expectedTempObj, dataTransformService.toDataLoggerRequest(tempMsg));

        String pressMsg = "{\"event_id\":\"c3b5b0ef-601a-4936-ad72-3b6e9769e391\",\"created_at\":\"2021-09-28T12:33:00.267113Z\"," +
                "\"read_at\":\"2021-09-28T12:33:00.218963Z\",\"device_id\":\"0001\",\"device_name\":\"growlabv1\"," +
                "\"data\":{\"unit\":\"hPa\",\"type\":\"PRESS\",\"value\":\"101166.07255874875\"}}";
        JsonObject expectedPressObj = Json.createObjectBuilder()
                .add("sensor", "growlabv1")
                .add("iso_time", "2021-09-28T12:33:00.218963Z")
                .add("pressure", "101166.07255874875")
                .build();
        assertEquals(expectedPressObj, dataTransformService.toDataLoggerRequest(pressMsg));

        String humidMsg = "{\"event_id\":\"d075d50b-c81d-43c4-b23a-19c4e9141d59\",\"created_at\":\"2021-09-28T12:33:10.014476Z\"," +
                "\"read_at\":\"2021-09-28T12:33:09.966873Z\",\"device_id\":\"0001\",\"device_name\":\"growlabv1\"," +
                "\"data\":{\"unit\":\"%\",\"type\":\"HUMID\",\"value\":\"55.86345305351051\"}}";
        JsonObject expectedHumidObj = Json.createObjectBuilder()
                .add("sensor", "growlabv1")
                .add("iso_time", "2021-09-28T12:33:09.966873Z")
                .add("humidity", "55.86345305351051")
                .build();
        assertEquals(expectedHumidObj, dataTransformService.toDataLoggerRequest(humidMsg));
    }
}
