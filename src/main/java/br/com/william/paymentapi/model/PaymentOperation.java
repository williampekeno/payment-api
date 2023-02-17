package br.com.william.paymentapi.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class PaymentOperation {
 
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String cnp;
}
