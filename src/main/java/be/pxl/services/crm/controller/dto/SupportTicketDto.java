package be.pxl.services.crm.controller.dto;

import be.pxl.services.crm.domain.TicketStatus;

import java.time.LocalDateTime;

public record SupportTicketDto(
        Long id,
        String title,
        String description,
        TicketStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
