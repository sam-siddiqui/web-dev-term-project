package com.MusicStore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.MusicStore.Entity.Billing;
import com.MusicStore.Entity.Student;


@Repository
public interface BillingRepository extends JpaRepository<Billing, Integer> {
	@Query("SELECT b FROM Billing b WHERE b.student_id = ?1" )
	public List<Billing> billsearch(int bill_keyword);

	@Query("SELECT b FROM Billing b WHERE b.payment_status Like %?1%" )
	public List<Billing> findByPaymentStatus(String string);

	@Query("SELECT b FROM Billing b WHERE b.student_id = ?1" )
	public List<Billing> findByStudent(Student s);


}
