package dev.sergevas.iot.cg.data.shipper.adapter.in.messaging;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.camel.Exchange;
import org.apache.camel.spi.HeaderFilterStrategy;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@RegisterForReflection
@Named("removeAllHeaderFilterStrategy")
public class RemoveAllHeaderFilterStrategy implements HeaderFilterStrategy {
    @Override
    public boolean applyFilterToCamelHeaders(String headerName, Object headerValue, Exchange exchange) {
        return true;
    }

    @Override
    public boolean applyFilterToExternalHeaders(String headerName, Object headerValue, Exchange exchange) {
        return false;
    }
}
