package com.mail.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.mail.app.model.InvalidCustomer;

public interface InvalidCustomerRepository extends CrudRepository<InvalidCustomer, Long> {

}
