package dev.sergevas.iot.cg.data.shipper.function.controller;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;

@ApplicationScoped
public class DataTransformService {

    @Inject
    Logger logger;

    public JsonObject toRequest() {
        return null;
    }
}
