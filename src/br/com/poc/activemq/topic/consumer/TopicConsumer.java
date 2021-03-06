package br.com.poc.activemq.topic.consumer;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TopicConsumer {
	 @SuppressWarnings("resource")
	    public static void main(String[] args) throws Exception {

	        InitialContext context = new InitialContext();
	        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
	       
	        Connection connection = factory.createConnection(); 
	        
	        connection.setClientID("estoque");
	        
	        connection.start();
	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	        Destination topico = (Destination) context.lookup("loja");
	        MessageConsumer consumer = session.createConsumer(topico );

	        consumer.setMessageListener(new MessageListener() {

	        	
	            @Override
	            public void onMessage(Message message) {
	            	System.out.println("consumindo !!!");
	                TextMessage textMessage = (TextMessage) message;

	                try {
	                    System.out.println(textMessage.getText());
	                } catch (JMSException e) {
	                    e.printStackTrace();
	                }
	            }

	        });


	        new Scanner(System.in).nextLine();

	        session.close();
	        connection.close();
	        context.close();
	    }
}
