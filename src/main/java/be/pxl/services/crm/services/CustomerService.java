package be.pxl.services.crm.services;

import be.pxl.services.crm.controller.dto.CustomerDto;
import be.pxl.services.crm.controller.dto.SupportTicketDto;
import be.pxl.services.crm.controller.request.CustomerCreateRequest;
import be.pxl.services.crm.controller.request.CustomerUpdateRequest;
import be.pxl.services.crm.domain.Customer;
import be.pxl.services.crm.domain.SupportTicket;
import be.pxl.services.crm.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Optional<CustomerDto> getCustomer(Long id) {
        return customerRepository.findById(id).map(CustomerService::CustomerMapperToDto);
    }
    @Transactional
    public List<CustomerDto> listCustomers() {
        return customerRepository.findAll().stream().map(CustomerService::CustomerMapperToDto).toList();
    }

    public CustomerDto createCustomer(CustomerCreateRequest customer) {
        Customer newCustomer = new Customer(
                customer.firstName(),
                customer.lastName(),
                customer.email(),
                customer.phone(),
                customer.address(),
                customer.city(),
                customer.zipCode()
        );
        Customer savedCustomer = customerRepository.save(newCustomer);
        return CustomerMapperToDto(savedCustomer);
    }
    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerUpdateRequest customerDetails) {
        return CustomerMapperToDto(customerRepository.findById(id)
                .map(customer -> {
                    customer.setFirstName(customerDetails.firstName());
                    customer.setLastName(customerDetails.lastName());
                    customer.setEmail(customerDetails.email());
                    customer.setPhone(customerDetails.phone());
                    customer.setAddress(customerDetails.address());
                    customer.setCity(customerDetails.city());
                    customer.setZipCode(customerDetails.zipCode());
                    return customerRepository.save(customer);
                }).orElseThrow(null));
    }

    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public static CustomerDto CustomerMapperToDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getCity(),
                customer.getZipCode(),
                customer.getSupportTickets().stream().map(CustomerService::SupportTicketMapperToDto).toList(),
                customer.getClosedSupportTickets().stream().map(CustomerService::SupportTicketMapperToDto).toList()
        );
    }

    public static SupportTicketDto SupportTicketMapperToDto(SupportTicket ticket) {
        return new SupportTicketDto(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }
}