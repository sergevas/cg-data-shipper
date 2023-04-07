package dev.sergevas.iot.cg.data.shipper.application.domain;

import java.util.Arrays;

public enum DataType {

    LIGHT("LIGHT"),
    TEMP("TEMP"),
    HUMID("HUMID"),
    PRESS("PRESS"),
    CAMERA_MODE("CAMERA_MODE"),
    HEALTH("HEALTH"),
    SOIL_TEMP("SOIL_TEMP"),
    SOIL_MOISTURE("SOIL_MOISTURE"),
    PUMP_STATE("PUMP_STATE");

    private String code;

    DataType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static DataType getByCode(String code) {
        return Arrays.stream(DataType.values())
                .filter(t -> t.code.equals(code))
                .findAny()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "DataType{" +
                "code='" + code + '\'' +
                '}';
    }
}
