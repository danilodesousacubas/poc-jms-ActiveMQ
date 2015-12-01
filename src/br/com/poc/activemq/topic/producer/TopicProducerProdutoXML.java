package br.com.poc.activemq.topic.producer;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;

import br.com.poc.activemq.modelo.Pedido;
import br.com.poc.activemq.modelo.PedidoFactory;

public class TopicProducerProdutoXML {

	  public static void main(String[] args) throws Exception {

		  //Inicializa o contexto
		  //Pega (lookup) a connectionFactory do middleware ou do brocker
		  //Cria a connection a partir da connectionFactory
		  //Cria a sessão a partir da conexão
		  //Pega a Queue Destination por lookup
		  //cria o produtor/consumidor através da sessao e passa a destination Queue/topico com parametro
		  
	        InitialContext context = new InitialContext();
	        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

	        Connection connection = factory.createConnection();
	        connection.start();
	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	        Destination topico = (Destination) context.lookup("loja");
	        MessageProducer producer = session.createProducer(topico);

	        StringWriter writer = new StringWriter();
	        
	        PedidoFactory pedidoFactory = new PedidoFactory();
	        Pedido pedido = pedidoFactory.geraPedidoComValores();
	        
	        JAXB.marshal(pedido, writer);
	        
	        String xml = writer.toString();
	        TextMessage message = session.createTextMessage(xml);
	      
	        try{
	        
	        	producer.send(message);
	        	System.out.println("produzindo " + message.getText());

	        }catch (Exception e){
	        	System.out.println("erro geral");
	        	e.printStackTrace();
	        }
	        		
	        session.close();
	        connection.close();
	        context.close();
	    }
}