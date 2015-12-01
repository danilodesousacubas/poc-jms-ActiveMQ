package br.com.poc.activemq.topic.producer;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TopicProducer {

	  public static void main(String[] args) throws Exception {

	        InitialContext context = new InitialContext();
	        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

	        Connection connection = factory.createConnection();
	        connection.start();
	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	        Destination topico = (Destination) context.lookup("loja");
	        MessageProducer producer = session.createProducer(topico);

	        String msg = " <pedido><id>2</id></pedido>" + new Date();
	        Message message = session.createTextMessage(msg);
	        producer.send(message);
	        System.out.println("produzindo " + msg);

	        session.close();
	        connection.close();
	        context.close();
	    }
}