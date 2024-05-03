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

	public List<Billing> getBillingByStudent(Student s) { return billRepo.findByStudent(s);}

	public List<Billing> getBillingByStudentID(String student_id) { return billRepo.findByStudentID(student_id);}

	public List<Billing> getPendingBillingByStudent(Student s) { return billRepo.findPendingByStudent(s, "Pending");}

	public List<Billing> getPendingBillingByStudentID(String student_id) { return billRepo.findPendingByStudentID(student_id, "Pending");}

	public List<Billing> getPaidBillingByStudent(Student s) { return billRepo.findPendingByStudent(s, "Paid");}

	public List<Billing> getPaidBillingByStudentID(String student_id) { return billRepo.findPendingByStudentID(student_id, "Paid");}


	public Billing getBillingbyId(int id) {
		return billRepo.findById(id).get();
	}
	public List<Billing> getPendingBilling() {
	    return billRepo.findByPaymentStatus("Pending");
	}
	public List<Billing> getPaidBilling() {
	    return billRepo.findByPaymentStatus("Paid");
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
