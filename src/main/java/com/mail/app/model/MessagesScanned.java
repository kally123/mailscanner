package com.mail.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messagesscanned")
public class MessagesScanned {

	@Id
	private int id;
	private Integer messagesScanned;

	public MessagesScanned() {
	}

	public MessagesScanned(Integer messagesScanned) {
		this.messagesScanned = messagesScanned;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getMessagesScanned() {
		return messagesScanned;
	}

	public void setMessagesScanned(Integer messagesScanned) {
		this.messagesScanned = messagesScanned;
	}

}
