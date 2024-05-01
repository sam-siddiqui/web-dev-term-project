package com.MusicStore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.MusicStore.Entity.Customer;
import com.MusicStore.Repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository custRepo;

	public void save(@NonNull Customer c) {
		custRepo.save(c);
	}

	public List<Customer> getAllCustomer() {
		return custRepo.findAll();

	}
}
