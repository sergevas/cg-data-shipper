package dev.sergevas.iot.cg.data.shipper.function.model;

import java.util.Arrays;

public enum DataType {

    LIGHT("LIGHT"),
    TEMP("TEMP"),
    HUMID("HUMID"),
    PRESS("PRESS"),
    HEALTH("HEALTH"),
    GROWER("GROWER");

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
