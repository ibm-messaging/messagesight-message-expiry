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

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MQTT_Subscriber {
	static String copyright() {
		return com.ibm.colgrave.samples.messagesight.expiry.subscriber.Copyright.IBM_COPYRIGHT;
	}

	public static void main(String[] args) {
		boolean validArgs = false;
		boolean cleanSession = false;
		boolean subscribe = false;
		String serverURI = null;

		if (args.length == 1) {
			serverURI = args[0];
			cleanSession = true;
			subscribe = true;
			validArgs = true;
		} else if (args.length == 2) {
			serverURI = args[0];
			cleanSession = false;
			subscribe = Boolean.parseBoolean(args[1]);
			validArgs = true;
		}

		if (validArgs == false) {
			System.err.println("usage: MQTT_Subscriber <URL> <true|false>");
			System.err.println("Valid combinations of arguments are:");
			System.err
					.println("URL - connect with cleanSession = true, establish subscription");
			System.err
					.println("URL true - connect with cleanSession = false, establish durable subscription");
			System.err
					.println("URL false - connect with cleanSession = false, do not establish subscription (a durable subscription must already exist for this to be useful");
		} else {
			MqttClient client;
			try {
				client = new MqttClient(serverURI, "MQTT_Subscriber");
				if (client != null) {
					client.setCallback(new MQTT_Callback());
					MqttConnectOptions connectOptions = new MqttConnectOptions();
					connectOptions.setCleanSession(cleanSession);
					client.connect(connectOptions);
					System.out.println("Have connected");
					if (subscribe) {
						client.subscribe("expirationTest");
						System.out.println("Have subscribed");
					}
				}
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
	}

}