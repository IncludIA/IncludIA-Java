package com.fiap.gs2025.IncludIA_Java.controller;

import com.fiap.gs2025.IncludIA_Java.dto.request.IdiomaRequest;
import com.fiap.gs2025.IncludIA_Java.dto.request.SkillRequest;
import com.fiap.gs2025.IncludIA_Java.dto.response.IdiomaResponse;
import com.fiap.gs2025.IncludIA_Java.dto.response.SkillResponse;
import com.fiap.gs2025.IncludIA_Java.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/skills")
    public ResponseEntity<SkillResponse> createSkill(@Valid @RequestBody SkillRequest request) {
        SkillResponse response = adminService.createSkill(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/skills")
    public ResponseEntity<Page<SkillResponse>> getAllSkills(@PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(adminService.getAllSkills(pageable));
    }

    @PostMapping("/idiomas")
    public ResponseEntity<IdiomaResponse> createIdioma(@Valid @RequestBody IdiomaRequest request) {
        IdiomaResponse response = adminService.createIdioma(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/idiomas")
    public ResponseEntity<Page<IdiomaResponse>> getAllIdiomas(@PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(adminService.getAllIdiomas(pageable));
    }
}