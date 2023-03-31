package dev.sergevas.iot.cg.data.shipper.function.controller;

import dev.sergevas.iot.cg.data.shipper.datalogger.api.controller.DataLoggerRequestBuilder;
import dev.sergevas.iot.cg.data.shipper.function.model.DataType;

import javax.enterprise.context.ApplicationScoped;
import javax.json.*;
import java.io.StringReader;
import java.util.Optional;

import static dev.sergevas.iot.cg.data.shipper.function.model.SharedNames.*;

@ApplicationScoped
public class DataTransformService {

    public JsonObject toDataLoggerRequest(String sensorData) {
        JsonObject shipperRequestObj = this.readFromString(sensorData);
        String deviceName = shipperRequestObj.getString(DEVICE_NAME);
        String readAt = shipperRequestObj.getString(READ_AT);
        JsonObject dataObj = shipperRequestObj.getJsonObject(DATA);
        String typeCode = dataObj.getString(TYPE);
        DataType dataType = DataType.getByCode(typeCode);
        JsonObject dataLoggerRequestObj;
        final DataLoggerRequestBuilder requestBuilder = new DataLoggerRequestBuilder()
                .sensor(deviceName)
                .isoTime(readAt);
        switch (dataType) {
            case TEMP:
                requestBuilder.temperature(toDouble(dataObj, VALUE));
                break;
            case HUMID:
                requestBuilder.humidity(toDouble(dataObj, VALUE));
                break;
            case PRESS:
                requestBuilder.pressure(toDouble(dataObj, VALUE));
                break;
            case LIGHT:
                requestBuilder.light(toDouble(dataObj, VALUE));
                break;
            case HEALTH:
                JsonObject healthObj = dataObj.getJsonObject(VALUE);
                requestBuilder.status(healthObj.getString(STATUS));

                JsonArray checksArr = healthObj.getJsonArray(CHECKS);

                checksArr.stream()
                        .map(JsonValue::asJsonObject)
                        .filter(jsonObj -> SYSTEM_INFO.equals(jsonObj.getString(NAME)))
                        .findAny()
                        .map(jsonObj -> jsonObj.getJsonObject(DATA).getString(CPU_TEMP))
                        .map(Double::valueOf)
                        .ifPresent(cpuTemperature -> requestBuilder.cpuTemperature(cpuTemperature));
                ;

                checksArr.stream()
                        .map(JsonValue::asJsonObject)
                        .filter(jsonObj -> DISK_SPACE.equals(jsonObj.getString(NAME)))
                        .findAny()
                        .map(jsonObj -> jsonObj.getJsonObject(DATA).getJsonNumber(FREE_BYTES))
                        .map(JsonNumber::longValue)
                        .ifPresent(diskSpaceFree -> requestBuilder.diskSpaceFree(diskSpaceFree));

                checksArr.stream()
                        .map(JsonValue::asJsonObject)
                        .filter(jsonObj -> DISK_SPACE.equals(jsonObj.getString(NAME)))
                        .findAny()
                        .map(jsonObj -> jsonObj.getJsonObject(DATA).getJsonNumber(TOTAL_BYTES))
                        .map(JsonNumber::longValue)
                        .ifPresent(diskSpaceTotal -> requestBuilder.diskSpaceTotal(diskSpaceTotal));

                checksArr.stream()
                        .map(JsonValue::asJsonObject)
                        .filter(jsonObj -> HEAP_MEMORY.equals(jsonObj.getString(NAME)))
                        .findAny()
                        .map(jsonObj -> jsonObj.getJsonObject(DATA).getJsonNumber(MAX_BYTES))
                        .map(JsonNumber::longValue)
                        .ifPresent(heapMemoryMax -> requestBuilder.heapMemoryMax(heapMemoryMax));

                final Long heapMemoryTotal = checksArr.stream()
                        .map(JsonValue::asJsonObject)
                        .filter(jsonObj -> HEAP_MEMORY.equals(jsonObj.getString(NAME)))
                        .findAny()
                        .map(jsonObj -> jsonObj.getJsonObject(DATA).getJsonNumber(TOTAL_BYTES))
                        .map(JsonNumber::longValue)
                        .orElse(null);
                requestBuilder.heapMemoryTotal(heapMemoryTotal);

                final Long heapMemoryFree = checksArr.stream()
                        .map(JsonValue::asJsonObject)
                        .filter(jsonObj -> HEAP_MEMORY.equals(jsonObj.getString(NAME)))
                        .findAny()
                        .map(jsonObj -> jsonObj.getJsonObject(DATA).getJsonNumber(FREE_BYTES))
                        .map(JsonNumber::longValue)
                        .orElse(null);
                requestBuilder.heapMemoryFree(heapMemoryFree);

                Optional.ofNullable(heapMemoryTotal)
                        .ifPresent(hmt -> Optional.ofNullable(heapMemoryFree)
                                .ifPresent(hmf -> requestBuilder
                                        .heapMemoryUsed(heapMemoryTotal - heapMemoryFree)));
                break;
            case GROWER:
                JsonObject growerObj = dataObj.getJsonObject(VALUE);
                requestBuilder.deviceId(growerObj.getString(DEVICE_ID));
                requestBuilder.soilTemp(toDouble(growerObj, SOIL_TEMP));
                requestBuilder.soilMoisture(toDouble(growerObj, SOIL_MOISTURE));
                requestBuilder.pumpStat(growerObj.getString(PUMP_STAT));
        }
        dataLoggerRequestObj = requestBuilder.build();
        return dataLoggerRequestObj;
    }

    public JsonObject readFromString(String sensorData) {
        JsonReader reader = Json.createReader(new StringReader(sensorData));
        return reader.readObject();
    }

    public Double toDouble(JsonObject dataObj, String field) {
        return Optional.ofNullable(dataObj.getString(field))
                .map(Double::valueOf)
                .orElse(null);
    }
}
