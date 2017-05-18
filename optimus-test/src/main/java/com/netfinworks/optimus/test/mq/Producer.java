package com.netfinworks.optimus.test.mq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.netfinworks.optimus.entity.BaseEntity;

/**
 * mq生产者
 * @author baodekang
 *
 */
public class Producer {

	private String user = ActiveMQConnection.DEFAULT_USER;
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private String subject = "DepositOrder";
	private Destination destination = null;
	private Connection connection = null;
	private Session session = null;
	private MessageProducer producer = null;
	
	private void initialize() throws JMSException{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue(subject);
		producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}
	
	public void produceMessage(BaseEntity entity) throws JMSException{
		initialize();
		connection.start();
		ObjectMessage objectMessage = session.createObjectMessage();
		objectMessage.setObject(entity);
		producer.send(objectMessage);
		System.out.println("Producer:->Message sent complete!");
	}
	
	public void close() throws JMSException{
		System.out.println("Producer:->Closing connection");
		if(producer != null){
			producer.close();
		}
		if(session != null){
			session.close();
		}
		if(connection != null){
			connection.close();
		}
	}
}
