package com.mail.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.mail.app.model.MessagesScanned;

public interface MessagesScannedRepository extends CrudRepository<MessagesScanned, Integer> {

	public MessagesScanned findMessagesScannedById(Integer id);

}
