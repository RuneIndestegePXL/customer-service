package be.pxl.services.crm.controller;

import be.pxl.services.crm.controller.dto.SupportTicketDto;
import be.pxl.services.crm.controller.request.SupportTicketCreateRequest;
import be.pxl.services.crm.controller.request.SupportTicketUpdateRequest;
import be.pxl.services.crm.domain.SupportTicket;
import be.pxl.services.crm.services.SupportTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/supporttickets")
public class SupportTicketController {

    private static final Logger log = LoggerFactory.getLogger(SupportTicketController.class);
    private final SupportTicketService supportTicketService;

    public SupportTicketController(SupportTicketService supportTicketService) {
        this.supportTicketService = supportTicketService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportTicket> getSupportTicket(@PathVariable Long id) {
        log.info("Fetching support ticket with id: {}", id);
        Optional<SupportTicket> ticketOpt = supportTicketService.getSupportTicket(id);
        return ticketOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<SupportTicket> listSupportTickets() {
        log.info("Fetching all support tickets");
        return supportTicketService.listSupportTickets();
    }

    @PostMapping
    public SupportTicketDto createSupportTicket(@RequestBody SupportTicketCreateRequest ticket) {
        log.info("Creating support ticket with title: {} and description: {}", ticket.title(), ticket.description());
        return supportTicketService.createSupportTicket(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportTicketDto> updateSupportTicket(@PathVariable Long id,
                                                             @RequestBody SupportTicketUpdateRequest ticket) {
        log.info("Updating support ticket with id: {}", id);
        SupportTicketDto updated = supportTicketService.updateSupportTicket(id, ticket);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupportTicket(@PathVariable Long id) {
        log.info("Deleting support ticket with id: {}", id);
        if (supportTicketService.deleteSupportTicket(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}