/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and other Contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors:
 * IBM - Initial Contribution
 *******************************************************************************/

package com.ibm.colgrave.samples.messagesight.expiry.publisher;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import com.ibm.ima.jms.ImaJmsException;
import com.ibm.ima.jms.ImaJmsFactory;
import com.ibm.ima.jms.ImaProperties;

public class JMS_Publisher {
	static String copyright() {
		return com.ibm.colgrave.samples.messagesight.expiry.publisher.Copyright.IBM_COPYRIGHT;
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("usage: JMS_Publisher <server> <port>");
		} else {
			TopicConnectionFactory connectionFactory;
			try {
				connectionFactory = ImaJmsFactory.createTopicConnectionFactory();
				ImaProperties imaProperties = (ImaProperties) connectionFactory;
				imaProperties.put("Server", args[0]);
				imaProperties.put("Port", args[1]);
				ImaJmsException[] exceptions = imaProperties.validate(false);
				if (exceptions != null) {
					for (ImaJmsException e : exceptions) {
						System.out.println(e.getMessage());
					}
					return;
				} else {
					TopicConnection connection = connectionFactory.createTopicConnection();
					String clientId = "JMS_Publisher";
					connection.setClientID(clientId);
					TopicSession session = connection.createTopicSession(false, Session.DUPS_OK_ACKNOWLEDGE);
					Topic topic = session.createTopic("expirationTest");
					TopicPublisher publisher = session.createPublisher(topic);
					TextMessage message1 = session.createTextMessage("message1");
					publisher.publish(message1);
					TextMessage message2 = session.createTextMessage("message2");
					// Send this with an expiration time ten seconds from now
					publisher.publish(message2, DeliveryMode.PERSISTENT, 3, 10000);
					TextMessage message3 = session.createTextMessage("message3");
					publisher.publish(message3);
					System.out.println("Messages published");
				}
			} catch (JMSException e2) {
				e2.printStackTrace();
			}
		}
	}
}
