package com.nexaclinica.api.controller;

import com.nexaclinica.api.dto.RelatorioDTO;
import com.nexaclinica.api.service.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/relatorios")
@CrossOrigin(origins = "*")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/resumo")
    public ResponseEntity<RelatorioDTO> obterResumo() {
        return ResponseEntity.ok(relatorioService.gerarResumo());
    }
}
