package ru.Liga.waiter.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.waiter.entity.Payment;
import ru.Liga.waiter.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/waiter/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public Payment create(@RequestBody Payment payment) {
        return service.save(payment);
    }

    @GetMapping
    public List<Payment> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
