package br.com.poc.activemq.queue.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class ConsumerQueueOnceOneMSG {

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
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//Consume da fila fisica
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);

		
		Message message = consumer.receive(30000);
		
		System.out.println("Recebendo msg: "+ message);

		//new Scanner(System.in).nextLine(); // parar o programa para testar a
		
		session.close();
		connection.close();
		context.close();
		
		System.out.println("acabou close tudo");
	}
}
