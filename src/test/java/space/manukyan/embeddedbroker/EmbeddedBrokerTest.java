package space.manukyan.embeddedbroker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmbeddedBrokerTest {

    private static final String QUEUE_NAME = "my-queue";
    EmbeddedBroker broker;

    @Before
    public void setUp() throws Exception {
        broker = new EmbeddedBroker();
        broker.start();
    }

    @After
    public void tearDown() throws Exception {
        broker.stop();
    }

    @Test
    public void testSend() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
