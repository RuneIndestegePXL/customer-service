package be.pxl.services.crm.controller.dto;

import java.util.List;

public record CustomerDto (
    Long id,
    String firstName,
    String lastName,
    String email,
    String phone,
    String address,
    String city,
    String zipCode,
    List<SupportTicketDto> supportTickets,
    List<SupportTicketDto> closedSupportTickets
){
}
