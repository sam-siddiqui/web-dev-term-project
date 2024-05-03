package com.MusicStore.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.MusicStore.Entity.Paybill;

public interface PaybillingRepository  extends JpaRepository<Paybill, Integer> {

}
