package dev.sergevas.iot.cg.data.shipper.datalogger.api.boundary;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import np.com.madanpokharel.embed.nats.EmbeddedNatsConfig;
import np.com.madanpokharel.embed.nats.EmbeddedNatsServer;
import np.com.madanpokharel.embed.nats.NatsServerConfig;
import np.com.madanpokharel.embed.nats.NatsVersion;

import java.util.Collections;
import java.util.Map;

public class NatsMockServer implements QuarkusTestResourceLifecycleManager {

    EmbeddedNatsServer natsServer;


    @Override
    public Map<String, String> start() {
        EmbeddedNatsConfig config = new EmbeddedNatsConfig.Builder()
                .withNatsServerConfig(new NatsServerConfig.Builder()
                        .withNatsVersion(NatsVersion.LATEST)
                        .build())
                .build();
        natsServer = new EmbeddedNatsServer(config);
        try {
            natsServer.startServer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Collections.emptyMap();
    }

    @Override
    public void stop() {
        try {
            natsServer.stopServer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
