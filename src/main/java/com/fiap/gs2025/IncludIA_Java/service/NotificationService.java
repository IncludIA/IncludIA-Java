package com.fiap.gs2025.IncludIA_Java.service;

import com.fiap.gs2025.IncludIA_Java.dto.response.NotificationResponse;
import com.fiap.gs2025.IncludIA_Java.enums.NotificationType;
import com.fiap.gs2025.IncludIA_Java.exceptions.ResourceNotFoundException;
import com.fiap.gs2025.IncludIA_Java.models.*;
import com.fiap.gs2025.IncludIA_Java.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.fiap.gs2025.IncludIA_Java.security.CustomUserDetails;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private MatchRepository matchRepository;

    public void sendMatchNotification(Match match) {
        rabbitTemplate.convertAndSend("match-queue", match.getId());
    }

    @RabbitListener(queues = "match-queue")
    @Transactional
    public void handleMatchEvent(UUID matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match não encontrado no listener"));

        Candidate candidate = match.getCandidate();
        Recruiter recruiter = match.getVaga().getRecruiter();

        Notification notifCandidate = new Notification();
        notifCandidate.setId(UUID.randomUUID());
        notifCandidate.setCandidate(candidate);
        notifCandidate.setTipo(NotificationType.NOVO_MATCH);
        notifCandidate.setMensagem("Você deu match com a vaga: " + match.getVaga().getTitulo());
        notifCandidate.setRead(false);
        notifCandidate.setCreatedAt(Instant.now());

        Notification notifRecruiter = new Notification();
        notifRecruiter.setId(UUID.randomUUID());
        notifRecruiter.setRecruiter(recruiter);
        notifRecruiter.setTipo(NotificationType.NOVO_MATCH);
        notifRecruiter.setMensagem("Sua vaga " + match.getVaga().getTitulo() + " deu match com " + candidate.getNome());
        notifRecruiter.setRead(false);
        notifRecruiter.setCreatedAt(Instant.now());

        notificationRepository.save(notifCandidate);
        notificationRepository.save(notifRecruiter);

        Chat chat = new Chat();
        chat.setId(UUID.randomUUID());
        chat.setMatch(match);
        chat.setCreatedAt(Instant.now());
        chatRepository.save(chat);
    }

    @Transactional
    public void createProfileViewNotification(Recruiter recruiter, Candidate candidate) {
        Notification notification = new Notification();
        notification.setId(UUID.randomUUID());
        notification.setCandidate(candidate);
        notification.setTipo(NotificationType.PERFIL_VISITADO);
        notification.setMensagem("Um recrutador da empresa " + recruiter.getEmpresa().getNomeFantasia() + " visitou seu perfil.");
        notification.setRead(false);
        notification.setCreatedAt(Instant.now());

        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getUnreadNotificationsForUser(UUID userId, String role) {
        if ("ROLE_CANDIDATE".equals(role)) {
            Candidate candidate = candidateRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));
            return notificationRepository.findByCandidateAndIsReadFalse(candidate).stream()
                    .map(NotificationResponse::new)
                    .collect(Collectors.toList());
        } else {
            Recruiter recruiter = recruiterRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Recrutador não encontrado"));
            return notificationRepository.findByRecruiterAndIsReadFalse(recruiter).stream()
                    .map(NotificationResponse::new)
                    .collect(Collectors.toList());
        }
    }


    @Transactional(readOnly = true)
    public Page<NotificationResponse> getUnreadNotificationsForUser(Pageable pageable) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        if ("ROLE_CANDIDATE".equals(role)) {
            Candidate candidate = candidateRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Candidato não encontrado"));
            return notificationRepository.findByCandidateAndIsReadFalseOrderByCreatedAtDesc(candidate, pageable)
                    .map(NotificationResponse::new);
        } else {
            Recruiter recruiter = recruiterRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Recrutador não encontrado"));
            return notificationRepository.findByRecruiterAndIsReadFalseOrderByCreatedAtDesc(recruiter, pageable)
                    .map(NotificationResponse::new);
        }
    }
}