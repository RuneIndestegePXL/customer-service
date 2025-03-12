package be.pxl.services.crm.controller.request;

public record SupportTicketUpdateRequest (
        long id,
        String title,
        String description,
        String status
) {
}
