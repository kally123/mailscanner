package com.mail.app.components;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class MailConfig {

	private Store store;

	public Store getStore() {
		return store;
	}

	public Store establishConnectionWithKey(Properties properties) {
		Session mailSession = Session.getDefaultInstance(properties, null);
		try {
			store = mailSession.getStore("imaps");
			store.connect(properties.get("mail.host") + "", properties.get("mail.user") + "",
					properties.get("mail.appkey") + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return store;
	}

	public void closeMail() throws MessagingException {
		store.close();
	}
}
