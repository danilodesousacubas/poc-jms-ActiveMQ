package br.com.poc.activemq.queue.producer;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProducerQueueClientAcknowledge {

	//context
	//pega connectionFacotry por lookup
	//do connectinFacorty pega a connection
	//inializa a connection
	//com a connection cria a sessao(seta o tipo de sessao , auto_acknowledge, client acknowledge), 
	//com o context, pega o destination através de lookup
	//com a session cria o produtor
	//com a session cria a messageText
	//com o produtor envia a mensagem
	//fecha sessao, fecha connection, fecha o contexto
	
	public static void main (String args[]) throws NamingException, JMSException{
		InitialContext context = new InitialContext();
		ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = cf.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination queueDestination = (Destination) context.lookup("financeiro");
		MessageProducer mp = session.createProducer(queueDestination);
		
		Message message = session.createTextMessage("teste HAHA" + new Date());
		
		try{
			mp.send(message);
			System.out.println("producao da msg:  " + message.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		session.close();
		connection.close();
		context.close();
		
		
		
	}
}
