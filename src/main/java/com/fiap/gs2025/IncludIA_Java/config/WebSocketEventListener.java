package com.fiap.gs2025.IncludIA_Java.config;

import com.fiap.gs2025.IncludIA_Java.models.Candidate;
import com.fiap.gs2025.IncludIA_Java.models.Recruiter;
import com.fiap.gs2025.IncludIA_Java.repository.CandidateRepository;
import com.fiap.gs2025.IncludIA_Java.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.Optional;

@Component
public class WebSocketEventListener {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private RecruiterRepository recruiterRepository;

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        updateStatus(event.getUser(), true);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        updateStatus(event.getUser(), false);
    }

    private void updateStatus(Principal user, boolean isOnline) {
        if (user == null) return;
        String email = user.getName();

        Optional<Candidate> candidate = candidateRepository.findByEmail(email);
        if (candidate.isPresent()) {
            Candidate c = candidate.get();
            c.setOnline(isOnline);
            candidateRepository.save(c);
            return;
        }

        Optional<Recruiter> recruiter = recruiterRepository.findByEmail(email);
        if (recruiter.isPresent()) {
            Recruiter r = recruiter.get();
            r.setOnline(isOnline);
            recruiterRepository.save(r);
        }
    }
}