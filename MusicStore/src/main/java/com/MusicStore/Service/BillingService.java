package com.MusicStore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.MusicStore.Entity.Billing;
import com.MusicStore.Entity.Student;
import com.MusicStore.Repository.BillingRepository;
@Service
public class BillingService {

	@Autowired
	private BillingRepository billRepo;

	public void save(@NonNull Billing bill) {
		billRepo.save(bill);
	}

	public List<Billing> getAllBilling() {
		return billRepo.findAll();

	}
	

	public Billing getBillingbyId(int id) {
		return billRepo.findById(id).get();
	}
	public List<Billing> getPendingBilling() {
	    return billRepo.findByPaymentStatus("pending");
	}
	public List<Billing> getPaidBilling() {
	    return billRepo.findByPaymentStatus("paid");
	}
	public void deleteById(int id) {
		billRepo.deleteById(id);
	}

	 public List<Billing> listAll(int bill_keyword) {
	if (bill_keyword != 0) {
	 return billRepo.billsearch(bill_keyword);
	}
	return billRepo.findAll();
	 }
	 public void updatePaymentStatus(@NonNull Integer billingId, String payment_status) {
	        Billing billing = billRepo.findById(billingId).orElse(null);
	        if (billing != null) {
	            billing.setPayment_status(payment_status);
	            billRepo.save(billing);
	        } else {
	            // Handle case where billing entry with the given ID is not found
	            // You may throw an exception or handle it according to your requirement
	        }
	    }

	@SuppressWarnings("null")
	public void deleteByStudent(Student s) {
		 List<Billing> billingList = billRepo.findByStudent(s);

	        // Delete each billing information found
	        for (Billing billing : billingList) {
	        	billRepo.delete(billing);
	        }
	    }
	
}
