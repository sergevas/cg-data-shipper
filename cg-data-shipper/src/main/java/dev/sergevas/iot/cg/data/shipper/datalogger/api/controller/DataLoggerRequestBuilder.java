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
    private static final String STATUS = "status";
    public static final String DISK_SPACE_TOTAL = "disk_space_total";
    public static final String DISK_SPACE_FREE = "disk_space_free";
    public static final String HEAP_MEMORY_TOTAL = "heap_memory_total";
    public static final String HEAP_MEMORY_FREE = "heap_memory_free";
    public static final String HEAP_MEMORY_MAX = "heap_memory_max";

    private String sensor;
    private Double cpuTemperature;
    private String isoTime;
    private Double temperature;
    private Double pressure;
    private Double humidity;
    private Double light;
    private String status;
    private Long diskSpaceTotal;
    private Long diskSpaceFree;
    private Long heapMemoryTotal;
    private Long heapMemoryFree;
    private Long heapMemoryMax;


    public DataLoggerRequestBuilder sensor(String sensor) {
        this.sensor = sensor;
        return this;
    }

    public DataLoggerRequestBuilder cpuTemperature(Double cpuTemperature) {
        this.cpuTemperature = cpuTemperature;
        return this;
    }

    public DataLoggerRequestBuilder isoTime(String isoTime) {
        this.isoTime = isoTime;
        return this;
    }

    public DataLoggerRequestBuilder temperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public DataLoggerRequestBuilder pressure(Double pressure) {
        this.pressure = pressure;
        return this;
    }

    public DataLoggerRequestBuilder humidity(Double humidity) {
        this.humidity = humidity;
        return this;
    }

    public DataLoggerRequestBuilder light(Double light) {
        this.light = light;
        return this;
    }

    public DataLoggerRequestBuilder status(String status) {
        this.status = status;
        return this;
    }

    public DataLoggerRequestBuilder diskSpaceTotal(Long diskSpaceTotal) {
        this.diskSpaceTotal = diskSpaceTotal;
        return this;
    }

    public DataLoggerRequestBuilder diskSpaceFree(Long diskSpaceFree) {
        this.diskSpaceFree = diskSpaceFree;
        return this;
    }

    public DataLoggerRequestBuilder heapMemoryTotal(Long heapMemoryTotal) {
        this.heapMemoryTotal = heapMemoryTotal;
        return this;
    }

    public DataLoggerRequestBuilder heapMemoryFree(Long heapMemoryFree) {
        this.heapMemoryFree = heapMemoryFree;
        return this;
    }

    public DataLoggerRequestBuilder heapMemoryMax(Long heapMemoryMax) {
        this.heapMemoryMax = heapMemoryMax;
        return this;
    }

    public JsonObject build() {
        JsonObjectBuilder job = Json.createObjectBuilder();
        addStringIfNotNull(job, SENSOR, sensor)
                .addStringIfNotNull(job, ISO_TIME, isoTime)
                .addDoubleIfNotNull(job, CPU_TEMPERATURE, cpuTemperature)
                .addDoubleIfNotNull(job, TEMPERATURE, temperature)
                .addDoubleIfNotNull(job, PRESSURE, pressure)
                .addDoubleIfNotNull(job, HUMIDITY, humidity)
                .addDoubleIfNotNull(job, LIGHT, light)
                .addStringIfNotNull(job, STATUS, status)
                .addLongIfNotNull(job, DISK_SPACE_TOTAL, diskSpaceTotal)
                .addLongIfNotNull(job, DISK_SPACE_FREE, diskSpaceFree)
                .addLongIfNotNull(job, HEAP_MEMORY_TOTAL, heapMemoryTotal)
                .addLongIfNotNull(job, HEAP_MEMORY_FREE, heapMemoryFree)
                .addLongIfNotNull(job, HEAP_MEMORY_MAX, heapMemoryMax);
        return job.build();
    }

    public DataLoggerRequestBuilder addStringIfNotNull(JsonObjectBuilder jsonObjectBuilder,  String name, String value) {
        Optional.ofNullable(value)
                .ifPresent(v -> jsonObjectBuilder.add(name, value));
        return this;
    }

    public DataLoggerRequestBuilder addLongIfNotNull(JsonObjectBuilder jsonObjectBuilder, String name, Long value) {
        Optional.ofNullable(value)
                .ifPresent(v -> jsonObjectBuilder.add(name, value));
        return this;
    }

    public DataLoggerRequestBuilder addDoubleIfNotNull(JsonObjectBuilder jsonObjectBuilder,  String name, Double value) {
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
                && Objects.equals(light, that.light) && Objects.equals(status, that.status)
                && Objects.equals(diskSpaceTotal, that.diskSpaceTotal) && Objects.equals(diskSpaceFree, that.diskSpaceFree)
                && Objects.equals(heapMemoryTotal, that.heapMemoryTotal) && Objects.equals(heapMemoryFree, that.heapMemoryFree)
                && Objects.equals(heapMemoryMax, that.heapMemoryMax);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensor, cpuTemperature, isoTime, temperature, pressure, humidity, light, status,
                diskSpaceTotal, diskSpaceFree, heapMemoryTotal, heapMemoryFree, heapMemoryMax);
    }

    @Override
    public String toString() {
        return "DataLoggerRequestBuilder{" +
                "sensor='" + sensor + '\'' +
                ", cpuTemperature=" + cpuTemperature +
                ", isoTime='" + isoTime + '\'' +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", light=" + light +
                ", status='" + status + '\'' +
                ", diskSpaceTotal=" + diskSpaceTotal +
                ", diskSpaceFree=" + diskSpaceFree +
                ", heapMemoryTotal=" + heapMemoryTotal +
                ", heapMemoryFree=" + heapMemoryFree +
                ", heapMemoryMax=" + heapMemoryMax +
                '}';
    }
}
