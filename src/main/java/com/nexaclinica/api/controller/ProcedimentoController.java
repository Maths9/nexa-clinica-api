package com.nexaclinica.api.controller;

import com.nexaclinica.api.entity.Procedimento;
import com.nexaclinica.api.service.ProcedimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/procedimentos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProcedimentoController {

    private final ProcedimentoService procedimentoService;

    @GetMapping
    public ResponseEntity<List<Procedimento>> listarTodos() {
        return ResponseEntity.ok(procedimentoService.listarAtivos());
    }

    @PostMapping
    public ResponseEntity<Procedimento> salvar(@RequestBody Procedimento procedimento) {
        return ResponseEntity.ok(procedimentoService.salvar(procedimento));
    }
}
