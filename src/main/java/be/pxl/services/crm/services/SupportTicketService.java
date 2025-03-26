package be.pxl.services.crm.services;

import be.pxl.services.crm.controller.dto.SupportTicketDto;
import be.pxl.services.crm.controller.request.SupportTicketCreateRequest;
import be.pxl.services.crm.controller.request.SupportTicketUpdateRequest;
import be.pxl.services.crm.domain.SupportTicket;
import be.pxl.services.crm.domain.TicketStatus;
import be.pxl.services.crm.repository.SupportTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;

    public SupportTicketService(SupportTicketRepository supportTicketRepository) {
        this.supportTicketRepository = supportTicketRepository;
    }

    @Transactional
    public Optional<SupportTicket> getSupportTicket(Long id) {
        return supportTicketRepository.findById(id);
    }

    @Transactional
    public List<SupportTicket> listSupportTickets() {
        return supportTicketRepository.findAll();
    }

    @Transactional
    public SupportTicketDto createSupportTicket(SupportTicketCreateRequest request) {
        SupportTicket ticket = new SupportTicket(request.title(), request.description());

        return SupportTicketMapperToDto(supportTicketRepository.save(ticket));
    }

    @Transactional
    public SupportTicketDto updateSupportTicket(Long id, SupportTicketUpdateRequest ticketDetails) {
        return SupportTicketMapperToDto(supportTicketRepository.findById(id)
                .map(ticket -> {
                    ticket.setTitle(ticketDetails.title());
                    ticket.setDescription(ticketDetails.description());
                    ticket.setStatus(TicketStatus.valueOf(ticketDetails.status()));
                    return supportTicketRepository.save(ticket);
                }).orElse(null));
    }


    @Transactional
    public boolean deleteSupportTicket(Long id) {
        if (supportTicketRepository.existsById(id)) {
            supportTicketRepository.deleteById(id);
            return true;
        }
        return false;
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