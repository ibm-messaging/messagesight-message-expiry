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

package com.ibm.colgrave.samples.messagesight.expiry.subscriber;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTT_Callback implements MqttCallback {
	static String copyright() {
		return com.ibm.colgrave.samples.messagesight.expiry.subscriber.Copyright.IBM_COPYRIGHT;
	}

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("MQTT_Callback.connectionLost");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		if (topic.equals("expirationTest")) {
			System.out.println("Received message " + message.toString());
		} else {
			System.err.println("Received a message on an unexpected topic: " + topic);
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("MQTT_Callback.deliveryComplete");
	}
}