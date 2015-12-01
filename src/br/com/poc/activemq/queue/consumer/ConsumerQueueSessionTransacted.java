package br.com.poc.activemq.queue.consumer;

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

public class ConsumerQueueSessionTransacted {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		//initial context
		//factory
		//connection
		//cria session
		//lockup dest
		//session.creatmessage(dest)
		
		
		InitialContext context = new InitialContext();
		
		//cria factory
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		// peta Conexao
		Connection connection = factory.createConnection();
		connection.start();

		//Cria session
		Session session = connection.createSession(false, Session.SESSION_TRANSACTED);
		
		//Consume da fila fisica
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);
		
		  consumer.setMessageListener(new MessageListener() {

	            @Override
	            public void onMessage(Message message) {

	                TextMessage textMessage = (TextMessage) message;
	                try {
//						message.acknowledge();
	                	session.commit();
	                    System.out.println("consumiu " + textMessage.getText());
	                } catch (JMSException e) {
	                    e.printStackTrace();
					}
	            }

	        });
		  
		  new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
		
		System.out.println("acabou close tudo");
	}
}
