package dev.sergevas.iot.cg.data.shipper.datalogger.api.controller;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Objects;
import java.util.Optional;

public class DataLoggerRequestBuilder {

    public static final String BME280 = "BME280";

    private static final String SENSOR = "sensor";
    private static final String CPU_TEMPERATURE = "cpu_temperature";
    private static final String ISO_TIME = "iso_time";
    private static final String TIME = "time";
    private static final String TEMPERATURE = "temperature";
    private static final String PRESSURE = "pressure";
    private static final String HUMIDITY = "humidity";
    private static final String LIGHT = "light";

    private String sensor;
    private String cpuTemperature;
    private String isoTime;
    private String temperature;
    private String pressure;
    private String humidity;
    private String light;

    public DataLoggerRequestBuilder sensor(String sensor) {
        this.sensor = sensor;
        return this;
    }

    public DataLoggerRequestBuilder cpuTemperature(String cpuTemperature) {
        this.cpuTemperature = cpuTemperature;
        return this;
    }

    public DataLoggerRequestBuilder isoTime(String isoTime) {
        this.isoTime = isoTime;
        return this;
    }

    public DataLoggerRequestBuilder temperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public DataLoggerRequestBuilder pressure(String pressure) {
        this.pressure = pressure;
        return this;
    }

    public DataLoggerRequestBuilder humidity(String humidity) {
        this.humidity = humidity;
        return this;
    }

    public DataLoggerRequestBuilder light(String light) {
        this.light = light;
        return this;
    }

    public JsonObject build() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        addIfNotNull(job, SENSOR, sensor)
                .addIfNotNull(job, ISO_TIME, isoTime)
                .addIfNotNull(job, CPU_TEMPERATURE, cpuTemperature)
                .addIfNotNull(job, TEMPERATURE, temperature)
                .addIfNotNull(job, PRESSURE, pressure)
                .addIfNotNull(job, HUMIDITY, humidity)
                .addIfNotNull(job, LIGHT, light);
        return job.build();
    }

    public DataLoggerRequestBuilder addIfNotNull(JsonObjectBuilder jsonObjectBuilder,  String name, String value) {
        Optional.ofNullable(value)
                .ifPresent(v -> jsonObjectBuilder.add(name, value));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataLoggerRequestBuilder that = (DataLoggerRequestBuilder) o;
        return Objects.equals(sensor, that.sensor) && Objects.equals(cpuTemperature, that.cpuTemperature)
                && Objects.equals(isoTime, that.isoTime) && Objects.equals(temperature, that.temperature)
                && Objects.equals(pressure, that.pressure) && Objects.equals(humidity, that.humidity)
                && Objects.equals(light, that.light);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensor, cpuTemperature, isoTime, temperature, pressure, humidity, light);
    }

    @Override
    public String toString() {
        return "DataLoggerRequestBuilder{" +
                "sensor='" + sensor + '\'' +
                ", cpuTemperature='" + cpuTemperature + '\'' +
                ", isoTime='" + isoTime + '\'' +
                ", temperature='" + temperature + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                ", light='" + light + '\'' +
                '}';
    }
}
