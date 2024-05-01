package com.MusicStore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.MusicStore.Entity.Paybill;
import com.MusicStore.Repository.PaybillingRepository;
@Service
public class PaybillService {
	
	@Autowired
	private PaybillingRepository paybillRepo;

	public void save(@NonNull Paybill paybill) {
		paybillRepo.save(paybill);
	}

	public List<Paybill> getAllpayBilling() {
		return paybillRepo.findAll();

	}

	public Paybill getpayBillingbyId(int id) {
		return paybillRepo.findById(id).get();
	}

	public void deleteById(int id) {
		paybillRepo.deleteById(id);
	}
	

}
