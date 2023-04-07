package dev.sergevas.iot.cg.data.shipper.adapter.in.messaging;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterForReflection
public class NatsReadingsEventReceiver extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("nats:{{cg.nats.subject.root}}.>?servers={{nats.username}}:{{nats.password}}@{{nats.servers}}" +
                "&maxReconnectAttempts={{nats.max.reconnects}}&exchangePattern=InOnly" +
                "&headerFilterStrategy=#removeAllHeaderFilterStrategy")
                .bean("cgNatsInboundMessageHandler", "handle");
    }
}
