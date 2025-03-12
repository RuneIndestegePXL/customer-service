package be.pxl.services.crm.controller;

import be.pxl.services.crm.controller.dto.CustomerDto;
import be.pxl.services.crm.controller.request.CustomerCreateRequest;
import be.pxl.services.crm.controller.request.CustomerUpdateRequest;
import be.pxl.services.crm.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {
        log.info("Fetching customer with id: {}", id);
        Optional<CustomerDto> customerOpt = customerService.getCustomer(id);
        return customerOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public Iterable<CustomerDto> listCustomers() {
        log.info("Fetching all customers");
        return customerService.listCustomers();
    }

    @PostMapping
    public CustomerDto createCustomer(@RequestBody CustomerCreateRequest customer) {
        log.info("Creating customer with firstName: {} and lastName: {}", customer.firstName(), customer.lastName());
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id,
                                                   @RequestBody CustomerUpdateRequest customer) {
        log.info("Updating customer with id: {}", id);
        CustomerDto updated = customerService.updateCustomer(id, customer);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.info("Deleting customer with id: {}", id);
        if (customerService.deleteCustomer(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
