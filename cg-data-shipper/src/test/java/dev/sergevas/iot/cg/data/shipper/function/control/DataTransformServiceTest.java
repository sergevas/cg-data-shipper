package dev.sergevas.iot.cg.data.shipper.function.control;

import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataTransformServiceTest {

    @Test
    void toRequest() {
        DataTransformService dataTransformService = new DataTransformService();
        String healthMsg = """
                {
                  "event_id": "1892eb49-2f3e-44e6-94a6-b72485b31dfa",
                  "created_at": "2021-09-28T11:38:50.307868Z",
                  "read_at": "2021-09-28T11:38:49Z",
                  "device_id": "0001",
                  "device_name": "growlabv1",
                  "data": {
                    "type": "HEALTH",
                    "value": {
                      "checks": [
                        {
                          "name": "deadlock",
                          "status": "UP"
                        },
                        {
                          "data": {
                            "total": "28.98 GB",
                            "percentFree": "69.73%",
                            "totalBytes": 31116636160,
                            "free": "20.21 GB",
                            "freeBytes": 21696647168
                          },
                          "name": "diskSpace",
                          "status": "UP"
                        },
                        {
                          "data": {
                            "total": "31.07 MB",
                            "max": "61.88 MB",
                            "percentFree": "66.43%",
                            "totalBytes": 32575488,
                            "maxBytes": 64880640,
                            "free": "10.29 MB",
                            "freeBytes": 10793312
                          },
                          "name": "heapMemory",
                          "status": "UP"
                        },
                        {
                          "data": {
                            "cpuTemp": "43.312"
                          },
                          "name": "systemInfo",
                          "status": "UP"
                        }
                      ],
                      "status": "UP"
                    }
                  }
                }""";
        JsonObject expectedHealthObj = Json.createObjectBuilder()
                .add("sensor", "growlabv1")
                .add("iso_time", "2021-09-28T11:38:49Z")
                .add("status", "UP")
                .add("cpu_temperature", 43.312)
                .add("disk_space_total", 31116636160L)
                .add("disk_space_free", 21696647168L)
                .add("heap_memory_total", 32575488)
                .add("heap_memory_free", 10793312)
                .add("heap_memory_max", 64880640)
                .add("heap_memory_used", 21782176)
                .build();
        assertEquals(expectedHealthObj, dataTransformService.toDataLoggerRequest(healthMsg));

        String lightMsg = """
                {
                  "event_id": "9949c724-c0d3-4b1b-8dfc-f56bc6c86339",
                  "created_at": "2021-09-28T11:38:30.227855Z",
                  "read_at": "2021-09-28T11:38:30.210143Z",
                  "device_id": "0001",
                  "device_name": "growlabv1",
                  "data": {
                    "unit": "lx",
                    "type": "LIGHT",
                    "value": "1783.33"
                  }
                }""";
        JsonObject expectedLightObj = Json.createObjectBuilder()
                .add("sensor", "growlabv1")
                .add("iso_time", "2021-09-28T11:38:30.210143Z")
                .add("light", 1783.33)
                .build();
        assertEquals(expectedLightObj, dataTransformService.toDataLoggerRequest(lightMsg));

        String tempMsg = """
                   {
                              "event_id": "18acbe97-b681-4aa7-83e7-1ccdd85e71c7",
                              "created_at": "2021-09-28T12:33:00.265483Z",
                              "read_at": "2021-09-28T12:33:00.218963Z",
                              "device_id": "0001",
                              "device_name": "growlabv1",
                              "data": {
                                "unit": "°C",
                                "type": "TEMP",
                                "value": "23.742282452486688"
                              }
                            }
                """;
        JsonObject expectedTempObj = Json.createObjectBuilder()
                .add("sensor", "growlabv1")
                .add("iso_time", "2021-09-28T12:33:00.218963Z")
                .add("temperature", 23.742282452486688)
                .build();
        assertEquals(expectedTempObj, dataTransformService.toDataLoggerRequest(tempMsg));

        String pressMsg = """
                {
                  "event_id": "c3b5b0ef-601a-4936-ad72-3b6e9769e391",
                  "created_at": "2021-09-28T12:33:00.267113Z",
                  "read_at": "2021-09-28T12:33:00.218963Z",
                  "device_id": "0001",
                  "device_name": "growlabv1",
                  "data": {
                    "unit": "hPa",
                    "type": "PRESS",
                    "value": "101166.07255874875"
                  }
                }""";
        JsonObject expectedPressObj = Json.createObjectBuilder()
                .add("sensor", "growlabv1")
                .add("iso_time", "2021-09-28T12:33:00.218963Z")
                .add("pressure", 101166.07255874875)
                .build();
        assertEquals(expectedPressObj, dataTransformService.toDataLoggerRequest(pressMsg));

        String humidMsg = """
                                
                {
                  "event_id": "d075d50b-c81d-43c4-b23a-19c4e9141d59",
                  "created_at": "2021-09-28T12:33:10.014476Z",
                  "read_at": "2021-09-28T12:33:09.966873Z",
                  "device_id": "0001",
                  "device_name": "growlabv1",
                  "data": {
                    "unit": "%",
                    "type": "HUMID",
                    "value": "55.86345305351051"
                  }
                }""";
        JsonObject expectedHumidObj = Json.createObjectBuilder()
                .add("sensor", "growlabv1")
                .add("iso_time", "2021-09-28T12:33:09.966873Z")
                .add("humidity", 55.86345305351051)
                .build();
        assertEquals(expectedHumidObj, dataTransformService.toDataLoggerRequest(humidMsg));

        String cameraModeMsg = """
                {
                  "event_id": "d09d44f4-cb22-4f27-a8c4-64f1e4494705",
                  "created_at": "2023-04-04T20:18:12.86087631Z",
                  "topic": "cg.growlabv1.CAMERA_MODE",
                  "read_at": "2023-04-04T20:18:12.846029Z",
                  "device_id": "0001",
                  "device_name": "growlabv1",
                  "data": {
                    "type": "CAMERA_MODE",
                    "value": "NIGHT"
                  }
                }""";

        JsonObject expectedCameraModeObj = Json.createObjectBuilder()
                .add("sensor", "growlabv1")
                .add("iso_time", "2023-04-04T20:18:12.846029Z")
                .add("camera_mode", "NIGHT")
                .build();
        assertEquals(expectedCameraModeObj, dataTransformService.toDataLoggerRequest(cameraModeMsg));

        String growerSoilTempMsg = """
                {
                    "device_type": "GROWER",
                    "event_id": "5ac84638-fd04-4238-9337-e6b1b9cb710d",
                    "created_at": "2023-04-01T20:17:10.5270736Z",
                    "topic": "cg.0002.SOIL_TEMP",
                    "read_at": "2023-04-01T20:17:10.5250741Z",
                    "device_id": "0002",
                    "device_name": "Pepper Grow Bag 01",
                    "data": {
                      "unit": "°C",
                      "type": "SOIL_TEMP",
                      "value": 26.58
                    }
                  }""";
        JsonObject expectedGrowerSoilTempObj = Json.createObjectBuilder()
                .add("sensor", "Pepper Grow Bag 01")
                .add("iso_time", "2023-04-01T20:17:10.5250741Z")
                .add("soil_temp", 26.58)
                .build();
        assertEquals(expectedGrowerSoilTempObj, dataTransformService.toDataLoggerRequest(growerSoilTempMsg));

        String growerSoilMoistureMsg = """
                {
                     "device_type": "GROWER",
                     "event_id": "62c082c3-f00d-4279-9e9d-113134a92983",
                     "created_at": "2023-04-01T20:17:10.5300409Z",
                     "topic": "cg.0002.SOIL_MOISTURE",
                     "read_at": "2023-04-01T20:17:10.5250741Z",
                     "device_id": "0002",
                     "device_name": "Pepper Grow Bag 01",
                     "data": {
                       "unit": "%",
                       "type": "SOIL_MOISTURE",
                       "value": 5.3
                     }
                   }""";
        JsonObject expectedGrowerSoilMoistureObj = Json.createObjectBuilder()
                .add("sensor", "Pepper Grow Bag 01")
                .add("iso_time", "2023-04-01T20:17:10.5250741Z")
                .add("soil_moisture", 5.3)
                .build();
        assertEquals(expectedGrowerSoilMoistureObj, dataTransformService.toDataLoggerRequest(growerSoilMoistureMsg));

        String growerPumpStateMsg = """
                {
                      "device_type": "GROWER",
                      "event_id": "491379a6-bc10-4d6d-b3a1-dc1a19eb571b",
                      "created_at": "2023-04-01T20:17:10.5340389Z",
                      "topic": "cg.0002.PUMP_STATE",
                      "read_at": "2023-04-01T20:17:10.5250741Z",
                      "device_id": "0002",
                      "device_name": "Pepper Grow Bag 01",
                      "data": {
                        "type": "PUMP_STATE",
                        "value": "off"
                      }
                    }""";
        JsonObject expectedGrowerPumpStateObj = Json.createObjectBuilder()
                .add("sensor", "Pepper Grow Bag 01")
                .add("iso_time", "2023-04-01T20:17:10.5250741Z")
                .add("pump_stat", "off")
                .build();
        assertEquals(expectedGrowerPumpStateObj, dataTransformService.toDataLoggerRequest(growerPumpStateMsg));
    }
}
