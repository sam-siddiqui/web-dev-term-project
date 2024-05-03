package com.MusicStore.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.MusicStore.Entity.Show_Billing;
import com.MusicStore.Repository.Show_BillingRepository;

@Service
public class Show_BillingService {
	@Autowired
	private Show_BillingRepository show_billRepo;

	public void save(@NonNull Show_Billing show_bill) {
		show_billRepo.save(show_bill);
	}

	public List<Show_Billing> getAllBilling() {
		return show_billRepo.findAll();

	}

	public Show_Billing getShowBillingbyId(int id) {
		return show_billRepo.findById(id).get();
	}
	public List<Show_Billing> getShowPendingBilling() {
	    return show_billRepo.findByShowPaymentStatus("Pending");
	}
	public List<Show_Billing> getShowPaidBilling() {
	    return show_billRepo.findByShowPaymentStatus("Paid");
	}
	public void deleteById(int id) {
		show_billRepo.deleteById(id);
	}
	 public List<Show_Billing> listAll(int show_bill_keyword) {
	if (show_bill_keyword != 0) {
	 return show_billRepo.showbillsearch(show_bill_keyword);
	}
	return show_billRepo.findAll();
	 }
	 public void updatePaymentStatus(@NonNull Integer billingId, String payment_status) {
	        Show_Billing show_billing = show_billRepo.findById(billingId).orElse(null);
	        if (show_billing != null) {
	        	show_billing.setPayment_status(payment_status);
	            show_billRepo.save(show_billing);
	        } else {
	            // Handle case where billing entry with the given ID is not found
	            // You may throw an exception or handle it according to your requirement
	        }
	    }
}
