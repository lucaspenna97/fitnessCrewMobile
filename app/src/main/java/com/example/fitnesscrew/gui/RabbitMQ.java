package com.example.fitnesscrew.gui;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.sql.Date;

public class RabbitMQ {

    public static final String userName = "zonk";
    public static final String password = "zonk";
    public static final String host = "http://191.252.201.38:15672";
    public static final String virtualHost = "/";
    public static final int portNumber = 5672;

    public RabbitMQ () {}

    public static void sendRabbitMQMessage (String nameExchange, String nameQueue, String nameBind, String message) throws Exception{


        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setHost(host);
        connectionFactory.setPort(portNumber);
        connectionFactory.setVirtualHost(virtualHost);

        Connection connection = connectionFactory.newConnection();
        if (connection == null) return;

        System.out.println("Conexão RabbitMq estabelida: ");
        System.out.println("Porta: " + connection.getPort() + " Endereço: " + connection.getAddress());

        AMQP.BasicProperties properties = generateRabbitMQBasicProperties("86400000", "fitnessCrew", "Fitness Crew App", "text/plain");

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(nameExchange, "direct", true);
        channel.queueDeclare(nameQueue, true, false, false, null);
        channel.queueBind(nameQueue, nameExchange, nameBind);
        channel.basicPublish(nameExchange, nameBind, properties, message.getBytes());

        System.out.println("Mensagem enviada ao RabbitMQ");

        channel.close();
        connection.close();

    }

    public static void receiveRabbitMQMessage (String nameQueue) {

        try {

            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setUsername(userName);
            connectionFactory.setPassword(password);
            connectionFactory.setHost(host);
            connectionFactory.setPort(portNumber);
            connectionFactory.setVirtualHost(virtualHost);

            Connection connection = connectionFactory.newConnection();
            if (connection == null) return;

            System.out.println("Conexão RabbitMq estabelida: ");
            System.out.println("Porta: " + connection.getPort() + " Endereço: " + connection.getAddress());

            Channel channel = connection.createChannel();
            channel.basicConsume(nameQueue, true,  (s, delivery) -> {

                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("Mensagem: " + message);

            }, s -> {

                System.out.println("Erro ao receber mensagem do RabbitMQ.");

            });

        } catch (Exception e) {
            System.err.println("Erro ao conectar com o RabbitMQ: " + e.getMessage());
        }
    }

    public static void countMessagesAndConsumers (String nameQueue) {

        try {

            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setUsername(userName);
            connectionFactory.setPassword(password);
            connectionFactory.setHost(host);
            connectionFactory.setPort(portNumber);
            connectionFactory.setVirtualHost(virtualHost);

            Connection connection = connectionFactory.newConnection();
            if (connection == null) return;

            System.out.println("Conexão RabbitMq estabelida: ");
            System.out.println("Porta: " + connection.getPort() + " Endereço: " + connection.getAddress());

            Channel channel = connection.createChannel();
            AMQP.Queue.DeclareOk response = channel.queueDeclarePassive(nameQueue);

            System.out.println(response.getMessageCount() + " mensagens na fila " + nameQueue);
            System.out.println(response.getConsumerCount() + " consumidores na fila " + nameQueue);

        } catch (Exception e) {
            System.err.println("Erro ao conectar com o RabbitMQ: " + e.getMessage());
        }
    }

    public static AMQP.BasicProperties generateRabbitMQBasicProperties (String expiration, String userId, String appId, String contentType) {

        int nonPersistentMessage = 1;
        int persistentMessage = 2;

        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(persistentMessage);
        builder.expiration(expiration);
        builder.priority(9);
        builder.userId(userId);
        builder.appId(appId);
        builder.contentEncoding("UTF-8");
        builder.contentType(contentType);
        builder.timestamp(Date.valueOf(String.valueOf(System.currentTimeMillis())));

        return builder.build();
    }

}