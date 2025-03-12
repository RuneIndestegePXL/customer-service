package be.pxl.services.crm.controller.request;

public record SupportTicketCreateRequest(
    String title,
    String description,
    Long customerId
) {
}
