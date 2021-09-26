package dev.sergevas.iot.cg.data.shipper.function.controller;

import dev.sergevas.iot.cg.data.shipper.datalogger.api.controller.DataLoggerRequestBuilder;
import dev.sergevas.iot.cg.data.shipper.function.model.DataType;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;
import java.io.StringReader;

import static dev.sergevas.iot.cg.data.shipper.function.model.SharedNames.*;

@ApplicationScoped
public class DataTransformService {

    @Inject
    Logger logger;

    public JsonObject toDataLoggerRequest(String sensorData) {
        JsonObject shipperRequestObj = this.readFromString(sensorData);
        String deviceName = shipperRequestObj.getString(DEVICE_NAME);
        String readAt = shipperRequestObj.getString(READ_AT);
        JsonObject dataObj = shipperRequestObj.getJsonObject(DATA);
        String typeCode = dataObj.getString(TYPE);
        DataType dataType = DataType.getByCode(typeCode);
        JsonObject dataLoggerRequestObj = null;
        DataLoggerRequestBuilder requestBuilder = new DataLoggerRequestBuilder()
                .sensor(deviceName)
                .isoTime(readAt);
        switch (dataType) {
            case TEMP:
                requestBuilder.temperature(dataObj.getString(VALUE));
                break;
            case HUMID:
                requestBuilder.humidity(dataObj.getString(VALUE));
                break;
            case PRESS:
                requestBuilder.pressure(dataObj.getString(VALUE));
                break;
            case LIGHT:
                requestBuilder.light(dataObj.getString(VALUE));
                break;
            case HEALTH:
                JsonObject healthObj = dataObj.getJsonObject(VALUE);
                JsonArray checksArr = healthObj.getJsonArray(CHECKS);
                String cpuTemperature = checksArr.stream()
                        .map(JsonValue::asJsonObject)
                        .filter(jsonObj -> SYSTEM_INFO.equals(jsonObj.getString(NAME)))
                        .findAny()
                        .map(jsonObj -> jsonObj.getJsonObject(DATA).getString(CPU_TEMP))
                        .orElse(null);
                requestBuilder.cpuTemperature(cpuTemperature);
        }
        dataLoggerRequestObj = requestBuilder.build();
        return dataLoggerRequestObj;
    }

    public JsonObject readFromString(String sensorData) {
        JsonReader reader = Json.createReader(new StringReader(sensorData));
        return reader.readObject();
    }
}
