package br.com.william.paymentapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.william.paymentapi.error.ResourceNotFoundException;
import br.com.william.paymentapi.model.Payment;
import br.com.william.paymentapi.model.PaymentOperation;
import br.com.william.paymentapi.model.PaymentStatus;
import br.com.william.paymentapi.model.PaymentType;
import br.com.william.paymentapi.repository.PaymentRepository;


@RequestMapping("/payment")
@RestController
public class PaymentController {

    @Autowired
    private PaymentRepository repository;

    @GetMapping
    public List<Payment>listPayment(){
        return repository.findAll();
    }

    //Metodo GET para buscar por CNPJ ou CPF
    @GetMapping(value = "/findcnp")
    public List<Payment>listByCnp(@RequestBody PaymentOperation paymentOperation){
        paymentOperation.setCnp(cnpCleaner(paymentOperation.getCnp())); 
        verifyCnpExists(paymentOperation.getCnp());
        return repository.findByCnp(paymentOperation.getCnp());
    }

    //Metodo GET para buscar pagamentos por ID
    @GetMapping(value = "/findid")
    public Payment findByid(@RequestBody PaymentOperation paymentOperation){
        verifyItemExists(paymentOperation.getId());
        return repository.findById(paymentOperation.getId()).orElse(null);
    }

    //Metodo GET para buscar por Status
    @GetMapping(value = "/findstatus")
    public List<Payment>listByStatus(@RequestBody PaymentOperation paymentOperation){
        return repository.findByPaymentStatus(paymentOperation.getPaymentStatus());
    }

    //Metodo POST para salvar pagamentos, com condições para filtrar tipos de Pagamento
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Payment paymentRequest(@Validated @RequestBody Payment payment){
        payment.setCnp(cnpCleaner(payment.getCnp()));
        if ((payment.getPaymentType().equals(PaymentType.CREDIT_CARD)) || (payment.getPaymentType().equals(PaymentType.DEBIT_CARD))) {

            if(payment.getCardNumber() == null)
                throw new ResourceNotFoundException("Número do cartão não informado");
        }
        return repository.save(payment);
    }

    //Metodo PUT para fazer update de dados, passando o ID
    @PutMapping(value = "/update")
    public Payment updatePaymentStatus(@RequestBody PaymentOperation paymentOperation){

        verifyItemExists(paymentOperation.getId());
        Payment obj = repository.findById(paymentOperation.getId()).orElse(null);
        obj.setPaymentStatus(paymentOperation.getPaymentStatus());

        return repository.save(obj);
    }

    //Metodo DELETE, para deletar pagamentos se estiver com status pendente
    @DeleteMapping(value = "/delete")
    public Object deletePayment(@RequestBody PaymentOperation paymentOperation){
        Map<String, String> map = new HashMap<>();

        verifyItemExists(paymentOperation.getId());
        Payment obj = repository.findById(paymentOperation.getId()).orElse(null);
            if(obj.getPaymentStatus().equals(PaymentStatus.PENDING) ){
                repository.delete(obj);
    
                return new ResponseEntity<>(listPayment(), HttpStatus.OK);
            }
            else{
                map.put("message", "Pagamento já processado"); 
                return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
            }
        }


        //metodos para verificar e tratar exceções
    private void verifyItemExists(Long id){
        Payment payment = repository.findById(id).orElse(null);
        if(payment == null)
            throw new ResourceNotFoundException("Pagamento não Encontrado");
    }

    private void verifyCnpExists(String cnp){
        List<Payment> listcnp = repository.findByCnp(cnp);
        if(listcnp == null)
            throw new ResourceNotFoundException("CNPJ/CPF não Encontrado");
    }

    //Metodo para 'limpar' o campo de CNPJ/CPF
    private String cnpCleaner(String cnp){
        return cnp.replace(".", "").replace("-", "").replace("/", "");
    }
}