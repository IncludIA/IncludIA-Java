package com.fiap.gs2025.IncludIA_Java.dtos.response;

import com.fiap.gs2025.IncludIA_Java.models.ProfileView;

import java.time.Instant;
import java.util.UUID;

public record ProfileViewResponse(
        UUID id,
        RecruiterProfileResponse recruiter,
        Instant viewedAt
) {
    public ProfileViewResponse(ProfileView view) {
        this(
                view.getId(),
                new RecruiterProfileResponse(view.getRecruiter()),
                view.getViewedAt()
        );
    }
}