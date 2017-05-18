package com.netfinworks.optimus.test.mq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * mq消费者
 * @author baodekang
 *
 */
public class Consumer implements MessageListener{

	private String user = ActiveMQConnection.DEFAULT_USER;
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private String subject = "DepositOrder_back";
	private Destination destination = null;
	private Connection connection = null;
	private Session session = null;
	private MessageConsumer consumer = null;   //初始化 消息消费者  
	
	private void initialize() throws JMSException{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue(subject);
		consumer = session.createConsumer(destination);
	}
	
	public void start() throws JMSException{
		initialize();
		connection.start();
		System.out.println("Consumer:->Begin listening...");
		consumer.setMessageListener(this);
	}
	
	@Override
	public void onMessage(Message message) {
		// try {
		// if(message instanceof ObjectMessage){
		// ObjectMessage objMesg = (ObjectMessage) message;
		// DepositOrderEntity entity = (DepositOrderEntity) objMesg.getObject();
		// if(!"4".equals(entity.getOrderStatus())){
		// System.out.println("执行扣款");
		// }
		// }
		// else{
		// System.out.println("Consumer:->Received: " + message);
		// }
		// } catch (JMSException e) {
		// e.printStackTrace();
		// }
		
	}

}
