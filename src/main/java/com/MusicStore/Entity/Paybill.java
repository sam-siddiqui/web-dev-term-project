package com.MusicStore.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "billing")
@Entity
public class Paybill {
	public Paybill() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int billing_id;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;
	private String amount;
	private String payment_date;
	private String payment_status;
	public int getBilling_id() {
		return billing_id;
	}
	public void setBilling_id(int billing_id) {
		this.billing_id = billing_id;
	}
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}
	public String getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}
	public Paybill(int billing_id, Student student, String amount, String payment_date,
			String payment_status) {
		super();
		this.billing_id = billing_id;
		this.student = student;
		this.amount = amount;
		this.payment_date = payment_date;
		this.payment_status = payment_status;
	}
	
	
	
}
