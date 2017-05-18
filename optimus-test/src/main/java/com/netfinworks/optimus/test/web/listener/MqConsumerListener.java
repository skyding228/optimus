package com.netfinworks.optimus.test.web.listener;

import javax.jms.JMSException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.netfinworks.optimus.test.mq.Consumer;

public class MqConsumerListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Consumer consumer = new Consumer();
		try {
			consumer.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
