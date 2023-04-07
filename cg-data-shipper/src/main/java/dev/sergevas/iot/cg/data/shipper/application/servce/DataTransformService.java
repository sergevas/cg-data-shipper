package dev.sergevas.iot.cg.data.shipper.application.servce;

import dev.sergevas.iot.cg.data.shipper.application.domain.DataType;

import javax.enterprise.context.ApplicationScoped;
import javax.json.*;
import java.io.StringReader;
import java.util.Optional;

@ApplicationScoped
public class DataTransformService {
    public static final String DEVICE_NAME = "device_name";
    public static final String READ_AT = "read_at";
    public static final String DATA = "data";
    public static final String TYPE = "type";
    public static final String VALUE = "value";
    public static final String CHECKS = "checks";
    public static final String NAME = "name";
    public static final String SYSTEM_INFO = "systemInfo";
    public static final String CPU_TEMP = "cpuTemp";
    public static final String STATUS = "status";
    public static final String DISK_SPACE = "diskSpace";
    public static final String HEAP_MEMORY = "heapMemory";
    public static final String FREE_BYTES = "freeBytes";
    public static final String MAX_BYTES = "maxBytes";
    public static final String TOTAL_BYTES = "totalBytes";

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
                requestBuilder.temperature(toDoubleFromString(dataObj, VALUE));
                break;
            case HUMID:
                requestBuilder.humidity(toDoubleFromString(dataObj, VALUE));
                break;
            case PRESS:
                requestBuilder.pressure(toDoubleFromString(dataObj, VALUE));
                break;
            case LIGHT:
                requestBuilder.light(toDoubleFromString(dataObj, VALUE));
                break;
            case CAMERA_MODE:
                requestBuilder.cameraMode(dataObj.getString(VALUE));
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
            case SOIL_TEMP:
                requestBuilder.soilTemp(toDoubleFromNumber(dataObj, VALUE));
                break;
            case SOIL_MOISTURE:
                requestBuilder.soilMoisture(toDoubleFromNumber(dataObj, VALUE));
                break;
            case PUMP_STATE:
                requestBuilder.pumpStat(dataObj.getString(VALUE));
                break;
        }
        dataLoggerRequestObj = requestBuilder.build();
        return dataLoggerRequestObj;
    }

    public JsonObject readFromString(String sensorData) {
        JsonReader reader = Json.createReader(new StringReader(sensorData));
        return reader.readObject();
    }

    public Double toDoubleFromString(JsonObject dataObj, String field) {
        return Optional.ofNullable(dataObj.getString(field))
                .map(Double::valueOf)
                .orElse(null);
    }

    public Double toDoubleFromNumber(JsonObject dataObj, String field) {
        return Optional.ofNullable(dataObj.getJsonNumber(field))
                .map(JsonNumber::doubleValue)
                .orElse(null);
    }
}
