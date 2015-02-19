package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import convertors.JobConverter;
import convertors.MeasurementConverter;
import datamodel.Job;
import datamodel.Measurement;
import httpmanager.RequestSenderWithMessage;
import mongo.MongoManager;

import java.io.IOException;

/**
 * Received messages that come from a queue
 */
public class Receiver {
    private Channel channel;
    private QueueingConsumer consumer;
    private Connection connection;
    private final String hostName;
    private final String queueName;
    private RabbitMqConfig rmqConf = new RabbitMqConfig();

    /**
     * Set parameters for RabbitMQ receiver
     */
    public Receiver() {
        hostName = rmqConf.getHost();
        queueName = rmqConf.getReceiveQueue();
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            /*
            * a client can be assigned only 1 message, the next message will be
            * assigned only after it finishes processing the first message
            */
            int prefetchCount = 1;

            channel.basicQos(prefetchCount);
            boolean durable = true;
            channel.queueDeclare(queueName, durable, false, false, null);
            consumer = new QueueingConsumer(channel);
            boolean autoAck = false;
            channel.basicConsume(queueName, autoAck, consumer);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Listen for messages
     */
    public void startReceiving() {
        while (true) {
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                Job job = JobConverter.jsonStringToJob(message);

                MongoManager mm = new MongoManager();
                String measurementString = mm.pullJsonById(job.getId());
                mm.closeConnection();

                // ugly remove of oid
                measurementString = measurementString.replace("{ \"$oid\" : \""  + job.getId() + "\"}", "\"" + job.getId() + "\"");

                Measurement measurement = MeasurementConverter.jsonStringToMeasurement(measurementString);

                //System.out.println(measurement.getJsonDocument());
                RequestSenderWithMessage.sendRequest("http://localhost:8008/jobFinished", "This is it!");

                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                System.out.println("[X] Done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Close receiving connection.
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}