package space.manukyan.embeddedbroker;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.qpid.server.SystemLauncher;

public class EmbeddedBroker {

    private static final String CONFIGURATION_FILE = "embedded-broker.json";

    private SystemLauncher systemLauncher;

    public EmbeddedBroker() {
    }

    public void start() throws Exception {
        systemLauncher = new SystemLauncher();
        systemLauncher.startup(getSystemConfig());
    }

    public void stop() {
        systemLauncher.shutdown();
        systemLauncher = null;
    }

    private Map<String, Object> getSystemConfig() {
        Map<String, Object> attributes = new HashMap<>();
        URL initialConfig = EmbeddedBroker.class.getClassLoader().getResource(CONFIGURATION_FILE);
        attributes.put("type", "Memory");
        attributes.put("initialConfigurationLocation", initialConfig.toExternalForm());
        attributes.put("startupLoggedToSystemOut", true);
        return attributes;
    }
}