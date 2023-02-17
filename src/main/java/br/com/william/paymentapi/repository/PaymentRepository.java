package br.com.william.paymentapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.william.paymentapi.model.Payment;
import br.com.william.paymentapi.model.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    List<Payment> findByCnp(String cnp);
    
    
}
