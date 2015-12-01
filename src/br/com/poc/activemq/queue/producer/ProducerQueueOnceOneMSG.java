package br.com.poc.activemq.queue.producer;

import java.util.Date;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class ProducerQueueOnceOneMSG {

    @SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

    	System.out.println("PRODUCER ONCE MSG START");
    	
        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection(); 
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("financeiro");
        MessageProducer producer = session.createProducer(fila);

        String Msg = "<pedido><id>55</id><data>" 
                + new Date() +"</data></pedido>";
        
        System.out.println(" produziu -> " + Msg);
        
        Message message = session.createTextMessage(Msg);
        producer.send(message);

        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
    }
}